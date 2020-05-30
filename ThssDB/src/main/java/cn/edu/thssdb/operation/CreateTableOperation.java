package cn.edu.thssdb.operation;

import cn.edu.thssdb.exception.CustomIOException;
import cn.edu.thssdb.exception.DatabaseNotExistException;
import cn.edu.thssdb.exception.WrongCreateTableException;
import cn.edu.thssdb.schema.Column;
import cn.edu.thssdb.schema.Database;
import cn.edu.thssdb.schema.Manager;

public class CreateTableOperation extends BaseOperation {
    private String name;            // 表名称
    private Column[] columns;       // 列定义
    private int primaryIndex;       // 主键索引

  final static String keyNotDefined = "Exception: create table failed (primary key not defined) !";
  final static String moreThanOneKey = "Exception: create table failed (more than one primary key defined) !";
  final static String duplicateColumnName = "Exception: create table failed (duplicate column name) !";

  /**
     * [method] 构造方法
     */
    public CreateTableOperation(String name, Column[] columns, int primaryIndex) {
        this.name = name;
        this.columns = columns;
        this.primaryIndex = primaryIndex;
    }

    /**
     * [method] 执行操作
     */
    public void exec() {

      if (database==null){
        throw new DatabaseNotExistException();
      }

      for(int i=0;i<columns.length;i++){
        for(int j=0;j<i;j++){
          if(columns[i].getName().equals(columns[j].getName())){
            throw new WrongCreateTableException(duplicateColumnName);
          }
        }
      }

      if(primaryIndex==-1){
        throw new WrongCreateTableException(keyNotDefined);
      }

      int keys = 0;

      for(Column column:columns){
        if(column.isPrimary()){
          keys++;
        }
      }

      if(keys!=1){
        throw new WrongCreateTableException(moreThanOneKey);
      }


      database.create(name,columns,primaryIndex);
    }
}
