package cn.edu.thssdb.schema;

import java.io.Serializable;

/***************
 * [class] 条目
 ***************/
public class Entry implements Comparable<Entry>, Serializable {
  private static final long serialVersionUID = -5809782578272943999L;
  public Comparable value;       // 值

  /**
   * [方法] 构造方法
   */
  public Entry(Comparable value) {
    this.value = value;
  }

  /**
   * [重写] 比较
   */
  @Override
  public int compareTo(Entry e) {
    return value.compareTo(e.value);
  }

  /**
   * [重写] 判等
   * @return 是否相等
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (this.getClass() != obj.getClass())
      return false;
    Entry e = (Entry) obj;
    return value.equals(e.value);
  }

  /**
   * [方法] 转化为字符串
   * @return 值对应字符串
   */
  public String toString() {
    return value.toString();
  }

  /**
   * [方法] 获取哈希码
   * @return 值对应哈希码
   */
  @Override
  public int hashCode() {
    return value.hashCode();
  }
}
