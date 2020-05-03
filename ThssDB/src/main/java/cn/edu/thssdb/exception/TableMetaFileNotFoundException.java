package cn.edu.thssdb.exception;

import java.io.FileNotFoundException;

/***************
 * [Exception] 元数据文件没有找到
 ***************/
public class TableMetaFileNotFoundException extends FileNotFoundException {
  @Override
  public String getMessage() {
    return "Exception: Meta file for this table is not found!";
  }
}