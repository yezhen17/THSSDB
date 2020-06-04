package cn.edu.thssdb.query;

import cn.edu.thssdb.parser.item.ConditionItem;
import cn.edu.thssdb.schema.Entry;
import cn.edu.thssdb.schema.Row;
import cn.edu.thssdb.schema.Table;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

// 根据使用的表，生成一个Row迭代器
public class QueryTable implements Iterator<Row> {
  private int tableNum;
  private ArrayList<Table> tables;
  Iterator<Row> t1;
  Iterator<Row> t2;
  int n1;
  int n2;
  Row cache;
  boolean t1_primary = false;

  public QueryTable(ArrayList<Table> tables) {
    this.tables = tables;
    this.tableNum = tables.size();
    if (this.tableNum == 1) {
      t1 = tables.get(0).iterator();
      n1 = tables.get(0).index.size();
      n2 = 1;
    } else {
      t1 = tables.get(0).iterator();
      t2 = tables.get(1).iterator();
      n1 = tables.get(0).index.size();
      n2 = tables.get(1).index.size();
      if (t1.hasNext()) {
        cache = t1.next();
      }

    }
  }

  public void setSingleCondition(ConditionItem cond, int c1Len) {
    int type = cond.getType();
    if (type == 1) {
      boolean is_t1 = true;
      if (cond.getIdx1() >= c1Len) is_t1 = false;

    }
  }

  @Override
  public boolean hasNext() {
    if (tableNum == 1) {
      return t1.hasNext();
    } else {
      return t1.hasNext() || t2.hasNext();
    }
  }

  @Override
  public Row next() {
    if (tableNum == 1) {
      return t1.next();
    } else {
      if (!t2.hasNext()) {
        cache = t1.next();
        t2 = tables.get(1).iterator();
      }
      return combineRow(cache, t2.next());
//      Row tmp = t2.next();
//      if (tmp != null) {
//        return combineRow(cache, tmp);
//      } else {
//        cache = t1.next();
//        t2 = tables.get(1).iterator();
//        return combineRow(cache, t2.next());
//      }
    }
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

//  public Row getRow(int tableId, int rowId) {
//    return tables.get(tableId).index
//  }
}