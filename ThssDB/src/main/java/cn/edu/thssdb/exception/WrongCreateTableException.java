package cn.edu.thssdb.exception;

/***************
 * [Exception] 表创建错误
 ***************/
public class WrongCreateTableException extends RuntimeException {
  public WrongCreateTableException(String msg){ super(msg); }
}
