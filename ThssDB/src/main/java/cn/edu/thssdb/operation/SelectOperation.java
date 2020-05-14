package cn.edu.thssdb.operation;

import cn.edu.thssdb.parser.item.FromItem;
import cn.edu.thssdb.parser.item.OrderByItem;
import cn.edu.thssdb.parser.item.SelectItem;
import cn.edu.thssdb.tree.ExpressionTree;

import java.util.ArrayList;

public class SelectOperation {
  private ArrayList<SelectItem> selectItems;
  private FromItem fromItem;
  private ExpressionTree expressionTree;
  private OrderByItem orderByItem;

  /**
   * [method] 构造方法
   */
  public SelectOperation(ArrayList<SelectItem> selectItems,FromItem fromItem, ExpressionTree expressionTree, OrderByItem orderByItem) {
    this.selectItems = selectItems;
    this.fromItem = fromItem;
    this.expressionTree = expressionTree;
    this.orderByItem = orderByItem;
  }

  /**
   * [method] 执行操作
   */
  public void exec() {
    // TODO
  }
}
