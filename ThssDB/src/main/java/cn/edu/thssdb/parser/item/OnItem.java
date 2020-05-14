package cn.edu.thssdb.parser.item;

public class OnItem {
  private String tableNameA;
  private String columnNameA;
  private String tableNameB;
  private String columnNameB;

  public OnItem(String tableNameA, String columnNameA, String tableNameB, String columnNameB){
    this.tableNameA = tableNameA;
    this.columnNameA = columnNameA;
    this.tableNameB = tableNameB;
    this.columnNameB = columnNameB;
  }

  public String getTableNameA() {
    return tableNameA;
  }

  public String getColumnNameA() {
    return columnNameA;
  }

  public String getTableNameB() {
    return tableNameB;
  }

  public String getColumnNameB() {
    return columnNameB;
  }
}
