package cn.edu.thssdb.transaction;

import cn.edu.thssdb.exception.CustomIOException;
import cn.edu.thssdb.log.Logger;
import cn.edu.thssdb.log.LoggerBuffer;
import cn.edu.thssdb.operation.BaseOperation;
import cn.edu.thssdb.schema.Manager;
import cn.edu.thssdb.schema.Table;

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

  // [method] 增删改操作
  public TransactionStatus writeTransaction(BaseOperation operation) {
    try {
      // TODO 加锁
      operation.exec();
      operations.add(operation);

    } catch (Exception e) {
      return new TransactionStatus(false, e.getMessage());
    } finally {
      // TODO 似乎不用去锁
    }


    return new TransactionStatus(true, "Success");
  }

  // 增删改操作
  public TransactionStatus readTransaction(BaseOperation operation) {
    try {
      // TODO 加读锁
      operation.exec();
      // operations.add(operation);

    } catch (Exception e) {
      return new TransactionStatus(false, e.getMessage());
    } finally {
      // TODO 去读锁
    }


    return new TransactionStatus(true, "Success");
  }

  public TransactionStatus commitTransaction() throws CustomIOException {
    // TODO 去锁


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
      // TODO 这里似乎不需要加锁，因为已经锁住了


      for (int i = operations.size(); i > index; i--) {
        BaseOperation op = operations.pop();
        op.undo();
      }
    } catch (Exception e) {
      return new TransactionStatus(false, e.getMessage());
    } finally {
      // TODO 去锁，把rollback的语句涉及的锁一个个去了
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
    writeLock.lock();
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
    return readLockList.remove(readLock);
  }

  // [method] 释放事务写锁（NONE）
  private boolean releaseTransactionWriteLock(String tableName) {
    // 获取锁对象
    Table table = manager.getTableByName(this.databaseName, tableName);
    if (table == null)
      return false;
    ReentrantReadWriteLock.WriteLock writeLock = table.lock.writeLock();
    // 释放写锁
    return writeLockList.remove(writeLock);
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
