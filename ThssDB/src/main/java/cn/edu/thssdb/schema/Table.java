package cn.edu.thssdb.schema;

import cn.edu.thssdb.exception.SerializeException;
import cn.edu.thssdb.index.BPlusTree;
import cn.edu.thssdb.utils.Global;
import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/***************
 * [class] 表
 ***************/
public class Table implements Iterable<Row> {
  ReentrantReadWriteLock lock;            // 可重入读写锁
  private String databaseName;            // 数据库名称
  public String tableName;                // 表名称
  public ArrayList<Column> columns;       // 列定义表
  public BPlusTree<Entry, Row> index;     // B+树索引
  private int primaryIndex;               // 主键索引
  private PersistentStorage<Row> persistentStorageData; // 数据持久化

  /**
   * [method] 构造方法
   * @param databaseName {String} 数据库名称
   * @param tableName {String} 表名称
   * @param columns {Column[]} 列定义表
   * @param primaryIndex {int} 主键索引
   */
  public Table(String databaseName, String tableName, Column[] columns, int primaryIndex) {
    // 还有很大的问题，需要区分新建Table和加载；或者增加一个加载的函数
    this.databaseName = databaseName;
    this.tableName = tableName;
    this.columns = new ArrayList<>(Arrays.asList(columns));
    this.primaryIndex = primaryIndex;
    String meta_storage_path = Global.DATA_ROOT_FOLDER + "\\" + databaseName
            + "\\" + tableName + "\\" + tableName + ".meta";
    String data_storage_path = Global.DATA_ROOT_FOLDER + "\\" + databaseName
            + "\\" + tableName + "\\" + tableName + ".data";
    this.persistentStorageData = new PersistentStorage<>(data_storage_path);
    this.index = new BPlusTree<>();
  }

  public void loadFromStorage() {
    // TODO 加载元数据
  }

  /**
   * [method] 恢复表
   * [note] 从持久化数据中恢复表
   * @exception IllegalArgumentException
   */
  private void recover(ArrayList<Row> rows) {
    // TODO
      for(Row row:rows){
          index.put(row.getEntries().get(primaryIndex),row);
      }
  }

    /**
     * [method] 插入行
     * @param row {Row} 待插入行
     * @exception IllegalArgumentException
     */
    public void insert(Row row) {
        // TODO
        index.put(row.getEntries().get(primaryIndex),row);
    }

    /**
     * [method] 删除行
     * TODO 可能利用索引
     * @exception IllegalArgumentException
     */
    public void delete(Row row) {
        // TODO
        index.remove(row.getEntries().get(primaryIndex));
    }

    /**
     * [method] 更新行
     * TODO 可能利用索引
     * @exception IllegalArgumentException
     */
    public void update(Row oldRow, Row newRow) {
        // TODO
        if(oldRow.getEntries().get(primaryIndex).compareTo(newRow.getEntries().get(primaryIndex))==0){
            index.update(newRow.getEntries().get(primaryIndex),newRow);
        }
        else{
            delete(oldRow);
            insert(newRow);
        }
    }

  /**
   * [method] 查找行
   * @exception IllegalArgumentException
   */
  public Row search(Entry entry) {
        // TODO
        return index.get(entry);
    }

  /**
   * [method] 序列化
   */
  private void serialize() throws IOException {
    // TODO
    try {
      lock.readLock().lock();
      persistentStorageData.serialize(iterator());
    } finally {
      lock.readLock().unlock();
    }
  }

  /**
   * [method] 反序列化
   */
  private ArrayList<Row> deserialize() throws ClassNotFoundException {
    // TODO
    return persistentStorageData.deserialize();
  }

  /**
   * [class] 表迭代器（按行）
   */
  private class TableIterator implements Iterator<Row> {
    private Iterator<Pair<Entry, Row>> iterator;

    TableIterator(Table table) {
      this.iterator = table.index.iterator();
    }

    @Override
    public boolean hasNext() {
      return iterator.hasNext();
    }

    @Override
    public Row next() {
      return iterator.next().getValue();
    }
  }

  /**
   * [method] 创建迭代器（按行）
   */
  @Override
  public Iterator<Row> iterator() {
    return new TableIterator(this);
  }

  /**
   * [method] 检查列定义
   */
  public static boolean checkColumns(Column[] columns, int primaryIndex) {
    // TODO 可整合至他处
    return true;
  }
}