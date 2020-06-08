package cn.edu.thssdb.operation;

import cn.edu.thssdb.exception.DatabaseNotExistException;
import cn.edu.thssdb.exception.TableNotExistException;
import cn.edu.thssdb.schema.*;

import java.util.*;

public class ShowOperation extends BaseOperation {

  private String name;      // 表名称
  private String stmt;
  private List<List<String>> showTable;
  private List<String> columnNames;

  /**
   * [method] 构造方法
   */
  public ShowOperation(String name) {
    this.name = name;
    this.stmt = "show table "+ this.name;
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

    showTable = new ArrayList<>();
    columnNames = new ArrayList<>();
    ArrayList<Column> columns = table.getColumns();

    for(int i=0;i<columns.size();i++){
      columnNames.add(columns.get(i).getName());
    }
    Iterator<Row> rowIterator = table.iterator();
    while (rowIterator.hasNext()){

      ArrayList<String> newRow = new ArrayList<>();
      Row row = rowIterator.next();
      for(Entry entry:row.getEntries()){
        newRow.add(entry.toString());
      }
      showTable.add(newRow);
    }
  }

  @Override
  public ArrayList<String> getTableName() {
    return new ArrayList<String>(Arrays.asList(this.name));
  }

  public String getStmt(){return this.stmt;}

  public List<List<String>> getData(){return showTable;}

  public List<String> getColumns(){return columnNames;}


}
