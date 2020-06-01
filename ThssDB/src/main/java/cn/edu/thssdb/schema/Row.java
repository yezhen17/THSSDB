package cn.edu.thssdb.schema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;


/***************
 * [class] 行
 ***************/
public class Row implements Serializable {
  private static final long serialVersionUID = -5809782578272943999L;
  protected ArrayList<Entry> entries;       // 条目表

  /**
   * [method] 构造方法
   */
  public Row() {
    this.entries = new ArrayList<>();
  }
  public Row(Entry[] entries) {
    this.entries = new ArrayList<>(Arrays.asList(entries));
  }
  public Row(ArrayList<Entry> entries) {
    this.entries = new ArrayList<>(entries);
  }

  /**
   * [method] 获取条目表
   * @return 条目表
   */
  public ArrayList<Entry> getEntries() {
    return entries;
  }

  /**
   * [method] 追加条目表
   * @param  entries {ArrayList<Entry>} 条目表
   */
  public void appendEntries(ArrayList<Entry> entries) {
    this.entries.addAll(entries);
  }

  /**
   * [method] 转化为字符串
   * @return 值对应字符串
   */
  public String toString() {
    if (entries == null)
      return "EMPTY";
    StringJoiner sj = new StringJoiner(",");
    for (Entry e : entries)
      sj.add(e.toString());
    return sj.toString();
  }
}
