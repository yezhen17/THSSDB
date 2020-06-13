package cn.edu.thssdb.exception;

/***************
 * [Exception] 数据库正在使用，无法删除
 ***************/
public class DatabaseOccupiedException extends RuntimeException {

  @Override
  public String getMessage() {
    return "Exception: Database is under use!";
  }
}
