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
}
