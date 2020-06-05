package cn.edu.thssdb.query;

import cn.edu.thssdb.exception.UnknownColumnException;
import cn.edu.thssdb.exception.WrongTableNameException;
import cn.edu.thssdb.parser.item.*;
import cn.edu.thssdb.schema.Column;
import cn.edu.thssdb.schema.Entry;
import cn.edu.thssdb.schema.Row;
import cn.edu.thssdb.schema.Table;
import cn.edu.thssdb.tree.Node;
import javafx.util.Pair;

import javax.management.Query;
import java.util.*;


// 用于处理查询结果，接口仍有问题，后期解决
public class QueryResult {

  private SelectContentItem selectContentItem;
  private FromItem fromItem;
  private MultipleConditionItem whereItem;
  private OrderByItem orderByItem;

  private int tableNum;

  private ArrayList<MetaInfo> metaInfos;

  private QueryTable queryTable;
  private ArrayList<Table> tables;
  private int firstTableColumnNum;

  private ArrayList<QueryColumnPlusData> queryRes;

  private List<List<String>> finalTable;
  private List<String> columnTitles;

  private ArrayList<QueryColumn> columns;
//  private List<Integer> index;
//  private List<Cell> attrs;

  public QueryResult(SelectContentItem selectContentItem, FromItem fromItem,
                     MultipleConditionItem whereItem, OrderByItem orderByItem, ArrayList<Table> tables) {
//    this.index = new ArrayList<>();
//    this.attrs = new ArrayList<>();

    this.selectContentItem = selectContentItem;
    this.fromItem = fromItem;
    this.whereItem = whereItem;
    this.orderByItem = orderByItem;

    this.metaInfos = new ArrayList<>();
    this.tables = tables;
    this.firstTableColumnNum = tables.get(0).getColumns().size();

    for (Table table: tables) {
      this.metaInfos.add(new MetaInfo(table.getTableName(), table.getColumns()));
    }
    this.tableNum = this.metaInfos.size();

    // 合并后的列信息
    this.columns = new ArrayList<>();
    for (MetaInfo metaInfo: metaInfos) {
      this.columns.addAll(metaInfo.getColumns());
    }
  }
  public void process() {
    ArrayList<Row> res = new ArrayList<>();



    if (whereItem != null) {
      Node<ConditionItem> root = whereItem.getRoot();
      if (root.isLeaf()) {
        ConditionItem tmp = root.getValue();
        int type = tmp.getType();
        if (type == 1 || type == 2 || type == 4) {
          whereItem.setColumn(this.columns);
          boolean isFirst = tmp.setSingleTableIdx(firstTableColumnNum);
          queryTable = new QueryTable(tables, tmp, isFirst);
          whereItem = null;
        }
      } else if (root.getLeft().isLeaf() && root.getRight().isLeaf() && root.getOp().equals("and")) {
        //
      }
    } else {
      queryTable = new QueryTable(tables);
    }

    // JOIN ON 条件与表达式树合并
    FromItem.JoinType joinType = fromItem.getJoinType();

    for (OnItem c: fromItem.getOnItems()) {
      if (whereItem != null) {
        whereItem = new MultipleConditionItem(whereItem, new MultipleConditionItem(
                new ConditionItem(new ComparerItem(c.getColumnA()), new ComparerItem(c.getColumnB()),
                        "=")), "AND");
      } else {
        whereItem = new MultipleConditionItem(
                new ConditionItem(new ComparerItem(c.getColumnA()), new ComparerItem(c.getColumnB()), "="));
      }

    }
    boolean retain_left = false;
    boolean retain_right = false;
    switch (joinType) {
      // 自然连接还需要增强条件
      case NATURAL_INNER_JOIN: {
        for (QueryColumn c1: metaInfos.get(0).getColumns()) {
          for (QueryColumn c2: metaInfos.get(1).getColumns()) {
            if (c1.getName().equalsIgnoreCase(c2.getName())) {
              ConditionItem conditionItem = new ConditionItem(new ComparerItem(c1.getColumn()),
                      new ComparerItem(c2.getColumn()), "=");
              if (whereItem != null) {
                whereItem = new MultipleConditionItem(whereItem, new MultipleConditionItem(conditionItem), "AND");
              } else {
                whereItem = new MultipleConditionItem(conditionItem);
              }

            }
          }
        }
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

    if (whereItem != null) {
      whereItem.setColumn(this.columns);
    }
    res = queryTable.traverse(whereItem, retain_left, retain_right);
//    if (whereItem != null) {
//      whereItem.setColumn(this.columns);
//      // 接下来筛选符合条件的Row
//      if (queryTable.getTableNum() == 1) {
//        for (QueryTable it = queryTable; it.hasNext(); ) {
//          Row r = it.next();
//          if (whereItem.getTreeValue(r).getValue()) {
//            res.add(r);
//          }
//        }
//      } else {
//        int column_count1 = metaInfos.get(0).getColumnNum();
//        int column_count2 = metaInfos.get(1).getColumnNum();
//        int n1 = queryTable.n1;
//        int n2 = queryTable.n2;
//        boolean [] t1_count = new boolean[n1];
//        boolean [] t2_count = new boolean[n2];
//        int i = 0;
//        for (QueryTable it = queryTable; it.hasNext(); ) {
//          Row r = it.next();
//          if (whereItem.getTreeValue(r).getValue()) {
//            res.add(r);
//            t1_count[i / n2] = true;
//            t2_count[i % n2] = true;
//          } else {
//            // LEFT OUTER JOIN
//            if (retain_left) {
//              if ((i % n2) == (n2 - 1) && !t1_count[i / n2]) {
//                ArrayList<Entry> entries = new ArrayList<>();
//                ArrayList<Entry> r_entries = r.getEntries();
//                for (int k = 0; k < column_count1; k++) {
//                  entries.add(new Entry(r_entries.get(k)));
//                }
//                for (int k = 0; k < column_count2; k++) {
//                  entries.add(new Entry(null));
//                }
//                res.add(new Row(entries));
//              }
//            }
//            // RIGHT OUTER JOIN
//            if (retain_right) {
//              if ((i / n2) == (n1 - 1) && !t2_count[i % n2]) {
//                ArrayList<Entry> entries = new ArrayList<>();
//                ArrayList<Entry> r_entries = r.getEntries();
//
//                for (int k = 0; k < column_count1; k++) {
//                  entries.add(new Entry(null)); // 以null填充
//                }
//                for (int k = column_count1; k < column_count1 + column_count2; k++) {
//                  entries.add(new Entry(r_entries.get(k)));
//                }
//                res.add(new Row(entries));
//              }
//            }
//          }
//          i++;
//        }
//      }
//    } else {
//      // 如果没有条件，直接一一放到res中，可能效率高一丁点
//      for (QueryTable it = queryTable; it.hasNext(); ) {
//        Row r = it.next();
//        res.add(r);
//      }
//    }




//    if (res.size() == 0) {
//      return new ArrayList<>();
//    }

    // 排序（如需要）
    sortArray(res, orderByItem.getOrder());

    // select选择子项
    this.queryRes = columnToData(this.selectContentItem.getSelectContent());
    generateQueryRecord(res);

    // 转换为行的列表
    finalTable = new ArrayList<>();

    columnTitles = new ArrayList<>();
//    finalTable.add(titles);

    int final_size = this.queryRes.get(0).getDataSize();
    for (int i = 0; i < final_size; i++) {
      finalTable.add(new ArrayList<>());
    }
    for (QueryColumnPlusData qc: this.queryRes) {
      columnTitles.add(qc.getTitle());
      ArrayList<String> data = qc.getData();
      for (int j = 0; j < data.size(); j++) {
        finalTable.get(j).add(data.get(j));
      }
    }

    // 去重
    if (this.selectContentItem.isDistinct()) {
      List<List<String>> tmp_table = new ArrayList<>();
      for (List<String> row: finalTable) {
        boolean flag = true;
        for (List<String> other: tmp_table) {
          if (other.equals(row)) {
            flag = false;
            break;
          }
        }
        if (flag) tmp_table.add(row);
      }
      finalTable = tmp_table;
    }
  }

  // 对结果排序，indices指的是排序属性的下标
  private void sortArray(ArrayList<Row> rows, int order) {
    if (order == 0) {
      return;
    }
    // 排序依据的列索引
    ArrayList<Integer> indices = new ArrayList<>();
    for (ColumnFullNameItem c: orderByItem.getColumnList()) {
      int i = 0;
      for (QueryColumn column: columns) {
        if (column.compareTo(c)) {
          indices.add(i); // TODO 没有找到报错
          break;
        }
        i++;
      }
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

  // 根据表名和列名得到该列索引
  private int findIndex(String tableName, String columnName) {
    int i = 0;
    for (QueryColumn c: this.columns) {
      if (c.compareTo(tableName, columnName)) return i;
      i++;
    }
    throw new UnknownColumnException();
  }

  private String getFullColumnName (String tableName, String columnName) {
    if (tableName == null) {
      return columnName.toLowerCase();
    } else {
      return tableName.toLowerCase() + "." + columnName.toLowerCase();
    }
  }

  // 将形如 T.C, C, T.*, *, 1, 1+A, A+1, ... 的列信息转化为QueryColumnPlusData类型，以供后续处理
  private ArrayList<QueryColumnPlusData> columnToData(ArrayList<SelectItem> columns) {
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
                res.add(new QueryColumnPlusData(qc.getFullColumnName(), i, qc.getType()));
                i++;
              }
            } else {
              if (this.tableNum == 1) {
                if (!tableName.equalsIgnoreCase(fromItem.getTableNameA())) {
                  throw new WrongTableNameException();
                }
                int i = 0;
                for (QueryColumn qc: this.columns) {
                  res.add(new QueryColumnPlusData(qc.getFullColumnName(), i, qc.getType()));
                  i++;
                }
              } else {
                if (tableName.equalsIgnoreCase(fromItem.getTableNameA())) {
                  int t1_column_count = metaInfos.get(0).getColumnNum();
                  for (int i = 0; i < t1_column_count; i++) {
                    QueryColumn tmp = this.columns.get(i);
                    res.add(new QueryColumnPlusData(tmp.getFullColumnName(), i, tmp.getType()));
                  }
                } else if (tableName.equalsIgnoreCase(fromItem.getTableNameB())) {
                  int t1_column_count = metaInfos.get(0).getColumnNum();
                  int total_count = this.columns.size();
                  for (int i = t1_column_count; i < total_count; i++) {
                    QueryColumn tmp = this.columns.get(i);
                    res.add(new QueryColumnPlusData(tmp.getFullColumnName(), i, tmp.getType()));
                  }
                } else {
                  throw new WrongTableNameException();
                }
              }
            }
          } else {
            int idx = findIndex(tableName, columnName);
            res.add(new QueryColumnPlusData(getFullColumnName(tableName, columnName),
                    idx, this.columns.get(idx).getType()));
          }
          break;
        }
        case 2: {
          String tableName = c.getTableName();
          String columnName = c.getColumnName();
          int idx = findIndex(tableName, columnName);
          res.add(new QueryColumnPlusData(c.getConstNum1(), getFullColumnName(tableName, columnName),
                  c.getOp(), idx, this.columns.get(idx).getType()));
          break;
        }
        case 3: {
          String tableName = c.getTableName();
          String columnName = c.getColumnName();
          int idx = findIndex(tableName, columnName);
          res.add(new QueryColumnPlusData(getFullColumnName(tableName, columnName), c.getConstNum2(),
                  c.getOp(), idx, this.columns.get(idx).getType()));
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
          res.add(new QueryColumnPlusData(getFullColumnName(tableName, columnName), c.getAggregateFun(),
                  idx, this.columns.get(idx).getType()));
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

  public List<List<String>> getFinalTable() {
    return finalTable;
  }

  public List<String> getColumnTitles() {
    return columnTitles;
  }
}
