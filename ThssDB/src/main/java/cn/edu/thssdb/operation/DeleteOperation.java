package cn.edu.thssdb.operation;

import cn.edu.thssdb.exception.DatabaseNotExistException;
import cn.edu.thssdb.exception.TableNotExistException;
import cn.edu.thssdb.parser.item.MultipleConditionItem;
import cn.edu.thssdb.query.QueryColumn;
import cn.edu.thssdb.schema.*;

import javax.management.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class DeleteOperation extends BaseOperation {
  private String tableName;
  private MultipleConditionItem whereItem = null; // null则删除所有
  private ArrayList<Row> rowsHasDelete;
  private Table table;
  private int primaryIndex;

  /**
   * [method] 构造方法
   */
  public DeleteOperation(String tableName) {
    this.tableName = tableName;
    rowsHasDelete = new ArrayList<>();
  }

  public DeleteOperation(String tableName, MultipleConditionItem whereItem) {
    this.tableName = tableName;
    this.whereItem = whereItem;
    rowsHasDelete = new ArrayList<>();
  }

  /**
   * [method] 执行操作
   */
  public void exec() {
    if (database == null){
      throw new DatabaseNotExistException();
    }
    table = database.get(tableName);
    if (table == null) {
      throw new TableNotExistException();
    } else {
      Iterator<Row> rowIterator = table.iterator();
      if (whereItem == null) {
        while(rowIterator.hasNext()){
          Row row = rowIterator.next();
          rowsHasDelete.add(row);
        }
        table.delete();
      } else {
        ArrayList<Column> columns = table.getColumns();
        ArrayList<QueryColumn> queryColumns = new ArrayList<>();
        for (Column column : columns) {
          queryColumns.add(new QueryColumn(column, tableName));
        }
        whereItem.setColumn(queryColumns);
        while (rowIterator.hasNext()) {
          Row row = rowIterator.next();
          if (whereItem.getTreeValue(row).getValue()) {
            table.delete(row);
            rowsHasDelete.add(row);
          }
        }
      }
    }
  }

  /**
   * [method] 执行操作
   */
  public void undo(){
    for(Row row:rowsHasDelete){
      table.insert(row);
    }
  }

  /**
   * [method] 获取记录
   */
  public LinkedList<String> getLog(){
    LinkedList<String> log = new LinkedList<>();
    primaryIndex = table.primaryIndex;
    for(Row row: rowsHasDelete){
      log.add("DELETE " + tableName + " " + row.getEntries().get(primaryIndex).toString());
    }
    return log;
  }

  @Override
  public ArrayList<String> getTableName() {
    return new ArrayList<>(Arrays.asList(this.tableName));
  }

}
