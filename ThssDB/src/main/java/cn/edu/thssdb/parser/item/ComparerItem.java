package cn.edu.thssdb.parser.item;

public class ComparerItem {
  private ColumnFullNameItem c = null;
  private LiteralValueItem l = null;
  private boolean isC;

  public ComparerItem() {

  }

  public ComparerItem(ColumnFullNameItem c) {
    this.c = c;
    this.isC = true;
  }

  public ComparerItem(LiteralValueItem l) {
    this.l = l;
    this.isC = false;
  }

  public boolean getIsC() {
    return this.isC;
  }

  public LiteralValueItem getL() {
    return l;
  }

  public ColumnFullNameItem getC() {
    return c;
  }
}
