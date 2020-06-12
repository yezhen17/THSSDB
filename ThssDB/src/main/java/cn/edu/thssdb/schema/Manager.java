package cn.edu.thssdb.schema;

import cn.edu.thssdb.exception.*;
import cn.edu.thssdb.utils.Global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/***************
 * [class] 管理器
 * [note] 单例模式（Holder）
 ***************/
public class Manager {
  private HashMap<String, Database> databases;   // 数据库哈希表
  private static ReentrantReadWriteLock lock;    // 可重入读写锁
  private Meta meta;                             // 元数据管理
  private ArrayList<String> databasesList;       // 数据库名称列表
  private HashMap<String, Integer> onlineDatabases; // 正在使用的数据库哈希表，记录了几个客户端正在使用

  /**
   * [method] 获取管理器实例
   * @return 管理器实例
   */
  public static Manager getInstance() {
    return Manager.ManagerHolder.INSTANCE;
  }

  /**
   * [method] 构造方法
   */
  public Manager() {
    databases = new HashMap<>();
    lock = new ReentrantReadWriteLock();
    databasesList = new ArrayList<>();
    onlineDatabases = new HashMap<>();
    meta = new Meta(Global.DATA_ROOT_FOLDER, "manager.meta");
    ArrayList<String[]> db_list = this.meta.readFromFile();
    System.out.println(db_list);
    for (String [] db_info: db_list) {
      databases.put(db_info[0], new Database(db_info[0]));
      databasesList.add(db_info[0]);
    }
  }

  /**
   * [method] 判断数据库是否存在
   * @param name {String} 数据库名称
   * @return {boolean} 数据库是否存在
   */
  public boolean contains(String name) {
    return databases.containsKey(name);
  }

  /**
   * [method] 写元数据
   */
  public void writeMeta() {
    ArrayList<String> db_list = new ArrayList<>();
    for (String name: databasesList) {
      db_list.add(name);
    }
    this.meta.writeToFile(db_list);
  }

  /**
   * [method] 创建数据库
   * @param name {String} 数据库名称
   * @exception DuplicateDatabaseException 重复数据库
   */
  public void createDatabaseIfNotExists(String name) {
    if (databases.containsKey(name))
      throw new DuplicateDatabaseException();
    databases.put(name, new Database(name));
    databasesList.add(name);
    writeMeta();
  }

  /**
   * [method] 删除数据库
   * @param name {String} 数据库名称
   */
  public void deleteDatabase(String name) {
    if (!databases.containsKey(name))
      throw new DatabaseNotExistException();
    databases.get(name).wipeData();
    databases.remove(name);
    databasesList.remove(name);
    onlineDatabases.remove(name);
    writeMeta();
  }

  /**
   * [method] 退出数据库
   * @param name {String} 数据库名称
   */
  public void quitDatabase(String name) {
    if (onlineDatabases.keySet().contains(name)) {
      int count = onlineDatabases.get(name);
      if (count > 1) onlineDatabases.replace(name, count - 1);
      else {
        onlineDatabases.remove(name);
        getDatabaseByName(name).persist();
      }
    }
  }

  /**
   * [method] 切换数据库
   * @param name {String} 数据库名称
   */
  public void switchDatabase(String name) {
    if (databasesList.contains(name)) {
      if (!onlineDatabases.keySet().contains(name)) {
        databases.get(name).recover();
        onlineDatabases.put(name, 1);
      } else {
        onlineDatabases.replace(name, onlineDatabases.get(name) + 1);
      }
    } else {
      throw new DatabaseNotExistException();
    }
  }

  /**
   * [method] 通过名称获取数据库
   * return {Database} 数据库，没有则返回null
   */
  public Database getDatabaseByName(String name) {
    if(!databases.containsKey(name))
      return null;
    return databases.get(name);
  }

  /**
   * [method] 通过名称获取表
   * return {Table} 表，没有则返回null
   */
  public Table getTableByName(String databaseName, String tableName) {
    if(!databases.containsKey(databaseName))
      return null;
    return databases.get(databaseName).get(tableName);
  }

  /**
   * [method] 展示所有数据库名称
   * return {String} 返回一定格式的数据名称信息
   * @return {String}
   */
  public String showAllDatabases() {
    StringBuffer info = new StringBuffer();
    for (String name: databasesList) {
      info.append(name + '\n');
    }
    return info.toString();
  }

  /**
   * [class] Holder类
   */
  private static class ManagerHolder {
    private static final Manager INSTANCE = new Manager();
  }
}
