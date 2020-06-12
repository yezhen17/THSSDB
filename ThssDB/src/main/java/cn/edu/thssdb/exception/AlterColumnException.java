package cn.edu.thssdb.exception;

/***************
 * [Exception] 更改列异常
 ***************/
public class AlterColumnException extends RuntimeException {
  public AlterColumnException (String msg) {
    super(msg);
  }
}
