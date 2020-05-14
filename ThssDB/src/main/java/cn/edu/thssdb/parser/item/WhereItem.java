package cn.edu.thssdb.parser.item;

public class WhereItem {

  private ValueType valueType;
  private String tableName;
  private String columnName;
  private double num;
  private boolean isNull;


  public enum ValueType{
    COLUMN, NUMBER, STRING, NULL
  }

  public WhereItem(String tableName, String columnName){
    this.tableName = tableName;
    this.columnName = columnName;
  }

  public WhereItem(Double num){
    this.num = num;
  }

  public ValueType getValueType() {
    return valueType;
  }

  public String getTableName() {
    return tableName;
  }

  public String getColumnName() {
    return columnName;
  }

  public double getNum() {
    return num;
  }

  public boolean isNull() {
    return isNull;
  }

  public WhereItem(boolean isNull){
    this.isNull = isNull;
  }
}
