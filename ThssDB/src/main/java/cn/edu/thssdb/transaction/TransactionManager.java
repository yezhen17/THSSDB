package cn.edu.thssdb.transaction;

import cn.edu.thssdb.exception.DatabaseNotExistException;
import cn.edu.thssdb.exception.DatabaseOccupiedException;
import cn.edu.thssdb.schema.Database;
import cn.edu.thssdb.schema.Logger;
import cn.edu.thssdb.operation.*;
import cn.edu.thssdb.schema.Manager;
import cn.edu.thssdb.schema.Table;
import cn.edu.thssdb.utils.Global;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.HashMap;
import java.util.LinkedList;

/*
 事务管理类
 */
public class TransactionManager {
  private String databaseName;                                          // 数据库名称
  private Manager manager;                                              // 管理器对象
  private Logger logger;                                                // 日志对象
  private LinkedList<BaseOperation> operations;                         // 操作列表
  private HashMap<String, Integer> savepoints;                          // 检查点散列表
  private LinkedList<ReentrantReadWriteLock.ReadLock> readLockList;     // 读锁列表
  private LinkedList<ReentrantReadWriteLock.WriteLock> writeLockList;   // 写锁列表
  private boolean underTransaction = false;                             // 是否在transaction过程


  public TransactionManager(String databaseName, Logger logger) {
    this.databaseName = databaseName;
    this.manager = Manager.getInstance();
    this.logger = logger;
    this.operations = new LinkedList<>();
    this.savepoints = new HashMap<>();
    this.readLockList = new LinkedList<>();
    this.writeLockList = new LinkedList<>();
  }

  // 设置当前使用的数据库
  public void setDatabase(String databaseName) {
    this.databaseName = databaseName;
    Database db = manager.getDatabaseByName(databaseName);
    if (db == null) throw new DatabaseNotExistException();
    this.logger = db.getLogger();
  }

  public TransactionStatus exec(BaseOperation operation) {
    if (operation instanceof SelectOperation || operation instanceof ShowOperation) return readTransaction(operation);
    else if (operation instanceof UpdateOperation || operation instanceof DeleteOperation
    || operation instanceof InsertOperation) return writeTransaction(operation);
    else if (operation instanceof CommitOperation) return commitTransaction();
    else if (operation instanceof RollbackOperation) return rollbackTransaction(operation.getSavepoint());
    else if (operation instanceof SavepointOperation) return savepointTransaction(operation.getSavepoint());
    else if (operation instanceof BeginTransactionOperation) return beginTransaction();
    else if (operation instanceof CheckpointOperation) return checkpointTransaction();
    else return endTransaction(operation);
  }

  private TransactionStatus endTransaction(BaseOperation operation) {
    if (underTransaction) {
      commitTransaction();
    }
    try {
      // 检查删除的数据库此时是否被占用
      if (operation instanceof DropDatabaseOperation) {
        for (Table table: Manager.getInstance().
                getDatabaseByName(((DropDatabaseOperation) operation).getName()).getTables()) {
          if (table.lock.isWriteLocked()) throw new DatabaseOccupiedException();
        }
      }
      operation.exec();
      if (operation instanceof CreateTableOperation ||
              operation instanceof DropTableOperation || operation instanceof AlterTableOperation) {
        logger.writeLines(operation.getLog());
      }
      underTransaction = false;
    } catch (Exception e) {
      return new TransactionStatus(false, e.getMessage());
    }
    return new TransactionStatus(true, "");
  }

  private TransactionStatus beginTransaction() {
    if (databaseName == null) throw new DatabaseNotExistException();
    if (underTransaction) return new TransactionStatus(false, "Exception: Transaction ongoing!");
    else {
      underTransaction = true;
      return new TransactionStatus(true, "");
    }
  }

  private TransactionStatus checkpointTransaction() {
    if (underTransaction) {
      commitTransaction();
    }
    if (databaseName == null) throw new DatabaseNotExistException();
    manager.getDatabaseByName(databaseName).persist();
    return new TransactionStatus(true, "");
  }


