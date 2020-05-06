package cn.edu.thssdb.schema;

import cn.edu.thssdb.exception.*;
import cn.edu.thssdb.query.QueryResult;
import cn.edu.thssdb.query.QueryTable;
import cn.edu.thssdb.type.ColumnType;
import cn.edu.thssdb.utils.Global;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/***************
 * [class] 数据库
 ***************/
public class Database {
  private String name;                        // 数据库名称
  private HashMap<String, Table> tables;      // 表哈希表
  ReentrantReadWriteLock lock;                // 可重入读写锁
  private Meta meta;// 持久化存储


  /**
   * [method] 构造方法
   * @param name {String} 数据库名称
   */
  public Database(String name) throws ClassNotFoundException {
    // 需要区分是加载已有数据库还是新建
    this.name = name;
    this.tables = new HashMap<>();
    this.lock = new ReentrantReadWriteLock();
    String folder = Global.DATA_ROOT_FOLDER + "\\" + name;
    String meta_name = name + ".meta";
    this.meta = new Meta(folder, meta_name, true);
  }

  /**
   * [method] 判断表是否存在
   * @param name {String} 表名称
   * @return {boolean} 表是否存在
   */
  public boolean contains(String name) {
    try {
      lock.readLock().lock();
      return tables.containsKey(name);
    } finally {
      lock.readLock().unlock();
    }
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
    try {
      lock.readLock().lock();
      if (tables.containsKey(name))
        throw new DuplicateTableException();
    } finally {
      lock.readLock().unlock();
    }
    try {
      lock.writeLock().lock();
      tables.put(name, new Table(this.name, name, columns, primaryIndex));
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      lock.writeLock().unlock();
    }
  }

  /**
   * [method] 删除表
   * @param name {String} 表名称
   * @exception TableNotExistException 表不存在
   */
  public void drop(String name) {
    try {
      lock.readLock().lock();
      if (!tables.containsKey(name))
        throw new TableNotExistException();
    } finally {
      lock.readLock().unlock();
    }
    try {
      lock.writeLock().lock();
      tables.remove(name);
    } finally {
      lock.writeLock().unlock();
    }
  }

  /**
   * [method] 查询表
   * @param queryTables {QueryTable[]} 查询条件
   * @return {String} 查询结果
   * @exception TODO
   */
  public String select(QueryTable[] queryTables) {
    // TODO 查询模块
    try {
      lock.readLock().lock();
      QueryResult queryResult = new QueryResult(queryTables);
    } finally {
      lock.readLock().unlock();
    }
    return null;
  }

  /**
   * [method] 恢复数据库
   * [note] 从持久化数据中恢复数据库
   */
  public void recover() throws MetaFileNotFoundException, CustomIOException, WrongMetaFormatException {
    // TODO
    ArrayList<String[]> table_list = this.meta.readFromFile();

    // 目前 一行一个table名
    for (String [] table_info: table_list) {
      try {
        tables.put(table_info[0], new Table(this.name, table_info[0]));
      } catch (Exception e) {
        e.printStackTrace();
        throw new WrongMetaFormatException();
      }
    }
  }

  /**
   * [method] 存储数据库（持久化）
   * [note] 将数据库持久化存储
   * @exception TODO
   */
  public void persist() throws DataFileNotFoundException, CustomIOException {
    // TODO
    for(Object key: tables.keySet())
    {
      tables.get(key).persist();
    }
    this.meta.writeToFile((ArrayList<String>) tables.keySet()); // 目前 一行一个table名
  }


  /**
   * [method] 退出数据库
   * @exception TODO
   */
  public void quit() {
    // TODO
  }




}
