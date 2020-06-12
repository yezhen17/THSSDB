package cn.edu.thssdb.schema;

import cn.edu.thssdb.exception.*;
import cn.edu.thssdb.index.BPlusTree;
import cn.edu.thssdb.index.BPlusTreeIterator;
import cn.edu.thssdb.type.ColumnType;
import cn.edu.thssdb.type.ComparisonType;
import cn.edu.thssdb.utils.Global;
import javafx.util.Pair;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/***************
 * [class] 表
 ***************/
public class Table implements Iterable<Row> {
  public ReentrantReadWriteLock lock;    // 可重入读写锁
  private String databaseName;            // 数据库名称
  private String tableName;                // 表名称
  private String folder;

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
  public Table(String databaseName, String tableName, Column[] columns, int primaryIndex) {
    initData(databaseName, tableName);
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
  public Table(String databaseName, String tableName) {
    initData(databaseName, tableName);
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
   * @exception IllegalArgumentException
   */
  private void initData(String databaseName, String tableName) throws CustomIOException {
    this.databaseName = databaseName;
    this.tableName = tableName;
    folder = Paths.get(Global.DATA_ROOT_FOLDER, databaseName, tableName).toString();
    String meta_name = tableName + ".meta";
    String data_name = tableName + ".data";
    this.persistentStorageData = new PersistentStorage<>(folder, data_name);
    this.tableMeta = new Meta(folder, meta_name);
  }

  /**
   * [method] 将metadata持久化
   * @exception CustomIOException
   */
  private void persistMeta() throws CustomIOException {
    ArrayList<String> meta_data = new ArrayList<>();
    meta_data.add(Global.DATABASE_NAME_META + " " + databaseName);
    meta_data.add(Global.TABLE_NAME_META + " " + tableName);
    meta_data.add(Global.PRIMARY_KEY_INDEX_META + " " + primaryIndex);
    for (Column column : columns) {
      meta_data.add(column.toString(' '));
    }
    this.tableMeta.writeToFile(meta_data);
  }

  /**
   * [method] 恢复metadata
   * @exception MetaFileNotFoundException, CustomIOException
   */
  private void recoverMeta() {
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
  public synchronized void persist() {
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
  }

  /**
   * [method] 插入行
   * @param row {String} 待插入行的字符串形式
   * @exception IllegalArgumentException
   */
  public void insert(String row) {
    try {
      String[] info = row.split(",");
      ArrayList<Entry> entries = new ArrayList<>();
      int i = 0;
      for (Column c: columns) {
        entries.add(new Entry(ColumnType.getColumnTypeValue(c.getType(), info[i])));
        i++;
      }
      index.put(entries.get(primaryIndex), new Row(entries));
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * [method] 由Row删除行
   * @exception IllegalArgumentException
   */
  public void delete(Row row) {
    index.remove(row.getEntries().get(primaryIndex));
  }



  /**
   * [method] 由Entry（主键的）删除行
   * @exception IllegalArgumentException
   */
  public void delete(Entry entry) {
    index.remove(entry);
  }

  /**
   * [method] 由值（主键的）删除行
   * @exception IllegalArgumentException
   */
  public void delete(String val) {
    ColumnType c = columns.get(primaryIndex).getType();
    Entry primaryEntry = new Entry(ColumnType.getColumnTypeValue(c, val));
    index.remove(primaryEntry);
  }

  /**
   * [method] 删除所有行
   */
  public void delete() {
    index.clear();
    index = new BPlusTree<>();
  }

  /**
   * [method] 更新行
   * TODO 可能利用索引
   * @exception IllegalArgumentException
   */
  public void update(Row oldRow, Row newRow) {
    // index.update(newRow.getEntries().get(primaryIndex), newRow);
    if(oldRow.getEntries().get(primaryIndex).compareTo(newRow.getEntries().get(primaryIndex))==0) {
        index.update(newRow.getEntries().get(primaryIndex), newRow);
    }
    else {
      try {
        delete(oldRow);
        insert(newRow);
      }
      catch (DuplicateKeyException e){
        throw e;
      }
    }
  }

  public void updateNoRemove(Row oldRow, Row newRow) {
    index.updateNoRemove(oldRow.getEntries().get(primaryIndex), newRow);
  }

  public void updateAll(int idx, Comparable val) {
    BPlusTreeIterator<Entry, Row> it = index.iterator();
    while (it.hasNext()) {
      it.next().getValue().getEntries().set(idx, new Entry(val));
    }
  }

  /**
   * [method] 由Entry查找行
   * @exception IllegalArgumentException
   */
  public Row search(Entry entry) {
    // TODO
    return index.get(entry);
  }

  public void addColumn(String name, ColumnType type, int maxLen) {
    columns.add(new Column(name, type, false, false, maxLen));
    Iterator<Row> it = iterator();
    while (it.hasNext()) {
      it.next().getEntries().add(new Entry(null));
    }
  }

  public void dropColumn(String name) {
    int i = 0;
    for (Column column: columns) {
      if (column.getName().equals(name)) {
        break;
      }
      i++;
    }
    if (i == primaryIndex) throw new AlterColumnException("Exception: Cannot drop primary index column!");
    if (i == columns.size()) throw new UnknownColumnException();
    else columns.remove(i);
    Iterator<Row> it = iterator();
    while (it.hasNext()) {
      it.next().getEntries().remove(i);
    }
  }

  public void alterColumn(String name, ColumnType type, int maxLen) {
    int old_max_len;
    int i = 0;
    for (Column column: columns) {
      if (column.getName().equals(name)) {
        break;
      }
      i++;
    }
    if (i == primaryIndex) throw new AlterColumnException("Exception: Cannot alter primary index column!");
    if (i == columns.size()) throw new UnknownColumnException();
    else {
      ColumnType old_type = columns.get(i).getType();
      if (old_type == type && type != ColumnType.STRING ||
              old_type == type && type == ColumnType.STRING && columns.get(i).getMaxLength() == maxLen) return;
      else {
        if (old_type != ColumnType.STRING) {
          if (type == ColumnType.STRING) throw new AlterColumnException("Exception: Column cannot be altered to new type!");
        } else if (old_type == ColumnType.STRING) {
          if (type != ColumnType.STRING) throw new AlterColumnException("Exception: Column cannot be altered to new type!");
        }
        Column c = columns.remove(i);
        old_max_len = c.getMaxLength();
        columns.add(i, new Column(name, type, false, c.isNotNull(), maxLen));
      }

    }
    Iterator<Row> it = iterator();
    while (it.hasNext()) {
      ArrayList<Entry> entries = it.next().getEntries();
      Entry entry = entries.get(i);
      if (entry.value == null) continue;
      if (type == ColumnType.STRING) {
        if (((String)entry.value).length() > maxLen) {
          boolean not_null = columns.remove(i).isNotNull();
          columns.add(i, new Column(name, type, false, not_null, old_max_len));
          throw new AlterColumnException("Exception: String exceeds max length!");
        }
      } else {
        entry.value = ColumnType.getColumnTypeValue(type, Double.valueOf(entry.toString()));
      }

    }
  }

  /**
   * [method] 序列化
   */
  private void serialize() {
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
  private ArrayList<Row> deserialize() {
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
    new File(this.folder).delete();
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