package cn.edu.thssdb.operation;
import cn.edu.thssdb.exception.DuplicateKeyException;
import cn.edu.thssdb.exception.TableNotExistException;
import cn.edu.thssdb.exception.WrongInsertException;
import cn.edu.thssdb.parser.item.LiteralValueItem;
import cn.edu.thssdb.schema.*;

import java.util.ArrayList;

public class InsertOperation extends BaseOperation {

  private String tableName;
  private ArrayList<String> columnNames=null;
  private ArrayList<ArrayList<LiteralValueItem>> values;

  private Table table;

  final static String wrongColumnNum = "Exception: wrong insert format (columns unmatched)!";//列数不匹配
  final static String wrongColumnType = "Exception: wrong insert format (type unmatched)!";//类型不匹配
  final static String wrongValueNum = "Exception: wrong insert format (number of columns and values unmatched)!";//列数与值数不匹配
  final static String duplicateValueType = "Exception: wrong insert format (duplicate name of columns)!";//类型不匹配
  final static String wrongColumnName = "Exception: wrong insert format (wrong column name)!";//属性名不在列定义中


  /**
   * [method] 构造方法
   */

  public InsertOperation(String tableName, ArrayList<ArrayList<LiteralValueItem>> values) {
    this.tableName = tableName;
    this.values = values;
  }

  public InsertOperation(String tableName, ArrayList<String> columnNames, ArrayList<ArrayList<LiteralValueItem>> values) {
    this.tableName = tableName;
    this.columnNames = columnNames;
    this.values = values;
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

    ArrayList<Column> columns = table.columns;
    int primaryKeyIndex = table.primaryIndex;
    String primaryKey = columns.get(primaryKeyIndex).getName();


    if(columnNames==null){
      for(ArrayList<LiteralValueItem> value:values){
        if(value.size()!=columns.size()){

          throw new WrongInsertException(wrongColumnNum);
        }

        ArrayList<Entry> entries = new ArrayList<>();

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
              else {
                throw new WrongInsertException(wrongColumnType);
              }
              break;
          }

          Row newRow = new Row(entries);
          table.insert(newRow);
        }
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

      for(int i=0;i<columnNames.size();i++){
        for(int j=0;j<i;j++){
          if(columnNames.get(i).equals(columnNames.get(j))){
            throw new WrongInsertException(duplicateValueType);
          }
        }
        boolean hasMatched = false;
        for(int j=0;j<columns.size();j++){
          if(columnNames.get(i).equals(table.columns.get(j).getName())){
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
                      int tmp = Integer.parseInt(value.get(i).getString());
                      if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(tmp))){
                        throw new DuplicateKeyException();
                      }
                      entries.add(new Entry(tmp));
                    } catch (NumberFormatException e){
                      throw e;
                    }
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
                  }
                  else {
                    throw new WrongInsertException(wrongColumnType);
                  }
                  break;
                case STRING:
                  if(value.get(j).getType()==LiteralValueItem.Type.STRING){
                    if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(new Entry(value.get(j).getString())))){
                      throw new DuplicateKeyException();
                    }
                    entries.add(new Entry(value.get(j).getString()));
                  }
                  else {
                    throw new WrongInsertException(wrongColumnType);
                  }
                  break;
              }
              hasMatched = true;
              value.remove(value.get(j));
              break;
            }
          }
          if(hasMatched){
            continue;
          } else {
            if(columns.get(i).isNotNull()){
              throw new WrongInsertException("Exception: wrong insert format ( column"+columns.get(i).getName()+"is not null )");
            } else {
              entries.add(new Entry(null));
            }
          }
        }

        Row newRow = new Row(entries);
        table.insert(newRow);
      }

    }

  }
}
