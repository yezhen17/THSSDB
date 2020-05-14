package cn.edu.thssdb.parser;

import java.util.ArrayList;

public class MyVisitor extends SQLBaseVisitor{

  public MyVisitor(){super();}


  @Override
  public Object visitParse(SQLParser.ParseContext ctx) {
    int n = ctx.getChildCount();
    ArrayList res = new ArrayList<>();
    for (int i = 0; i <= n-1; ++i)
      res.addAll((ArrayList)visit(ctx.getChild(i)));
    return res;
  }


  @Override
  public Object visitSql_stmt_list(SQLParser.Sql_stmt_listContext ctx) {
    //System.out.println(ctx.getText());
    // ctx类的children是Sql_stmt_Context类（语句）或者TerminalNodeImpl类（；）
    int n = ctx.getChildCount();
    ArrayList res = new ArrayList();
    //+2是为了跳过；
    for (int i = 0; i < n; i += 2)
      res.add(visit(ctx.getChild(i)));
    //res的内容是自己定义的statement类的列表
    return res;
  }

  @Override
  public Object visitSql_stmt(SQLParser.Sql_stmtContext ctx) {
    System.out.println(ctx.getText());
    //ctx.getChild(0)是每个句子的语法树
    return visit(ctx.getChild(0));
  }

  @Override
  public Object visitCreate_db_stmt(SQLParser.Create_db_stmtContext ctx) {
    String dbName = (String) visit(ctx.getChild(2));
    //TODO add statement
    return visitChildren(ctx);
  }

  @Override
  public Object visitDrop_db_stmt(SQLParser.Drop_db_stmtContext ctx) {
    String dbName;
    if(ctx.getChildCount()>3){
      dbName = (String) visit(ctx.getChild(4));
    }
    else {
      dbName = (String) visit(ctx.getChild(2));
    }
    //todo add statement
    return visitChildren(ctx);
  }

  @Override
  public Object visitCreate_table_stmt(SQLParser.Create_table_stmtContext ctx) {
    String tableName = (String) visit(ctx.getChild(2));
    return super.visitCreate_table_stmt(ctx);
  }

  @Override
  public Object visitColumn_def(SQLParser.Column_defContext ctx) {
    return null;
  }

  @Override
  public Object visitColumn_name(SQLParser.Column_nameContext ctx) {
    return ctx.getChild(0).getText();
  }

  @Override
  public Object visitType_name(SQLParser.Type_nameContext ctx) {
    if(ctx.getChildCount()==1){
      try {
      //  return new Type(ctx.getChild(0).getText());
        return null;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    else {
      try {
        int strLen = Integer.parseInt(ctx.getChild(2).getText());
       // return new Type(ctx.getChild(0).getText(),strLen);
        return null;
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
    return null;
  }

  @Override
  public Object visitTable_constraint(SQLParser.Table_constraintContext ctx) {
    //todo 多主键
    String primaryKey = ctx.getChild(3).getText();
    return primaryKey;
  }

  @Override
  public Object visitUse_db_stmt(SQLParser.Use_db_stmtContext ctx) {
    String dbName = (String) visit(ctx.getChild(1));
    //TODO add statement
    return visitChildren(ctx);
  }

  @Override public Object visitDatabase_name(SQLParser.Database_nameContext ctx) {
    return ctx.getChild(0).getText();
  }

  @Override public Object visitDrop_table_stmt(SQLParser.Drop_table_stmtContext ctx) {
    String tableName;
    if(ctx.getChildCount()>3){
      tableName = (String) visit(ctx.getChild(4));
    }
    else {
      tableName = (String) visit(ctx.getChild(2));
    }
    //todo add statement
    return visitChildren(ctx);
  }

  @Override
  public Object visitTable_name(SQLParser.Table_nameContext ctx) {
    return ctx.getChild(0).getText();
  }
}
