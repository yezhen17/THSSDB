package cn.edu.thssdb.parser;

import cn.edu.thssdb.operation.CreateDatabaseOperation;
import cn.edu.thssdb.operation.CreateTableOperation;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.ArrayList;

public class GrammarTest {
  public static void main(String[] args) {

    System.out.println(Double.valueOf(0.0).compareTo(-Double.MAX_VALUE));
    
    CharStream input = CharStreams.fromString("rollback t;");
    // use db;CREATE TABLE tableName(a1 int primary key, primary key(a1));iNSERT INTO tableName(a1) VALUES (1);delete from tableName where a1 = 1;
    // CREATE TABLE tableName(a1 int primary key, primary key(a1));
    // INSERT INTO tableName(a1) VALUES (1);
    // delete from tableName where a1 = 1;
//            "create database mydb;"
//            "CREATE TABLE tableName(attrName1 int primary key, attrName2 long, attrName3 string(10) NOT NULL,attrName4 float NOT NULL primary key, attrName5 double primary key NOT NULL, PRIMARY KEY(attrName1));"
//            "create user username identified by '123' "
//            "DELETE  FROM  tableName  WHERE  (attrName = attValue) and (a=b);"
//            "drop database mydb; "
//            "drop database if exists mydb;"
//            "use db;"
//            "drop table mytable; "
//            "drop table if exists mytable;"
//            "show table mytable"
//            "INSERT INTO tableName(attrName1, attrName2, attrNameN) VALUES (1, 2, null)"
//            "select count(*) from t1, t2 where t1.a = t2.b or a = 2 and 3 <> 5;" +
//            "UPDATE  tableName  SET  attrName = '123456'  WHERE  attrName = attrValue "
//            "select a + 1, 1 / b, c from t1 join t2 on t1.a = t2.a and t1.b = t2.b " +
//                    "where t1.a = t2.b or a = 2 and 3 <> 5 order by t1.a desc;");

    SQLLexer lexer = new SQLLexer(input);
    lexer.removeErrorListeners();
    lexer.addErrorListener(MyErrorListener.INSTANCE);
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);
    SQLParser parser = new SQLParser(tokenStream);
    parser.removeErrorListeners();
    parser.addErrorListener(MyErrorListener.INSTANCE);

    try {
      MyVisitor visitor = new MyVisitor();
      ArrayList operations = (ArrayList) visitor.visit(parser.parse());
      System.out.print(operations);

      for(int i=0;i<operations.size();i++){
        Object operation = operations.get(i);
        if(operation instanceof CreateDatabaseOperation){
          ((CreateDatabaseOperation) operation).exec();
        } else if(operation instanceof CreateTableOperation){
          ((CreateTableOperation) operation).exec();
        }
      }



    } catch (Exception e)
    {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }
}
