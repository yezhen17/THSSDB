package cn.edu.thssdb.parser.item;

import java.util.ArrayList;

public class SelectContentItem {
  private ArrayList<SelectItem> selectContent;
  private boolean isDistinct;

  public SelectContentItem(ArrayList<SelectItem> selectContent, boolean isDistinct) {
    this.selectContent = selectContent;
    this.isDistinct = isDistinct;
  }

  public ArrayList<SelectItem> getSelectContent() {
    return selectContent;
  }

  public boolean isDistinct() {
    return isDistinct;
  }
}
