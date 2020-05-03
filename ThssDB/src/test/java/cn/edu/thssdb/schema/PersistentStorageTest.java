package cn.edu.thssdb.schema;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PersistentStorageTest {
  private PersistentStorage<Row> persistentStorage;
  private ArrayList<Row> test;
  private Entry [][] entries;

  @Before
  public void setUp() {
    persistentStorage = new PersistentStorage<>("test", "tmp.data", true);
    test = new ArrayList<>();
    entries = new Entry[3][3];
    for (int i = 0; i < 3; i++) {
      entries[i][0] = new Entry(i);
      entries[i][1] = new Entry(i * 1.0);
      entries[i][2] = new Entry(String.valueOf(i));
    }
    Row row0 = new Row(entries[0]);
    Row row1 = new Row(entries[1]);
    Row row2 = new Row(entries[2]);
    test.add(row0);
    test.add(row1);
    test.add(row2);
  }

  @Test
  public void testSerialize() throws IOException {
    persistentStorage.serialize(test);
  }

  @Test
  public void testDeserialize() throws ClassNotFoundException {
    ArrayList<Row> res = persistentStorage.deserialize();
    int i = 0;
    for (Row row : res) {
      int j = 0;
      for (Entry entry : row.entries) {
        assertEquals(entry.value, entries[i][j].value);
        j++;
      }
      i++;
    }
    File f = new File("test\\tmp.data");
    f.delete();
  }

  @Test
  public void testSerializeIter() throws IOException {
    persistentStorage.serialize(test.iterator());
  }
}

