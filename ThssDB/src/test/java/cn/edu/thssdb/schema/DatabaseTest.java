package cn.edu.thssdb.schema;

import cn.edu.thssdb.exception.CustomIOException;
import cn.edu.thssdb.exception.DataFileNotFoundException;
import cn.edu.thssdb.exception.DuplicateTableException;
import cn.edu.thssdb.exception.MetaFileNotFoundException;
import cn.edu.thssdb.type.ColumnType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.print.attribute.standard.DateTimeAtCompleted;
import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;


public class DatabaseTest {
  private Database database;
  private HashMap<String, Table> tables;

  @Before
  public void setUp() {
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
  public void testPersistRecover() throws IOException {
    Column [] cs = new Column[2];
    cs[0] = new Column("c1", ColumnType.INT, true, true, 100);
    cs[1] = new Column("c2", ColumnType.STRING, false, true, 100);
    database.create("t1", cs, 0);
    database.persist();
    database.recover();
    assertEquals(database.get("t1").getColumns().get(0).toString(' '), "c1 INT true true 100");
    File fr = Paths.get("data", "db1", "db1.meta").toFile();
    BufferedReader reader = new BufferedReader(new FileReader(fr));
    String str;
    ArrayList<String []> lines = new ArrayList<>();
    while ((str = reader.readLine()) != null) {
      lines.add(str.split(" "));
    }
    assertEquals(lines.get(0)[0], "t1");
    reader.close();
    database.drop("t1");
  }

  @After
  public void clean() {
    database.wipeData();
  }
}
