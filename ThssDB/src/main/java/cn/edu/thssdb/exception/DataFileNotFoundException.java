package cn.edu.thssdb.exception;

import java.io.FileNotFoundException;

/***************
 * [Exception] 数据文件没有找到
 ***************/
public class DataFileNotFoundException extends FileNotFoundException {
  @Override
  public String getMessage() {
    return "Exception: Data file for this table is not found!";
  }
}
