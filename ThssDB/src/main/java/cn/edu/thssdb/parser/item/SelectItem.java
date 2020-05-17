package cn.edu.thssdb.parser.item;

import cn.edu.thssdb.schema.Column;

import java.util.ArrayList;

public class SelectItem {

  /*
    type  example                             parameter
    0     3.4                                 constNum1
    1     A.B / A.* / (null.)B / (null.)*     tableName,columnName
    2     3 + A                               tableName,columnName,constNum1,op
    3     A + 3                               tableName,columnName,constNum1,op
    4     3 + 4                               constNum1,constNum2,op
    5     avg/sum/count/min/max(col)          tableName,columnName,aggregateFun
    6     distinct c1,c2,...                  tableName,columnName
   */

  private int type;
  private double constNum1;
  private double constNum2;
  private String tableName;     // name/null
  private String columnName;    // name/*
  private String op;            // + - * /
  private String aggregateFun;  // SUM,COUNT...

  public SelectItem(double constNum1){
    this.type = 0;
    this.constNum1 = constNum1;
  }

  public SelectItem(ColumnFullNameItem columnFullNameItem){
    this.type = 1;
    this.tableName = columnFullNameItem.getTableName();
    this.columnName = columnFullNameItem.getColumnName();
  }

  public SelectItem(ColumnFullNameItem columnFullNameItem, Double constNum2, String op){
    this.type = 3;
    this.tableName = columnFullNameItem.getTableName();
    this.columnName = columnFullNameItem.getColumnName();
    this.constNum2 = constNum2;
    this.op = op;
  }

  public SelectItem(Double constNum1, ColumnFullNameItem columnFullNameItem, String op){
    this.type = 2;
    this.tableName = tableName;
    this.columnName = columnName;
    this.constNum1 = constNum1;
    this.op = op;
  }

  public SelectItem(Double constNum1, Double constNum2, String op){
    this.type = 4;
    this.constNum1 = constNum1;
    this.constNum2 = constNum2;
    this.op = op;
  }

  public SelectItem(ColumnFullNameItem columnFullNameItem, String aggregateFun){
    this.type = 5;
    this.tableName = columnFullNameItem.getTableName();
    this.columnName = columnFullNameItem.getColumnName();
    this.aggregateFun = aggregateFun.toLowerCase();
  }

  public SelectItem(String tableName,String columnName,String aggregateFun){
    this.tableName = tableName;
    this.columnName = columnName;
    this.aggregateFun = aggregateFun;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public double getConstNum1() {
    return constNum1;
  }

  public double getConstNum2() {
    return constNum2;
  }

  public String getTableName() {
    return tableName;
  }

  public String getColumnName() {
    return columnName;
  }

  public String getOp() {
    return op;
  }

  public String getAggregateFun() {
    return aggregateFun;
  }
}
