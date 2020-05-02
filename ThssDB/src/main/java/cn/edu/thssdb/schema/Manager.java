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
   * [方法] 获取管理器实例
   * @return 管理器实例
   */
  public static Manager getInstance() {
    return Manager.ManagerHolder.INSTANCE;
  }

  /**
   * [方法] 构造方法
   */
  public Manager() {
    // TODO
  }

  /**
   * [方法] 创建数据库
   */
  private void createDatabaseIfNotExists() {
    // TODO
  }

  /**
   * [方法] 删除数据库
   */
  private void deleteDatabase() {
    // TODO
  }

  /**
   * [方法] 切换数据库
   */
  public void switchDatabase() {
    // TODO
  }

  /**
   * [内部类] Holder类
   */
  private static class ManagerHolder {
    private static final Manager INSTANCE = new Manager();
  }
}
