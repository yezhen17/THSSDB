package cn.edu.thssdb.query;

import cn.edu.thssdb.parser.item.ColumnFullNameItem;
import cn.edu.thssdb.schema.Column;
import cn.edu.thssdb.type.ColumnType;

public class QueryColumn extends Column {
  private String tableName;

  public QueryColumn (String name, ColumnType type, boolean primary, boolean notNull, int maxLength, String tableName) {
    super(name, type, primary, notNull, maxLength);
    this.tableName = tableName;
  }

  public QueryColumn (Column column, String tableName) {
    super(column);
    this.tableName = tableName;
  }

  public boolean compareTo(QueryColumn c) {
    return c.name.equalsIgnoreCase(this.name) &&
            c.tableName.equalsIgnoreCase(this.tableName);
  }

  public boolean compareTo(ColumnFullNameItem c) {
    return c.getColumnName().equalsIgnoreCase(this.name) && (c.getTableName() == null ||
            c.getTableName().equalsIgnoreCase(this.tableName));
  }

  public boolean compareTo(String tableName, String columnName) {
    return columnName.equalsIgnoreCase(this.name) && (tableName == null ||
            tableName.equalsIgnoreCase(this.tableName));
  }

  public ColumnType getType() {
    return this.type;
  }

  public ColumnFullNameItem getColumn() {
    return new ColumnFullNameItem(this.tableName, this.name);
  }

  public String getFullColumnName() {
    if (this.tableName == null) {
      return this.name.toLowerCase();
    } else {
      return this.tableName.toLowerCase() + "." + this.name.toLowerCase();
    }
  }
}
