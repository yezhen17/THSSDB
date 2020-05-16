package cn.edu.thssdb.operation;

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
        // TODO 调用 cn.edu.thssdb.schema.Manager.deleteDatabase
    }
}
