package cn.edu.thssdb.query;

import cn.edu.thssdb.schema.Entry;
import cn.edu.thssdb.schema.Row;
import cn.edu.thssdb.schema.Table;

import java.util.ArrayList;

public class BaseQueryTable {
  private int tableNum;
  private ArrayList<Table> tables;

  public BaseQueryTable() {

  }

  // 将两个表的Row合并为一个
  public static Row combineRow(Row r1, Row r2) {

    ArrayList<Entry> res = new ArrayList<>();
    res.addAll(r1.getEntries());
    res.addAll(r2.getEntries());
    return new Row(res);
  }

  public int getTableNum() {
    return tableNum;
  }
}
