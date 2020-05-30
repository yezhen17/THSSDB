package cn.edu.thssdb.operation;

import cn.edu.thssdb.exception.CustomIOException;
import cn.edu.thssdb.exception.DataFileNotFoundException;
import cn.edu.thssdb.exception.MetaFileNotFoundException;
import cn.edu.thssdb.schema.Row;

import java.util.LinkedList;

public class BaseOperation {
  private String username = "";
  private String databaseName = "";


  public BaseOperation() {

  }

  private String getUserOperationDatabase() {
    return "";
  } // 获取当前用户正在操作的数据库

  public void exec() throws CustomIOException, ClassNotFoundException, MetaFileNotFoundException, DataFileNotFoundException {

  }

  public void undo() {

  }

  /**
   * [method] 获取记录
   */
  public LinkedList<String> getLog(){
    return null;
  }

  public boolean isTransactionType() {
    return this instanceof BeginTransactionOperation ||
            this instanceof CommitOperation ||
            this instanceof DeleteOperation ||
            this instanceof InsertOperation ||
            this instanceof UpdateOperation ||
            this instanceof RollbackOperation ||
            this instanceof SavePointOperation ||
            this instanceof CheckpointOperation;
  }
}
