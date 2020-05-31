package cn.edu.thssdb.schema;

import cn.edu.thssdb.type.ColumnType;

/***************
 * [class] 列
 ***************/
public class Column implements Comparable<Column> {
  protected String name;        // 名字
  protected ColumnType type;    // 类型
  private boolean primary;      // 是否为主键
  private boolean notNull;      // 是否可以为空
  private int maxLength;        // 最大长度（For String）

  /**
   * [method] 构造方法
   */
  public Column(String name, ColumnType type, boolean primary, boolean notNull, int maxLength) {
    this.name = name;
    this.type = type;
    this.primary = primary;
    this.notNull = notNull;
    this.maxLength = maxLength;
  }

  public Column(Column column) {
    this.name = column.getName();
    this.type = column.getType();
    this.primary = column.isPrimary();
    this.notNull = column.isNotNull();
    this.maxLength = column.getMaxLength();
  }

  /**
   * [override] 比较
   */
  @Override
  public int compareTo(Column e) {
    return name.compareTo(e.name);
  }

  public String getName() {
    return name;
  }

  /**
   * [method] 转化为字符串
   * @return 值对应字符串
   */
  public String toString(char delimitor) {
    return name + delimitor + type + delimitor + primary + delimitor + notNull + delimitor + maxLength;
  }

  public int getMaxLength() {
    return maxLength;
  }

  public ColumnType getType() {
    return type;
  }

  public boolean isNotNull() {
    return notNull;
  }

  public boolean isPrimary() {
    return primary;
  }

}
