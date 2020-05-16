package cn.edu.thssdb.operation;

import cn.edu.thssdb.parser.SQLParser;
import cn.edu.thssdb.parser.item.*;
import cn.edu.thssdb.schema.Table;
import cn.edu.thssdb.tree.ExpressionTree;

import java.util.ArrayList;

public class SelectOperation extends BaseOperation {
  private SelectContentItem selectContentItem;
  private FromItem fromItem;
  private MultipleConditionItem whereItem;
  private OrderByItem orderByItem;

  /**
   * [method] 构造方法
   */
  public SelectOperation(SelectContentItem selectContentItem,FromItem fromItem,
                         MultipleConditionItem whereItem, OrderByItem orderByItem) {
    this.selectContentItem = selectContentItem;
    this.fromItem = fromItem;
    this.whereItem = whereItem;
    this.orderByItem = orderByItem;
  }

  /**
   * [method] 执行操作
   */
  public void exec() {
    // TODO

  }
}
