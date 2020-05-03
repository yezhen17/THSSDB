package cn.edu.thssdb.schema;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;


public class DatabaseTest {
    private HashMap<String, Table> tables;

    @Before
    public void setUp() throws IOException {
        tables.put("test1", new Table("db1", "t1", null, 0));
    }

    @Test
    public void testPersist() throws IOException {

    }

    @Test
    public void testRecover() throws IOException {

    }
}
