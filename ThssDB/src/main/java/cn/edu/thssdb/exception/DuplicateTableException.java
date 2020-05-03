package cn.edu.thssdb.exception;

/***************
 * [Exception] 重复表
 ***************/
public class DuplicateTableException extends RuntimeException {
  @Override
  public String getMessage() {
    return "Exception: creation caused duplicated table!";
  }
}
