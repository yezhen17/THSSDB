package cn.edu.thssdb.parser.item.tree;

public class Node<V> {
  private String op;
  private V value;
  private Node leftChild;
  private Node rightChild;

  public Node(String op, Node<V> left, Node<V> right) {
    this.op = op;
    this.leftChild = left;
    this.rightChild = right;
    this.value = null;
  }

  public Node(V value) {
    this.op = null;
    this.leftChild = null;
    this.rightChild = null;
    this.value = value;
  }

  public boolean isLeaf () {
    return leftChild == null && rightChild == null;
  }

  public Node getLeft () {
    return leftChild;
  }

  public Node getRight () {
    return rightChild;
  }

  public V getValue() {
    return value;
  }

  public String getOp() {
    return op;
  }
}
