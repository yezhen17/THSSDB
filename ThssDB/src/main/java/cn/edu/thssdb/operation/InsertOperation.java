package cn.edu.thssdb.operation;
import cn.edu.thssdb.exception.DatabaseNotExistException;
import cn.edu.thssdb.exception.DuplicateKeyException;
import cn.edu.thssdb.exception.TableNotExistException;
import cn.edu.thssdb.exception.WrongInsertException;
import cn.edu.thssdb.parser.item.LiteralValueItem;
import cn.edu.thssdb.schema.*;

import java.util.ArrayList;
import java.util.LinkedList;

public class InsertOperation extends BaseOperation {

  private String tableName;
  private ArrayList<String> columnNames=null;
  private ArrayList<ArrayList<LiteralValueItem>> values;
  private ArrayList<Row> rowsHasInsert;


  private Table table;

  final static String wrongColumnNum = "Exception: wrong insert operation (columns unmatched)!";//列数不匹配
  final static String wrongColumnType = "Exception: wrong insert operation (type unmatched)!";//类型不匹配
  final static String wrongValueNum = "Exception: wrong insert operation (number of columns and values unmatched)!";//列数与值数不匹配
  final static String duplicateValueType = "Exception: wrong insert operation (duplicate name of columns)!";//类型不匹配
  final static String wrongColumnName = "Exception: wrong insert operation (wrong column name)!";//属性名不在列定义中



  /**
   * [method] 构造方法
   */

  public InsertOperation(String tableName, ArrayList<ArrayList<LiteralValueItem>> values) {
    this.tableName = tableName;
    this.values = values;
    rowsHasInsert = new ArrayList<>();
  }

  public InsertOperation(String tableName, ArrayList<String> columnNames, ArrayList<ArrayList<LiteralValueItem>> values) {
    this.tableName = tableName;
    this.columnNames = columnNames;
    this.values = values;
    rowsHasInsert = new ArrayList<>();
  }


