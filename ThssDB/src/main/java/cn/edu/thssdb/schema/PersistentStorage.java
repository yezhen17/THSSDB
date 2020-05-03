package cn.edu.thssdb.schema;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PersistentStorage<V> {
    public String folder_name;
    public String file_name;
    public PersistentStorage(String folder_name, String file_name) {
        this.folder_name = folder_name;
        this.file_name = file_name;
        File d = new File(folder_name);
        d.mkdirs();
        new File(folder_name + "\\" + file_name);
    }

    public void serialize_single(V input) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file_name));
        objectOutputStream.writeObject(input);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public void serialize(ArrayList<V> input) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file_name));
        for (V obj: input) {
            objectOutputStream.writeObject(obj);
        }
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public void serialize(Iterator<V> iterator) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file_name));
        while (iterator.hasNext()) {
            objectOutputStream.writeObject(iterator.next());
        }
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public V deserialize_single() throws ClassNotFoundException {
        try {
            ArrayList<V> objs = new ArrayList<>();
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file_name));
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
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file_name));
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
