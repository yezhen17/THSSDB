package cn.edu.thssdb.query;

import cn.edu.thssdb.parser.item.MultipleConditionItem;
import cn.edu.thssdb.schema.Row;

import java.lang.reflect.Array;
import java.util.*;

import javafx.scene.control.Cell;

public class QueryResult {
  private int tableNum;

  private List<MetaInfo> metaInfoInfos;
  private List<Integer> index;
  private List<Cell> attrs;

  public QueryResult(QueryTable[] queryTables) {
    // TODO
    this.index = new ArrayList<>();
    this.attrs = new ArrayList<>();



  }

  // 对结果排序，indices指的是排序属性的下标
  public static void sortArray(ArrayList<Row> rows, boolean isDesc, ArrayList<Integer> indices) {
    Collections.sort(rows, (r1, r2) -> {
      int flag = 0;
      for (int index: indices) {
        flag = r1.getEntries().get(index).compareTo(r2.getEntries().get(index));
        if (flag != 0) {
          break;
        }
      }
      if (isDesc) {
        return -1 * flag;
      } else {
        return flag;
      }
    });
  }


  // 笛卡尔积 循环

  // FROM 子句
  public static Row combineRow(LinkedList<Row> rows) {
    // TODO ON 条件
    // FROM ON 连接 Join
    for (Row row: rows) {

    }
    return null;
  }


  // WHERE 子句
  public static boolean judge(Row row, MultipleConditionItem whereItem) {
    // TODO
    return whereItem.getTreeValue(row).getValue();
  }

  // SELECT 子句
  public Row generateQueryRecord(Row row) {
    // TODO
    return null;
  }
}
