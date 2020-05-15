package cn.edu.thssdb.parser.item;

import cn.edu.thssdb.type.ColumnType;

public class TypeItem {
  private ColumnType columnType;
  private int strLen;

  public TypeItem(ColumnType columnType){
    this.columnType = columnType;
    this.strLen = 0;
  }

  public TypeItem(ColumnType columnType, int strLen){
    this.columnType = columnType;
    this.strLen = strLen;
  }

  public ColumnType getColumnType() {
    return columnType;
  }

  public int getStrLen() {
    return strLen;
  }
}
