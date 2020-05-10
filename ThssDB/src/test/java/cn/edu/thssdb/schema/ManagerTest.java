package cn.edu.thssdb.schema;
import cn.edu.thssdb.exception.*;
import cn.edu.thssdb.type.ColumnType;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.*;

public class ManagerTest {
    private Manager manager;

    @Before
    public void setUp() {
        manager = Manager.getInstance();
    }
    @Test
    public void testCreateAndDrop() throws CustomIOException {
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
    public void testSwitchAndShow() throws ClassNotFoundException, DataFileNotFoundException, CustomIOException, MetaFileNotFoundException {
        manager.createDatabaseIfNotExists("db_1");
        assertTrue(manager.contains("db_1"));
        manager.createDatabaseIfNotExists("db_2");
        assertTrue(manager.contains("db_2"));
        assertEquals("db_1\ndb_2\n", manager.showAllDatabases());
        assertEquals(null, manager.getCurrentDatabaseName());
        manager.switchDatabase("db_1");
        assertEquals("db_1", manager.getCurrentDatabaseName());
        manager.switchDatabase("db_2");
        assertEquals("db_2", manager.getCurrentDatabaseName());
        manager.deleteDatabase("db_1");
        assertFalse(manager.contains("db_1"));
        manager.deleteDatabase("db_2");
        assertFalse(manager.contains("db_2"));
    }

}
