package cn.edu.thssdb.operation;
import cn.edu.thssdb.schema.Row;

public class InsertOperation extends BaseOperation {
    private Row row;            // 待插入行

    /**
     * [method] 构造方法
     */
    public InsertOperation(Row row) {
        this.row = row;
    }

    /**
     * [method] 执行操作
     */
    public void exec() {
        // TODO 调用 cn.edu.thssdb.schema.Table.insert
    }
}
