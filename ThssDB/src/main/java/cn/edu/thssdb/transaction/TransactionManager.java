package cn.edu.thssdb.transaction;

import cn.edu.thssdb.exception.CustomIOException;
import cn.edu.thssdb.log.Logger;
import cn.edu.thssdb.log.LoggerBuffer;
import cn.edu.thssdb.operation.BaseOperation;
import cn.edu.thssdb.schema.Manager;
import cn.edu.thssdb.schema.Table;
import cn.edu.thssdb.utils.Global;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.HashMap;
import java.util.LinkedList;

public class TransactionManager {
//  LoggerBuffer loggerBuffer;
  private static ReentrantLock lock = new ReentrantLock();              // 互斥锁
  private String databaseName;                                          // 数据库名称
  private Manager manager;                                              // 管理器对象
  private Logger logger;                                                // 日志对象
  private LinkedList<BaseOperation> operations;                         // 操作列表
  private HashMap<String, Integer> savepoints;                          // 检查点散列表
  private LinkedList<ReentrantReadWriteLock.ReadLock> readLockList;     // 读锁列表
  private LinkedList<ReentrantReadWriteLock.WriteLock> writeLockList;   // 写锁列表


  // TODO 锁，应该需要记录每个operation用了什么锁
  // TODO 注意，两段锁，需得到事务的所有操作后，统一进行加锁后才可执行

  public TransactionManager(String databaseName, Logger logger) {
//    this.loggerBuffer = loggerBuffer;
    this.databaseName = databaseName;
    this.manager = Manager.getInstance();
    this.logger = logger;
    this.operations = new LinkedList<>();
    this.savepoints = new HashMap<>();
    this.readLockList = new LinkedList<>();
    this.writeLockList = new LinkedList<>();
  }



  // 增删改操作
  public TransactionStatus readTransaction(BaseOperation operation) {
    // *** READ_UNCOMMITTED ***
    if (Global.DATABASE_ISOLATION_LEVEL == Global.ISOLATION_LEVEL.READ_UNCOMMITTED) {
      // 执行 TODO
      try {
        operation.exec();
      } catch (Exception e) {
        return new TransactionStatus(false, e.getMessage());
      }
      return new TransactionStatus(true, "Success");
    }
    // *** READ_COMMITTED ***
    if (Global.DATABASE_ISOLATION_LEVEL == Global.ISOLATION_LEVEL.READ_COMMITTED)  {
      // 加即时读锁
      ArrayList<String> tableNames = operation.getTableName();
      if (tableNames != null)
        for (String tableName : tableNames) { this.getTransactionReadLock(tableName); }
      // 执行 TODO
      try {
        operation.exec();
      } catch (Exception e) {
        return new TransactionStatus(false, e.getMessage());
      }
      // 解即时读锁
      if (tableNames != null)
        for (String tableName : tableNames) { this.releaseTransactionReadLock(tableName); }
      return new TransactionStatus(true, "Success");
    }
    // *** REPEATABLE_READ | SERIALIZATION ***
    if ((Global.DATABASE_ISOLATION_LEVEL == Global.ISOLATION_LEVEL.REPEATABLE_READ) ||
            (Global.DATABASE_ISOLATION_LEVEL == Global.ISOLATION_LEVEL.SERIALIZATION)) {
      // 加非即时读锁
      ArrayList<String> tableNames = operation.getTableName();
      if (tableNames != null)
        for (String tableName : tableNames) { this.getTransactionReadLock(tableName); }
      // 执行 TODO
      try {
        operation.exec();
      } catch (Exception e) {
        return new TransactionStatus(false, e.getMessage());
      }
      return new TransactionStatus(true, "Success");
    }
    return new TransactionStatus(false, "Unknown isolation level!");
  }

  // [method] 增删改操作
  public TransactionStatus writeTransaction(BaseOperation operation) {
    // *** READ_UNCOMMITTED | READ_COMMITTED | REPEATABLE_READ | SERIALIZATION ***
    if ((Global.DATABASE_ISOLATION_LEVEL == Global.ISOLATION_LEVEL.READ_COMMITTED) ||
            (Global.DATABASE_ISOLATION_LEVEL == Global.ISOLATION_LEVEL.READ_UNCOMMITTED) ||
            (Global.DATABASE_ISOLATION_LEVEL == Global.ISOLATION_LEVEL.REPEATABLE_READ) ||
            (Global.DATABASE_ISOLATION_LEVEL == Global.ISOLATION_LEVEL.SERIALIZATION)) {
      // 加非即时写锁
      ArrayList<String> tableNames = operation.getTableName();
      if (tableNames != null)
        for (String tableName : tableNames) { this.getTransactionWriteLock(tableName); }
      // 执行 TODO
      try {
        operation.exec();
      } catch (Exception e) {
        return new TransactionStatus(false, e.getMessage());
      }
      return new TransactionStatus(true, "Success");
    }
    return new TransactionStatus(false, "Unknown isolation level!");
  }


  public TransactionStatus commitTransaction() throws CustomIOException {
    // 解非即时读写锁
    this.releaseTransactionReadWriteLock();
    LinkedList<String> log = new LinkedList<>();
    // 写入外存的Log
    while (!operations.isEmpty()) {
      BaseOperation op = operations.pop();
      log.addAll(op.getLog());
    }
    log.add("COMMIT");
    logger.writeLines(log);
    return new TransactionStatus(true, "Success");
  }

  public TransactionStatus savepoint(String name) {
    savepoints.put(name, operations.size());
    return new TransactionStatus(true, "Success");
  }

  public TransactionStatus rollback(String name) {
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
        // TODO 在此根据Operation类型去锁即可
        BaseOperation op = operations.pop();
        op.undo();
      }
    } catch (Exception e) {
      return new TransactionStatus(false, e.getMessage());
    }
    return new TransactionStatus(true, "Success");
  }

  // *****************************************************************
  // [method] 获取事务读锁（READ_UNCOMMITTED | READ_COMMITTED | REPEATABLE_READ）
  private boolean getTransactionReadLock(String tableName) {
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

  // [method] 获取事务写锁（READ_UNCOMMITTED | READ_COMMITTED | REPEATABLE_READ）
  private boolean getTransactionWriteLock(String tableName) {
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

  // [method] 获取事务读写锁（SERIALIZATION）（一次加锁法）
  // 引用 this.operations
  private boolean getTransactionReadWriteLock() {
    try {
      TransactionManager.lock.lock();
      // 一次加锁 - 先加写锁，再加读锁
      // TODO 引用 this.operations
    } finally {
      TransactionManager.lock.unlock();
    }
    return true;
  }

  // [method] 释放事务读锁（READ_COMMITTED）
  private boolean releaseTransactionReadLock(String tableName) {
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

  // [method] 释放事务读写锁（READ_UNCOMMITTED | READ_COMMITTED | REPEATABLE_READ | SERIALIZATION）
  private void releaseTransactionReadWriteLock() {
    // 释放写锁
    while (!writeLockList.isEmpty()) {
      writeLockList.remove().unlock();
    }
    // 释放读锁
    while (!readLockList.isEmpty()) {
      readLockList.remove().unlock();
    }
  }
}
