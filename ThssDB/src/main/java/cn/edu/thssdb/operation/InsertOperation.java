package cn.edu.thssdb.operation;
import cn.edu.thssdb.exception.DatabaseNotExistException;
import cn.edu.thssdb.exception.DuplicateKeyException;
import cn.edu.thssdb.exception.TableNotExistException;
import cn.edu.thssdb.exception.WrongInsertException;
import cn.edu.thssdb.parser.item.LiteralValueItem;
import cn.edu.thssdb.schema.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

public class InsertOperation extends BaseOperation {

  private String tableName;
  private ArrayList<String> columnNames = null;
  private ArrayList<ArrayList<LiteralValueItem>> values;
  private ArrayList<Row> rowsHasInsert;
  private ArrayList<Row> rowsToInsert;

  private Table table;

  final static String wrongColumnNum = "Exception: wrong insert operation (columns unmatched)!";//列数不匹配
  final static String wrongColumnType = "Exception: wrong insert operation (type unmatched)!";//类型不匹配
  final static String wrongValueNum = "Exception: wrong insert operation (number of columns and values unmatched)!";//列数与值数不匹配
  final static String duplicateValueType = "Exception: wrong insert operation (duplicate name of columns)!";//列名重复
  final static String wrongColumnName = "Exception: wrong insert operation (wrong column name)!";//属性名不在列定义中
  final static String duplicateKey = "Exception: wrong insert operation (insertion causes duplicate key)!";//主键重复
  final static String wrongstringlength = "Exception: wrong insert operation (string exceeds length limit)!";//字符串过长



  /**
   * [method] 构造方法
   */

  public InsertOperation(String tableName, ArrayList<ArrayList<LiteralValueItem>> values) {
    this.tableName = tableName;
    this.values = values;
    rowsHasInsert = new ArrayList<>();
    rowsToInsert = new ArrayList<>();
  }

  public InsertOperation(String tableName, ArrayList<String> columnNames, ArrayList<ArrayList<LiteralValueItem>> values) {
    this.tableName = tableName;
    this.columnNames = columnNames;
    this.values = values;
    rowsHasInsert = new ArrayList<>();
    rowsToInsert = new ArrayList<>();
  }


