package cn.edu.thssdb.query;

import cn.edu.thssdb.exception.WrongTableNameException;
import cn.edu.thssdb.parser.item.*;
import cn.edu.thssdb.schema.Entry;
import cn.edu.thssdb.schema.Row;

import java.lang.reflect.Array;
import java.util.*;

import javafx.scene.control.Cell;

public class QueryResult {

  private SelectContentItem selectContentItem;
  private FromItem fromItem;
  private MultipleConditionItem whereItem;
  private OrderByItem orderByItem;

  private int tableNum;

  private List<MetaInfo> metaInfos;

  private ArrayList<QueryColumnPlusData> queryRes;

  private ArrayList<QueryColumn> columns;
  private List<Integer> index;
  private List<Cell> attrs;

  public QueryResult(QueryTable queryTables) {
    // TODO
    this.index = new ArrayList<>();
    this.attrs = new ArrayList<>();

    this.tableNum = metaInfos.size();
    ArrayList<Row> res = new ArrayList<>();

    this.columns = new ArrayList<>();
    for (MetaInfo metaInfo: metaInfos) {
      this.columns.addAll(metaInfo.getColumns());
    }

    FromItem.JoinType joinType = fromItem.getJoinType();
    for (OnItem c: fromItem.getOnItems()) {
      whereItem = new MultipleConditionItem(whereItem, new MultipleConditionItem(
              new ConditionItem(new ComparerItem(c.getColumnA()), new ComparerItem(c.getColumnB()),
                      "=")), "AND");
    }
    boolean retain_left = false;
    boolean retain_right = false;
    switch (joinType) {
      case NATURAL_INNER_JOIN: {
        for (QueryColumn c1: metaInfos.get(0).getColumns()) {
          for (QueryColumn c2: metaInfos.get(1).getColumns()) {
            if (c1.getName().equalsIgnoreCase(c2.getName())) {
              ConditionItem conditionItem = new ConditionItem(new ComparerItem(c1.getColumn()),
                      new ComparerItem(c2.getColumn()), "=");
              whereItem = new MultipleConditionItem(whereItem, new MultipleConditionItem(conditionItem), "AND");
            }
          }
        }
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
      int column_count1 = metaInfos.get(0).getColumnNum();
      int column_count2 = metaInfos.get(1).getColumnNum();
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
        } else {
          if (retain_left) {
            if ((i % n2) == (n2 - 1) && !t1_count[i / n2]) {
              ArrayList<Entry> entries = new ArrayList<Entry>();
              ArrayList<Entry> r_entries = r.getEntries();
              for (int k = 0; k < column_count1; k++) {
                entries.add(new Entry(r_entries.get(k)));
              }
              for (int k = 0; k < column_count2; k++) {
                entries.add(new Entry(null));
              }
              res.add(new Row(entries));
            }
          }
          if (retain_right) {
            if ((i / n2) == (n1 - 1) && !t2_count[i % n2]) {
              ArrayList<Entry> entries = new ArrayList<Entry>();
              ArrayList<Entry> r_entries = r.getEntries();

              for (int k = 0; k < column_count1; k++) {
                entries.add(new Entry(null));
              }
              for (int k = column_count1; k < column_count1 + column_count2; k++) {
                entries.add(new Entry(r_entries.get(k)));
              }
              res.add(new Row(entries));
            }
          }
        }
        i++;
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

    this.queryRes = columnToIndices(this.selectContentItem.getSelectContent());
    generateQueryRecord(res);

    // 转换为行的列表
    ArrayList<ArrayList<String>> res_table = new ArrayList<>();

    ArrayList<String> titles = new ArrayList<>();
    res_table.add(titles);
    for (int i = 0; i < res.size(); i++) {
      res_table.add(new ArrayList<>());
    }
    for (QueryColumnPlusData qc: this.queryRes) {
      titles.add(qc.getTitle());
      ArrayList<String> data = qc.getData();
      for (int i = 0; i < res.size(); i++) {
        res_table.get(i + 1).add(data.get(i));
      }
    }

    // 去重
    if (this.selectContentItem.isDistinct()) {
      ArrayList<ArrayList<String>> tmp_table = new ArrayList<>();
      for (ArrayList<String> row: res_table) {
        boolean flag = true;
        for (ArrayList<String> other: tmp_table) {
          if (other.equals(row)) {
            flag = false;
            break;
          }
        }
        if (flag) tmp_table.add(row);
      }
      res_table = tmp_table;
    }
  }

//  private boolean compareStrings(ArrayList<String> a1, ArrayList<String> a2) {
//    boolean res = true;
//    for (int i = 0; i < a1.size(); i++) {
//      if(a1.equals(a))
//    }
//  }

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

  private int findIndex(String tableName, String columnName) {
    int i = 0;
    for (QueryColumn c: this.columns) {
      if (c.compareTo(tableName, columnName)) return i;
      i++;
    }
    return -1;
  }
//  // WHERE 子句
//  public static boolean judge(Row row, MultipleConditionItem whereItem) {
//    // TODO
//    // whereItem.setColumn(xxx);
//    return whereItem.getTreeValue(row).getValue();
//  }

  private String getFullColumnName (String tableName, String columnName) {
    if (tableName == null) {
      return columnName.toLowerCase();
    } else {
      return tableName.toLowerCase() + "." + columnName.toLowerCase();
    }
  }

  private ArrayList<QueryColumnPlusData> columnToIndices(ArrayList<SelectItem> columns) {
    ArrayList<QueryColumnPlusData> res = new ArrayList<>();
    for (SelectItem c: columns) {
      int type = c.getType();
      switch (type) {
        case 0: {
          res.add(new QueryColumnPlusData(c.getConstNum1())); break;
        }
        case 1: {
          String tableName = c.getTableName();
          String columnName = c.getColumnName();
          if (columnName.equals("*")) {
            if (tableName == null) {
              int i = 0;
              for (QueryColumn qc: this.columns) {
                res.add(new QueryColumnPlusData(qc.getFullColumnName(), i));
                i++;
              }
            } else {
              if (this.tableNum == 1) {
                if (!tableName.equalsIgnoreCase(fromItem.getTableNameA())) {
                  throw new WrongTableNameException();
                }
                int i = 0;
                for (QueryColumn qc: this.columns) {
                  res.add(new QueryColumnPlusData(qc.getFullColumnName(), i));
                  i++;
                }
              } else {
                if (tableName.equalsIgnoreCase(fromItem.getTableNameA())) {
                  int t1_column_count = metaInfos.get(0).getColumnNum();
                  for (int i = 0; i < t1_column_count; i++) {
                    res.add(new QueryColumnPlusData(this.columns.get(i).getFullColumnName(), i));
                  }
                } else if (tableName.equalsIgnoreCase(fromItem.getTableNameB())) {
                  int t1_column_count = metaInfos.get(0).getColumnNum();
                  int total_count = this.columns.size();
                  for (int i = t1_column_count; i < total_count; i++) {
                    res.add(new QueryColumnPlusData(this.columns.get(i).getFullColumnName(), i));
                  }
                } else {
                  throw new WrongTableNameException();
                }
              }
            }
          } else {
            int idx = findIndex(tableName, columnName);
            res.add(new QueryColumnPlusData(getFullColumnName(tableName, columnName), idx));
          }
          break;
        }
        case 2: {
          String tableName = c.getTableName();
          String columnName = c.getColumnName();
          int idx = findIndex(tableName, columnName);
          res.add(new QueryColumnPlusData(c.getConstNum1(), getFullColumnName(tableName, columnName),
                  c.getOp(), idx));
          break;
        }
        case 3: {
          String tableName = c.getTableName();
          String columnName = c.getColumnName();
          int idx = findIndex(tableName, columnName);
          res.add(new QueryColumnPlusData(getFullColumnName(tableName, columnName), c.getConstNum2(),
                  c.getOp(), idx));
          break;
        }
        case 4: {
          res.add(new QueryColumnPlusData(c.getConstNum1(), c.getConstNum2(), c.getOp()));
          break;
        }
        default: {
          String tableName = c.getTableName();
          String columnName = c.getColumnName();
          int idx = findIndex(tableName, columnName);
          res.add(new QueryColumnPlusData(getFullColumnName(tableName, columnName), c.getAggregateFun(), idx));
          break;
        }
      }
    }
    return res;
  }

  // SELECT 子句
  public void generateQueryRecord(ArrayList<Row> rows) {
    for (QueryColumnPlusData qc: this.queryRes) {
      qc.generateTitle();
      qc.generateData(rows);
    }
  }
}
