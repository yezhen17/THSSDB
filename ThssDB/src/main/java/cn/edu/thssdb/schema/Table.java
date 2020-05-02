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
  private PersistentStorage<Row> persistentStorage;

  public Table(String databaseName, String tableName, Column[] columns) {
    // TODO
    persistentStorage = new PersistentStorage<>("tmp.data"); // this is the storage file name
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
    try {
      lock.readLock().lock();
      persistentStorage.serialize(iterator());
    } finally {
      lock.readLock().unlock();
    }
  }

  private ArrayList<Row> deserialize() throws ClassNotFoundException {
    // TODO
    return persistentStorage.deserialize();
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
