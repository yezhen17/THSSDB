package cn.edu.thssdb.schema;

import cn.edu.thssdb.exception.DuplicateTableException;
import cn.edu.thssdb.exception.SerializeException;
import cn.edu.thssdb.exception.TableMetaFileNotFoundException;
import cn.edu.thssdb.exception.WrongMetaFormatException;
import cn.edu.thssdb.index.BPlusTree;
import cn.edu.thssdb.type.ColumnType;
import cn.edu.thssdb.utils.Global;
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
  ReentrantReadWriteLock lock;            // 可重入读写锁
  private String databaseName;            // 数据库名称
  public String tableName;                // 表名称
  public ArrayList<Column> columns;       // 列定义表
  public BPlusTree<Entry, Row> index;     // B+树索引
  private int primaryIndex;               // 主键索引
  private PersistentStorage<Row> persistentStorageData; // 数据持久化
  private Meta tableMeta;

  /**
   * [method] 构造方法
   * @param databaseName {String} 数据库名称
   * @param tableName {String} 表名称
   * @param columns {Column[]} 列定义表
   * @param primaryIndex {int} 主键索引
   */
  public Table(String databaseName, String tableName, Column[] columns, int primaryIndex) throws IOException {
    // 还有很大的问题，需要区分新建Table和加载；或者增加一个加载的函数
    this.databaseName = databaseName;
    this.tableName = tableName;
    this.columns = new ArrayList<>(Arrays.asList(columns));
    this.primaryIndex = primaryIndex;
    String folder = Global.DATA_ROOT_FOLDER + "\\" + databaseName + "\\" + tableName;
    String meta_name = tableName + ".meta";
    String data_name = tableName + ".data";
    this.persistentStorageData = new PersistentStorage<>(folder, data_name);
    this.tableMeta = new Meta(folder, meta_name, true);
    this.index = new BPlusTree<>();
    ArrayList<String> meta_data = new ArrayList<>();
    meta_data.add(Global.DATABASE_NAME_META + " " + databaseName);
    meta_data.add(Global.TABLE_NAME_META + " " + tableName);
    meta_data.add(Global.PRIMARY_KEY_INDEX_META + " " + String.valueOf(primaryIndex));
    for (Column column : columns) {
      meta_data.add(column.toString(' '));
    }
    this.tableMeta.writeToFile(meta_data);
  }

  public Table(String databaseName, String tableName) throws IOException {
    // TODO 加载元数据
    this.databaseName = databaseName;
    this.tableName = tableName;
    String folder = Global.DATA_ROOT_FOLDER + "\\" + databaseName + "\\" + tableName;
    String meta_name = tableName + ".meta";
    String data_name = tableName + ".data";
    this.persistentStorageData = new PersistentStorage<>(folder, data_name);
    this.tableMeta = new Meta(folder, meta_name, false);
    ArrayList<String []> meta_data = this.tableMeta.readFromFile();
    String [] database_name = meta_data.get(0);
    try {
      if (database_name[0] != Global.DATABASE_NAME_META) {
        throw new WrongMetaFormatException();

      }
      if (this.databaseName != database_name[1]) {
        throw new WrongMetaFormatException();
      }
    } catch (Exception e) {
      throw new WrongMetaFormatException();
    }
    String [] table_name = meta_data.get(1);
    try {
      if (table_name[0] != Global.TABLE_NAME_META) {
        throw new WrongMetaFormatException();

      }
      if (this.tableName != table_name[1]) {
        throw new WrongMetaFormatException();
      }
    } catch (Exception e) {
      throw new WrongMetaFormatException();
    }
    String [] primary_key = meta_data.get(2);
    try {
      if (primary_key[0] != Global.PRIMARY_KEY_INDEX_META) {
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
        boolean primary = column_info[2] == "true";
        boolean notNull = column_info[3] == "true";
        int maxLength = Integer.parseInt(column_info[4]);
        this.columns.add(new Column(name, type, primary, notNull, maxLength));
      } catch (Exception e) {
        throw new WrongMetaFormatException();
      }
    }

  }

  /**
   * [method] 恢复表
   * [note] 从持久化数据中恢复表
   * @exception IllegalArgumentException
   */
  private void recover(ArrayList<Row> rows) {
    try {
      lock.writeLock().lock();
      for(Row row:rows){
        index.put(row.getEntries().get(primaryIndex), row);
      }
    } finally {
      lock.writeLock().unlock();
    }
  }

  /**
   * [method] 插入行
   * @param row {Row} 待插入行
   * @exception IllegalArgumentException
   */
  public void insert(Row row) {
    try {
      lock.writeLock().lock();
      index.put(row.getEntries().get(primaryIndex),row);
    } finally {
      lock.writeLock().unlock();
    }
  }

  /**
   * [method] 删除行
   * TODO 可能利用索引
   * @exception IllegalArgumentException
   */
  public void delete(Row row) {
    try {
      lock.writeLock().lock();
      index.remove(row.getEntries().get(primaryIndex));
    } finally {
      lock.writeLock().unlock();
    }
  }

  /**
   * [method] 更新行
   * TODO 可能利用索引
   * @exception IllegalArgumentException
   */
  public void update(Row oldRow, Row newRow) {
    try {
      lock.writeLock().lock();
      if(oldRow.getEntries().get(primaryIndex).compareTo(newRow.getEntries().get(primaryIndex))==0) {
        index.update(newRow.getEntries().get(primaryIndex), newRow);
      }
      else{
        delete(oldRow);
        insert(newRow);
      }
    } finally {
      lock.writeLock().unlock();
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