  /**
   * [method] 执行操作
   */
  public void exec() {

    if (database == null) {
      throw new DatabaseNotExistException();
    }

    table = database.get(tableName);
    if (table == null) {
      throw new TableNotExistException();
    }

    ArrayList<Column> columns = table.getColumns();
    int primaryKeyIndex = table.primaryIndex;
    String primaryKey = columns.get(primaryKeyIndex).getName();


    if (columnNames == null) {

      for (ArrayList<LiteralValueItem> value : values) {

        if (value.size() != columns.size()) {
          throw new WrongInsertException(wrongColumnNum);
        }

        ArrayList<Entry> entries = new ArrayList<>();

        // 类型检查
        for (int i = 0; i < columns.size(); i++) {
//          switch (columns.get(i).getType()){
//            case INT:
//              if(value.get(i).getType()==LiteralValueItem.Type.INT_OR_LONG){
//                try {
//                  int tmp = Integer.parseInt(value.get(i).getString());
//                  if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(tmp))){
//                    throw new DuplicateKeyException();
//                  }
//                  entries.add(new Entry(tmp));
//                } catch (NumberFormatException e){
//                  throw e;
//                }
//              }
//              else if(value.get(i).getType()==LiteralValueItem.Type.NULL){
//                if(columns.get(i).isNotNull()){
//                  throw new WrongInsertException("Exception: wrong insert operation ( "+columns.get(i).getName()+" cannot be null)");
//                }
//                entries.add((new Entry(null)));
//              }
//              else {
//                throw new WrongInsertException(wrongColumnType);
//              }
//              break;
//            case LONG:
//              if(value.get(i).getType()==LiteralValueItem.Type.INT_OR_LONG){
//                try {
//                  long tmp = Long.parseLong(value.get(i).getString());
//                  if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(tmp))){
//                    throw new DuplicateKeyException();
//                  }
//                  entries.add(new Entry(tmp));
//                } catch (NumberFormatException e){
//                  throw e;
//                }
//              }
//              else if(value.get(i).getType()==LiteralValueItem.Type.NULL){
//                if(columns.get(i).isNotNull()){
//                  throw new WrongInsertException("Exception: wrong insert operation ( "+columns.get(i).getName()+" cannot be null)");
//                }
//                entries.add((new Entry(null)));
//              }
//              else {
//                throw new WrongInsertException(wrongColumnType);
//              }
//              break;
//            case DOUBLE:
//              if(value.get(i).getType()==LiteralValueItem.Type.FLOAT_OR_DOUBLE){
//                try {
//                  double tmp = Double.parseDouble(value.get(i).getString());
//                  if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(tmp))){
//                    throw new DuplicateKeyException();
//                  }
//                  entries.add(new Entry(tmp));
//                } catch (NumberFormatException e){
//                  throw e;
//                }
//              }
//              else if(value.get(i).getType()==LiteralValueItem.Type.NULL){
//                if(columns.get(i).isNotNull()){
//                  throw new WrongInsertException("Exception: wrong insert operation ( "+columns.get(i).getName()+" cannot be null)");
//                }
//                entries.add((new Entry(null)));
//              }
//              else {
//                throw new WrongInsertException(wrongColumnType);
//              }
//              break;
//            case FLOAT:
//              if(value.get(i).getType()==LiteralValueItem.Type.FLOAT_OR_DOUBLE){
//                try {
//                  float tmp = Float.parseFloat(value.get(i).getString());
//                  if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(tmp))){
//                    throw new DuplicateKeyException();
//                  }
//                  entries.add(new Entry(tmp));
//                } catch (NumberFormatException e){
//                  throw e;
//                }
//              }
//              else if(value.get(i).getType()==LiteralValueItem.Type.NULL){
//                if(columns.get(i).isNotNull()){
//                  throw new WrongInsertException("Exception: wrong insert operation ( "+columns.get(i).getName()+" cannot be null)");
//                }
//                entries.add((new Entry(null)));
//              }
//              else {
//                throw new WrongInsertException(wrongColumnType);
//              }
//              break;
//            case STRING:
//              if(value.get(i).getType()==LiteralValueItem.Type.STRING){
//                if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(value.get(i).getString()))){
//                  throw new DuplicateKeyException();
//                }
//                entries.add(new Entry(value.get(i).getString()));
//              }
//              else if(value.get(i).getType()==LiteralValueItem.Type.NULL){
//                if(columns.get(i).isNotNull()){
//                  throw new WrongInsertException("Exception: wrong insert operation ( "+columns.get(i).getName()+" cannot be null)");
//                }
//                entries.add((new Entry(null)));
//              }
//              else {
//                throw new WrongInsertException(wrongColumnType);
//              }
//              break;
//          }
          entries = matchType(columns.get(i), value.get(i), primaryKey, entries);
        }


        Row newRow = new Row(entries);


        // 主键检查
        if (table.index.contains(newRow.getEntries().get(table.primaryIndex))) {
          throw new WrongInsertException(duplicateKey);
        } else {
          rowsToInsert.add(newRow);
        }

      }


    } else {
      if (columnNames.size() > columns.size()) {
        throw new WrongInsertException(wrongColumnNum);
      }
      for (ArrayList<LiteralValueItem> items : values) {
        if (items.size() != columnNames.size()) {
          throw new WrongInsertException(wrongValueNum);
        }
      }

      // 列名重复或不存在
      for (int i = 0; i < columnNames.size(); i++) {
        for (int j = 0; j < i; j++) {
          if (columnNames.get(i).equals(columnNames.get(j))) {
            throw new WrongInsertException(duplicateValueType);
          }
        }
        boolean hasMatched = false;
        for (int j = 0; j < columns.size(); j++) {
          if (columnNames.get(i).equals(table.getColumns().get(j).getName())) {
            hasMatched = true;
            break;
          }
        }
        if (hasMatched == false) {
          throw new WrongInsertException(wrongColumnName);
        }
      }

      for (ArrayList<LiteralValueItem> value : values) {

        ArrayList<Entry> entries = new ArrayList<>();

        for (int i = 0; i < columns.size(); i++) {
          boolean hasMatched = false;

          for (int j = 0; j < columnNames.size(); j++) {
            if (columns.get(i).getName().equals(columnNames.get(j))) {
//              switch (columns.get(i).getType()){
//                case INT:
//                  if(value.get(j).getType()==LiteralValueItem.Type.INT_OR_LONG){
//                    try {
//                      int tmp = Integer.parseInt(value.get(j).getString());
//                      if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(tmp))){
//                        throw new DuplicateKeyException();
//                      }
//                      entries.add(new Entry(tmp));
//                    } catch (NumberFormatException e){
//                      throw e;
//                    }
//                  } else if(value.get(j).getType()==LiteralValueItem.Type.NULL){
//                    if(columns.get(i).isNotNull()){
//                      throw new WrongInsertException("Exception: wrong insert operation ( "+columns.get(i).getName()+" cannot be null)");
//                    }
//                    entries.add((new Entry(null)));
//                  }
//                  else {
//                    throw new WrongInsertException(wrongColumnType);
//                  }
//                  break;
//                case LONG:
//                  if(value.get(j).getType()==LiteralValueItem.Type.INT_OR_LONG){
//                    try {
//                      long tmp = Long.parseLong(value.get(j).getString());
//                      if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(tmp))){
//                        throw new DuplicateKeyException();
//                      }
//                      entries.add(new Entry(tmp));
//                    } catch (NumberFormatException e){
//                      throw e;
//                    }
//                  } else if(value.get(j).getType()==LiteralValueItem.Type.NULL){
//                    if(columns.get(i).isNotNull()){
//                      throw new WrongInsertException("Exception: wrong insert operation ( "+columns.get(i).getName()+" cannot be null)");
//                    }
//                    entries.add((new Entry(null)));
//                  }
//                  else {
//                    throw new WrongInsertException(wrongColumnType);
//                  }
//                  break;
//                case DOUBLE:
//                  if(value.get(j).getType()==LiteralValueItem.Type.FLOAT_OR_DOUBLE){
//                    try {
//                      double tmp = Double.parseDouble(value.get(j).getString());
//                      if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(tmp))){
//                        throw new DuplicateKeyException();
//                      }
//                      entries.add(new Entry(tmp));
//                    } catch (NumberFormatException e){
//                      throw e;
//                    }
//                  } else if(value.get(j).getType()==LiteralValueItem.Type.NULL){
//                    if(columns.get(i).isNotNull()){
//                      throw new WrongInsertException("Exception: wrong insert operation ( "+columns.get(i).getName()+" cannot be null)");
//                    }
//                    entries.add((new Entry(null)));
//                  }
//                  else {
//                    throw new WrongInsertException(wrongColumnType);
//                  }
//                  break;
//                case FLOAT:
//                  if(value.get(j).getType()==LiteralValueItem.Type.FLOAT_OR_DOUBLE){
//                    try {
//                      float tmp = Float.parseFloat(value.get(j).getString());
//                      if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(tmp))){
//                        throw new DuplicateKeyException();
//                      }
//                      entries.add(new Entry(tmp));
//                    } catch (NumberFormatException e){
//                      throw e;
//                    }
//                  } else if(value.get(j).getType()==LiteralValueItem.Type.NULL){
//                    if(columns.get(i).isNotNull()){
//                      throw new WrongInsertException("Exception: wrong insert operation ( "+columns.get(i).getName()+" cannot be null)");
//                    }
//                    entries.add((new Entry(null)));
//                  }
//                  else {
//                    throw new WrongInsertException(wrongColumnType);
//                  }
//                  break;
//                case STRING:
//                  if(value.get(j).getType()==LiteralValueItem.Type.STRING){
//                    if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(value.get(j).getString()))){
//                      throw new DuplicateKeyException();
//                    }
//                    entries.add(new Entry(value.get(j).getString()));
//                  } else if(value.get(j).getType()==LiteralValueItem.Type.NULL){
//                    if(columns.get(i).isNotNull()){
//                      throw new WrongInsertException("Exception: wrong insert operation ( "+columns.get(i).getName()+" cannot be null)");
//                    }
//                    entries.add((new Entry(null)));
//                  }
//                  else {
//                    throw new WrongInsertException(wrongColumnType);
//                  }
//                  break;
//              }
              entries = matchType(columns.get(i), value.get(j), primaryKey, entries);
              hasMatched = true;
              break;
            }
          }

          // 将没匹配到的列的值置为null
          if (hasMatched) {
            continue;
          } else {
            if (columns.get(i).isNotNull()) {
              throw new WrongInsertException("Exception: wrong insert operation ( column " + columns.get(i).getName() + " cannot be null )");
            } else {
              entries.add(new Entry(null));
            }
          }
        }

        Row newRow = new Row(entries);
        // 主键检查
        if (table.index.contains(newRow.getEntries().get(table.primaryIndex))) {
          throw new WrongInsertException(duplicateKey);
        } else {
          rowsToInsert.add(newRow);
        }

      }

    }
    insert();

  }

