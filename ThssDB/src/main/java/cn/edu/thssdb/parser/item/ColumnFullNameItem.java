package cn.edu.thssdb.parser.item;

public class ColumnFullNameItem {
  private String tableName = null;
  private String columnName;

  public ColumnFullNameItem(String tableName, String columnName) {
    if (tableName != null) this.tableName = tableName.toUpperCase();
    this.columnName = columnName.toUpperCase();
  }

  public String getTableName() {
    return tableName;
  }

  public String getColumnName() {
    return columnName;
  }

  public boolean compareTo(ColumnFullNameItem c) {
    return c.getColumnName().equals(this.columnName) && (c.getTableName() == null ||
            c.getTableName().equals(this.tableName));
  }

  public boolean compareTo(String tableName, String columnName) {
    return columnName.equals(this.columnName) && (tableName == null ||
            tableName.equals(this.tableName));
  }
}
