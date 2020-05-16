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


}
