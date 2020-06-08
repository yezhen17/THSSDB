package cn.edu.thssdb.schema;

import cn.edu.thssdb.exception.KeyNotExistException;
import cn.edu.thssdb.index.BPlusTree;
import cn.edu.thssdb.type.ColumnType;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertSame;


public class TableTest {
  private Database database;
  private Table table;
  private ArrayList<Entry> entries;
  private ArrayList<Row> rows;
  private HashMap<Entry,Row> map;
  private int primaryIndex=0;

  @Before
  public void setUp() {
    Column c0 = new Column("c0", ColumnType.INT,true,true,0);
    Column c1 = new Column("c1", ColumnType.LONG,false,false,0);
    Column c2 = new Column("c2", ColumnType.DOUBLE,false,false,0);
    Column c3 = new Column("c3", ColumnType.FLOAT,false,false,0);
    Column c4 = new Column("c4", ColumnType.STRING,false,false,100);
    Column[] columns = new Column[5];
    columns[0] = c0;
    columns[1] = c1;
    columns[2] = c2;
    columns[3] = c3;
    columns[4] = c4;
    database = new Database("testdb");
    database.create("test", columns, primaryIndex);
    table = database.get("test");
    // table = new Table("testdb","test", columns, primaryIndex);

    entries = new ArrayList<>();
    rows = new ArrayList<>();
    map = new HashMap<>();

    for (int i = 0; i < 5; i++){
      Entry[] entry = new Entry[5];
      entry[0] = new Entry(i);
      entry[1] = new Entry(2.0);
      entry[2] = new Entry(3.0);
      entry[3] = new Entry(4.0);
      entry[4] = new Entry("test str");
      entries.add(entry[primaryIndex]);
      rows.add(new Row(entry));
      map.put(entry[primaryIndex], new Row(entry));
    }

    for (int i = 0; i < 4; i++){
      table.insert(rows.get(i));
    }
  }

  @Test
  public void testRecover() {
    try {
      Entry[] entry = new Entry[5];
      entry[0] = new Entry(7);
      entry[1] = new Entry((long) 1);
      entry[2] = new Entry(2.0);
      entry[3] = new Entry((double) 3.0);
      entry[4] = new Entry("test str2");
      ArrayList<Row> rows = new ArrayList<>();
      rows.add(new Row(entry));
      Method method = table.getClass().getDeclaredMethod("recover",rows.getClass());
      method.setAccessible(true);
      method.invoke(table, rows);
      assertEquals(rows.get(0),table.search(new Entry(7)));
    }
    catch (NoSuchMethodException e){
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testSearch() {
    for(int i=0;i<4;i++){
      assertEquals(rows.get(i),table.search(entries.get(i)));
    }
  }

  @Test
  public void testInsert() {
    table.insert(rows.get(4));
    assertEquals(rows.get(4),table.search(entries.get(4)));
  }

  @Test
  public void testDelete() {
    try{
      table.delete(rows.get(3));
      assertEquals(table.index.size(),3);
      for(int i=0;i<3;i++){
        assertEquals(rows.get(i),table.search(entries.get(i)));
      }
    }
    catch (KeyNotExistException e){
      e.printStackTrace();
    }
  }



  @Test
  public void testUpdate() {
    try{
      table.update(rows.get(3),rows.get(4));
      assertEquals(table.search(entries.get(3)),null);
    }
    catch (KeyNotExistException e){
      assertEquals(table.search(entries.get(4)),rows.get(4));
    }

    Entry[] entry = new Entry[5];
    entry[0] = new Entry(2);
    entry[1] = new Entry(2.0);
    entry[2] = new Entry(3.0);
    entry[3] = new Entry(3.0);
    entry[4] = new Entry("test str");
    Row row = new Row(entry);
    table.update(rows.get(2),row);
    assertEquals(table.search(entries.get(2)),row);
  }

  @Test
  public void testSerialize() {
    try {
      Method method = Table.class.getDeclaredMethod("serialize",null);
      method.setAccessible(true);
      method.invoke(table,null);

      Field storageField = table.getClass().getDeclaredField("persistentStorageData");
      storageField.setAccessible(true);

      Field filenameField = storageField.get(table).getClass().getDeclaredField("full_path");
      filenameField.setAccessible(true);

      File f = new File((String) filenameField.get(storageField.get(table)));
      f.delete();
    }
    catch (NoSuchMethodException e){
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testDeserialize() {
    try {
      Method sMethod = Table.class.getDeclaredMethod("serialize",null);
      sMethod.setAccessible(true);
      sMethod.invoke(table,null);

      Method method = Table.class.getDeclaredMethod("deserialize",null);
      method.setAccessible(true);
      ArrayList<Row> res = (ArrayList<Row>) method.invoke(table);
      for (int i = 0; i < 4; i++) {
        assertEquals(res.get(i).toString(), rows.get(i).toString());
      }
      Field storageField = table.getClass().getDeclaredField("persistentStorageData");
      storageField.setAccessible(true);

      Field filenameField = storageField.get(table).getClass().getDeclaredField("full_path");
      filenameField.setAccessible(true);

      File f = new File((String) filenameField.get(storageField.get(table)));
      f.delete();
    }
    catch (NoSuchMethodException e){
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testIterator() {
    Iterator<Row> iterator = table.iterator();
    int idx = 0;
    while (iterator.hasNext()){
      Row row = iterator.next();
      assertEquals(row.getEntries(),map.get(row.getEntries().get(primaryIndex)).getEntries());
    }
  }

  @After
  public void clean() {
    database.wipeData();
  }
}
