package cn.edu.thssdb.schema;

import cn.edu.thssdb.exception.CustomIOException;
import cn.edu.thssdb.exception.MetaFileNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class MetaTest {
  Meta test;
  @Before
  public void setUp() throws CustomIOException {
    test = new Meta("test", "tmp.meta");
  }

  @Test
  public void testCreateDelete() {
    File f = Paths.get("test", "tmp.meta").toFile();
    assertTrue(f.isFile());
    f.delete();
    assertThrows(MetaFileNotFoundException.class, () -> test.readFromFile());
  }

  @Test
  public void testReadOverWrite() throws CustomIOException, MetaFileNotFoundException {
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
