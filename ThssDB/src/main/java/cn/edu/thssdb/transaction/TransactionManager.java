package cn.edu.thssdb.transaction;

import cn.edu.thssdb.exception.CustomIOException;
import cn.edu.thssdb.log.Logger;
import cn.edu.thssdb.log.LoggerBuffer;
import cn.edu.thssdb.operation.BaseOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class TransactionManager {
//  LoggerBuffer loggerBuffer;
  Logger logger;
  LinkedList<BaseOperation> operations;
  HashMap<String, Integer> savepoints;

  // TODO 锁，应该需要记录每个operation用了什么锁

  public TransactionManager(Logger logger) {
//    this.loggerBuffer = loggerBuffer;
    this.logger = logger;
    operations = new LinkedList<>();
    savepoints = new HashMap<>();
  }

  // 增删改操作
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

}
