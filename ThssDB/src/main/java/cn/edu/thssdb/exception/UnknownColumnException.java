package cn.edu.thssdb.exception;

/***************
 * [Exception] 未知列
 ***************/
public class UnknownColumnException extends RuntimeException {
  @Override
  public String getMessage() {
    return "Exception: Unknown column!";
  }
}