  /**
   * [method] 撤销操作
   */
  public void undo() {
    for (Row row : rowsHasInsert) {
      table.delete(row);
    }
  }

  /**
   * [method] 获取记录
   */
  public LinkedList<String> getLog() {
    LinkedList<String> log = new LinkedList<>();
    for (Row row : rowsHasInsert) {
      log.add("INSERT " + tableName + " " + row.toString());
    }
    return log;
  }


  /**
   * [method] 确认无异常后插入
   */
  private void insert() {
    ArrayList<Entry> entries = new ArrayList<>();
    for (Row row : rowsToInsert) {
      entries.add(row.getEntries().get(table.primaryIndex));
    }
    HashSet<Entry> set = new HashSet<>(entries);
    if (set.size() != entries.size()) {
      throw new WrongInsertException(duplicateKey);
    }

    for (Row row : rowsToInsert) {
      table.insert(row);
      rowsHasInsert.add(row);
    }
    rowsToInsert.clear();

  }

  @Override
  public ArrayList<String> getTableName() {
    return new ArrayList<>(Arrays.asList(this.tableName));
  }

  private ArrayList<Entry> matchType(Column column, LiteralValueItem value, String primaryKey, ArrayList<Entry> entries) {
    LiteralValueItem.Type value_type = value.getType();
    switch (column.getType()) {
      case INT:
        if (value_type == LiteralValueItem.Type.INT_OR_LONG) {
          try {
            int tmp = Integer.parseInt(value.getString());
            if (column.getName().equals(primaryKey) && table.index.contains(new Entry(tmp))) {
              throw new DuplicateKeyException();
            }
            entries.add(new Entry(tmp));
          } catch (NumberFormatException e) {
            throw e;
          }
        } else if (value_type == LiteralValueItem.Type.NULL) {
          if (column.isNotNull()) {
            throw new WrongInsertException("Exception: wrong insert operation ( " + column.getName() + " cannot be null)");
          }
          entries.add((new Entry(null)));
        } else {
          throw new WrongInsertException(wrongColumnType);
        }
        break;
      case LONG:
        if (value_type == LiteralValueItem.Type.INT_OR_LONG) {
          try {
            long tmp = Long.parseLong(value.getString());
            if (column.getName().equals(primaryKey) && table.index.contains(new Entry(tmp))) {
              throw new DuplicateKeyException();
            }
            entries.add(new Entry(tmp));
          } catch (NumberFormatException e) {
            throw e;
          }
        } else if (value_type == LiteralValueItem.Type.NULL) {
          if (column.isNotNull()) {
            throw new WrongInsertException("Exception: wrong insert operation ( " + column.getName() + " cannot be null)");
          }
          entries.add((new Entry(null)));
        } else {
          throw new WrongInsertException(wrongColumnType);
        }
        break;
      case DOUBLE:
        if (value_type == LiteralValueItem.Type.FLOAT_OR_DOUBLE || value_type == LiteralValueItem.Type.INT_OR_LONG) {
          try {
            double tmp = Double.parseDouble(value.getString());
            if (column.getName().equals(primaryKey) && table.index.contains(new Entry(tmp))) {
              throw new DuplicateKeyException();
            }
            entries.add(new Entry(tmp));
          } catch (NumberFormatException e) {
            throw e;
          }
        } else if (value_type == LiteralValueItem.Type.NULL) {
          if (column.isNotNull()) {
            throw new WrongInsertException("Exception: wrong insert operation ( " + column.getName() + " cannot be null)");
          }
          entries.add((new Entry(null)));
        } else {
          throw new WrongInsertException(wrongColumnType);
        }
        break;
      case FLOAT:
        if (value_type == LiteralValueItem.Type.FLOAT_OR_DOUBLE || value_type == LiteralValueItem.Type.INT_OR_LONG) {
          try {
            float tmp = Float.parseFloat(value.getString());
            if (column.getName().equals(primaryKey) && table.index.contains(new Entry(tmp))) {
              throw new DuplicateKeyException();
            }
            entries.add(new Entry(tmp));
          } catch (NumberFormatException e) {
            throw e;
          }
        } else if (value_type == LiteralValueItem.Type.NULL) {
          if (column.isNotNull()) {
            throw new WrongInsertException("Exception: wrong insert operation ( " + column.getName() + " cannot be null)");
          }
          entries.add((new Entry(null)));
        } else {
          throw new WrongInsertException(wrongColumnType);
        }
        break;
      case STRING:
        if (value_type == LiteralValueItem.Type.STRING) {
          if (column.getName().equals(primaryKey) && table.index.contains(new Entry(value.getString()))) {
            throw new DuplicateKeyException();
          }
          if(value.getString().length()>column.getMaxLength()){
            throw new WrongInsertException(wrongstringlength);
          }
          entries.add(new Entry(value.getString()));
        } else if (value_type == LiteralValueItem.Type.NULL) {
          if (column.isNotNull()) {
            throw new WrongInsertException("Exception: wrong insert operation ( " + column.getName() + " cannot be null)");
          }
          entries.add((new Entry(null)));
        } else {
          throw new WrongInsertException(wrongColumnType);
        }
        break;
    }
    return entries;
  }

}

