package cn.edu.thssdb.query;

import cn.edu.thssdb.schema.Column;
import java.util.ArrayList;
import java.util.List;

class MetaInfo {

  private String tableName;
  private List<Column> columns;

  MetaInfo(String tableName, ArrayList<Column> columns) {
    this.tableName = tableName;
    this.columns = columns;
  }

  int columnFind(String name) {
    // TODO
    return 0;
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