package cn.edu.thssdb.query;

import cn.edu.thssdb.parser.item.ColumnFullNameItem;
import cn.edu.thssdb.schema.Column;
import cn.edu.thssdb.type.ColumnType;

public class QueryColumn {
  private String columnName;
  private String tableName;
  private ColumnType type;

//  public QueryColumn (String name, ColumnType type, boolean primary, boolean notNull, int maxLength, String tableName) {
//    super(name, type, primary, notNull, maxLength);
//    this.tableName = tableName;
//  }

  public QueryColumn (Column column, String tableName) {
    this.columnName = column.getName();
    this.tableName = tableName;
    this.type = column.getType();
  }

  public boolean compareTo(QueryColumn c) {
    return c.columnName.equalsIgnoreCase(this.columnName) &&
            c.tableName.equalsIgnoreCase(this.tableName);
  }

  public boolean compareTo(ColumnFullNameItem c) {
    return c.getColumnName().equalsIgnoreCase(this.columnName) && (c.getTableName() == null ||
            c.getTableName().equalsIgnoreCase(this.tableName));
  }

  public boolean compareTo(String tableName, String columnName) {
    return columnName.equalsIgnoreCase(this.columnName) && (tableName == null ||
            tableName.equalsIgnoreCase(this.tableName));
  }

  public ColumnType getType() {
    return this.type;
  }

  public String getColumnName() {
    return this.columnName;
  }

  public String getTableName() {
    return tableName;
  }

  public ColumnFullNameItem getColumn() {
    return new ColumnFullNameItem(this.tableName, this.columnName);
  }

  // 获取显示的全名
  public String getFullColumnName() {
    if (this.tableName == null) {
      return this.columnName;
    } else {
      return this.tableName + "." + this.columnName;
    }
  }
}
