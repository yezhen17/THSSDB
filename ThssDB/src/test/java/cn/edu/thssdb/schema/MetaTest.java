package cn.edu.thssdb.schema;

import cn.edu.thssdb.exception.CustomIOException;
import cn.edu.thssdb.exception.TableMetaFileNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class MetaTest {
  Meta test;
  @Before
  public void setUp() {
    test = new Meta("test", "tmp.meta", true);
  }

  @Test
  public void testCreateDelete() {
    File f = new File("test\\tmp.meta");
    assertTrue(f.isFile());
    f.delete();
    assertThrows(TableMetaFileNotFoundException.class, () -> {
      test.readFromFile();
    });
  }

  @Test
  public void testReadOverWrite() throws CustomIOException, TableMetaFileNotFoundException {
    String s1 = "FIRST SECOND THIRD";
    String s2 = "1 2 3";
    ArrayList<String> a = new ArrayList<>();
    a.add(s1);
    a.add(s2);
    test.writeToFile(a);
    ArrayList<String []> res = test.readFromFile();
    assertEquals(res.get(0)[1], "SECOND");
    assertEquals(res.get(1)[2], "3");
  }

}
