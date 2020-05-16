package cn.edu.thssdb.tree;

public class LogicTree extends BaseTree<Boolean> {
  // ...

  public LogicTree(Node root) {
    super(root);
  }

  protected Boolean merge(Boolean v1, Boolean v2, String op) {
    if (op.equals("and")) {
      return v1 && v2;
    } else {
      return v1 || v2;
    }
  }
}
