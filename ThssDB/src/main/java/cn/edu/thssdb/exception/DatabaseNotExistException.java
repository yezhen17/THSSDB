package cn.edu.thssdb.exception;

/***************
 * [Exception] 数据库不存在
 ***************/
public class DatabaseNotExistException extends RuntimeException{
  @Override
  public String getMessage() {
    return "Exception: database doesn't exist!";
  }
}
