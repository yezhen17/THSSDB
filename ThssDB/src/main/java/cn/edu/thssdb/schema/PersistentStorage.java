package cn.edu.thssdb.schema;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PersistentStorage<V> {
  private String folder_name;
  private String file_name;
  private String full_path;
  public PersistentStorage(String folder_name, String file_name, boolean just_created) {
    this.folder_name = folder_name;
    this.file_name = file_name;
    this.full_path = folder_name + "\\" + file_name;
    if (!just_created) {
      File d = new File(folder_name);
      d.mkdirs();
      new File(this.full_path);
    }
  }

  public void serialize_single(V input) throws IOException {
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(full_path));
    objectOutputStream.writeObject(input);
    objectOutputStream.flush();
    objectOutputStream.close();
  }

  public void serialize(ArrayList<V> input) throws IOException {
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(full_path));
    for (V obj: input) {
      objectOutputStream.writeObject(obj);
    }
    objectOutputStream.flush();
    objectOutputStream.close();
  }

  public void serialize(Iterator<V> iterator) throws IOException {
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(full_path));
    while (iterator.hasNext()) {
      objectOutputStream.writeObject(iterator.next());
    }
    objectOutputStream.flush();
    objectOutputStream.close();
  }

  public V deserialize_single() throws ClassNotFoundException {
    try {
      ArrayList<V> objs = new ArrayList<>();
      ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(full_path));
      V obj = (V) objectInputStream.readObject();
      objectInputStream.close();
      return obj;
    } catch (IOException e) {
      return null;
    }
  }

  public ArrayList<V> deserialize() throws ClassNotFoundException {
    try {
      ArrayList<V> objs = new ArrayList<>();
      ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(full_path));
      while (true) {
        try {
          V obj = (V) objectInputStream.readObject();
          objs.add(obj);
        } catch (EOFException e) {
          break;
        }
      }
      objectInputStream.close();
      return objs;
    } catch (IOException e) {
      return null;
    }
  }
}
