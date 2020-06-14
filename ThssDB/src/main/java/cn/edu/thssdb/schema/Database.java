package cn.edu.thssdb.schema;

import cn.edu.thssdb.exception.*;
import cn.edu.thssdb.operation.BaseOperation;
import cn.edu.thssdb.parser.MyParser;
import cn.edu.thssdb.utils.Global;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/***************
 * [class] 数据库
 ***************/
public class Database {
  private String name;                        // 数据库名称
  private HashMap<String, Table> tables;      // 表哈希表
  private ReentrantReadWriteLock lock;        // 可重入读写锁
  private Meta meta;                          // 元数据管理
  private Logger logger;                      // 日志管理
  private ArrayList<Table> droppedTables;     // 删除的表

  /**
   * [method] 构造方法
   * @param name {String} 数据库名称
   */
  public Database(String name) throws CustomIOException {
    // 需要区分是加载已有数据库还是新建
    this.name = name;
    this.tables = new HashMap<>();
    this.lock = new ReentrantReadWriteLock();
    String folder = Paths.get(Global.DATA_ROOT_FOLDER, name).toString();
    String meta_name = name + ".meta";
    this.meta = new Meta(folder, meta_name); // 暂时不加载表到内存
    String logger_name = name + ".log";
    this.logger = new Logger(folder, logger_name);
    this.droppedTables = new ArrayList<>();
  }

  public Logger getLogger() {
    return logger;
  }

  /**
   * [method] 判断表是否存在
   * @param name {String} 表名称
   * @return {boolean} 表是否存在
   */
  public boolean contains(String name) {
    return tables.containsKey(name);
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
   * [method] 获取表
   * @param name {String} 表名称
   * @return {String} 查询结果,没有返回null
   */
  public Table get(String name) {
    if (!tables.containsKey(name))
      return null;
    return tables.get(name);
  }

  /**
   * [method] 删除表
   * @param name {String} 表名称
   * @exception TableNotExistException 表不存在
   */
  public void drop(String name) {
    if (!tables.containsKey(name))
      throw new TableNotExistException();
    if (tables.get(name).lock.isWriteLocked()) throw new TableOccupiedException();
    droppedTables.add(tables.remove(name));// .drop();
    // meta.writeToFile(tables.keySet());
  }

  /**
   * [method] 恢复数据库
   * [note] 从持久化数据中恢复数据库
   */
  public synchronized void recover() {
    ArrayList<String[]> table_list = this.meta.readFromFile();
    // 目前 一行一个table名
    for (String [] table_info: table_list) {
      tables.put(table_info[0], new Table(this.name, table_info[0]));
    }
    logRecover();
  }

  public void logRecover() {
    try {
      ArrayList<String> logs = this.logger.readLog();
      for (String log: logs) {
        String [] info = log.split(" ");
        String type = info[0];
        if (type.equals("DELETE")) {
          tables.get(info[1]).delete(info[2]);
        } else if (type.equals("INSERT")) {
          tables.get(info[1]).insert(info[2]);
        } else if (!type.equals("COMMIT")) {
          ArrayList<BaseOperation> operations = MyParser.getOperations(log);
          for (BaseOperation op: operations) {
            try {
              op.setCurrentUser(null, name);
              op.exec();
            } catch (Exception e) {

            }
          }
        }
      }
    } catch (Exception e) {
      throw new CustomIOException();
    }
  }

  /**
   * [method] 存储数据库（持久化）
   * [note] 将数据库持久化存储
   */
  public synchronized void persist() {
    ArrayList<String> keys = new ArrayList<>();
    for(String key: tables.keySet())
    {
      tables.get(key).persist();
      keys.add(key);
    }
    for (Table table: droppedTables) {
      table.drop();
    }
    droppedTables.clear();
    this.meta.writeToFile(keys); // 目前 一行一个table名
    this.logger.eraseFile();
  }

  /**
   * [method] 删除数据库
   */
  public void wipeData() {
    for (Table table: tables.values()) {
      table.drop();
    }
    tables.clear();
    this.logger.deleteFile();
    this.meta.deleteFile();
    Paths.get(Global.DATA_ROOT_FOLDER, name).toFile().delete();
  }

  public ArrayList<Table> getTables() {
    ArrayList<Table> res = new ArrayList<>();
    for (Table table: tables.values()) {
      res.add(table);
    }
    return res;
  }
}
