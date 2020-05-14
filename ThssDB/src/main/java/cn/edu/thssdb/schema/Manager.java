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
  private String current_database = null;        // 当前的数据库
  private Meta meta;                             // 元数据管理
  private ArrayList<String> databasesList;       // 数据库名称列表

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
    try {
      // 目前没有权限管理，可扩展
      meta = new Meta(Global.DATA_ROOT_FOLDER, "manager.data", true);
      ArrayList<String[]> db_list = this.meta.readFromFile();
      System.out.println(db_list);
      for (String [] db_info: db_list) {
        databases.put(db_info[0], new Database(db_info[0]));
        databasesList.add(db_info[0]);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }

  /**
   * [method] 判断数据库是否存在
   * @param name {String} 数据库名称
   * @return {boolean} 数据库是否存在
   */
  public boolean contains(String name) {
    try {
      lock.readLock().lock();
      return databases.containsKey(name);
    } finally {
      lock.readLock().unlock();
    }
  }

  /**
   * [method] 创建数据库
   * @param name {String} 数据库名称
   * @exception DuplicateDatabaseException 重复数据库
   */
  public void createDatabaseIfNotExists(String name) throws CustomIOException {
    try {
      lock.readLock().lock();
      if (databases.containsKey(name))
        throw new DuplicateDatabaseException();
    } finally {
      lock.readLock().unlock();
    }
    try {
      lock.writeLock().lock();
      databases.put(name, new Database(name));
      databasesList.add(name);
    } finally {
      lock.writeLock().unlock();
    }
  }

  /**
   * [method] 删除数据库
   * @param name {String} 数据库名称
   * @exception DatabaseNotExistException 数据库不存在
   */
  public void deleteDatabase(String name) {
    try {
      lock.readLock().lock();
      if (!databases.containsKey(name))
        throw new DatabaseNotExistException();
    } finally {
      lock.readLock().unlock();
    }
    try {
      lock.writeLock().lock();
      databases.get(name).wipeData();
      databases.remove(name);
    } finally {
      lock.writeLock().unlock();
    }
  }


  /**
   * [method] 切换数据库
   * @param name {String} 数据库名称
   */
  public void switchDatabase(String name) throws DataFileNotFoundException, CustomIOException, MetaFileNotFoundException, ClassNotFoundException {
    try {
      lock.writeLock().lock();
      if (current_database != null) {
        databases.get(current_database).quit();
      }
      if (databasesList.contains(name)) {
        databases.get(name).recover();
        current_database = name;
      } else {
        throw new DatabaseNotExistException();
      }
    } finally {
    lock.writeLock().unlock();
    }
  }

  /**
   * [method] 获取当前数据库名称
   * @return {String} 当前数据库名称，没有则返回null
   */
  public String getCurrentDatabaseName() {
    try {
      lock.readLock().lock();
      return current_database;
    } finally {
      lock.readLock().unlock();
    }
  }

  /**
   * [method] 展示所有数据库名称
   * @return {String} 返回一定格式的数据名称信息
   */
  public String showAllDatabases() {
    try {
      lock.readLock().lock();
      StringBuffer info = new StringBuffer();
      for (String name: databasesList) {
        info.append(name + '\n');
      }
      return info.toString();
    } finally {
      lock.readLock().unlock();
    }
  }

  /**
   * [class] Holder类
   */
  private static class ManagerHolder {
    private static final Manager INSTANCE = new Manager();
  }
}
