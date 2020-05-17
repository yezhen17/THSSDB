package cn.edu.thssdb.parser;

import cn.edu.thssdb.operation.*;
import cn.edu.thssdb.parser.item.*;
import cn.edu.thssdb.schema.Column;
import cn.edu.thssdb.type.ColumnType;
import com.sun.org.apache.xalan.internal.xsltc.dom.SimpleResultTreeImpl;

import java.lang.reflect.Array;
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
    for (int i = 0; i < n; i += 2) {
      res.add(visit(ctx.getChild(i)));
      System.out.println(ctx.getChild(i + 1).getText());
    }
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
    return new CreateDatabaseOperation(dbName);
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
    return new DropDatabaseOperation(dbName);
  }

  @Override
  public Object visitCreate_table_stmt(SQLParser.Create_table_stmtContext ctx) {
    String tableName = (String) visit(ctx.getChild(2));
    int n = ctx.getChildCount();
    ArrayList<ColumnDefItem> columnDefItems = new ArrayList<>();
    String primaryKey = null;
    for(int i=4; i< n; i+=2){
      if(visit(ctx.getChild(i)) instanceof ColumnDefItem){
        columnDefItems.add((ColumnDefItem) visit(ctx.getChild(i)));
      }
      else if(visit(ctx.getChild(i)).getClass().getName().equals("java.lang.String")){
        primaryKey = (String) visit(ctx.getChild(i));
      }
    }
    ArrayList<Column> columns = new ArrayList<>();
    int primaryKeyIndex = 0;
    for(int i=0;i<columnDefItems.size();i++){
      ColumnDefItem c = columnDefItems.get(i);
      if(primaryKey.equals(c.getColumnName())){
        //todo 报错:主键不匹配，不唯一等
        primaryKeyIndex = i;
        c.setPrimaryKey(true);
      }

      Column column = new Column(c.getColumnName(),c.getTypeItem().getColumnType(),c.isPrimaryKey(),c.isNotNull(),c.getTypeItem().getStrLen());

      columns.add(column);

    }

    Column[] pColumns = new Column[columns.size()] ;
    for(int i=0;i<columns.size();i++){
      pColumns[i]=columns.get(i);
    }
    return new CreateTableOperation(tableName,pColumns,primaryKeyIndex);
  }

  @Override
  public Object visitColumn_def(SQLParser.Column_defContext ctx) {
    String columnName = (String) visit(ctx.getChild(0));
    TypeItem typeItem = (TypeItem) visit(ctx.getChild(1));
    int n = ctx.getChildCount();
    boolean isPrimaryKey = false;
    boolean isNotNull = false;
    for(int i=2; i<n; i++){
      if(ctx.getChild(i).getText().toUpperCase().equals("KEY")){
        isPrimaryKey = true;
      }
      if(ctx.getChild(i).getText().toUpperCase().equals("NULL")){
        isNotNull = true;
      }
    }
    return new ColumnDefItem(columnName,typeItem,isPrimaryKey,isNotNull);
  }

  @Override
  public Object visitColumn_name(SQLParser.Column_nameContext ctx) {
    return ctx.getChild(0).getText();
  }

  @Override
  public Object visitType_name(SQLParser.Type_nameContext ctx) {
    if(ctx.getChildCount()==1){
      try {
        return new TypeItem(ColumnType.string2ColumnType(ctx.getChild(0).getText().toUpperCase()));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    else {
      try {
        int strLen = Integer.parseInt(ctx.getChild(2).getText());
        return new TypeItem(ColumnType.string2ColumnType(ctx.getChild(0).getText().toUpperCase()),strLen);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  @Override
  public Object visitTable_constraint(SQLParser.Table_constraintContext ctx) {
    //todo 目前单主键
    String primaryKey = ctx.getChild(3).getText();
    return primaryKey;
  }

  @Override
  public Object visitUse_db_stmt(SQLParser.Use_db_stmtContext ctx) {
    String dbName = (String) visit(ctx.getChild(1));
    //TODO add statement
    return new UseOperation(dbName);
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
    return new DropTableOperation(tableName);
  }

  @Override
  public Object visitTable_name(SQLParser.Table_nameContext ctx) {
    return ctx.getChild(0).getText();
  }

  @Override
  public Object visitSelect_stmt(SQLParser.Select_stmtContext ctx) {
    SelectContentItem select_content_item = null;
    FromItem from_item = null;
    MultipleConditionItem where_item = null;
    OrderByItem order_by_item = null;
    ArrayList<String> column_full_name = new ArrayList<>();
    select_content_item = (SelectContentItem) visit(ctx.getChild(1));
    from_item = (FromItem) visit(ctx.getChild(3));
    ArrayList<ColumnFullNameItem> order_by_columns = new ArrayList<>();
    int order = 0;
    boolean has_order = false;
    int idx_first_order_column = -1;

    if (ctx.getChildCount() > 4) {
      if (ctx.getChild(4).getText().equalsIgnoreCase("WHERE")) {
        where_item = (MultipleConditionItem) visit(ctx.getChild(5));
        if (ctx.getChildCount() > 6) {
          has_order = true;
          idx_first_order_column = 9;
        }
      } else  {
        idx_first_order_column = 7;
        has_order = true;
      }
      // select_content = (String) visit(ctx.getChild(4));
    }
    if (has_order) {
      order = 1;
      int i =idx_first_order_column;
      while (true) {
        order_by_columns.add((ColumnFullNameItem) visit(ctx.getChild(i)));
        if (i + 1 >= ctx.getChildCount()) {
          break;
        }
        String nxt = ctx.getChild(i + 1).getText();
        if (nxt.equalsIgnoreCase("DESC")) {
          order = -1;
        }
        if (!nxt.equalsIgnoreCase(",")) {
          break;
        }
        i += 2;
      }
      column_full_name.add((String) visit(ctx.getChild(6)));
    }

    order_by_item = new OrderByItem(order_by_columns, order);

    return new SelectOperation(select_content_item, from_item, where_item, order_by_item);
//    WholeSelectionItem res = new WholeSelectionItem(select_content_item, from_item, where_item, order_by_item);
//    return res;
  }

  @Override
  public SelectContentItem visitSelect_content(SQLParser.Select_contentContext ctx) {
    ArrayList<SelectItem> select_content = new ArrayList<>();
    boolean is_distinct = false;
    if (ctx.getChild(0).getText().equalsIgnoreCase("DISTINCT")) {
      for (int i = 1; i < ctx.getChildCount(); i += 2) {
        select_content.add((SelectItem) visit(ctx.getChild(i)));
      }
      is_distinct = true;
    } else if (ctx.getChild(0).getText().equalsIgnoreCase("ALL")) {
      for (int i = 1; i < ctx.getChildCount(); i += 2) {
        select_content.add((SelectItem) visit(ctx.getChild(i)));
      }
    } else {
      for (int i = 0; i < ctx.getChildCount(); i += 2) {
        select_content.add((SelectItem) visit(ctx.getChild(i)));
      }
    }
    return new SelectContentItem(select_content, is_distinct);
  }

  @Override
  public FromItem visitJoin_content(SQLParser.Join_contentContext ctx) {
    FromItem join_content;

    ArrayList<String> child_text = new ArrayList<>();
    if (ctx.getChildCount() == 1) {
      return new FromItem(ctx.getChild(0).getText(), null, FromItem.JoinType.NONE, new ArrayList<>());
    }
    for (int i = 0; i < ctx.getChildCount(); i++) {
      child_text.add(ctx.getChild(i).getText());
    }
    if (child_text.get(1).equals(",")) {
      join_content = new FromItem(child_text.get(0), child_text.get(2),
              FromItem.JoinType.PRODUCT, new ArrayList<>());
    } else if (child_text.get(1).equalsIgnoreCase("NATURAL")) {
      int right_pos = 2;
      if (child_text.get(1).equalsIgnoreCase("INNER")) {
        right_pos = 3;
      }
      join_content = new FromItem(child_text.get(0), child_text.get(right_pos),
              FromItem.JoinType.NATURAL_INNER_JOIN, new ArrayList<>());
    } else if (child_text.get(1).equalsIgnoreCase("INNER")) {
      join_content = new FromItem(child_text.get(0), child_text.get(3),
              FromItem.JoinType.INNER_JOIN_ON, (ArrayList<OnItem>) visit(ctx.getChild(5))); // TODO
    } else if (child_text.get(1).equalsIgnoreCase("JOIN")) {
      join_content = new FromItem(child_text.get(0), child_text.get(2),
              FromItem.JoinType.INNER_JOIN_ON, (ArrayList<OnItem>) visit(ctx.getChild(4))); // TODO
    } else {
      int right_pos = 3;
      if (child_text.get(2).equalsIgnoreCase("OUTER")) {
        right_pos = 4;
      }
      FromItem.JoinType join_type = FromItem.JoinType.LEFT_OUTER_JOIN_ON;
      if (child_text.get(1).equalsIgnoreCase("RIGHT")) {
        join_type = FromItem.JoinType.RIGHT_OUTER_JOIN_ON;
      } else if (child_text.get(1).equalsIgnoreCase("FULL")) {
        join_type = FromItem.JoinType.FULL_OUTER_JOIN_ON;
      }
      join_content = new FromItem(child_text.get(0), child_text.get(right_pos),
              join_type, new ArrayList<>());
    }
    return join_content;
  }

  @Override
  public ColumnFullNameItem visitColumn_full_name(SQLParser.Column_full_nameContext ctx) {
    if (ctx.getChildCount() > 1) {
      return new ColumnFullNameItem(ctx.getChild(0).getText(), ctx.getChild(2).getText());
    } else {
      return new ColumnFullNameItem(null, ctx.getChild(0).getText());
    }
  }

  @Override
  public ArrayList<OnItem> visitOn_content(SQLParser.On_contentContext ctx) {
    int num = ctx.getChildCount() / 3;
    ArrayList<OnItem> res = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      res.add(new OnItem(
              (ColumnFullNameItem) visit(ctx.getChild(3 * i)),
              (ColumnFullNameItem) visit(ctx.getChild(3 * i + 2))
      ));
    }
    return res;
  }

  @Override
  public Double visitNumeric_value(SQLParser.Numeric_valueContext ctx) {
    return Double.valueOf(ctx.getChild(0).getText());
  }

  @Override
  public ColumnFullNameItem visitResult_column(SQLParser.Result_columnContext ctx) {
    if (ctx.getChild(0).getText().equals("*")) {
      return new ColumnFullNameItem(null, "*");
    } else if (ctx.getChildCount() > 1) {
      return new ColumnFullNameItem(ctx.getChild(0).getText(), ctx.getChild(2).getText());
    } else {
      return (ColumnFullNameItem) visit(ctx.getChild(0));
    }
  }

  @Override
  public MultipleConditionItem visitMultiple_condition(SQLParser.Multiple_conditionContext ctx) {
    if (ctx.getChildCount() == 1) {
      return new MultipleConditionItem((ConditionItem) visit(ctx.getChild(0)));
    } else {
      if (ctx.getChild(0).getText().equalsIgnoreCase("(")) {
        return (MultipleConditionItem) visit(ctx.getChild(1));
      } else {
        return new MultipleConditionItem((MultipleConditionItem) visit(ctx.getChild(0)),
                (MultipleConditionItem) visit(ctx.getChild(2)),
                ctx.getChild(1).getText());
      }
    }

  }

  @Override
  public Object visitComparer(SQLParser.ComparerContext ctx) {
    if (ctx.getChild(0) instanceof SQLParser.Literal_valueContext) {
      return new ComparerItem((LiteralValueItem) visit(ctx.getChild(0)));
    } else {
      return new ComparerItem((ColumnFullNameItem) visit(ctx.getChild(0)));
    }
  }

  @Override
  public Object visitCondition(SQLParser.ConditionContext ctx) {
    if (ctx.getChild(1) instanceof SQLParser.ComparatorContext) {
      return new ConditionItem((ComparerItem) visit(ctx.getChild(0)),
              (ComparerItem) visit(ctx.getChild(2)),
              ctx.getChild(1).getText());
    } else {
      return new ConditionItem(new ComparerItem((ColumnFullNameItem) ctx.getChild(0)),
              new ComparerItem(), "IS_NULL");
    }
  }

  @Override
  public SelectItem visitSelect_item(SQLParser.Select_itemContext ctx) {
    int child_count = ctx.getChildCount();
    if (child_count == 1) {
      if (ctx.getChild(0) instanceof SQLParser.Result_columnContext) {
        return new SelectItem((ColumnFullNameItem) visit(ctx.getChild(0)));
      } else {
        return new SelectItem((Double) visit(ctx.getChild(0)));
      }

    } else {
      if (ctx.getChild(0) instanceof SQLParser.Column_full_nameContext) {
        return new SelectItem((ColumnFullNameItem) visit(ctx.getChild(0)),
                (Double) visit(ctx.getChild(2)),
                ctx.getChild(1).getText().toUpperCase());
      } else {
        if (ctx.getChild(2) instanceof SQLParser.Column_full_nameContext) {
          return new SelectItem((Double) visit(ctx.getChild(0)),
                  (ColumnFullNameItem) visit(ctx.getChild(2)),
                  ctx.getChild(1).getText().toUpperCase());
        } else {
          return new SelectItem((Double) visit(ctx.getChild(0)),
                  (Double) visit(ctx.getChild(2)),
                  ctx.getChild(1).getText().toUpperCase());
        }
      }
    }
  }

  @Override
  public Object visitSelect_item_2(SQLParser.Select_item_2Context ctx) {
    if (ctx.getChild(2).getText().equalsIgnoreCase("*")) {
      return new SelectItem(new ColumnFullNameItem(null, "*"),
              ctx.getChild(0).getText().toUpperCase());
    } else {
      return new SelectItem((ColumnFullNameItem) visit(ctx.getChild(2)),
              ctx.getChild(0).getText().toUpperCase());
    }
  }

  @Override
  public Object visitShow_table_stmt(SQLParser.Show_table_stmtContext ctx) {
    String tableName = (String) visit(ctx.getChild(2));
    return new ShowOperation(tableName);
  }

  @Override
  public Object visitCreate_user_stmt(SQLParser.Create_user_stmtContext ctx) {
    String user = (String) visit(ctx.getChild(2));
    String psd = (String) visit(ctx.getChild(5));
    //todo
    return null;
  }

  @Override
  public Object visitDrop_user_stmt(SQLParser.Drop_user_stmtContext ctx) {
    int child_count = ctx.getChildCount();
    String user = null;
    if (child_count == 3) {
      user = (String) visit(ctx.getChild(2));
    } else if (child_count == 5) {
      user = (String) visit(ctx.getChild(4));
    }
    //todo
    return null;
  }

  @Override
  public Object visitUser_name(SQLParser.User_nameContext ctx) {
    return ctx.getChild(0).getText();
  }

  @Override
  public Object visitPassword(SQLParser.PasswordContext ctx) {
    return ctx.getChild(0).getText();
  }

  @Override
  public Object visitLiteral_value(SQLParser.Literal_valueContext ctx) {
    String str = ctx.getChild(0).getText();
    if(str.equalsIgnoreCase("NULL")){
      return new LiteralValueItem(LiteralValueItem.Type.NULL,"null");
    }
    else if(str.charAt(0)=='\''){
      return new LiteralValueItem(LiteralValueItem.Type.STRING,str.substring(1,str.length()-1));
    }
    else {
      try {
        long i = Long.parseLong(str);
        //int i = Integer.valueOf(str);
      }
      catch (NumberFormatException e){
        try {
          double d = Double.valueOf(str);
        }
        catch (NumberFormatException r){
          throw new IllegalArgumentException();
        }
        return new LiteralValueItem(LiteralValueItem.Type.FLOAT_OR_DOUBLE,str);
      }
      return new LiteralValueItem(LiteralValueItem.Type.INT_OR_LONG,str);
    }
  }

  @Override
  public Object visitDelete_stmt(SQLParser.Delete_stmtContext ctx) {
    if (ctx.getChildCount() == 3) {
      return new DeleteOperation(ctx.getChild(2).getText());
    } else {
      return new DeleteOperation(ctx.getChild(2).getText(), (MultipleConditionItem) visit(ctx.getChild(4)));
    }
  }

  @Override
  public Object visitUpdate_stmt(SQLParser.Update_stmtContext ctx) {
    if(ctx.getChildCount()==6){
      String tableName = (String) visit(ctx.getChild(1));
      String columnName = (String) visit(ctx.getChild(3));
      LiteralValueItem literalValueItem = (LiteralValueItem) visit(ctx.getChild(5));
      return new UpdateOperation(tableName,columnName,literalValueItem);
    }
    else{
      String tableName = (String) visit(ctx.getChild(1));
      String columnName = (String) visit(ctx.getChild(3));
      LiteralValueItem literalValueItem = (LiteralValueItem) visit(ctx.getChild(5));
      MultipleConditionItem multipleConditionItem = (MultipleConditionItem) visit(ctx.getChild(7));
      return new UpdateOperation(tableName,columnName,literalValueItem,multipleConditionItem);
    }
  }

  @Override
  public Object visitInsert_stmt(SQLParser.Insert_stmtContext ctx) {
    String tableName = (String) visit(ctx.getChild(2));
    int n = ctx.getChildCount();
    ArrayList<String> columnNames = new ArrayList<>();
    ArrayList<ArrayList<LiteralValueItem>> values = new ArrayList<>();
    String text = (String) visit(ctx.getChild(3));
    if(text.equalsIgnoreCase("VALUES")){
      for(int i=4;i<n;i+=2){
        values.add((ArrayList<LiteralValueItem>) visit(ctx.getChild(i)));
      }
      return new InsertOperation(tableName,values);
    }
    else{
      int i;
      for(i=4;i<n;i++){
        if(ctx.getChild(i).getText().equalsIgnoreCase("VALUES"))
          break;
        if(ctx.getChild(i).getText().equalsIgnoreCase(",")||
                ctx.getChild(i).getText().equalsIgnoreCase(")"))
          continue;
        columnNames.add((String) visit(ctx.getChild(i)));
      }
      i++;
      for(;i<n;i+=2){
        values.add((ArrayList<LiteralValueItem>) visit(ctx.getChild(i)));
      }
      return new InsertOperation(tableName,columnNames,values);
    }
  }

  @Override
  public Object visitValue_entry(SQLParser.Value_entryContext ctx) {
    int n = ctx.getChildCount();
    ArrayList<LiteralValueItem> literalValueItems = new ArrayList<>();
    for(int i=1;i<n;i+=2){
      literalValueItems.add((LiteralValueItem) visit(ctx.getChild(i)));
    }
    return literalValueItems;
  }
}

