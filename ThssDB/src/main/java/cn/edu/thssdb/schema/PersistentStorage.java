package cn.edu.thssdb.schema;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PersistentStorage<V> {
    public String file_name;
    public PersistentStorage(String file_name) {
        this.file_name = file_name;
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
