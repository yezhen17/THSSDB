package cn.edu.thssdb.operation;

import cn.edu.thssdb.schema.Row;

public class UpdateOperation extends BaseOperation {
    private Row oldRow;
    private Row newRow;

    /**
     * [method] 构造方法
     */
    public UpdateOperation(Row oldRow, Row newRow) {
        this.oldRow = oldRow;
        this.newRow = newRow;
    }


    /**
     * [method] 执行操作
     */
    public void exec() {
        // TODO 调用 cn.edu.thssdb.schema.Table.update
    }


}
