package cn.edu.thssdb.operation;

import cn.edu.thssdb.exception.DatabaseNotExistException;
import cn.edu.thssdb.schema.Database;
import cn.edu.thssdb.schema.Manager;

public class DropTableOperation extends BaseOperation {
    private String name;      // 表名称

    /**
     * [method] 构造方法
     */
    public DropTableOperation(String name) {
        this.name = name;
    }

    /**
     * [method] 执行操作
     */
    public void exec() {

      if (database==null){
        throw new DatabaseNotExistException();
      }

      database.drop(name);
    }
}
