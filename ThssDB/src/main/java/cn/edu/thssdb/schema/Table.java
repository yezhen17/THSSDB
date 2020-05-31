package cn.edu.thssdb.schema;

import cn.edu.thssdb.exception.*;
import cn.edu.thssdb.index.BPlusTree;
import cn.edu.thssdb.type.ColumnType;
import cn.edu.thssdb.utils.Global;
import com.sun.org.apache.bcel.internal.generic.DUP;
import javafx.util.Pair;
import org.omg.PortableInterceptor.INACTIVE;

import javax.xml.bind.annotation.XmlElementDecl;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/***************
 * [class] 表
 ***************/
public class Table implements Iterable<Row> {
  public ReentrantReadWriteLock lock;     // 可重入读写锁
  private String databaseName;            // 数据库名称
  private String tableName;                // 表名称

  private ArrayList<Column> columns;       // 列定义表
  public BPlusTree<Entry, Row> index;     // B+树索引
  public int primaryIndex;               // 主键索引
  private PersistentStorage<Row> persistentStorageData; // 数据持久化
  private Meta tableMeta;

  /**
   * [method] 无metadata构造方法
   * @param databaseName {String} 数据库名称
   * @param tableName {String} 表名称
   * @param columns {Column[]} 列定义表
   * @param primaryIndex {int} 主键索引
   */
  public Table(String databaseName, String tableName, Column[] columns, int primaryIndex) throws CustomIOException {
    initData(databaseName, tableName, true);
    this.lock = new ReentrantReadWriteLock();
    this.columns = new ArrayList<>(Arrays.asList(columns));
    this.primaryIndex = primaryIndex;
    this.index = new BPlusTree<>();
    // persistMeta();
  }

  /**
   * [method] 读取metadata构造方法
   * @param databaseName {String} 数据库名称
   * @param tableName {String} 表名称
   */
  public Table(String databaseName, String tableName) throws CustomIOException, MetaFileNotFoundException, ClassNotFoundException {
    initData(databaseName, tableName, false);
    this.lock = new ReentrantReadWriteLock();
    this.columns = new ArrayList<>();
    this.index = new BPlusTree<>();
    recoverMeta();
    recover(deserialize());
  }

  /**
   * [method] 初始化元数据和数据存储相关
   * @param databaseName {String} 数据库名称
   * @param tableName {String} 表名称
   * @param just_created {boolean} 是否是新建的Table
   * @exception IllegalArgumentException
   */
  private void initData(String databaseName, String tableName, boolean just_created) throws CustomIOException {
    this.databaseName = databaseName;
    this.tableName = tableName;
    String folder = Global.DATA_ROOT_FOLDER + "\\" + databaseName + "\\" + tableName;
    String meta_name = tableName + ".meta";
    String data_name = tableName + ".data";
    this.persistentStorageData = new PersistentStorage<>(folder, data_name, just_created);
    this.tableMeta = new Meta(folder, meta_name, just_created);
  }

  /**
   * [method] 将metadata持久化
   * @exception CustomIOException
   */
  private void persistMeta() throws CustomIOException {
    ArrayList<String> meta_data = new ArrayList<>();
    meta_data.add(Global.DATABASE_NAME_META + " " + databaseName);
    meta_data.add(Global.TABLE_NAME_META + " " + tableName);
    meta_data.add(Global.PRIMARY_KEY_INDEX_META + " " + String.valueOf(primaryIndex));
    for (Column column : columns) {
      meta_data.add(column.toString(' '));
    }
    this.tableMeta.writeToFile(meta_data);
  }

  /**
   * [method] 恢复metadata
   * @exception MetaFileNotFoundException, CustomIOException
   */
  private void recoverMeta() throws MetaFileNotFoundException, CustomIOException {
    ArrayList<String []> meta_data = this.tableMeta.readFromFile();
    try {
      String [] database_name = meta_data.get(0);
      if (!database_name[0].equals(Global.DATABASE_NAME_META)) {
        throw new WrongMetaFormatException();
      }
      if (!this.databaseName.equals(database_name[1])) {
        throw new WrongMetaFormatException();
      }
    } catch (Exception e) {
      throw new WrongMetaFormatException();
    }

    try {
      String [] table_name = meta_data.get(1);
      if (!table_name[0].equals(Global.TABLE_NAME_META)) {
        throw new WrongMetaFormatException();

      }
      if (!this.tableName.equals(table_name[1])) {
        throw new WrongMetaFormatException();
      }
    } catch (Exception e) {
      throw new WrongMetaFormatException();
    }

    try {
      String [] primary_key = meta_data.get(2);
      if (!primary_key[0].equals(Global.PRIMARY_KEY_INDEX_META)) {
        throw new WrongMetaFormatException();
      }
      this.primaryIndex = Integer.parseInt(primary_key[1]);
    } catch (Exception e) {
      throw new WrongMetaFormatException();
    }
    for (int i = 3; i < meta_data.size(); i++) {
      String [] column_info = meta_data.get(i);
      try {
        String name = column_info[0];
        ColumnType type = ColumnType.string2ColumnType(column_info[1]);
        boolean primary = column_info[2].equals("true");
        boolean notNull = column_info[3].equals("true");
        int maxLength = Integer.parseInt(column_info[4]);
        this.columns.add(new Column(name, type, primary, notNull, maxLength));
      } catch (Exception e) {
        throw new WrongMetaFormatException();
      }
    }
  }

