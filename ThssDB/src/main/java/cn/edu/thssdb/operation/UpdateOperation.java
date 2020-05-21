package cn.edu.thssdb.operation;

import cn.edu.thssdb.exception.TableNotExistException;
import cn.edu.thssdb.exception.WrongUpdateException;
import cn.edu.thssdb.parser.item.LiteralValueItem;
import cn.edu.thssdb.parser.item.MultipleConditionItem;
import cn.edu.thssdb.query.QueryColumn;
import cn.edu.thssdb.schema.*;
import cn.edu.thssdb.type.ColumnType;

import java.util.ArrayList;
import java.util.Iterator;

public class UpdateOperation extends BaseOperation {
  private String tableName;
  private String columnName;
  private LiteralValueItem literalValueItem;
  private MultipleConditionItem whereItem = null;

  private Table table = null;

  final static String wrongColumnName = "Exception: wrong update operation ( no such column )!";
  final static String wrongColumnType = "Exception: wrong update operation ( type unmatched )!";
  final static String columnNotNull = "Exception: wrong update operation ( this column cannot be null )!";//列数与值数不匹配

  /**
   * [method] 构造方法
   */

  public UpdateOperation(String tableName, String columnNmae, LiteralValueItem literalValueItem) {
    this.tableName = tableName;
    this.columnName = columnNmae;
    this.literalValueItem = literalValueItem;
  }

  public UpdateOperation(String tableName, String columnName, LiteralValueItem literalValueItem, MultipleConditionItem multipleConditionItem) {
    this.tableName = tableName;
    this.columnName = columnName;
    this.literalValueItem = literalValueItem;
    this.whereItem = multipleConditionItem;
  }

  /**
     * [method] 执行操作
     */
  public void exec() {
    Manager manager = Manager.getInstance();
    Database database = manager.getDatabaseByName(manager.getCurrentDatabaseName());

    table = database.get(tableName);
    if(table==null){
      throw new TableNotExistException();
    }

    ArrayList<Column> columns = table.getColumns();
    Column columnToUpdate = null;
    for(Column column:columns){
      if(column.getName().equals(columnName)){
        columnToUpdate = column;
        break;
      }
    }
    if(columnToUpdate == null){
      throw new WrongUpdateException(wrongColumnName);
    }

    LiteralValueItem.Type itemType = literalValueItem.getType();
    ColumnType columnType = columnToUpdate.getType();
    String itemString = literalValueItem.getString();
    Comparable valueToUpdate;

    if(itemType==LiteralValueItem.Type.INT_OR_LONG){
      if(columnType==ColumnType.INT){
        valueToUpdate = Integer.valueOf(itemString);
      } else if(columnType==ColumnType.LONG){
        valueToUpdate = Long.valueOf(itemString);
      } else {
        throw new WrongUpdateException(wrongColumnType);
      }
    } else if(itemType==LiteralValueItem.Type.FLOAT_OR_DOUBLE){
      if(columnType==ColumnType.DOUBLE){
        valueToUpdate = Double.valueOf(itemString);
      } else if(columnType==ColumnType.FLOAT){
        valueToUpdate = Float.valueOf(itemString);
      } else {
        throw new WrongUpdateException(wrongColumnType);
      }
    } else if(columnType==ColumnType.STRING){
      if(columnType==ColumnType.STRING){
        valueToUpdate = itemString;
      } else {
        throw new WrongUpdateException(wrongColumnType);
      }
    } else {
      if(columnToUpdate.isNotNull()){
        throw new WrongUpdateException(columnNotNull);
      }
      valueToUpdate = null;
    }

    Iterator<Row> rowIterator = table.iterator();
    if(whereItem == null){
      while (rowIterator.hasNext()){
        Row oldRow = rowIterator.next();
        table.update(oldRow,getNewRow(oldRow,valueToUpdate));
      }
    } else {
      ArrayList<QueryColumn> queryColumns = new ArrayList<>();
      for(Column column:columns){
        queryColumns.add(new QueryColumn(column,tableName));
      }
      whereItem.setColumn(queryColumns);
      while (rowIterator.hasNext()){
        Row oldRow = rowIterator.next();
        if(whereItem.getTreeValue(oldRow).getValue()){
          table.update(oldRow,getNewRow(oldRow,valueToUpdate));
        }
      }
    }

  }

  private Row getNewRow(Row oldRow, Comparable valueToUpdate) {
    ArrayList<Entry> entries = new ArrayList<>();
    for (int index = 0; index < table.getColumns().size(); index++) {
      if (table.getColumns().get(index).getName().equals(columnName)) {
        entries.add(new Entry(valueToUpdate));
      } else {
        entries.add(new Entry(oldRow.getEntries().get(index)));
      }
    }
    Row newRow = new Row(entries);
    return newRow;
  }


}
