package cn.edu.thssdb.transaction;

import cn.edu.thssdb.exception.CustomIOException;
import cn.edu.thssdb.log.LoggerBuffer;
import cn.edu.thssdb.operation.BaseOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class TransactionManager {
  LoggerBuffer loggerBuffer;
  LinkedList<BaseOperation> operations;
  HashMap<String, Integer> savepoints;

  public TransactionManager(LoggerBuffer loggerBuffer) {
    this.loggerBuffer = loggerBuffer;
    operations = new LinkedList<>();
    savepoints = new HashMap<>();
  }

  public TransactionStatus commonTransaction(BaseOperation operation) {
    try {
      // TODO 加锁
      operation.exec();
      // 写日志

    } catch (Exception e) {
      return new TransactionStatus(false, e.getMessage());
    } finally {
      // TODO 去锁
    }

    operations.add(operation);
    return new TransactionStatus(true, "Success");
  }

  public void commitTransaction() throws CustomIOException {
    // TODO 去锁
    loggerBuffer.emptyBuffer();
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
      // TODO 加锁
      for (int i = operations.size(); i > index; i--) {
        BaseOperation op = operations.pop();
        op.undo();
      }
    } catch (Exception e) {
      return new TransactionStatus(false, e.getMessage());
    } finally {
      // TODO 去锁
    }
    return new TransactionStatus(true, "Success");
  }

}