  /**
   * [method] 执行操作
   */
  public void exec() {

    if (database==null){
      throw new DatabaseNotExistException();
    }

    table = database.get(tableName);
    if(table==null){
      throw new TableNotExistException();
    }

    ArrayList<Column> columns = table.getColumns();
    int primaryKeyIndex = table.primaryIndex;
    String primaryKey = columns.get(primaryKeyIndex).getName();


    if(columnNames==null){

      for(ArrayList<LiteralValueItem> value:values){

        if(value.size()!=columns.size()){
          throw new WrongInsertException(wrongColumnNum);
        }

        ArrayList<Entry> entries = new ArrayList<>();

        // 类型检查
        for (int i=0;i<columns.size();i++){
          switch (columns.get(i).getType()){
            case INT:
              if(value.get(i).getType()==LiteralValueItem.Type.INT_OR_LONG){
                try {
                  int tmp = Integer.parseInt(value.get(i).getString());
                  if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(tmp))){
                    throw new DuplicateKeyException();
                  }
                  entries.add(new Entry(tmp));
                } catch (NumberFormatException e){
                  throw e;
                }
              }
              else if(value.get(i).getType()==LiteralValueItem.Type.NULL){
                if(columns.get(i).isNotNull()){
                  throw new WrongInsertException("Exception: wrong insert operation ( "+columns.get(i).getName()+" cannot be null)");
                }
                entries.add((new Entry(null)));
              }
              else {
                throw new WrongInsertException(wrongColumnType);
              }
              break;
            case LONG:
              if(value.get(i).getType()==LiteralValueItem.Type.INT_OR_LONG){
                try {
                  long tmp = Long.parseLong(value.get(i).getString());
                  if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(tmp))){
                    throw new DuplicateKeyException();
                  }
                  entries.add(new Entry(tmp));
                } catch (NumberFormatException e){
                  throw e;
                }
              }
              else if(value.get(i).getType()==LiteralValueItem.Type.NULL){
                if(columns.get(i).isNotNull()){
                  throw new WrongInsertException("Exception: wrong insert operation ( "+columns.get(i).getName()+" cannot be null)");
                }
                entries.add((new Entry(null)));
              }
              else {
                throw new WrongInsertException(wrongColumnType);
              }
              break;
            case DOUBLE:
              if(value.get(i).getType()==LiteralValueItem.Type.FLOAT_OR_DOUBLE){
                try {
                  double tmp = Double.parseDouble(value.get(i).getString());
                  if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(tmp))){
                    throw new DuplicateKeyException();
                  }
                  entries.add(new Entry(tmp));
                } catch (NumberFormatException e){
                  throw e;
                }
              }
              else if(value.get(i).getType()==LiteralValueItem.Type.NULL){
                if(columns.get(i).isNotNull()){
                  throw new WrongInsertException("Exception: wrong insert operation ( "+columns.get(i).getName()+" cannot be null)");
                }
                entries.add((new Entry(null)));
              }
              else {
                throw new WrongInsertException(wrongColumnType);
              }
              break;
            case FLOAT:
              if(value.get(i).getType()==LiteralValueItem.Type.FLOAT_OR_DOUBLE){
                try {
                  float tmp = Float.parseFloat(value.get(i).getString());
                  if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(tmp))){
                    throw new DuplicateKeyException();
                  }
                  entries.add(new Entry(tmp));
                } catch (NumberFormatException e){
                  throw e;
                }
              }
              else if(value.get(i).getType()==LiteralValueItem.Type.NULL){
                if(columns.get(i).isNotNull()){
                  throw new WrongInsertException("Exception: wrong insert operation ( "+columns.get(i).getName()+" cannot be null)");
                }
                entries.add((new Entry(null)));
              }
              else {
                throw new WrongInsertException(wrongColumnType);
              }
              break;
            case STRING:
              if(value.get(i).getType()==LiteralValueItem.Type.STRING){
                if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(value.get(i).getString()))){
                  throw new DuplicateKeyException();
                }
                entries.add(new Entry(value.get(i).getString()));
              }
              else if(value.get(i).getType()==LiteralValueItem.Type.NULL){
                if(columns.get(i).isNotNull()){
                  throw new WrongInsertException("Exception: wrong insert operation ( "+columns.get(i).getName()+" cannot be null)");
                }
                entries.add((new Entry(null)));
              }
              else {
                throw new WrongInsertException(wrongColumnType);
              }
              break;
          }
        }

        Row newRow = new Row(entries);
        table.insert(newRow);
        rowsHasInsert.add(newRow);

      }
    }
    else {
      if(columnNames.size()>columns.size()){
        throw new WrongInsertException(wrongColumnNum);
      }
      for(ArrayList<LiteralValueItem> items:values){
        if(items.size()!=columnNames.size()){
          throw new WrongInsertException(wrongValueNum);
        }
      }

      // 列名重复或不存在
      for(int i=0;i<columnNames.size();i++){
        for(int j=0;j<i;j++){
          if(columnNames.get(i).equals(columnNames.get(j))){
            throw new WrongInsertException(duplicateValueType);
          }
        }
        boolean hasMatched = false;
        for(int j=0;j<columns.size();j++){
          if(columnNames.get(i).equals(table.getColumns().get(j).getName())){
            hasMatched = true;
            break;
          }
        }
        if(hasMatched==false){
          throw new WrongInsertException(wrongColumnName);
        }
      }

      for(ArrayList<LiteralValueItem> value:values){

        ArrayList<Entry> entries = new ArrayList<>();

        for(int i=0;i<columns.size();i++){
          boolean hasMatched = false;
          for(int j=0;j<columnNames.size();j++){
            if(columns.get(i).getName().equals(columnNames.get(j))){
              switch (columns.get(i).getType()){
                case INT:
                  if(value.get(j).getType()==LiteralValueItem.Type.INT_OR_LONG){
                    try {
                      int tmp = Integer.parseInt(value.get(j).getString());
                      if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(tmp))){
                        throw new DuplicateKeyException();
                      }
                      entries.add(new Entry(tmp));
                    } catch (NumberFormatException e){
                      throw e;
                    }
                  } else if(value.get(j).getType()==LiteralValueItem.Type.NULL){
                    if(columns.get(i).isNotNull()){
                      throw new WrongInsertException("Exception: wrong insert operation ( "+columns.get(i).getName()+" cannot be null)");
                    }
                    entries.add((new Entry(null)));
                  }
                  else {
                    throw new WrongInsertException(wrongColumnType);
                  }
                  break;
                case LONG:
                  if(value.get(j).getType()==LiteralValueItem.Type.INT_OR_LONG){
                    try {
                      long tmp = Long.parseLong(value.get(j).getString());
                      if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(tmp))){
                        throw new DuplicateKeyException();
                      }
                      entries.add(new Entry(tmp));
                    } catch (NumberFormatException e){
                      throw e;
                    }
                  } else if(value.get(j).getType()==LiteralValueItem.Type.NULL){
                    if(columns.get(i).isNotNull()){
                      throw new WrongInsertException("Exception: wrong insert operation ( "+columns.get(i).getName()+" cannot be null)");
                    }
                    entries.add((new Entry(null)));
                  }
                  else {
                    throw new WrongInsertException(wrongColumnType);
                  }
                  break;
                case DOUBLE:
                  if(value.get(j).getType()==LiteralValueItem.Type.FLOAT_OR_DOUBLE){
                    try {
                      double tmp = Double.parseDouble(value.get(j).getString());
                      if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(tmp))){
                        throw new DuplicateKeyException();
                      }
                      entries.add(new Entry(tmp));
                    } catch (NumberFormatException e){
                      throw e;
                    }
                  } else if(value.get(j).getType()==LiteralValueItem.Type.NULL){
                    if(columns.get(i).isNotNull()){
                      throw new WrongInsertException("Exception: wrong insert operation ( "+columns.get(i).getName()+" cannot be null)");
                    }
                    entries.add((new Entry(null)));
                  }
                  else {
                    throw new WrongInsertException(wrongColumnType);
                  }
                  break;
                case FLOAT:
                  if(value.get(j).getType()==LiteralValueItem.Type.FLOAT_OR_DOUBLE){
                    try {
                      float tmp = Float.parseFloat(value.get(j).getString());
                      if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(tmp))){
                        throw new DuplicateKeyException();
                      }
                      entries.add(new Entry(tmp));
                    } catch (NumberFormatException e){
                      throw e;
                    }
                  } else if(value.get(j).getType()==LiteralValueItem.Type.NULL){
                    if(columns.get(i).isNotNull()){
                      throw new WrongInsertException("Exception: wrong insert operation ( "+columns.get(i).getName()+" cannot be null)");
                    }
                    entries.add((new Entry(null)));
                  }
                  else {
                    throw new WrongInsertException(wrongColumnType);
                  }
                  break;
                case STRING:
                  if(value.get(j).getType()==LiteralValueItem.Type.STRING){
                    if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(value.get(j).getString()))){
                      throw new DuplicateKeyException();
                    }
                    entries.add(new Entry(value.get(j).getString()));
                  } else if(value.get(j).getType()==LiteralValueItem.Type.NULL){
                    if(columns.get(i).isNotNull()){
                      throw new WrongInsertException("Exception: wrong insert operation ( "+columns.get(i).getName()+" cannot be null)");
                    }
                    entries.add((new Entry(null)));
                  }
                  else {
                    throw new WrongInsertException(wrongColumnType);
                  }
                  break;
              }
              hasMatched = true;
              break;
            }
          }

          // 将没匹配到的列的值置为null
          if(hasMatched){
            continue;
          } else {
            if(columns.get(i).isNotNull()){
              throw new WrongInsertException("Exception: wrong insert operation ( column "+columns.get(i).getName()+" cannot be null )");
            } else {
              entries.add(new Entry(null));
            }
          }
        }

        Row newRow = new Row(entries);
        table.insert(newRow);
        rowsHasInsert.add(newRow);
      }

    }

  }

  /**
   * [method] 撤销操作
   */
  public void undo(){
    for(Row row: rowsHasInsert){
      table.delete(row);
    }
  }

  /**
   * [method] 获取记录
   */
  public LinkedList<String> getLog(){
    LinkedList<String> log = new LinkedList<>();
    for(Row row: rowsHasInsert){
      log.add("INSERT" + row.toString());
    }
    return log;
  }
}
