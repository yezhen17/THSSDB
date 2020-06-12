package cn.edu.thssdb.operation;

import cn.edu.thssdb.exception.DatabaseNotExistException;
import cn.edu.thssdb.exception.TableNotExistException;
import cn.edu.thssdb.parser.item.TypeItem;
import cn.edu.thssdb.schema.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class AlterTableOperation extends BaseOperation {
  private String tableName;      // 表名称
  private String columnName;
  private int type;
  private TypeItem columnType;
  private Table table;
  private String stmt;

  /**
   * [method] 构造方法
   */
  public AlterTableOperation(String tableName, String columnName, int type, TypeItem columnType, String stmt) {
    this.tableName = tableName;
    this.columnName = columnName;
    this.type = type;
    this.columnType = columnType;
    this.stmt = stmt;
  }

  /**
   * [method] 执行操作
   */
  public void exec() {

    if (database==null){
      throw new DatabaseNotExistException();
    }

    table = database.get(tableName);
    if (table == null) {
      throw new TableNotExistException();
    }

    if (type == 0) {
      table.addColumn(columnName, columnType.getColumnType(), columnType.getStrLen());
    } else if (type == 1) {
      table.dropColumn(columnName);
    } else {
      table.alterColumn(columnName, columnType.getColumnType(), columnType.getStrLen());
    }
  }

  @Override
  public ArrayList<String> getTableName() {
    return new ArrayList<>(Arrays.asList(this.tableName));
  }

  @Override
  public LinkedList<String> getLog() {
    return new LinkedList<>(Arrays.asList(stmt));
  }
}
