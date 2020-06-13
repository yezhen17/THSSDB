package cn.edu.thssdb.exception;

/***************
 * [Exception] 表正在使用，无法删除
 ***************/
public class TableOccupiedException extends RuntimeException {

  @Override
  public String getMessage() {
    return "Exception: Table is under use!";
  }
}
