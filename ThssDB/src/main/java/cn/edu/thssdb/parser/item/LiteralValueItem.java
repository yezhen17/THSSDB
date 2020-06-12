package cn.edu.thssdb.parser.item;

public class LiteralValueItem {

  public enum Type{
    INT_OR_LONG, FLOAT_OR_DOUBLE, STRING, NULL
  }

  private Type type;
  private String string;

  public LiteralValueItem(Type type, String string) {
    this.type = type;
    this.string = string;
  }

  public Type getType() {
    return type;
  }

  public String getString() {
    return string;
  }
}
