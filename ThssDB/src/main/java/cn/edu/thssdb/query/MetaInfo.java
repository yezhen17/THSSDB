package cn.edu.thssdb.query;

import cn.edu.thssdb.schema.Column;
import java.util.ArrayList;
import java.util.List;

/*
 一个或两个表的元数据
 */
class MetaInfo {
  private String tableName;
  private List<Column> columns;

  MetaInfo(String tableName, ArrayList<Column> columns) {
    this.tableName = tableName;
    this.columns = columns;
  }

  // 产生带表名的列列表
  public ArrayList<QueryColumn> getColumns() {
    ArrayList<QueryColumn> a = new ArrayList<>();
    for (Column c: columns) {
      a.add(new QueryColumn(c, tableName));
    }
    return a;
  }

  public int getColumnNum() {
    return columns.size();
  }
}