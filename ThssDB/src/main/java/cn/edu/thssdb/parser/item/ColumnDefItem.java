package cn.edu.thssdb.parser.item;

public class ColumnDefItem {
  private String columnName;
  private TypeItem typeItem;
  private boolean isPrimaryKey;
  private boolean isNotNull;

  public ColumnDefItem(String columnName, TypeItem typeItem, boolean isPrimaryKey, boolean isNotNull) {
    this.columnName = columnName.toUpperCase();
    this.typeItem = typeItem;
    this.isPrimaryKey = isPrimaryKey;
    this.isNotNull = isNotNull;
  }

  public void setPrimaryKey(boolean primaryKey) {
    isPrimaryKey = primaryKey;
  }

  public void setNotNull(boolean notNull) {
    isNotNull = notNull;
  }

  public String getColumnName() {
    return columnName;
  }

  public TypeItem getTypeItem() {
    return typeItem;
  }

  public boolean isPrimaryKey() {
    return isPrimaryKey;
  }

  public boolean isNotNull() {
    return isNotNull;
  }
}
