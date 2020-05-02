package cn.edu.thssdb.schema;

import cn.edu.thssdb.schema.Row;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PersistentStorage<V> {
    private String file_name;
    public PersistentStorage(String file_name) {
        this.file_name = file_name;
    }

    private void serialize(ArrayList<V> input) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file_name));
        for (V obj: input) {
            objectOutputStream.writeObject(obj);
        }
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    private ArrayList<V> deserialize() throws IOException, ClassNotFoundException {
        ArrayList<V> objs = new ArrayList<>();
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file_name));
        while (true) {
            V obj = (V) objectInputStream.readObject();
            if (obj != null) {
                objs.add(obj);
            } else {
                break;
            }
        }
        objectInputStream.close();
        return objs;
    }
}
