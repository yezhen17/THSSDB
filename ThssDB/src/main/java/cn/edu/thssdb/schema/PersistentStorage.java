package cn.edu.thssdb.schema;

import cn.edu.thssdb.exception.CustomIOException;
import cn.edu.thssdb.exception.WrongDataException;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

public class PersistentStorage<V> {
  private String folder_name;
  private String file_name;
  private String full_path;
  public PersistentStorage(String folder_name, String file_name) {
    this.folder_name = folder_name;
    this.file_name = file_name;
    this.full_path = Paths.get(folder_name, file_name).toString();
    File d = new File(this.folder_name);
    if (!d.isDirectory()) {
      d.mkdirs();
    }
    File f = new File(this.full_path);
    if (!f.isFile()) {
      try {
        f.createNewFile();
      } catch (IOException e) {
        throw new CustomIOException();
      }
    }
  }

  public void serialize(ArrayList<V> input) {
    try {
      FileOutputStream fos = new FileOutputStream(full_path);
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
      for (V obj: input) {
        objectOutputStream.writeObject(obj);
      }
      objectOutputStream.flush();
      objectOutputStream.close();
    } catch (IOException e) {
      throw new CustomIOException();
    }
  }

  public void serialize(Iterator<V> iterator) {
    try {
      FileOutputStream fos = new FileOutputStream(full_path);
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
      while (iterator.hasNext()) {
        objectOutputStream.writeObject(iterator.next());
      }
      objectOutputStream.flush();
      objectOutputStream.close();
    } catch (IOException e) {
      throw new CustomIOException();
    }

  }

  public ArrayList<V> deserialize() {
    try {
      ArrayList<V> objs = new ArrayList<>();
      FileInputStream fis = new FileInputStream(full_path);
      ObjectInputStream objectInputStream = new ObjectInputStream(fis);
      while (true) {
        try {
          V obj = (V) objectInputStream.readObject();
          objs.add(obj);
        } catch (EOFException e) {
          break;
        } catch (ClassNotFoundException e) {
          objectInputStream.close();
          new File(this.full_path).delete();
          throw new WrongDataException();
        }
      }
      objectInputStream.close();
      return objs;
    } catch (IOException e) {
      new File(this.full_path).delete();
      return new ArrayList<>();
    }
  }

  public void deleteFile() {
    File f = new File(this.full_path);
    f.delete();
  }
}
