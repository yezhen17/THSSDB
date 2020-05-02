package cn.edu.thssdb.schema;

import cn.edu.thssdb.exception.SerializeException;
import cn.edu.thssdb.index.BPlusTree;
import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/***************
 * [class] 表
 ***************/
public class Table implements Iterable<Row> {
  ReentrantReadWriteLock lock;            // 可重入读写锁
  private String databaseName;            // 数据库名称
  public String tableName;                // 表名称
  public ArrayList<Column> columns;       // 列 表
  public BPlusTree<Entry, Row> index;     // B+树索引
  private int primaryIndex;               // 主键索引

  /**
   * [方法] 构造方法
   */
  public Table(String databaseName, String tableName, Column[] columns) {
    // TODO
  }

  /**
   * [方法] 恢复表
   */
  private void recover() {
    // TODO
  }

  /**
   * [方法] 插入行
   */
  public void insert() {
    // TODO
  }

  /**
   * [方法] 删除行
   */
  public void delete() {
    // TODO
  }

  /**
   * [方法] 更新行
   */
  public void update() {
    // TODO
  }

  /**
   * [方法] 序列化
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
   * [方法] 反序列化
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
   * [内部类] 表迭代器（按行）
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
   * [方法] 创建迭代器（按行）
   */
  @Override
  public Iterator<Row> iterator() {
    return new TableIterator(this);
  }
}
