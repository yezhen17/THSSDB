package cn.edu.thssdb.type;

import cn.edu.thssdb.parser.SQLParser;

public enum ColumnType {
  INT, LONG, FLOAT, DOUBLE, STRING;

  public ColumnType checkType(Object o){
    if(o instanceof Integer) return INT;
    else if(o instanceof Long) return LONG;
    else if(o instanceof Float) return FLOAT;
    else if(o instanceof Double) return DOUBLE;
    else if(o instanceof String) return STRING;
    else throw new IllegalArgumentException();
  }

  public static ColumnType string2ColumnType(String s) throws Exception {
    if(s.equals("INT")) return INT;
    else if(s.equals("LONG")) return LONG;
    else if(s.equals("FLOAT")) return FLOAT;
    else if(s.equals("DOUBLE")) return DOUBLE;
    else if(s.equals("STRING")) return STRING;
    else throw new Exception();
  }

  public static String columnType2String(ColumnType c) throws Exception {
    if(c == INT) return "INT";
    else if(c == LONG) return "LONG";
    else if(c == FLOAT) return "FLOAT";
    else if(c == DOUBLE) return "DOUBLE";
    else if(c == STRING) return "STRING";
    else throw new Exception();
  }
}
