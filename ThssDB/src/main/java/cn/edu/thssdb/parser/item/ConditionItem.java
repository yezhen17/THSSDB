package cn.edu.thssdb.parser.item;
import cn.edu.thssdb.parser.item.ColumnFullNameItem;
import cn.edu.thssdb.query.QueryColumn;
import cn.edu.thssdb.schema.Column;
import cn.edu.thssdb.schema.Entry;
import cn.edu.thssdb.schema.Row;
import cn.edu.thssdb.type.ColumnType;
import cn.edu.thssdb.type.ComparisonType;
import cn.edu.thssdb.utils.Global;

import java.util.ArrayList;

public class ConditionItem {
  private boolean value;
  //  private int c1;
//  private int c2;
  ComparerItem c1 = null;
  ComparerItem c2 = null;
//  ColumnFullNameItem c1 = null;
//  ColumnFullNameItem c2 = null;
//  LiteralValueItem l1 = null;
//  LiteralValueItem l2 = null;
  int idx1;
  int idx2;
  Entry e1;
  Entry e2;
  int type;
  private ComparisonType cmp;
  private boolean is_bool = true;

  public ConditionItem(ComparerItem c1, ComparerItem c2, String cmp) {
    this.c1 = c1;
    this.c2 = c2;
    this.cmp = ComparisonType.string2ComparisonType(cmp);
    this.is_bool = false;
  }

//  public ConditionItem(ColumnFullNameItem c1, ColumnFullNameItem c2, String cmp) {
//    this.c1 = c1;
//    this.c2 = c2;
//    this.cmp = ComparisonType.string2ComparisonType(cmp);
//    this.is_bool = false;
//  }
//
//  public ConditionItem(LiteralValueItem l1, LiteralValueItem l2, String cmp) {
//    this.l1 = l1;
//    this.l2 = l2;
//    this.cmp = ComparisonType.string2ComparisonType(cmp);
//    this.is_bool = false;
//  }
//
//  public ConditionItem(LiteralValueItem l1, ColumnFullNameItem c2, String cmp) {
//    this.l1 = l1;
//    this.c2 = c2;
//    this.cmp = ComparisonType.string2ComparisonType(cmp);
//    this.is_bool = false;
//  }
//
//  public ConditionItem(ColumnFullNameItem c1, LiteralValueItem l2, String cmp) {
//    this.c1 = c1;
//    this.l2 = l2;
//    this.cmp = ComparisonType.string2ComparisonType(cmp);
//    this.is_bool = false;
//  }

  public ConditionItem(boolean value) {
    this.value = value;
    this.is_bool = true;
  }

  public boolean evaluate(Row row) {
    if (this.is_bool) {
      return this.value;
    }
    int cmp_res = 0;
    switch (this.type) {
      case 0: {
        Entry tmp1 = row.getEntries().get(idx1);
        Entry tmp2 = row.getEntries().get(idx2);
        if (tmp1 == null || tmp2 == null) return false;
        else cmp_res = tmp1.compareTo(tmp2);
        break;
      }
      case 1: {
        Entry tmp = row.getEntries().get(idx1);
        if (tmp.value == null) return false;
        else cmp_res = tmp.compareTo(e2);
        break;
      }
      case 2: {
        Entry tmp = row.getEntries().get(idx2);
        if (tmp.value == null) return false;
        else cmp_res = e1.compareTo(tmp);
        break;
      }
      case 3: {
        cmp_res = e1.compareTo(e2);
        break;
      }
      default: {
        break; // 不会出现
      }
    }
    // int cmp_res = row.getEntries().get(c1).compareTo(row.getEntries().get(c1));

    boolean res;
    switch (cmp) {
      case LT: res = cmp_res == -1; break; // <
      case GT: res = cmp_res == 1; break;// >
      case NGT: res = cmp_res <= 0; break; // <=
      case NLT: res = cmp_res >= 0; break; // >=
      case EQ: res = cmp_res == 0; break; // ==
      case NEQ: res = cmp_res != 0; break; // >=
      default: res = row.getEntries().get(idx1) == null; break;
    }
    return res;
  }

  public boolean getValue() {
    return this.value;
  }

  public void convertConditionToIndex(ArrayList<QueryColumn> columns) {
    if (c1.getIsC()) {
      int i = 0;
      for (QueryColumn column : columns) {
        if (column.compareTo(c1.getC())) {
          idx1 = i;
          break;
        }
        i++;
      }
      if (c2.getIsNull()) return;
      if (c2.getIsC()) {
        type = 0;
        i = 0;
        for (QueryColumn column : columns) {
          if (column.compareTo(c2.getC())) {
            idx2 = i;
            break;
          }
          i++;
        }
      } else {
        type = 1;
        ColumnType type = columns.get(idx1).getType();
        String l = c2.getL().getString();
        Comparable val = null;
        if (c2.getL().getType() != LiteralValueItem.Type.NULL) {

          // TODO 似乎不用catch
          if(type == ColumnType.INT) val = Integer.valueOf(l);
          else if(type == ColumnType.LONG) val = Long.valueOf(l);
          else if(type == ColumnType.FLOAT) val = Float.valueOf(l);
          else if(type == ColumnType.DOUBLE) val = Double.valueOf(l);
          else if(type == ColumnType.STRING) val = l;
        }
        e2 = new Entry(val);
      }
    } else {
      if (c2.getIsC()) {
        type = 2;
        int i = 0;
        for (QueryColumn column : columns) {
          if (column.compareTo(c2.getC())) {
            idx2 = i;
            break;
          }
          i++;
        }
        ColumnType type = columns.get(idx2).getType();
        String l = c1.getL().getString();
        Comparable val = null;
        if (c1.getL().getType() != LiteralValueItem.Type.NULL) {

          // TODO 似乎不用catch
          if(type == ColumnType.INT) val = Integer.valueOf(l);
          else if(type == ColumnType.LONG) val = Long.valueOf(l);
          else if(type == ColumnType.FLOAT) val = Float.valueOf(l);
          else if(type == ColumnType.DOUBLE) val = Double.valueOf(l);
          else if(type == ColumnType.STRING) val = l;
        }
        e1 = new Entry(val);
      } else {
        type = 3;
        // 两个都是值，无法判断类型，默认字符串比较
        e1 = new Entry(c1.getL().getString());
        if (c2.getIsNull()) return;
        e2 = new Entry(c2.getL().getString());
      }

    }
  }
}
