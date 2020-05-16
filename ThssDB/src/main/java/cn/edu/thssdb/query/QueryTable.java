package cn.edu.thssdb.query;

import cn.edu.thssdb.schema.Entry;
import cn.edu.thssdb.schema.Row;
import cn.edu.thssdb.schema.Table;
import com.sun.org.apache.xpath.internal.operations.String;

import java.util.ArrayList;
import java.util.Iterator;

public class QueryTable implements Iterator<Row> {
  private int tableNum;
  private ArrayList<Table> tables;
  Iterator<Row> t1;
  Iterator<Row> t2;
  int n1;
  int n2;
  Row cache;

  QueryTable(ArrayList<Table> tables) {
    // TODO
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
      n1 = tables.get(1).index.size();
      cache = t1.next();
    }
  }

  @Override
  public boolean hasNext() {
    // TODO
    if (tableNum == 1) {
      return t1.hasNext();
    } else {
      return t1.hasNext() || t2.hasNext();
    }
  }

  @Override
  public Row next() {
    // TODO
    if (tableNum == 1) {
      return t1.next();
    } else {
      Row tmp = t2.next();
      if (tmp != null) {
        return combineRow(cache, tmp);
      } else {
        cache = t1.next();
        t2 = tables.get(1).iterator();
        return combineRow(cache, t2.next());
      }
    }
  }

  // 笛卡尔积 循环

  // FROM 子句
  public static Row combineRow(Row r1, Row r2) {
    // TODO ON 条件
    // FROM ON 连接 Join
    ArrayList<Entry> a = r1.getEntries();
    a.addAll(r2.getEntries());
    return new Row(a);
  }

  public int getTableNum() {
    return tableNum;
  }
}