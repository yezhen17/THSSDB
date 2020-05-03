package cn.edu.thssdb.schema;

import cn.edu.thssdb.type.ColumnType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ColumnTest {
  Column test;
  @Before
  public void setUp() {
    test = new Column("name", ColumnType.INT, true, false, 100);
  }

  @Test
  public void testToString() {
    assertEquals(test.toString(' '), "name INT true false 100");
  }
}
