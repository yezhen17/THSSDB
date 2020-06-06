package cn.edu.thssdb.parser.item;

public class ColumnFullNameItem {
  private String tableName = null;
  private String columnName = null;

  public ColumnFullNameItem() {

  }

  public ColumnFullNameItem(String tableName, String columnName) {
    this.tableName = tableName;
    this.columnName = columnName;
  }

  public String getTableName() {
    return tableName;
  }

  public String getColumnName() {
    return columnName;
  }

  public boolean compareTo(ColumnFullNameItem c) {
    return c.getColumnName().equalsIgnoreCase(this.columnName) && (c.getTableName() == null ||
            c.getTableName().equalsIgnoreCase(this.tableName));
  }

  public boolean compareTo(String tableName, String columnName) {
    return columnName.equalsIgnoreCase(this.columnName) && (tableName == null ||
            tableName.equalsIgnoreCase(this.tableName));
  }


}
