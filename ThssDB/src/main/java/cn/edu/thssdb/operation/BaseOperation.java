package cn.edu.thssdb.operation;

import cn.edu.thssdb.schema.Database;
import cn.edu.thssdb.schema.Manager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BaseOperation {
  protected String username = "";
  protected String databaseName = "";
  protected Database database;

  // 在执行语句前都会调用这个
  public void setCurrentUser(String username, String databaseName) {
    this.username = username;
    this.databaseName = databaseName;
    this.database = Manager.getInstance().getDatabaseByName(databaseName);
  }

  public ArrayList<String> getTableName(){
    return null;
  }


  public void exec() {
    // 被继承
  }

  public void undo() {
    // 被继承
  }

  public String getSavepoint() {
    return null;
  }

  /**
   * [method] 获取记录
   */
  public LinkedList<String> getLog(){
    return null;
  }

  public List<List<String>> getData() {
    return null;
  }

  public List<String> getColumns() {
    return null;
  }

  public String getStmt() {
    return null;
  }
}
