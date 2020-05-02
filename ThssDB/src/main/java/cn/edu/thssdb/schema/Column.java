package cn.edu.thssdb.schema;

import cn.edu.thssdb.type.ColumnType;

/***************
 * [class] 列
 ***************/
public class Column implements Comparable<Column> {
  private String name;        // 名字
  private ColumnType type;    // 类型
  private int primary;        // 是否为主键
  private boolean notNull;    // 是否可以为空
  private int maxLength;      // 最大长度（For String）

  /**
   * [方法] 构造方法
   */
  public Column(String name, ColumnType type, int primary, boolean notNull, int maxLength) {
    this.name = name;
    this.type = type;
    this.primary = primary;
    this.notNull = notNull;
    this.maxLength = maxLength;
  }

  /**
   * [重写] 比较
   */
  @Override
  public int compareTo(Column e) {
    return name.compareTo(e.name);
  }

  /**
   * [方法] 转化为字符串
   * @return 值对应字符串
   */
  public String toString() {
    return name + ',' + type + ',' + primary + ',' + notNull + ',' + maxLength;
  }
}
