package cn.edu.thssdb.operation;

import cn.edu.thssdb.exception.CustomIOException;
import cn.edu.thssdb.schema.Manager;

public class DropDatabaseOperation extends BaseOperation {
    private String name;      // 数据库名称

    /**
     * [method] 构造方法
     */
    public DropDatabaseOperation(String name) {
        this.name = name;
    }

    /**
     * [method] 执行操作
     */
    public void exec() {
      Manager manager = Manager.getInstance();
      manager.deleteDatabase(name);
    }

    public String getName() {
    return name;
  }
}
