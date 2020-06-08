package cn.edu.thssdb.parser.item;

import java.util.ArrayList;

public class OrderByItem {
  private ArrayList<ColumnFullNameItem> columnList;
  private int order; // 1 asc; 0 no; -1 desc

  public OrderByItem(ArrayList<ColumnFullNameItem> columnList, int order){
    this.columnList = columnList;
    this.order = order;
  }

  public ArrayList<ColumnFullNameItem> getColumnList() {
    return columnList;
  }

  public int getOrder() {
    return order;
  }
}
