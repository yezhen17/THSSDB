package cn.edu.thssdb.operation;

import cn.edu.thssdb.schema.Entry;
import cn.edu.thssdb.schema.Row;

public class DeleteOperation {
    private Row row;            // 待删除行行描述
    private Entry entry;        // 待删除行主键描述

    /**
     * [method] 构造方法
     */
    public DeleteOperation(Row row) {
        this.row = row;
    }
    public DeleteOperation(Entry entry) {
        this.entry = entry;
    }

    /**
     * [method] 执行操作
     */
    public void exec() {
        // TODO 调用 cn.edu.thssdb.schema.Table.delete(cn.edu.thssdb.schema.Row) 或 cn.edu.thssdb.schema.Table.delete(cn.edu.thssdb.schema.Entry)
        // 判断 调用哪个重载
    }
}
