package cn.edu.thssdb.transaction;

import java.util.List;

public class TransactionStatus {
  boolean status;
  String message;
  List<List<String>> res;
  // TODO select返回表数据

  public TransactionStatus(boolean status, String message) {
    this.status = status;
    this.message = message;
    this.res = null;
  }

  public TransactionStatus(boolean status, String message, List<List<String>> res) {
    this.status = status;
    this.message = message;
    this.res = res;
  }

  public String getMessage() {
    return this.message;
  }

  public boolean getStatus() {
    return status;
  }

  public List<List<String>> getRes() {
    return res;
  }
}
