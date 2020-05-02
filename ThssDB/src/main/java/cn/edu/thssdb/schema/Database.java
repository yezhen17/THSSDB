package cn.edu.thssdb.schema;

import cn.edu.thssdb.exception.DatabaseNotExistException;
import cn.edu.thssdb.exception.DuplicateDatabaseException;
import cn.edu.thssdb.exception.DuplicateTableException;
import cn.edu.thssdb.exception.TableNotExistException;
import cn.edu.thssdb.query.QueryResult;
import cn.edu.thssdb.query.QueryTable;
import cn.edu.thssdb.type.ColumnType;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/***************
 * [class] 数据库
 ***************/
public class Database {
  private String name;                        // 数据库名称
  private HashMap<String, Table> tables;      // 表哈希表
  ReentrantReadWriteLock lock;                // 可重入读写锁

  /**
   * [method] 构造方法
   * @param name {String} 数据库名称
   */
  public Database(String name) {
    this.name = name;
    this.tables = new HashMap<>();
    this.lock = new ReentrantReadWriteLock();
    recover();
  }

  /**
   * [method] 创建表
   * [note] 传入的数据需合法
   * @param name {String} 表名称
   * @param columns {Column[]} 列定义
   * @param primaryIndex {int} 主键索引
   * @exception DuplicateTableException 重复表
   */
  public void create(String name, Column[] columns, int primaryIndex) {
    if (tables.containsKey(name))
      throw new DuplicateTableException();
    tables.put(name, new Table(this.name, name, columns, primaryIndex));
  }

  /**
   * [method] 删除表
   * @param name {String} 表名称
   * @exception TableNotExistException 表不存在
   */
  public void drop(String name) {
    if (!tables.containsKey(name))
      throw new TableNotExistException();
    tables.remove(name);
  }

  /**
   * [method] 查询表
   * @param queryTables {QueryTable[]} 查询条件
   * @return {String} 查询结果
   * @exception TODO
   */
  public String select(QueryTable[] queryTables) {
    // TODO 查询模块
    QueryResult queryResult = new QueryResult(queryTables);
    return null;
  }

  /**
   * [method] 恢复数据库
   * [note] 从持久化数据中恢复数据库
   * @exception TODO
   */
  private void recover() {
    // TODO
  }

  /**
   * [method] 存储数据库（持久化）
   * [note] 将数据库持久化存储
   * @exception TODO
   */
  private void persist() {
    // TODO
  }


  /**
   * [method] 退出数据库
   * @exception TODO
   */
  public void quit() {
    // TODO
  }




}
