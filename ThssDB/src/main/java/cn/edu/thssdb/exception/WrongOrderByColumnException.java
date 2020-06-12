package cn.edu.thssdb.exception;

/***************
 * [Exception] 排序时未知列
 ***************/
public class WrongOrderByColumnException extends RuntimeException {
  @Override
  public String getMessage() {
    return "Exception: Unknown column for order by!";
  }
}
