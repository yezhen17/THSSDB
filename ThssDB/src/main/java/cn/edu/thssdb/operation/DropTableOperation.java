package cn.edu.thssdb.operation;

import cn.edu.thssdb.exception.DatabaseNotExistException;
import cn.edu.thssdb.schema.Database;
import cn.edu.thssdb.schema.Manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

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

  @Override
  public ArrayList<String> getTableName() {
    return new ArrayList<>(Arrays.asList(this.name));
  }

  @Override
  public LinkedList<String> getLog() {
    return new LinkedList<>(Arrays.asList("DROP TABLE " + name));
  }
}
