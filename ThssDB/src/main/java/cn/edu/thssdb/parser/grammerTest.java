package cn.edu.thssdb.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.ArrayList;

public class grammerTest {
  public static void main(String[] args) {
    CharStream input = CharStreams.fromString(
            "create database mydb;"+
            "drop database mydb; "+
            "drop database if exists mydb;"+
            "use db;" +
            "drop table mytable; "+
            "drop table if exists mytable");
    /*
    +
            "create table mydb (a int, b string(124) not null, primary key (a));" +
            "insert into mydb (b, a) values (\"2342\", 1), (\"78964\", 2);" +
            "update mydb set b = \"1234\" where a == 1;" +
            "delete from mydb where a <> 1;" +
            "show databases;" +
            "show database db;" +
            "drop database mydb;" +
            "select t1.a,b from t1 join t2 on t1.a==t2.b natural join t3 where t3.x>0;"
     */
    SQLLexer lexer = new SQLLexer(input);
    lexer.removeErrorListeners();
    lexer.addErrorListener(MyErrorListener.INSTANCE);
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);
    SQLParser parser = new SQLParser(tokenStream);
    parser.removeErrorListeners();
    parser.addErrorListener(MyErrorListener.INSTANCE);

    try {
      MyVisitor visitor = new MyVisitor();
      ArrayList res = (ArrayList) visitor.visit(parser.parse());
      System.out.print(res);
    } catch (Exception e)
    {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }
}
