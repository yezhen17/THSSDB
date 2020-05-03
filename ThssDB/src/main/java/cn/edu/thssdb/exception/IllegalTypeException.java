package cn.edu.thssdb.exception;

/***************
 * [Exception] 不合法的数据类型
 ***************/
public class IllegalTypeException extends RuntimeException{
  @Override
  public String getMessage() {
    return "Exception: illegal data type";
  }
}
