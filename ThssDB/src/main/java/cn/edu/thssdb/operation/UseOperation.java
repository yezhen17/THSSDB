package cn.edu.thssdb.operation;

import cn.edu.thssdb.exception.CustomIOException;
import cn.edu.thssdb.exception.DataFileNotFoundException;
import cn.edu.thssdb.exception.MetaFileNotFoundException;
import cn.edu.thssdb.schema.Manager;

public class UseOperation extends BaseOperation {
    private String name;      // 数据库名称

    /**
     * [method] 构造方法
     */
    public UseOperation(String name) {
        this.name = name;
    }

    /**
     * [method] 执行操作
     */
    public void exec() throws ClassNotFoundException, MetaFileNotFoundException, CustomIOException, DataFileNotFoundException {
      Manager manager = Manager.getInstance();
      manager.switchDatabase(name);
      System.out.println("use database"+name);
    }

  public String getName() {
    return name;
  }


}
