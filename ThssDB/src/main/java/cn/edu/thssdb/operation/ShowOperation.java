package cn.edu.thssdb.operation;

import cn.edu.thssdb.exception.DatabaseNotExistException;
import cn.edu.thssdb.exception.TableNotExistException;
import cn.edu.thssdb.schema.*;
import cn.edu.thssdb.type.ColumnType;

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

    for(int i = 0; i < columns.size(); i++){
      columnNames.add(columns.get(i).getName());
    }

    ArrayList<String> columnTypes = new ArrayList<>();
    ArrayList<String> is_null = new ArrayList<>();
    ArrayList<String> is_primary = new ArrayList<>();
    for(Column column: columns){

      String type = ColumnType.columnType2String(column.getType());
      if (column.getType() == ColumnType.STRING) type += " (MAX_LENGTH: "+ column.getMaxLength() + ")";
      columnTypes.add(type);
      if (column.isNotNull()) {
        is_null.add("NOT NULL");
      } else {
        is_null.add("");
      }
      if (column.isPrimary()) {
        is_primary.add("PRIMARY KEY");
      } else {
        is_primary.add("");
      }
    }
    showTable.add(columnTypes);
    showTable.add(is_null);
    showTable.add(is_primary);
//    Iterator<Row> rowIterator = table.iterator();
//    while (rowIterator.hasNext()){
//
//      ArrayList<String> newRow = new ArrayList<>();
//      Row row = rowIterator.next();
//      for(Entry entry:row.getEntries()){
//        newRow.add(entry.toString());
//      }
//      showTable.add(newRow);
//    }
  }

  @Override
  public ArrayList<String> getTableName() {
    return new ArrayList<String>(Arrays.asList(this.name));
  }

  public String getStmt(){return this.stmt;}

  public List<List<String>> getData(){return showTable;}

  public List<String> getColumns(){return columnNames;}


}
