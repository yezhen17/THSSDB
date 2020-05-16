package cn.edu.thssdb.operation;
import cn.edu.thssdb.parser.item.LiteralValueItem;
import cn.edu.thssdb.schema.*;

import java.util.ArrayList;

public class InsertOperation extends BaseOperation {

  private String tableName;
  private ArrayList<String> columnNames=null;
  private ArrayList<ArrayList<LiteralValueItem>> values;

  private Table table;

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
      // TODO 调用 cn.edu.thssdb.schema.Table.insert
    Manager manager = Manager.getInstance();
    Database database = manager.getDatabaseByName(manager.getCurrentDatabaseName());

    table = database.get(tableName);
    if(table==null){
      //todo 没有对应的表
      return;
    }

    ArrayList<Column> columns = table.columns;
    int primaryKeyIndex = table.primaryIndex;
    String primaryKey = columns.get(primaryKeyIndex).getName();


    if(columnNames==null){
      for(ArrayList<LiteralValueItem> value:values){
        if(value.size()!=columns.size()){
          // todo:列数不匹配
          return;
        }

        ArrayList<Entry> entries = new ArrayList<>();

        for (int i=0;i<columns.size();i++){
          switch (columns.get(i).getType()){
            case INT:
              if(value.get(i).getType()==LiteralValueItem.Type.INT_OR_LONG){
                try {
                  int tmp = Integer.parseInt(value.get(i).getString());
                  if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(tmp))){
                    //todo 主键重复
                  }
                  entries.add(new Entry(tmp));
                } catch (NumberFormatException e){
                  throw e;
                }
              }
              else {
                //todo 类型不匹配
                return;
              }
              break;
            case LONG:
              if(value.get(i).getType()==LiteralValueItem.Type.INT_OR_LONG){
                try {
                  long tmp = Long.parseLong(value.get(i).getString());
                  if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(tmp))){
                    //todo 主键重复
                  }
                  entries.add(new Entry(tmp));
                } catch (NumberFormatException e){
                  throw e;
                }
              }
              else {
                //todo 类型不匹配
                return;
              }
              break;
            case DOUBLE:
              if(value.get(i).getType()==LiteralValueItem.Type.FLOAT_OR_DOUBLE){
                try {
                  double tmp = Double.parseDouble(value.get(i).getString());
                  if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(tmp))){
                    //todo 主键重复
                  }
                  entries.add(new Entry(tmp));
                } catch (NumberFormatException e){
                  throw e;
                }
              }
              else {
                //todo 类型不匹配
                return;
              }
              break;
            case FLOAT:
              if(value.get(i).getType()==LiteralValueItem.Type.FLOAT_OR_DOUBLE){
                try {
                  float tmp = Float.parseFloat(value.get(i).getString());
                  if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(tmp))){
                    //todo 主键重复
                  }
                  entries.add(new Entry(tmp));
                } catch (NumberFormatException e){
                  throw e;
                }
              }
              else {
                //todo 类型不匹配
                return;
              }
              break;
            case STRING:
              if(value.get(i).getType()==LiteralValueItem.Type.STRING){
                if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(value.get(i).getString()))){
                  //todo 主键重复
                }
                entries.add(new Entry(value.get(i).getString()));
              }
              else {
                //todo 类型不匹配
                return;
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
        //todo 列数不匹配
        return;
      }
      for(ArrayList<LiteralValueItem> items:values){
        if(items.size()!=columnNames.size()){
          //todo 列数与值数不匹配
          return;
        }
      }

      for(int i=0;i<columnNames.size();i++){
        for(int j=0;j<i;j++){
          if(columnNames.get(i).equals(columnNames.get(j))){
            //todo 列名重复
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
          //todo 列名不在表定义中
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
                        //todo 主键重复
                      }
                      entries.add(new Entry(tmp));
                    } catch (NumberFormatException e){
                      throw e;
                    }
                  }
                  else {
                    //todo 类型不匹配
                    return;
                  }
                  break;
                case LONG:
                  if(value.get(j).getType()==LiteralValueItem.Type.INT_OR_LONG){
                    try {
                      long tmp = Long.parseLong(value.get(j).getString());
                      if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(tmp))){
                        //todo 主键重复
                      }
                      entries.add(new Entry(tmp));
                    } catch (NumberFormatException e){
                      throw e;
                    }
                  }
                  else {
                    //todo 类型不匹配
                    return;
                  }
                  break;
                case DOUBLE:
                  if(value.get(j).getType()==LiteralValueItem.Type.FLOAT_OR_DOUBLE){
                    try {
                      double tmp = Double.parseDouble(value.get(j).getString());
                      if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(tmp))){
                        //todo 主键重复
                      }
                      entries.add(new Entry(tmp));
                    } catch (NumberFormatException e){
                      throw e;
                    }
                  }
                  else {
                    //todo 类型不匹配
                    return;
                  }
                  break;
                case FLOAT:
                  if(value.get(j).getType()==LiteralValueItem.Type.FLOAT_OR_DOUBLE){
                    try {
                      float tmp = Float.parseFloat(value.get(j).getString());
                      if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(tmp))){
                        //todo 主键重复
                      }
                      entries.add(new Entry(tmp));
                    } catch (NumberFormatException e){
                      throw e;
                    }
                  }
                  else {
                    //todo 类型不匹配
                    return;
                  }
                  break;
                case STRING:
                  if(value.get(j).getType()==LiteralValueItem.Type.STRING){
                    if(columns.get(i).getName().equals(primaryKey)&&table.index.contains(new Entry(new Entry(value.get(j).getString())))){
                      //todo 主键重复
                    }
                    entries.add(new Entry(value.get(j).getString()));
                  }
                  else {
                    //todo 类型不匹配
                    return;
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
              //todo 该列不能为null
              return;
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
