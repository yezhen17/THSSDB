package cn.edu.thssdb.operation;

import cn.edu.thssdb.exception.*;
import cn.edu.thssdb.parser.item.LiteralValueItem;
import cn.edu.thssdb.parser.item.MultipleConditionItem;
import cn.edu.thssdb.query.QueryColumn;
import cn.edu.thssdb.schema.*;
import cn.edu.thssdb.type.ColumnType;
import javafx.util.Pair;

import java.util.*;

public class UpdateOperation extends BaseOperation {
  private String tableName;
  private String columnName;
  private LiteralValueItem literalValueItem;
  private MultipleConditionItem whereItem = null;
  private ArrayList<Pair<Row,Row>> rowsHasUpdate;
  private int columnIdxToUpdate;


  private Table table = null;

  final static String wrongColumnName = "Exception: wrong update operation ( no such column )!";
  final static String wrongColumnType = "Exception: wrong update operation ( type unmatched )!";
  final static String columnNotNull = "Exception: wrong update operation ( this column cannot be null )!";//列数与值数不匹配
  final static String duplicateKey = "Exception: wrong update operation ( update causes duplicate key )!";

  /**
   * [method] 构造方法
   */

  public UpdateOperation(String tableName, String columnNmae, LiteralValueItem literalValueItem) {
    this.tableName = tableName;
    this.columnName = columnNmae;
    this.literalValueItem = literalValueItem;
    rowsHasUpdate = new ArrayList<>();
  }

  public UpdateOperation(String tableName, String columnName, LiteralValueItem literalValueItem, MultipleConditionItem multipleConditionItem) {
    this.tableName = tableName;
    this.columnName = columnName;
    this.literalValueItem = literalValueItem;
    this.whereItem = multipleConditionItem;
    rowsHasUpdate = new ArrayList<>();
  }

