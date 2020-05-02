package cn.edu.thssdb.schema;

import cn.edu.thssdb.exception.SerializeException;
import cn.edu.thssdb.index.BPlusTree;
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

  /**
   * [method] 构造方法
   * @param databaseName {String} 数据库名称
   * @param tableName {String} 表名称
   * @param columns {Column[]} 列定义表
   */
  public Table(String databaseName, String tableName, Column[] columns) {
    this.databaseName = databaseName;
    this.tableName = tableName;
    this.columns = new ArrayList<>(Arrays.asList(columns));
  }

  /**
   * [method] 恢复表
   * [note] 从持久化数据中恢复表
   * @exception TODO
   */
  private void recover() {
    // TODO
  }

  /**
   * [method] 插入行
   * @param row {Row} 待插入行
   * @exception TODO
   */
  public void insert(Row row) {
    // TODO
  }

  /**
   * [method] 删除行
   * TODO 可能利用索引
   * @exception TODO
   */
  public void delete() {
    // TODO
  }

  /**
   * [method] 更新行
   * TODO 可能利用索引
   * @exception TODO
   */
  public void update() {
    // TODO
  }

  /**
   * [method] 序列化
   */
  private void serialize() throws IOException {
    // TODO
    // 序列化和反序列化的基础用法，不过怎么和索引结合还是个问题
    // 可参考 https://blog.csdn.net/u014240769/article/details/84903409
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("temp.txt"));
    Iterator<Row> row_iter = iterator();
    while (row_iter.hasNext()) {
      objectOutputStream.writeObject(row_iter.next());
    }
    objectOutputStream.flush();
    objectOutputStream.close();
  }

  /**
   * [method] 反序列化
   */
  private ArrayList<Row> deserialize() throws IOException, ClassNotFoundException {
    // TODO
    ArrayList<Row> rows = new ArrayList<>();
    ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("temp.txt"));
    while (true) {
      Row row = (Row) objectInputStream.readObject();
      if (row != null) {
        rows.add(row);
      } else {
        break;
      }
    }
    objectInputStream.close();
    return rows;
    // return null;
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
}
