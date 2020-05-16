package cn.edu.thssdb.query;

import cn.edu.thssdb.parser.item.*;
import cn.edu.thssdb.schema.Row;

import java.awt.image.AreaAveragingScaleFilter;
import java.lang.reflect.Array;
import java.util.*;

import javafx.scene.control.Cell;

public class QueryResult {

  private SelectContentItem selectContentItem;
  private FromItem fromItem;
  private MultipleConditionItem whereItem;
  private OrderByItem orderByItem;

  private List<MetaInfo> metaInfos;

  private ArrayList<QueryColumn> columns;
  private List<Integer> index;
  private List<Cell> attrs;

  public QueryResult(QueryTable queryTables) {
    // TODO
    this.index = new ArrayList<>();
    this.attrs = new ArrayList<>();

    ArrayList<Row> res = new ArrayList<>();

    this.columns = new ArrayList<>();
    for (MetaInfo metaInfo: metaInfos) {
      this.columns.addAll(metaInfo.getColumns());
    }

    FromItem.JoinType joinType = fromItem.getJoinType();
    for (OnItem c: fromItem.getOnItems()) {
      whereItem = new MultipleConditionItem(whereItem, new MultipleConditionItem(
              new ConditionItem(new ComparerItem(c.getColumnA()), new ComparerItem(c.getColumnB()),
                      "==")), "AND");
    }
    boolean retain_left = false;
    boolean retain_right = false;
    switch (joinType) {
      case NATURAL_INNER_JOIN: {

      }
      case INNER_JOIN_ON: {
        break;
      }
      case LEFT_OUTER_JOIN_ON: {
        retain_left = true;
        break;
      }
      case RIGHT_OUTER_JOIN_ON: {
        retain_right = true;
        break;
      }
      case FULL_OUTER_JOIN_ON: {
        retain_left = true;
        retain_right = true;
        break;
      }
      default:{
        break;
      }
    }

    whereItem.setColumn(this.columns);

    if (queryTables.getTableNum() == 1) {
      for (QueryTable it = queryTables; it.hasNext(); ) {
        Row r = it.next();
        if (whereItem.getTreeValue(r).getValue()) {
          res.add(r);
        }
      }
    } else {
      int n1 = queryTables.n1;
      int n2 = queryTables.n2;
      boolean [] t1_count = new boolean[n1];
      boolean [] t2_count = new boolean[n2];
      int i = 0;
      for (QueryTable it = queryTables; it.hasNext(); ) {
        Row r = it.next();
        if (whereItem.getTreeValue(r).getValue()) {
          res.add(r);
          t1_count[i / n2] = true;
          t2_count[i % n2] = true;
        }
        i++;
      }
      for (int j = 0; j < n1; j++) {
        if (!t1_count[j]) {
          //TODO
        }
      }
      for (int j = 0; j < n2; j++) {
        if (!t2_count[j]) {
          //TODO
        }
      }
    }


    ArrayList<Integer> sort_indices = new ArrayList<>();
    for (ColumnFullNameItem c: orderByItem.getColumnList()) {
      int i = 0;
      for (QueryColumn column : columns) {
        if (column.compareTo(c)) {
          sort_indices.add(i); // TODO 没有找到报错
          break;
        }
        i++;
      }
    }

    sortArray(res, orderByItem.getOrder(), sort_indices);

  }

  // 对结果排序，indices指的是排序属性的下标
  public static void sortArray(ArrayList<Row> rows, int order, ArrayList<Integer> indices) {
    if (order == 0) {
      return;
    }
    Collections.sort(rows, (r1, r2) -> {
      int flag = 0;
      for (int index: indices) {
        flag = r1.getEntries().get(index).compareTo(r2.getEntries().get(index));
        if (flag != 0) {
          break;
        }
      }
      if (order == -1) {
        return -1 * flag;
      } else {
        return flag;
      }
    });
  }





//  // WHERE 子句
//  public static boolean judge(Row row, MultipleConditionItem whereItem) {
//    // TODO
//    // whereItem.setColumn(xxx);
//    return whereItem.getTreeValue(row).getValue();
//  }

  // SELECT 子句
  public Row generateQueryRecord(Row row) {
    // TODO
    return null;
  }
}
