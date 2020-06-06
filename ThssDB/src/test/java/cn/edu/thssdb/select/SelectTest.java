package cn.edu.thssdb.select;


import cn.edu.thssdb.operation.BaseOperation;
import cn.edu.thssdb.parser.MyParser;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class SelectTest {

  @Before
  public void setUp() {

  }

  @Test
  public void testParsing() {
    ArrayList<BaseOperation> operations = MyParser.getOperations(";;create database mydb;" +
            "CREATE TABLE tableName(attrName1 int primary key, attrName2 long, " +
                    "attrName3 string(10) NOT NULL,attrName4 float NOT NULL primary key, " +
                    "attrName5 double primary key NOT NULL, PRIMARY KEY(attrName1));" +
            "create user username identified by '123';" +
            "DELETE  FROM  tableName  WHERE  (attrName = attValue) and (a=b);" +
            "drop database mydb; " +
            "drop database if exists mydb;" +
            "use db;" +
            "drop table mytable; " +
            "drop table if exists mytable;" +
            "show table mytable;" +
            "INSERT INTO tableName(attrName1, attrName2, attrNameN) VALUES (1, 2, null);" +
            "select count(*) from t1, t2 where t1.a = t2.b or a = 2 and 3 <> 5;" +
            "UPDATE  tableName  SET  attrName = '123456'  WHERE  attrName = attrValue;" +
            "select a + 1, 1 / b, c from t1 join t2 on t1.a = t2.a and t1.b = t2.b " +
                    "where t1.a = t2.b or a = 2 and 3 <> 5 order by t1.a desc;");
    assertEquals(operations.size(), 14);
  }

}
