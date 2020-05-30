package cn.edu.thssdb.type;

public enum ComparisonType {
  LT, GT, NGT, NLT, EQ, NEQ;

  public static ComparisonType string2ComparisonType(String s) {
    if(s.equals("<")) return LT;
    else if(s.equals(">")) return GT;
    else if(s.equals("<=")) return NGT;
    else if(s.equals(">=")) return NLT;
    else if(s.equals("=")) return EQ;
    else if(s.equals("<>")) return NEQ;
    else return NEQ;
  }
}
