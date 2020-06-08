package cn.edu.thssdb.parser.item.tree;

import cn.edu.thssdb.schema.Row;

public class BaseTree<V> {

  protected Node<V> root;

  public BaseTree() {
    root = null;
  }

  public BaseTree(Node root) {
    this.root = root;
  }

  public V getTreeValue(Row row) {
    if (root == null) {
      return null;
    }
    return calculateTriple(root, row);
  }

  public Node<V> getRoot() {
    return this.root;
  }

  protected V calculateTriple(Node cur, Row row) {
    Node n1 = cur.getLeft();
    Node n2 = cur.getRight();
    if (n1 == null) {
      return (V) cur.getValue();
    } else {
      return merge(calculateTriple(n1, row), calculateTriple(n2, row), cur.getOp(), row);
    }
  }

  protected V merge(V v1, V v2, String op, Row row) {
    return null;
  }


}

