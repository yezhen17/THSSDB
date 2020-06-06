package cn.edu.thssdb.parser;

import cn.edu.thssdb.operation.BaseOperation;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.ArrayList;

public class MyParser {


  public MyParser() {

  }

  public static ArrayList<BaseOperation> getOperations(String statement) {
    SQLLexer lexer = new SQLLexer(CharStreams.fromString(statement));
    lexer.removeErrorListeners();
    lexer.addErrorListener(MyErrorListener.INSTANCE);
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);
    SQLParser parser = new SQLParser(tokenStream);
    parser.removeErrorListeners();
    parser.addErrorListener(MyErrorListener.INSTANCE);
    ArrayList res;
    try {
      MyVisitor visitor = new MyVisitor();
      res = (ArrayList) visitor.visit(parser.parse());
    } catch (Exception e) {
      throw e; // 这里抛出异常让上层处理
    }
    return res;
  }

}
