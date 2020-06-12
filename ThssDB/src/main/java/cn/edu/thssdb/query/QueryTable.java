package cn.edu.thssdb.query;

import cn.edu.thssdb.exception.KeyNotExistException;
import cn.edu.thssdb.parser.item.ConditionItem;
import cn.edu.thssdb.parser.item.MultipleConditionItem;
import cn.edu.thssdb.schema.Entry;
import cn.edu.thssdb.schema.Row;
import cn.edu.thssdb.schema.Table;
import cn.edu.thssdb.type.ComparisonType;

import java.util.ArrayList;
import java.util.Iterator;

// 根据使用的表，生成一个Row迭代器
public class QueryTable implements Iterator<Row> {
  private int tableNum;
  private ArrayList<Table> tables;
  Iterator<Row> t1;
  Iterator<Row> t2;
  int n1;
  int n2;
  Row cache;
  Iterable i2;

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
      i2 = tables.get(1);
      n1 = tables.get(0).index.size();
      n2 = tables.get(1).index.size();
//      if (t1.hasNext()) {
//        cache = t1.next();
//      }
    }
  }

  public QueryTable(ArrayList<Table> tables, ConditionItem cond, boolean t1Cond) {
    this.tables = tables;
    this.tableNum = tables.size();
    if (this.tableNum == 1) {
      Iterator<Row> tmp = tables.get(0).iterator();
      ArrayList<Row> new_rows = new ArrayList<>();
      while (tmp.hasNext()) {
        Row row = tmp.next();
        if (cond.evaluate(row)) {
          new_rows.add(row);
        }
      }
      t1 = new_rows.iterator();
      n1 = new_rows.size();
      n2 = 1;
    } else {
      if (t1Cond) {
        Table t = tables.get(0);
        Iterator<Row> tmp = t.iterator();
        ArrayList<Row> new_rows = new ArrayList<>();
        if (cond.getIdx1() == t.primaryIndex && cond.getCmp() == ComparisonType.EQ) {
          new_rows.add(t.search(cond.getE1()));
        } else {
          while (tmp.hasNext()) {
            Row row = tmp.next();
            if (cond.evaluate(row)) {
              new_rows.add(row);
            }
          }
        }
        t1 = new_rows.iterator();
        n1 = new_rows.size();
        t2 = tables.get(1).iterator();
        i2 = tables.get(1);
        n2 = tables.get(1).index.size();
      } else {
        Table t = tables.get(1);
        Iterator<Row> tmp = t.iterator();
        ArrayList<Row> new_rows = new ArrayList<>();
        if (cond.getIdx1() == t.primaryIndex && cond.getCmp() == ComparisonType.EQ) {
          new_rows.add(t.search(cond.getE1()));
        } else {
          while (tmp.hasNext()) {
            Row row = tmp.next();
            if (cond.evaluate(row)) {
              new_rows.add(row);
            }
          }
        }

        t2 = new_rows.iterator();
        n2 = new_rows.size();
        i2 = new_rows;
        t1 = tables.get(0).iterator();
        n1 = tables.get(0).index.size();
      }
//      if (t1.hasNext()) {
//        cache = t1.next();
//      }

    }
  }

  public void setTable(ConditionItem cond, boolean t1Cond) {
    if (t1Cond) {
      Table t = tables.get(0);
      Iterator<Row> tmp = t1;
      ArrayList<Row> new_rows = new ArrayList<>();
      if (cond.getIdx1() == t.primaryIndex && cond.getCmp() == ComparisonType.EQ) {
        new_rows.add(t.search(cond.getE1()));
      } else {
        while (tmp.hasNext()) {
          Row row = tmp.next();
          if (cond.evaluate(row)) {
            new_rows.add(row);
          }
        }
      }
      t1 = new_rows.iterator();
      n1 = new_rows.size();
//      if (t1.hasNext()) {
//        cache = t1.next();
//      }
    } else {
      Table t = tables.get(1);
      Iterator<Row> tmp = t2;
      ArrayList<Row> new_rows = new ArrayList<>();
      if (cond.getIdx1() == t.primaryIndex && cond.getCmp() == ComparisonType.EQ) {
        new_rows.add(t.search(cond.getE1()));
      } else {
        while (tmp.hasNext()) {
          Row row = tmp.next();
          if (cond.evaluate(row)) {
            new_rows.add(row);
          }
        }
      }
      t2 = new_rows.iterator();
      n2 = new_rows.size();
      i2 = new_rows;
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
        t2 = i2.iterator();
      }
      return combineRow(cache, t2.next());
    }
  }

  public ArrayList<Row> traverseSmart(MultipleConditionItem cond, boolean table1, int idx) {
    if (idx == -1) return traverse(cond, false, false);
    ArrayList<Row> res = new ArrayList<>();
    if (n1 == 0 || n2 == 0) return res;
    Iterator<Row> notPrim;
    Table primTable;
    if (table1) {
      notPrim = t2;
      primTable = tables.get(0);
      if (cond != null) {
        while (notPrim.hasNext()) {
          Row r = notPrim.next();
          Entry v = r.getEntries().get(idx);
          try {
            Row nr = combineRow(primTable.search(v), r);
            if(cond.getTreeValue(r).getValue()) res.add(nr);
          } catch (KeyNotExistException e) {

          }
        }
      } else {
        while (notPrim.hasNext()) {
          Row r = notPrim.next();
          Entry v = r.getEntries().get(idx);
          try {
            res.add(combineRow(primTable.search(v), r));
          } catch (KeyNotExistException e) {

          }
        }
      }
    } else {
      notPrim = t1;
      primTable = tables.get(1);
      if (cond != null) {
        while (notPrim.hasNext()) {
          Row r = notPrim.next();
          Entry v = r.getEntries().get(idx);
          try {
            Row nr = combineRow(r, primTable.search(v));
            if(cond.getTreeValue(r).getValue()) res.add(nr);
          } catch (KeyNotExistException e) {

          }
        }
      } else {
        while (notPrim.hasNext()) {
          Row r = notPrim.next();
          Entry v = r.getEntries().get(idx);
          try {
            res.add(combineRow(r, primTable.search(v)));
          } catch (KeyNotExistException e) {

          }
        }
      }
    }
    return res;
  }

  public ArrayList<Row> traverse(MultipleConditionItem cond, boolean retain_left, boolean retain_right) {
    ArrayList<Row> res = new ArrayList<>();
    if (n1 == 0 || tableNum == 2 && n2 == 0) return res;
    if (getTableNum() == 2) {
      if (t1.hasNext()) {
        cache = t1.next();
      }
    }
    if (cond != null) {
      // 接下来筛选符合条件的Row
      if (getTableNum() == 1) {
        while(hasNext()) {
          Row r = next();
          if (cond.getTreeValue(r).getValue()) {
            res.add(r);
          }
        }
      } else {
        if (retain_left || retain_right) {
          int column_count1 = tables.get(0).getColumns().size();
          int column_count2 = tables.get(1).getColumns().size();
          boolean [] t1_count = new boolean[n1];
          boolean [] t2_count = new boolean[n2];
          int i = 0;
          int j = 0;
          while(hasNext()) {
            Row r = next();
            if (cond.getTreeValue(r).getValue()) {
              res.add(r);
              t1_count[j] = true;
              t2_count[i] = true;
            } else {
              // LEFT OUTER JOIN
              if (retain_left) {
                if (i == (n2 - 1) && !t1_count[j]) {
                  ArrayList<Entry> entries = new ArrayList<>();
                  ArrayList<Entry> r_entries = r.getEntries();
                  for (int k = 0; k < column_count1; k++) {
                    entries.add(new Entry(r_entries.get(k)));
                  }
                  for (int k = 0; k < column_count2; k++) {
                    entries.add(new Entry(null));
                  }
                  res.add(new Row(entries));
                }
              }
              // RIGHT OUTER JOIN
              if (retain_right) {
                if (j == (n1 - 1) && !t2_count[i]) {
                  ArrayList<Entry> entries = new ArrayList<>();
                  ArrayList<Entry> r_entries = r.getEntries();

                  for (int k = 0; k < column_count1; k++) {
                    entries.add(new Entry(null)); // 以null填充
                  }
                  for (int k = column_count1; k < column_count1 + column_count2; k++) {
                    entries.add(new Entry(r_entries.get(k)));
                  }
                  res.add(new Row(entries));
                }
              }
            }
            i++;
            if (i == n2) {
              i = 0;
              j++;
            }
          }
        } else {
          while(hasNext()) {
            Row r = next();
            if (cond.getTreeValue(r).getValue()) {
              res.add(r);
            }
          }
        }
      }
    } else {
      // 如果没有条件，直接一一放到res中，可能效率高一丁点
      while(hasNext()) {
        Row r = next();
        res.add(r);
      }
    }
    return res;
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