  // 读操作
  private TransactionStatus readTransaction(BaseOperation operation) {
    // *** READ_UNCOMMITTED ***
    if (Global.DATABASE_ISOLATION_LEVEL == Global.ISOLATION_LEVEL.READ_UNCOMMITTED) {
      // 执行
      try {
        operation.exec();
        underTransaction = true;
      } catch (Exception e) {
        return new TransactionStatus(false, e.getMessage());
      }
      return new TransactionStatus(true, "", operation.getData(),
              operation.getColumns(), operation.getStmt());
    }
    // *** READ_COMMITTED ***
    if (Global.DATABASE_ISOLATION_LEVEL == Global.ISOLATION_LEVEL.READ_COMMITTED)  {
      // 加即时读锁
      ArrayList<String> tableNames = operation.getTableName();
      if (tableNames != null)
        for (String tableName : tableNames) { this.getTransactionReadLock(tableName); }
      // 执行
      try {
        operation.exec();
        underTransaction = true;
      } catch (Exception e) {
        return new TransactionStatus(false, e.getMessage());
      }
      // 解即时读锁
      if (tableNames != null)
        for (String tableName : tableNames) { this.releaseTransactionReadLock(tableName); }
      return new TransactionStatus(true, "", operation.getData(),
              operation.getColumns(), operation.getStmt());
    }
    // *** SERIALIZABLE ***
    if (Global.DATABASE_ISOLATION_LEVEL == Global.ISOLATION_LEVEL.SERIALIZABLE) {
      // 加非即时读锁
      ArrayList<String> tableNames = operation.getTableName();
      if (tableNames != null)
        for (String tableName : tableNames) { this.getTransactionReadLock(tableName); }
      // 执行
      try {
        operation.exec();
        underTransaction = true;
      } catch (Exception e) {
        return new TransactionStatus(false, e.getMessage());
      }
      return new TransactionStatus(true, "", operation.getData(),
              operation.getColumns(), operation.getStmt());
    }
    return new TransactionStatus(false, "Exception: Unknown isolation level!");
  }

  // [method] 增删改操作
  private TransactionStatus writeTransaction(BaseOperation operation) {
    // *** READ_UNCOMMITTED | READ_COMMITTED | SERIALIZABLE ***
    if ((Global.DATABASE_ISOLATION_LEVEL == Global.ISOLATION_LEVEL.READ_COMMITTED) ||
            (Global.DATABASE_ISOLATION_LEVEL == Global.ISOLATION_LEVEL.READ_UNCOMMITTED) ||
            (Global.DATABASE_ISOLATION_LEVEL == Global.ISOLATION_LEVEL.SERIALIZABLE)) {
      // 加非即时写锁
      ArrayList<String> tableNames = operation.getTableName();
      if (tableNames != null)
        for (String tableName : tableNames) { this.getTransactionWriteLock(tableName); }
      // 执行
      try {
        operation.exec();
        operations.add(operation);
        underTransaction = true;
      } catch (Exception e) {
        return new TransactionStatus(false, e.getMessage());
      }
      return new TransactionStatus(true, "Success");
    }
    return new TransactionStatus(false, "Exception: Unknown isolation level!");
  }


  private TransactionStatus commitTransaction() {
    if (databaseName == null) throw new DatabaseNotExistException();
    // 解非即时读写锁
    this.releaseTransactionReadWriteLock();
    LinkedList<String> log = new LinkedList<>();
    // 写入外存的Log
    while (!operations.isEmpty()) {
      BaseOperation op = operations.getFirst();
      log.addAll(op.getLog());
      operations.removeFirst();
    }
    log.add("COMMIT");
    logger.writeLines(log);
    underTransaction = false;
    return new TransactionStatus(true, "Success");
  }

  private TransactionStatus savepointTransaction(String name) {
    if (databaseName == null) throw new DatabaseNotExistException();
    if (!underTransaction) return new TransactionStatus(false, "Exception: No transaction ongoing!");
    if (name == null) return new TransactionStatus(false, "Exception: No savepoint given.");
    savepoints.put(name, operations.size());
    return new TransactionStatus(true, "Success");
  }

