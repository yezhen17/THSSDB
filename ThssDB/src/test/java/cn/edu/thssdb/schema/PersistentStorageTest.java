package cn.edu.thssdb.schema;

import cn.edu.thssdb.schema.PersistentStorage;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PersistentStorageTest {
    private PersistentStorage<Integer> persistentStorage;

    @Before
    public void setUp() {
        persistentStorage = new PersistentStorage<>("");
    }

    @Test
    public void testSerialize() {

    }

    @Test
    public void testDeserialize() {

    }
}

