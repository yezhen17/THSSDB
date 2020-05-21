package cn.edu.thssdb.operation;

import cn.edu.thssdb.exception.TableNotExistException;
import cn.edu.thssdb.parser.SQLParser;
import cn.edu.thssdb.parser.item.*;
import cn.edu.thssdb.query.QueryResult;
import cn.edu.thssdb.schema.Database;
import cn.edu.thssdb.schema.Manager;
import cn.edu.thssdb.schema.Table;

import java.util.ArrayList;

public class SelectOperation extends BaseOperation {
  private SelectContentItem selectContentItem;
  private FromItem fromItem;
  private MultipleConditionItem whereItem;
  private OrderByItem orderByItem;

  /**
   * [method] 构造方法
   */
  public SelectOperation(SelectContentItem selectContentItem, FromItem fromItem,
                         MultipleConditionItem whereItem, OrderByItem orderByItem) {
    this.selectContentItem = selectContentItem;
    this.fromItem = fromItem;
    this.whereItem = whereItem;
    this.orderByItem = orderByItem;
  }

  /**
   * [method] 执行操作
   */
  public void exec() {
    Manager manager = Manager.getInstance();
    Database database = manager.getDatabaseByName(manager.getCurrentDatabaseName());

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

    // todo return queryResult.process()
  }
}
