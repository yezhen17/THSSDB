package cn.edu.thssdb.tree;

import java.util.ArrayList;
import java.util.Iterator;

public class BaseTree<V> {

  protected Node<V> root;

  public BaseTree() {
    root = null;
  }

  public BaseTree(Node root) {
    this.root = root;
  }

  public V getTreeValue() {
    if (root == null) {
      return null;
    }
    return calculateTriple(root);
  }

  public Node<V> getRoot() {
    return this.root;
  }

  private V calculateTriple(Node cur) {
    Node n1 = cur.getLeft();
    Node n2 = cur.getRight();
    V v1, v2;
    if (!n1.isLeaf()) {
      v1 = calculateTriple(n1);
    } else {
      v1 = (V) n1.getValue();
    }
    if (!n2.isLeaf()) {
      v2 = calculateTriple(n2);
    } else {
      v2 = (V) n2.getValue();
    }
    return merge(v1, v2, cur.getOp());
  }

  protected V merge(V v1, V v2, String op) {
    return null;
  }


}

