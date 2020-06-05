package cn.edu.thssdb.parser.item;

import cn.edu.thssdb.query.QueryColumn;
import cn.edu.thssdb.schema.Column;
import cn.edu.thssdb.schema.Row;
import cn.edu.thssdb.tree.BaseTree;
import cn.edu.thssdb.tree.Expression;
import cn.edu.thssdb.tree.Node;

import java.util.ArrayList;

public class MultipleConditionItem extends BaseTree<ConditionItem> {
  private ArrayList<QueryColumn> columns;

  public MultipleConditionItem(MultipleConditionItem m1, MultipleConditionItem m2, String op) {
    if (op.equalsIgnoreCase("and") || op.equals("&&")) op = "and";
    if (op.equalsIgnoreCase("or") || op.equals("||")) op = "or";
    this.root = new Node<>(op, m1.getRoot(), m2.getRoot());
  }

  public MultipleConditionItem(ConditionItem c) {
    this.root = new Node<>(c);
  }

  public void setColumn(ArrayList<QueryColumn> columns) {

    this.columns = columns;
    convertConditionToIndex(this.root);
  }

  //  public MultipleConditionItem(ConditionItem c1, ConditionItem c2, String op) {
//    Node<ConditionItem> n1 = new Node<>(c1);
//    Node<ConditionItem> n2 = new Node<>(c2);
//    this.root = new Node<>(op, n1, n2);
//  }

  @Override
  protected ConditionItem merge(ConditionItem v1, ConditionItem v2, String op, Row row) {
    if (op.equals("and")) {
      return new ConditionItem(v1.evaluate(row) && v2.evaluate(row));
    } else if (op.equals("or")) {
      return new ConditionItem(v1.evaluate(row) || v2.evaluate(row));
    } else {
      return new ConditionItem(true); // 这种情况实际不会发生
    }
  }

  @Override
  protected ConditionItem calculateTriple(Node cur, Row row) {
    Node n1 = cur.getLeft();
    Node n2 = cur.getRight();
    if (n1 == null) {
      ConditionItem tmp = (ConditionItem) cur.getValue();
      return new ConditionItem(tmp.evaluate(row));
    } else {
      return merge(calculateTriple(n1, row), calculateTriple(n2, row), cur.getOp(), row);
    }
  }

  private void convertConditionToIndex(Node<ConditionItem> cur) {

    Node<ConditionItem> n1 = cur.getLeft();
    Node<ConditionItem> n2 = cur.getRight();
    if (n1 != null) {
      convertConditionToIndex(n1);
      convertConditionToIndex(n2);
    } else {
      cur.getValue().convertConditionToIndex(columns);
    }
//    else if (!n1.isLeaf()) {
//      convertConditionToIndex(n1);
//    } else {
//      n1.getValue().convertConditionToIndex(columns);
//    }
//    if (!n2.isLeaf()) {
//      convertConditionToIndex(n2);
//    } else {
//      n2.getValue().convertConditionToIndex(columns);
//    }
  }
}


