package cn.edu.thssdb.schema;

import cn.edu.thssdb.query.QueryResult;
import cn.edu.thssdb.query.QueryTable;
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
   * [方法] 构造方法
   */
  public Database(String name) {
    this.name = name;
    this.tables = new HashMap<>();
    this.lock = new ReentrantReadWriteLock();
    recover();
  }

  /**
   * [方法] 持久化
   */
  private void persist() {
    // TODO
  }

  /**
   * [方法] 创建表
   */
  public void create(String name, Column[] columns) {
    // TODO
  }

  /**
   * [方法] 删除表
   */
  public void drop() {
    // TODO
  }

  /**
   * [方法] ？
   */
  public String select(QueryTable[] queryTables) {
    // TODO
    QueryResult queryResult = new QueryResult(queryTables);
    return null;
  }

  /**
   * [方法] 恢复数据库
   */
  private void recover() {
    // TODO
  }

  /**
   * [方法] 退出数据库
   */
  public void quit() {
    // TODO
  }
}
