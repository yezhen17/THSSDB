package cn.edu.thssdb.schema;

import cn.edu.thssdb.exception.SerializeException;
import cn.edu.thssdb.index.BPlusTree;
import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Table implements Iterable<Row> {
  ReentrantReadWriteLock lock;
  private String databaseName;
  public String tableName;
  public ArrayList<Column> columns;
  public BPlusTree<Entry, Row> index;
  private int primaryIndex;

  public Table(String databaseName, String tableName, Column[] columns) {
    // TODO
  }

  private void recover() {
    // TODO
  }

  public void insert() {
    // TODO
  }

  public void delete() {
    // TODO
  }

  public void update() {
    // TODO
  }

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

  @Override
  public Iterator<Row> iterator() {
    return new TableIterator(this);
  }
}
