package cn.edu.thssdb.operation;

public class DropTableOperation {
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
        // TODO 调用 cn.edu.thssdb.schema.Database.drop
    }
}