  private TransactionStatus rollbackTransaction(String name) {
    if (databaseName == null) throw new DatabaseNotExistException();
    int index = 0;
    if (name != null) {
      Integer tmp = savepoints.get(name);
      if (tmp == null) {
        return new TransactionStatus(false, "Savepoint不存在");
      }
      index = tmp.intValue();
    }
    try {
      for (int i = operations.size(); i > index; i--) {
        BaseOperation op = operations.removeLast();
        if (op instanceof SelectOperation || op instanceof ShowOperation) {
          // 释放读锁 *** SERIALIZABLE ***
          if (Global.DATABASE_ISOLATION_LEVEL == Global.ISOLATION_LEVEL.SERIALIZABLE) {
            ArrayList<String> tableNames = op.getTableName();
            if (tableNames != null)
              for (String tableName : tableNames) { this.releaseTransactionReadLock(tableName); }
          }
        }
        else if (op instanceof UpdateOperation || op instanceof DeleteOperation || op instanceof InsertOperation) {
          // 释放写锁 *** READ_UNCOMMITTED | READ_COMMITTED | SERIALIZABLE ***
          ArrayList<String> tableNames = op.getTableName();
          if (tableNames != null)
            for (String tableName : tableNames) { this.releaseTransactionWriteLock(tableName); }
        }
        op.undo();
      }
      if (index == 0) underTransaction = false;
    } catch (Exception e) {
      return new TransactionStatus(false, e.getMessage());
    }
    return new TransactionStatus(true, "Success");
  }

  // *****************************************************************
  // [method] 获取事务读锁（READ_UNCOMMITTED | READ_COMMITTED）
  private boolean getTransactionReadLock(String tableName) {
    if (!Global.ISOLATION_STATUS) return true;
    // 获取锁对象
    Table table = manager.getTableByName(this.databaseName, tableName);
    if (table == null)
      return false;
    ReentrantReadWriteLock.ReadLock readLock = table.lock.readLock();
    // 获取读锁
    readLock.lock();
    readLockList.add(readLock);
    return true;
  }

  // [method] 获取事务写锁
  private boolean getTransactionWriteLock(String tableName) {
    if (!Global.ISOLATION_STATUS) return true;
    // 获取锁对象
    Table table = manager.getTableByName(this.databaseName, tableName);
    if (table == null)
      return false;
    ReentrantReadWriteLock.WriteLock writeLock = table.lock.writeLock();
    // 获取写锁
    if (!writeLock.tryLock()) {
      // 获取写锁失败 排除自身读锁的阻塞
      while (true) {
        if (!this.releaseTransactionReadLock(tableName))
          break;
      };
      // 再次获取
      writeLock.lock();
    }
    writeLockList.add(writeLock);
    return true;
  }

  // [method] 释放事务读锁（READ_COMMITTED）
  private boolean releaseTransactionReadLock(String tableName) {
    if (!Global.ISOLATION_STATUS) return true;
    // 获取锁对象
    Table table = manager.getTableByName(this.databaseName, tableName);
    if (table == null)
      return false;
    ReentrantReadWriteLock.ReadLock readLock = table.lock.readLock();
    // 释放读锁
    if (readLockList.remove(readLock)) {
      readLock.unlock();
      return true;
    }
    return false;
  }

  // [method] 释放事务写锁（NONE）
  private boolean releaseTransactionWriteLock(String tableName) {
    if (!Global.ISOLATION_STATUS) return true;
    // 获取锁对象
    Table table = manager.getTableByName(this.databaseName, tableName);
    if (table == null)
      return false;
    ReentrantReadWriteLock.WriteLock writeLock = table.lock.writeLock();
    // 释放写锁
    if (writeLockList.remove(writeLock)) {
      writeLock.unlock();
      return true;
    }
    return false;
  }

  // [method] 释放事务读写锁（READ_UNCOMMITTED | READ_COMMITTED | SERIALIZABLE）
  private void releaseTransactionReadWriteLock() {
    if (!Global.ISOLATION_STATUS) return;
    // 释放写锁
    while (!writeLockList.isEmpty()) {
      writeLockList.remove().unlock();
    }
    // 释放读锁
    while (!readLockList.isEmpty()) {
      readLockList.remove().unlock();
    }
  }

  public boolean isUnderTransaction() {
    return underTransaction;
  }
}
