package cn.edu.thssdb.parser.item;

public class ComparerItem {
  private ColumnFullNameItem c = null;
  private LiteralValueItem l = null;
  private boolean isC;
  private boolean isNull;

  public ComparerItem() {
    this.isNull = true;
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

  public boolean getIsNull() { return this.isNull; }

  public LiteralValueItem getL() {
    return l;
  }

  public ColumnFullNameItem getC() {
    return c;
  }
}
