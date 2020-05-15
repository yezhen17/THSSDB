package cn.edu.thssdb.parser.item;

public class ColumnFullNameItem {
  private String tableName;
  private String columnName;

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
}
