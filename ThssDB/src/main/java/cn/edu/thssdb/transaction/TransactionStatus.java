package cn.edu.thssdb.transaction;

import java.util.List;

/*
 事务状态类
 */
public class TransactionStatus {
  public class Table {
    public List<List<String>> data;
    public List<String> columns;
    public String title;

    public Table(List<List<String>> data, List<String> columns, String title) {
      this.data = data;
      this.columns = columns;
      this.title = title;
    }
  }
  boolean status;
  String message;
  Table res; // select 或 show

  public TransactionStatus(boolean status, String message) {
    this.status = status;
    this.message = message;
    this.res = null;
  }

  public TransactionStatus(boolean status, String message, List<List<String>> data, List<String> columns, String title) {
    this.status = status;
    this.message = message;
    this.res = new Table(data, columns, title);
  }

  public String getMessage() {
    return this.message;
  }

  public boolean getStatus() {
    return status;
  }

  public Table getRes() {
    return res;
  }
}
