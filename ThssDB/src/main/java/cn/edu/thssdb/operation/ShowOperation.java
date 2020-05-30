package cn.edu.thssdb.operation;

import cn.edu.thssdb.exception.DatabaseNotExistException;
import cn.edu.thssdb.exception.TableNotExistException;
import cn.edu.thssdb.schema.*;

import java.util.*;

public class ShowOperation extends BaseOperation {

  private String name;      // 表名称

  /**
   * [method] 构造方法
   */
  public ShowOperation(String name) {
    this.name = name;
  }

  /**
   * [method] 执行操作
   */
  public void exec() {

    if (database==null){
      throw new DatabaseNotExistException();
    }

    Table table = database.get(name);
    if(table==null){
      throw new TableNotExistException();
    }
    List<List<String>> showTable = new ArrayList<>();
    ArrayList<Column> columns = table.getColumns();
    ArrayList<String> columnNames = new ArrayList<>();
    for(int i=0;i<columns.size();i++){
      showTable.add(new ArrayList<String>());
      columnNames.add(columns.get(i).getName());
    }
    Iterator<Row> rowIterator = table.iterator();
    while (rowIterator.hasNext()){
      Row row = rowIterator.next();
      for(int i=0;i<row.getEntries().size();i++){
        if(row.getEntries().get(i).value==null){
          showTable.get(i).add("null");
        }
        else {
          showTable.get(i).add(row.getEntries().get(i).value.toString());
        }
      }
    }
    //todo return showTable and columnNames
    System.out.println(showTable);
    System.out.println(columnNames);


  }
}
