package cn.edu.thssdb.operation;

import cn.edu.thssdb.parser.item.LiteralValueItem;
import cn.edu.thssdb.parser.item.MultipleConditionItem;
import cn.edu.thssdb.schema.Row;

public class UpdateOperation extends BaseOperation {
  private Row oldRow;
  private Row newRow;
  private String tableName;
  private String columnNmae;
  private LiteralValueItem literalValueItem;
  private MultipleConditionItem multipleConditionItem = null;

  /**
   * [method] 构造方法
   */
  public UpdateOperation(Row oldRow, Row newRow) {
      this.oldRow = oldRow;
      this.newRow = newRow;
  }

  public UpdateOperation(String tableName, String columnNmae, LiteralValueItem literalValueItem) {
    this.tableName = tableName;
    this.columnNmae = columnNmae;
    this.literalValueItem = literalValueItem;
  }

  public UpdateOperation(String tableName, String columnNmae, LiteralValueItem literalValueItem, MultipleConditionItem multipleConditionItem) {
    this.tableName = tableName;
    this.columnNmae = columnNmae;
    this.literalValueItem = literalValueItem;
    this.multipleConditionItem = multipleConditionItem;
  }

  /**
     * [method] 执行操作
     */
  public void exec() {
      // TODO 调用 cn.edu.thssdb.schema.Table.update
  }


}
