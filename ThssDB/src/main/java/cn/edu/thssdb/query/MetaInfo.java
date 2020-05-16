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

  public List<QueryColumn> getColumns() {
    ArrayList<QueryColumn> a = new ArrayList<>();
    for (Column c: columns) {
      a.add(new QueryColumn(c, tableName));
    }
    return a;
  }
}