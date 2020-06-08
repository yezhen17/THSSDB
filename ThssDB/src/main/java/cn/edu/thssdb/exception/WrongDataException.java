package cn.edu.thssdb.exception;

/***************
 * [Exception] 数据文件错误
 ***************/
public class WrongDataException extends RuntimeException {
  @Override
  public String getMessage() {
    return "Exception: Internal error when recovering data!";
  }
}
