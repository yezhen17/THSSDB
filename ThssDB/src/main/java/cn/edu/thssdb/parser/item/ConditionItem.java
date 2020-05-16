package cn.edu.thssdb.parser.item;
import cn.edu.thssdb.parser.item.ColumnFullNameItem;
import cn.edu.thssdb.schema.Column;
import cn.edu.thssdb.schema.Row;
import cn.edu.thssdb.type.ComparisonType;
import cn.edu.thssdb.utils.Global;

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

  public boolean evaluate() {
    if (this.is_bool) {
      return this.value;
    }
    // int cmp_res = row.getEntries().get(c1).compareTo(row.getEntries().get(c1));
    int cmp_res = 0;
    boolean res;
    switch (cmp) {
      case LT: res = cmp_res == -1; break; // <
      case GT: res = cmp_res == 1; break;// >
      case NGT: res = cmp_res <= 0; break; // <=
      case NLT: res = cmp_res >= 0; break; // >=
      case EQ: res = cmp_res == 0; break; // ==
      case NEQ: res = cmp_res != 0; break; // >=
      default: res = true; break;
    }
    return res;
  }

}
