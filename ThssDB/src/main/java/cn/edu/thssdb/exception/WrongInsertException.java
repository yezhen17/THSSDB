package cn.edu.thssdb.exception;

/***************
 * [Exception] 插入错误
 ***************/
public class WrongInsertException extends RuntimeException {
  public WrongInsertException(String msg){ super(msg); }
}
