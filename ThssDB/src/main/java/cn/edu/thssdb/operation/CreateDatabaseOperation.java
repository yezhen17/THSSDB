package cn.edu.thssdb.operation;
import cn.edu.thssdb.exception.CustomIOException;
import cn.edu.thssdb.schema.Manager;
import cn.edu.thssdb.schema.Manager.*;

public class CreateDatabaseOperation extends BaseOperation {
    private String name;      // 数据库名称

    /**
     * [method] 构造方法
     */
    public CreateDatabaseOperation(String name) {
        this.name = name;
    }

    /**
     * [method] 执行操作
     */
    public void exec() throws CustomIOException {
      Manager manager = Manager.getInstance();
      manager.createDatabaseIfNotExists(name);
    }

    public String getName() {
    return name;
  }
}
