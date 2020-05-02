package cn.edu.thssdb.schema;

import cn.edu.thssdb.server.ThssDB;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/***************
 * [class] 管理器
 * [note] 单例模式（Holder）
 ***************/
public class Manager {
  private HashMap<String, Database> databases;                                  // 数据库哈希表
  private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();    // 可重入读写锁

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
    // TODO
  }

  /**
   * [method] 创建数据库
   * @param name {String} 数据库名称
   * @exception TODO
   */
  private void createDatabaseIfNotExists(String name) {
    // TODO
  }

  /**
   * [method] 删除数据库
   * @param name {String} 数据库名称
   * @exception TODO
   */
  private void deleteDatabase(String name) {
    // TODO
  }

  /**
   * [method] 切换数据库
   * @param name {String} 数据库名称
   * @exception TODO
   */
  public void switchDatabase(String name) {
    // TODO
  }

  /**
   * [class] Holder类
   */
  private static class ManagerHolder {
    private static final Manager INSTANCE = new Manager();
  }
}
