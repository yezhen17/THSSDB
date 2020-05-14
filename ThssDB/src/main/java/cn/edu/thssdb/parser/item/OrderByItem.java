package cn.edu.thssdb.parser.item;

import java.util.ArrayList;

public class OrderByItem {
  private ArrayList<String> tableList;
  private ArrayList<String> columnList;
  private boolean isDesc;

  public OrderByItem(ArrayList<String> tableList, ArrayList<String> columnList, boolean isDesc){
    this.tableList = tableList;
    this.columnList = columnList;
    this.isDesc = isDesc;
  }

  public ArrayList<String> getTableList() {
    return tableList;
  }

  public ArrayList<String> getColumnList() {
    return columnList;
  }

  public boolean isDesc() {
    return isDesc;
  }
}
