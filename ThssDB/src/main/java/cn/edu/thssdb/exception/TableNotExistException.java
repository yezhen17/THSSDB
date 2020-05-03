package cn.edu.thssdb.exception;

/***************
 * [Exception] 表不存在
 ***************/
public class TableNotExistException extends RuntimeException{
  @Override
  public String getMessage() {
    return "Exception: table doesn't exist!";
  }
}
