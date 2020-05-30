package cn.edu.thssdb.transaction;

public class TransactionStatus {
  boolean status;
  String message;
  public TransactionStatus(boolean status, String message) {
    this.status = status;
    this.message = message;
  }
}
