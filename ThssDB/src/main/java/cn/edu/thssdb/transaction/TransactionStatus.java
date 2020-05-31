package cn.edu.thssdb.transaction;

public class TransactionStatus {
  boolean status;
  String message;
  // TODO select返回表数据

  public TransactionStatus(boolean status, String message) {
    this.status = status;
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }

  public boolean getStatus() {
    return status;
  }
}
