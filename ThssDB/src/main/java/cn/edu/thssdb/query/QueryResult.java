package cn.edu.thssdb.query;

import cn.edu.thssdb.schema.Row;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.control.Cell;

public class QueryResult {

  private List<MetaInfo> metaInfoInfos;
  private List<Integer> index;
  private List<Cell> attrs;

  public QueryResult(QueryTable[] queryTables) {
    // TODO
    this.index = new ArrayList<>();
    this.attrs = new ArrayList<>();
  }

  // 笛卡尔积 循环

  // FROM 子句
  public static Row combineRow(LinkedList<Row> rows) {
    // TODO ON 条件
    // FROM ON 连接 Join
    return null;
  }

  // WHERE 子句
  public static boolean judge(Row row) {
    // TODO
    return false;
  }

  // SELECT 子句
  public Row generateQueryRecord(Row row) {
    // TODO
    return null;
  }
}
