package cn.edu.thssdb.query;

import cn.edu.thssdb.exception.UnknownColumnException;
import cn.edu.thssdb.exception.WrongOrderByColumnException;
import cn.edu.thssdb.exception.WrongTableNameException;
import cn.edu.thssdb.parser.item.*;
import cn.edu.thssdb.schema.Row;
import cn.edu.thssdb.schema.Table;
import cn.edu.thssdb.parser.item.tree.Node;

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

  public QueryResult(SelectContentItem selectContentItem, FromItem fromItem,
                     MultipleConditionItem whereItem, OrderByItem orderByItem, ArrayList<Table> tables) {

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
    ArrayList<Row> res;

    boolean no_where_left = false;
    if (whereItem != null && tableNum == 2) {
      Node<ConditionItem> root = whereItem.getRoot();
      if (root.isLeaf()) {
        ConditionItem tmp = root.getValue();
        int type = tmp.getType();
        if (type == 1 || type == 2 || type == 4) {
          whereItem.setColumn(this.columns);
          boolean isFirst = tmp.setSingleTableIdx(firstTableColumnNum);
          queryTable = new QueryTable(tables, tmp, isFirst);
          whereItem = null;
          no_where_left = true;
        } else {
          queryTable = new QueryTable(tables);
        }
      } else if (root.getLeft().isLeaf() && root.getRight().isLeaf() && root.getOp().equals("and")) {
        ConditionItem left = (ConditionItem) root.getLeft().getValue();
        ConditionItem right = (ConditionItem) root.getRight().getValue();
        int left_type = left.getType();
        int right_type = right.getType();
        queryTable = new QueryTable(tables);
        if (left_type == 1 || left_type == 2 || left_type == 4) {
          left.convertConditionToIndex(this.columns);
          boolean isFirst = left.setSingleTableIdx(firstTableColumnNum);
          queryTable.setTable(left, isFirst);
          whereItem = new MultipleConditionItem(right);
        }
        if (right_type == 1 || right_type == 2 || right_type == 4) {
          right.convertConditionToIndex(this.columns);
          boolean isFirst = right.setSingleTableIdx(firstTableColumnNum);
          queryTable.setTable(right, isFirst);
          if (whereItem.getRoot().isLeaf()) {
            whereItem = null;
            no_where_left = true;
          }
          else whereItem = new MultipleConditionItem(left);
        }
      } else {
        queryTable = new QueryTable(tables);
      }
    } else {
      if (tableNum == 2) no_where_left = true;
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
            if (c1.getColumnName().equals(c2.getColumnName())) {
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
    if (no_where_left && !retain_left && !retain_right && whereItem != null && whereItem.getRoot().isLeaf()) {
      // Node<ConditionItem> root = whereItem.getRoot();
      ConditionItem tmp = whereItem.getRoot().getValue();
      whereItem.setColumn(this.columns);
      int primary = tmp.getPrimaryIndexTable(firstTableColumnNum,
              tables.get(0).primaryIndex, tables.get(1).primaryIndex);
      if (primary < 0) {
        res = queryTable.traverse(whereItem, false, false);
      } else {
        whereItem = null;
        boolean t1 = primary < firstTableColumnNum;
        if (tmp.getIdx1() == primary) {
          int idx = tmp.getIdx2();
          if (idx >= firstTableColumnNum) {
            if (t1) {
              idx -= firstTableColumnNum;
            } else {
              idx = -1;
            }
          }
          res = queryTable.traverseSmart(null, t1, idx);
        } else {
          int idx = tmp.getIdx1();
          if (idx >= firstTableColumnNum) {
            if (!t1) {
              idx -= firstTableColumnNum;
            } else {
              idx = -1;
            }
          }
          res = queryTable.traverseSmart(null, t1, idx);
        }
      }
    } else {
      if (whereItem != null) {
        whereItem.setColumn(this.columns);
      }
      res = queryTable.traverse(whereItem, retain_left, retain_right);
    }

    // 排序（如需要）
    sortArray(res, orderByItem.getOrder());

    // select选择子项
    this.queryRes = columnToData(this.selectContentItem.getSelectContent());
    for (QueryColumnPlusData qc: this.queryRes) {
      qc.generateTitle();
      qc.generateData(res);
    }

    // 转换为行的列表
    finalTable = new ArrayList<>();
    columnTitles = new ArrayList<>();

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
      boolean flag = false;
      for (QueryColumn column: columns) {
        if (column.compareTo(c)) {
          indices.add(i);
          flag = true;
          break;
        }
        i++;
      }
      if (!flag) throw new WrongOrderByColumnException();
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
                if (!tableName.equals(fromItem.getTableNameA())) {
                  throw new WrongTableNameException();
                }
                int i = 0;
                for (QueryColumn qc: this.columns) {
                  res.add(new QueryColumnPlusData(qc.getFullColumnName(), i, qc.getType()));
                  i++;
                }
              } else {
                if (tableName.equals(fromItem.getTableNameA())) {
                  int t1_column_count = metaInfos.get(0).getColumnNum();
                  for (int i = 0; i < t1_column_count; i++) {
                    QueryColumn tmp = this.columns.get(i);
                    res.add(new QueryColumnPlusData(tmp.getFullColumnName(), i, tmp.getType()));
                  }
                } else if (tableName.equals(fromItem.getTableNameB())) {
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
            res.add(new QueryColumnPlusData(columnName, idx, this.columns.get(idx).getType()));
          }
          break;
        }
        case 2: {
          String tableName = c.getTableName();
          String columnName = c.getColumnName();
          int idx = findIndex(tableName, columnName);
          res.add(new QueryColumnPlusData(c.getConstNum1(), columnName, c.getOp(), idx, this.columns.get(idx).getType()));
          break;
        }
        case 3: {
          String tableName = c.getTableName();
          String columnName = c.getColumnName();
          int idx = findIndex(tableName, columnName);
          res.add(new QueryColumnPlusData(columnName, c.getConstNum2(), c.getOp(), idx, this.columns.get(idx).getType()));
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
          res.add(new QueryColumnPlusData(columnName, c.getAggregateFun(), idx, this.columns.get(idx).getType()));
          break;
        }
      }
    }
    return res;
  }

  public List<List<String>> getFinalTable() {
    return finalTable;
  }

  public List<String> getColumnTitles() {
    return columnTitles;
  }
}