  /**
     * [method] 执行操作
     */
  public void exec() {


    table = database.get(tableName);
    if(table==null){
      throw new TableNotExistException();
    }

    ArrayList<Column> columns = table.getColumns();
    Column columnToUpdate = null;

    int primaryKeyIndex = -1;
    for(int i = 0; i < columns.size(); i++){
      if(columns.get(i).getName().equals(columnName)){
        columnToUpdate = columns.get(i);
        columnIdxToUpdate = i;
        if(columnToUpdate.isPrimary()){
          primaryKeyIndex = i;
        }
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

    if (itemType == LiteralValueItem.Type.INT_OR_LONG){
      if (columnType == ColumnType.INT){
        valueToUpdate = Integer.valueOf(itemString);
      } else if (columnType == ColumnType.LONG){
        valueToUpdate = Long.valueOf(itemString);
      } else if (columnType == ColumnType.DOUBLE){
        valueToUpdate = Double.valueOf(itemString);
      } else if (columnType == ColumnType.FLOAT){
        valueToUpdate = Float.valueOf(itemString);
      } else {
        throw new WrongUpdateException(wrongColumnType);
      }
    } else if (itemType == LiteralValueItem.Type.FLOAT_OR_DOUBLE){
      if (columnType == ColumnType.DOUBLE){
        valueToUpdate = Double.valueOf(itemString);
      } else if (columnType == ColumnType.FLOAT){
        valueToUpdate = Float.valueOf(itemString);
      } else {
        throw new WrongUpdateException(wrongColumnType);
      }
    } else if(columnType == ColumnType.STRING){
      if(columnType == ColumnType.STRING){
        valueToUpdate = itemString;
      } else {
        throw new WrongUpdateException(wrongColumnType);
      }
    } else {
      if (columnToUpdate.isNotNull()){
        throw new WrongUpdateException(columnNotNull);
      }
      valueToUpdate = null;
    }

    Iterator<Row> rowIterator = table.iterator();
    if (whereItem == null){
      if (primaryKeyIndex != -1) {
        if (table.index.size() > 1) {
          throw new WrongUpdateException(duplicateKey);
        } else {
          if (rowIterator.hasNext()) {
            Row oldRow = rowIterator.next();
            Row newRow = getNewRow(oldRow, valueToUpdate);
            if (newRow != null) {
              table.update(oldRow, newRow);
              rowsHasUpdate.add(new Pair<>(oldRow,newRow));
            }
          }
        }
//        while (rowIterator.hasNext()){
//          Row oldRow = rowIterator.next();
//          Row newRow = getNewRow(oldRow, valueToUpdate);
//          if(table.index.contains(newRow.getEntries().get(primaryKeyIndex))){
//            undo();
//            throw new WrongUpdateException(duplicateKey);
//          }
//          else {
//            table.update(oldRow,newRow);
//            rowsHasUpdate.add(new Pair<>(oldRow,newRow));
//          }
//        }
      } else {
        while (rowIterator.hasNext()) {
          Row oldRow = rowIterator.next();
          Row newRow = getNewRow(oldRow, valueToUpdate);
          if (newRow == null) {
            continue;
          }
          rowsHasUpdate.add(new Pair<>(oldRow,newRow));
        }
        table.updateAll(columnIdxToUpdate, valueToUpdate);
//        while (rowIterator.hasNext()){
//          Row oldRow = rowIterator.next();
//          Row newRow = getNewRow(oldRow, valueToUpdate);
//          else {
//            table.update(oldRow,newRow);
//            rowsHasUpdate.add(new Pair<>(oldRow,newRow));
//          }
//        }
      }
    }
    else {
      ArrayList<QueryColumn> queryColumns = new ArrayList<>();
      for (Column column: columns) {
        queryColumns.add(new QueryColumn(column, tableName));
      }
      whereItem.setColumn(queryColumns);
      if (primaryKeyIndex != -1) {
        Entry entryToUpdate = new Entry(valueToUpdate);
        while (rowIterator.hasNext()) {
          Row oldRow = rowIterator.next();
          if (whereItem.getTreeValue(oldRow).getValue()) {
            Row newRow = getNewRow(oldRow, valueToUpdate);
            if (newRow == null) {
              continue;
            }
            if(table.index.contains(entryToUpdate)){
              undo();
              throw new WrongUpdateException(duplicateKey);
            }
            else {
              table.update(oldRow,newRow);
              rowsHasUpdate.add(new Pair<>(oldRow,newRow));
            }
          }
        }
      } else {
        while (rowIterator.hasNext()) {
          Row oldRow = rowIterator.next();
          if (whereItem.getTreeValue(oldRow).getValue()) {
            Row newRow = getNewRow(oldRow, valueToUpdate);
            if (newRow == null) {
              continue;
            }
            table.updateNoRemove(oldRow, newRow);
            rowsHasUpdate.add(new Pair<>(oldRow,newRow));
          }
        }
      }
    }
  }

  /**
   * [method] 撤销操作
   */
  public void undo(){
    for(int i=rowsHasUpdate.size()-1;i>=0;i--){
      table.update(rowsHasUpdate.get(i).getValue(),rowsHasUpdate.get(i).getKey());
    }
  }



  private Row getNewRow(Row oldRow, Comparable valueToUpdate) {

    ArrayList<Entry> entries = new ArrayList<>();
    ArrayList<Entry> old_entries = oldRow.getEntries();
    for (Entry e: old_entries) {
      entries.add(new Entry(e.value));
    }
    Entry tmp = new Entry(valueToUpdate);
    if (entries.get(columnIdxToUpdate).compareTo(tmp) == 0) {
      return null;
    } else {
      entries.set(columnIdxToUpdate, new Entry(valueToUpdate));
    }


//    for (int index = 0; index < table.getColumns().size(); index++) {
//      if (table.getColumns().get(index).getName().equals(columnName)) {
//        entries.add(new Entry(valueToUpdate));
//      } else {
//        entries.add(new Entry(oldRow.getEntries().get(index).value));
//      }
//    }
    Row newRow = new Row(entries);
    return newRow;
  }

  /**
   * [method] 获取记录
   */
  public LinkedList<String> getLog(){
    LinkedList<String> deleteLog = new LinkedList<>();
    LinkedList<String> insertLog = new LinkedList<>();
    int primaryIndex = table.primaryIndex;
    for(Pair<Row, Row> pair: rowsHasUpdate){
      deleteLog.add("DELETE " + tableName + " " + pair.getValue().getEntries().get(primaryIndex).toString());
      insertLog.add("INSERT " + tableName + " " + pair.getKey().toString());
    }
    deleteLog.addAll(insertLog);
    return deleteLog;
  }

  @Override
  public ArrayList<String> getTableName() {
    return new ArrayList<String>(Arrays.asList(this.tableName));
  }



}
