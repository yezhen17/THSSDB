package cn.edu.thssdb.exception;

public class UnknownColumnException extends RuntimeException {
  @Override
  public String getMessage() {
    return "Exception: Unknown column!";
  }
}
