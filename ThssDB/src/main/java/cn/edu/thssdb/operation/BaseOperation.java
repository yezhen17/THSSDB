package cn.edu.thssdb.operation;

import cn.edu.thssdb.exception.CustomIOException;
import cn.edu.thssdb.exception.DataFileNotFoundException;
import cn.edu.thssdb.exception.MetaFileNotFoundException;
import cn.edu.thssdb.schema.Database;
import cn.edu.thssdb.schema.Manager;
import cn.edu.thssdb.schema.Row;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.LinkedList;

public class BaseOperation {
  protected String username = "";
  protected String databaseName = "";
  protected Database database;


  public BaseOperation() {

  }

  // 在执行语句前都会调用这个
  public void setCurrentUser(String username, String databaseName) {
    this.username = username;
    this.databaseName = databaseName;
    this.database = Manager.getInstance().getDatabaseByName(databaseName);
  }

  public ArrayList<String> getTableName(){
    return null;
  }

//
//  private String getUserOperationDatabase() {
//    return "";
//  } // 获取当前用户正在操作的数据库

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
