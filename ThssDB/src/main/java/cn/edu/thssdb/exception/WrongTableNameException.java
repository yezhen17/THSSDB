package cn.edu.thssdb.exception;

/***************
 * [Exception] 表不存在
 ***************/
public class WrongTableNameException extends RuntimeException {
  @Override
  public String getMessage() {
    return "Exception: wrong table name in SQL!";
  }
}