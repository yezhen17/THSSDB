package cn.edu.thssdb.schema;
import cn.edu.thssdb.exception.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ManagerTest {
  private Manager manager;

  @Before
  public void setUp() {
    manager = Manager.getInstance();
  }
  @Test
  public void testCreateAndDrop() {
    if (manager.contains("db_1")) manager.deleteDatabase("db_1");
    if (manager.contains("db_2")) manager.deleteDatabase("db_2");
    boolean flag = false;
    manager.createDatabaseIfNotExists("db_1");
    assertTrue(manager.contains("db_1"));
    manager.createDatabaseIfNotExists("db_2");
    assertTrue(manager.contains("db_2"));
    try {
      manager.createDatabaseIfNotExists("db_2");
    }
    catch (DuplicateDatabaseException e) {
      flag = true;
    }
    assertTrue(flag);
    manager.deleteDatabase("db_1");
    assertFalse(manager.contains("db_1"));
    manager.deleteDatabase("db_2");
    assertFalse(manager.contains("db_2"));
  }

  @Test
  public void testSwitchAndShow() {
    if (manager.contains("db_1")) manager.deleteDatabase("db_1");
    if (manager.contains("db_2")) manager.deleteDatabase("db_2");
    manager.createDatabaseIfNotExists("db_1");
    assertTrue(manager.contains("db_1"));
    manager.createDatabaseIfNotExists("db_2");
    assertTrue(manager.contains("db_2"));
    manager.switchDatabase("db_1");
    manager.switchDatabase("db_2");
    manager.deleteDatabase("db_1");
    assertFalse(manager.contains("db_1"));
    manager.deleteDatabase("db_2");
    assertFalse(manager.contains("db_2"));
  }

}
