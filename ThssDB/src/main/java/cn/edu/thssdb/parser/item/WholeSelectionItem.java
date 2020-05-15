package cn.edu.thssdb.parser.item;

public class WholeSelectionItem {
  private SelectContentItem selectContentItem = null;
  private FromItem fromItem = null;
  private WhereItem whereItem = null;
  private OrderByItem orderByItem = null;

  public WholeSelectionItem(SelectContentItem selectContentItem,
                            FromItem fromItem,
                            WhereItem whereItem,
                            OrderByItem orderByItem) {
    this.selectContentItem = selectContentItem;
    this.fromItem = fromItem;
    this.whereItem = whereItem;
    this.orderByItem = orderByItem;
  }

  public SelectContentItem getSelectContentItem() {
    return this.selectContentItem;
  }

  public FromItem getFromItem() {
    return this.fromItem;
  }

  public WhereItem getWhereItem() {
    return this.whereItem;
  }

  public OrderByItem getOrderByItem() {
    return this.orderByItem;
  }
}
