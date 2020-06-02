package cn.edu.thssdb.exception;

/***************
 * [Exception] 更新错误
 ***************/
public class WrongUpdateException extends RuntimeException {
  public WrongUpdateException(String msg){ super(msg); }
}