  /**
   * [method] 持久化表（可能是切换数据库时，或者缓存置换？）
   * [note] 将表持久化
   * @exception
   */
  public synchronized void persist() throws DataFileNotFoundException, CustomIOException {
    serialize();
    persistMeta();
  }

  /**
   * [method] 恢复表
   * [note] 从持久化数据中恢复表
   * @exception IllegalArgumentException
   */
  private synchronized void recover(ArrayList<Row> rows) {
    for(Row row:rows){
      index.put(row.getEntries().get(primaryIndex), row);
    }
//    try {
//      lock.writeLock().lock();
//      for(Row row:rows){
//        index.put(row.getEntries().get(primaryIndex), row);
//      }
//    } finally {
//      lock.writeLock().unlock();
//    }
  }

  /**
   * [method] 插入行
   * @param row {Row} 待插入行
   * @exception IllegalArgumentException
   */
  public void insert(Row row) {
    try {
      index.put(row.getEntries().get(primaryIndex), row);
    } catch (DuplicateKeyException e){
      throw e;
    }

//    try {
//      lock.writeLock().lock();
//      index.put(row.getEntries().get(primaryIndex), row);
//    } finally {
//      lock.writeLock().unlock();
//    }
  }

  /**
   * [method] 由Row删除行
   * @exception IllegalArgumentException
   */
  public void delete(Row row) {
    index.remove(row.getEntries().get(primaryIndex));
//    try {
//      lock.writeLock().lock();
//      index.remove(row.getEntries().get(primaryIndex));
//    } finally {
//      lock.writeLock().unlock();
//    }
  }



  /**
   * [method] 由Entry（主键的）删除行
   * @exception IllegalArgumentException
   */
  public void delete(Entry entry) {
    index.remove(entry);
//    try {
//      lock.writeLock().lock();
//      index.remove(entry);
//    } finally {
//      lock.writeLock().unlock();
//    }
  }

  /**
   * [method] 删除所有行
   */
  public void delete() {
    index.clear();
    index = new BPlusTree<>();
//    try {
//      lock.writeLock().lock();
//      index.clear();
//      index = new BPlusTree<>();
//    } finally {
//      lock.writeLock().unlock();
//    }
  }

  /**
   * [method] 更新行
   * TODO 可能利用索引
   * @exception IllegalArgumentException
   */
  public void update(Row oldRow, Row newRow) {
    if(oldRow.getEntries().get(primaryIndex).compareTo(newRow.getEntries().get(primaryIndex))==0) {
        index.update(newRow.getEntries().get(primaryIndex), newRow);
    }
    else {
      try {
        insert(newRow);
      }
      catch (DuplicateKeyException e){
        throw e;
      }

      delete(oldRow);
    }
//    try {
//      lock.writeLock().lock();
//      if(oldRow.getEntries().get(primaryIndex).compareTo(newRow.getEntries().get(primaryIndex))==0) {
//        index.update(newRow.getEntries().get(primaryIndex), newRow);
//      }
//      else {
//        delete(oldRow);
//        insert(newRow);
//      }
//    } finally {
//      lock.writeLock().unlock();
//    }
  }

  /**
   * [method] 由Entry查找行
   * @exception IllegalArgumentException
   */
  public Row search(Entry entry) {
    // TODO
    return index.get(entry);
  }

  /**
   * [method] 更通用地，由column名和值查找行，这类函数还需根据查询模块的设计再调整和优化
   * @exception IllegalArgumentException
   */
  public ArrayList<Row> search(String column_name, Comparable value, int compare_type) {
    ArrayList<Row> res = new ArrayList<>();
    int i = 0;
    boolean has_column = false;
    for (Column c : columns) {
      if (c.getName() == column_name) {
        has_column = true;
        break;
      }
      i++;
    }
    assert has_column; // 参数检查打算在解析时进行，这里只是示意，由name找column index也可以封装
    if (i == primaryIndex) {
      if (compare_type == 0) {
        res.add(index.get(new Entry(value)));
      }
      // TODO
    } else {
      for (Iterator<Row> it = iterator(); it.hasNext(); ) {
        Row r = it.next();
        // 兼容 > == < 后续可以根据查询模块设计调整
        if (new Entry(value).compareTo(r.getEntries().get(i)) == compare_type) {
          res.add(r);
        }
      }
    }
    return res;
  }

  /**
   * [method] 序列化
   */
  private void serialize() throws DataFileNotFoundException, CustomIOException {
    // TODO
    persistentStorageData.serialize(iterator());
//    try {
//      lock.readLock().lock();
//      persistentStorageData.serialize(iterator());
//    } finally {
//      lock.readLock().unlock();
//    }
  }

  /**
   * [method] 反序列化
   */
  private ArrayList<Row> deserialize() throws ClassNotFoundException {
    // TODO
    return persistentStorageData.deserialize();
  }

  /**
   * [method] 删除table（包括表本身）
   */
  public void drop() {
    this.index.clear();
    this.tableMeta.deleteFile();
    this.persistentStorageData.deleteFile();
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

  public String getTableName() {
    return tableName;
  }

  public ArrayList<Column> getColumns() {
    return columns;
  }
}