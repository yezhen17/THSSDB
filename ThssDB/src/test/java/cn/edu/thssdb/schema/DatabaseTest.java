package cn.edu.thssdb.schema;

import cn.edu.thssdb.exception.CustomIOException;
import cn.edu.thssdb.exception.DataFileNotFoundException;
import cn.edu.thssdb.exception.DuplicateTableException;
import cn.edu.thssdb.exception.MetaFileNotFoundException;
import cn.edu.thssdb.type.ColumnType;
import org.junit.Before;
import org.junit.Test;

import javax.print.attribute.standard.DateTimeAtCompleted;
import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;


public class DatabaseTest {
  private Database database;
  private HashMap<String, Table> tables;

  @Before
  public void setUp() throws IOException, ClassNotFoundException {
    database = new Database("db1");
    tables = new HashMap<>();
//    Column [] cs = new Column[2];
//    cs[0] = new Column("c1", ColumnType.INT, true, true, 100);
//    cs[1] = new Column("c2", ColumnType.STRING, false, true, 100);
//    tables.put("t1", new Table("db1", "t1", cs, 0));

  }
  @Test
  public void testCreateAndDrop()  {
    boolean flag = false;
    Column [] cs = new Column[2];
    cs[0] = new Column("c1", ColumnType.INT, true, true, 100);
    cs[1] = new Column("c2", ColumnType.STRING, false, true, 100);
    database.create("table_1", cs, 0);
    assertTrue(database.contains("table_1"));
    database.create("table_2", cs, 0);
    assertTrue(database.contains("table_2"));
    try {
      database.create("table_2", cs, 0);
    }
    catch (DuplicateTableException e) {
      flag = true;
    }
    assertTrue(flag);
    database.drop("table_1");
    assertFalse(database.contains("table_1"));
    database.drop("table_2");
    assertFalse(database.contains("table_2"));
  }

  @Test
  public void testPersist() throws IOException {
    Column [] cs = new Column[2];
    cs[0] = new Column("c1", ColumnType.INT, true, true, 100);
    cs[1] = new Column("c2", ColumnType.STRING, false, true, 100);
    database.create("t1", cs, 0);
    database.persist();
    File d = new File("data\\db1\\t1");
    if (!d.exists()) {
      d.mkdir();
    }
    File fr = new File("data\\db1\\db1.meta");
    BufferedReader reader = new BufferedReader(new FileReader(fr));
    String str;
    ArrayList<String []> lines = new ArrayList<>();
    while ((str = reader.readLine()) != null) {
      lines.add(str.split(" "));
    }
    assertEquals(lines.get(0)[0], "t1");
//    assertEquals(lines.get(1)[0], "db1");
//    assertEquals(lines.get(2)[0], "0");
//    assertEquals(lines.get(3)[0], "c1");
//    assertEquals(lines.get(3)[1], "INT");
    reader.close();
  }

  @Test
  public void testRecover() throws IOException, ClassNotFoundException {
    File d = new File("data\\db1");
    if (!d.exists()) {
      d.mkdir();
    }
    File f = new File("data\\db1\\db1.meta");
    FileWriter fw = new FileWriter(f);
    fw.write("t1");
    fw.close();
    File d2 = new File("data\\db1\\t1");
    if (!d2.exists()) {
      d2.mkdir();
    }
    File f2 = new File("data\\db1\\t1\\t1.meta");
    FileWriter fw2 = new FileWriter(f2);
    fw2.write("DATABASE_NAME db1\nTABLE_NAME t1\nPRIMARY_KEY_INDEX 0\nc1 INT true true 100\nc2 STRING false true 100");
    fw2.close();
    tables.put("t1", new Table("db1", "t1"));
    database.recover();
    assertEquals(tables.get("t1").columns.get(0).toString(' '), "c1 INT true true 100");
    assertEquals(tables.get("t1").columns.get(1).toString(' '), "c2 STRING false true 100");
  }
}
