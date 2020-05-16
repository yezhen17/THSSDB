package cn.edu.thssdb.operation;

import cn.edu.thssdb.schema.Column;
import java.util.ArrayList;
import cn.edu.thssdb.schema.Database;
import cn.edu.thssdb.schema.Manager;

public class CreateTableOperation extends BaseOperation {
    private String name;            // 表名称
    private Column[] columns;       // 列定义
    private int primaryIndex;       // 主键索引

    /**
     * [method] 构造方法
     */
    public CreateTableOperation(String name, Column[] columns, int primaryIndex) {
        this.name = name;
        this.columns = columns;
        this.primaryIndex = primaryIndex;
    }

    /**
     * [method] 执行操作
     */
    public void exec() {
      Manager manager = Manager.getInstance();
      Database database = manager.getDatabaseByName(manager.getCurrentDatabaseName());
      database.create(name,columns,primaryIndex);
    }
}
