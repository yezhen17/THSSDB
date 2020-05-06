package cn.edu.thssdb.schema;
import cn.edu.thssdb.exception.DuplicateDatabaseException;
import cn.edu.thssdb.exception.DuplicateTableException;
import cn.edu.thssdb.type.ColumnType;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.*;

public class ManagerTest {
    private Manager manager;

    @Before
    public void setUp() throws IOException, ClassNotFoundException {
        manager = Manager.getInstance();
    }
    @Test
    public void testCreateAndDrop() throws ClassNotFoundException {
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

}
