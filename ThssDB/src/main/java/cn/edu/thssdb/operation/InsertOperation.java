package cn.edu.thssdb.operation;
import cn.edu.thssdb.parser.item.LiteralValueItem;
import cn.edu.thssdb.schema.Row;

import java.util.ArrayList;

public class InsertOperation extends BaseOperation {
  private Row row;            // 待插入行

  private String tableName;
  private ArrayList<String> columnNames;
  private ArrayList<ArrayList<LiteralValueItem>> values;

  public InsertOperation(String tableName, ArrayList<ArrayList<LiteralValueItem>> values) {
    this.tableName = tableName;
    this.values = values;
  }

  public InsertOperation(String tableName, ArrayList<String> columnNames, ArrayList<ArrayList<LiteralValueItem>> values) {
    this.tableName = tableName;
    this.columnNames = columnNames;
    this.values = values;
  }

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
