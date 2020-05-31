package cn.edu.thssdb.type;

import cn.edu.thssdb.exception.IllegalTypeException;
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

  public static ColumnType string2ColumnType(String s) {
    if(s.equals("INT")) return INT;
    else if(s.equals("LONG")) return LONG;
    else if(s.equals("FLOAT")) return FLOAT;
    else if(s.equals("DOUBLE")) return DOUBLE;
    else if(s.equals("STRING")) return STRING;
    else throw new IllegalTypeException();
  }

  public static String columnType2String(ColumnType c) {
    if(c == INT) return "INT";
    else if(c == LONG) return "LONG";
    else if(c == FLOAT) return "FLOAT";
    else if(c == DOUBLE) return "DOUBLE";
    else if(c == STRING) return "STRING";
    else throw new IllegalTypeException();
  }

  public static Comparable getColumnTypeValue(ColumnType c, String val) {
    if (val.equalsIgnoreCase("null")) return null;
    if(c == INT) return Integer.valueOf(val);
    else if(c == LONG) return Long.valueOf(val);
    else if(c == FLOAT) return Float.valueOf(val);
    else if(c == DOUBLE) return Double.valueOf(val);
    else if(c == STRING) return val;
    else throw new IllegalTypeException();
  }
}
