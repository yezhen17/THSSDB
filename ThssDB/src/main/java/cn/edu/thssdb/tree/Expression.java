package cn.edu.thssdb.tree;
import cn.edu.thssdb.parser.item.ColumnFullNameItem;
import cn.edu.thssdb.schema.Column;
import cn.edu.thssdb.schema.Row;
import cn.edu.thssdb.type.ComparisonType;

public class Expression {
  private boolean value;
//  private int c1;
//  private int c2;
  ColumnFullNameItem c1;
  ColumnFullNameItem c2;
  private ComparisonType cmp;
  private boolean is_bool;

  public Expression(ColumnFullNameItem c1, ColumnFullNameItem c2, ComparisonType cmp) {
    this.c1 = c1;
    this.c2 = c2;
    this.cmp = cmp;
    this.is_bool = false;
  }

  public Expression(boolean value) {
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
