package cn.edu.thssdb.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import org.junit.Before;
import org.junit.Test;

public class SQLParserTest {
  @Before
  public void setUp() {
    CharStream input = CharStreams.fromString("SELECT x FROM xx\r\n");
    SQLLexer lexer=new SQLLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    SQLParser parser = new SQLParser(tokens);
    ParseTree tree = parser.parse(); // parse
    System.out.println(tree.toStringTree(parser));
  }
}

