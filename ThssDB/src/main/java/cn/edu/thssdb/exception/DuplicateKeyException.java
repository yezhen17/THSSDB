package cn.edu.thssdb.exception;

/***************
 * [Exception] 重复键
 ***************/
public class DuplicateKeyException extends RuntimeException{
  @Override
  public String getMessage() {
    return "Exception: insertion caused duplicated keys!";
  }
}
