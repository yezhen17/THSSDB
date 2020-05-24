package cn.edu.thssdb.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.ArrayList;

public class GrammarTest {
  public static void main(String[] args) {
    
    CharStream input = CharStreams.fromString(
            "create database mydb;"+
            "drop database mydb; "+
            "drop database if exists mydb;"+
            "use db;" +
            "drop table mytable; "+
            "drop table if exists mytable;" +
            "select count(*) from t1, t2 where t1.a = t2.b or a = 2 and 3 <> 5;" +
            "update mydb set b = 'dafdfad';" +
            "select a + 1, 1 / b, c from t1 join t2 on t1.a = t2.a and t1.b = t2.b " +
                    "where t1.a = t2.b or a = 2 and 3 <> 5 order by t1.a desc;");

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
