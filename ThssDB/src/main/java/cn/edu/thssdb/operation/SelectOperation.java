package cn.edu.thssdb.operation;

import cn.edu.thssdb.exception.DatabaseNotExistException;
import cn.edu.thssdb.exception.TableNotExistException;
import cn.edu.thssdb.parser.SQLParser;
import cn.edu.thssdb.parser.item.*;
import cn.edu.thssdb.query.QueryResult;
import cn.edu.thssdb.schema.Database;
import cn.edu.thssdb.schema.Manager;
import cn.edu.thssdb.schema.Table;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectOperation extends BaseOperation {
  private SelectContentItem selectContentItem;
  private FromItem fromItem;
  private MultipleConditionItem whereItem;
  private OrderByItem orderByItem;
  private String stmt;

  List<List<String>> data = new ArrayList<>();
  List<String> columns = new ArrayList<>();

  /**
   * [method] 构造方法
   */
  public SelectOperation(SelectContentItem selectContentItem, FromItem fromItem,
                         MultipleConditionItem whereItem, OrderByItem orderByItem, String stmt) {
    this.selectContentItem = selectContentItem;
    this.fromItem = fromItem;
    this.whereItem = whereItem;
    this.orderByItem = orderByItem;
    this.stmt = stmt;
  }


  /**
   * [method] 执行操作
   */
  public void exec() {
//    Manager manager = Manager.getInstance();
//    Database database = manager.getDatabaseByName(manager.getCurrentDatabaseName());
    if (database==null){
      throw new DatabaseNotExistException();
    }
    ArrayList<Table> tables = new ArrayList<>();
    Table table1 = database.get(fromItem.getTableNameA());
    if (table1 == null){
      throw new TableNotExistException();
    } else {
      tables.add(table1);
    }
    if (fromItem.getTableNameB() != null) {
      Table table2 = database.get(fromItem.getTableNameB());
      if (table2 == null){
        throw new TableNotExistException();
      } else {
        tables.add(table2);
      }
    }
    QueryResult queryResult = new QueryResult(selectContentItem, fromItem, whereItem, orderByItem, tables);

    queryResult.process();

    data = queryResult.getFinalTable();
    columns = queryResult.getColumnTitles();
  }


  @Override
  public ArrayList<String> getTableName() {
    return new ArrayList<>(Arrays.asList(fromItem.getTableNameA(),fromItem.getTableNameB()));
  }

  public List<List<String>> getData() {
    return data;
  }

  public List<String> getColumns() {
    return columns;
  }

  public String getStmt() {
    return stmt;
  }
}
