package cn.edu.thssdb.operation;

import cn.edu.thssdb.exception.CustomIOException;
import cn.edu.thssdb.exception.DataFileNotFoundException;
import cn.edu.thssdb.exception.MetaFileNotFoundException;

public class BaseOperation {
  private String username = "";
  private String databaseName = "";


  public BaseOperation() {

  }

  private String getUserOperationDatabase() {
    return "";
  } // 获取当前用户正在操作的数据库

  public void exec() throws CustomIOException, ClassNotFoundException, MetaFileNotFoundException, DataFileNotFoundException {

  }
}
