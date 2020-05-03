package cn.edu.thssdb.schema;

import cn.edu.thssdb.type.ColumnType;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class ColumnTest {
    @Before
    public void setUp() {
        Column test = new Column("name", ColumnType.INT, true, false, 100);
        System.out.println(test.toString(' '));
    }

    @Test
    public void testPersist() throws IOException {

    }

    @Test
    public void testRecover() throws IOException {

    }
}
