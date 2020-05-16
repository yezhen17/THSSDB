package cn.edu.thssdb.parser.item;

import cn.edu.thssdb.tree.BaseTree;
import cn.edu.thssdb.tree.Expression;
import cn.edu.thssdb.tree.Node;

public class MultipleConditionItem extends BaseTree<ConditionItem> {

  public MultipleConditionItem(MultipleConditionItem m1, MultipleConditionItem m2, String op) {
    this.root = new Node<>(op, m1.getRoot(), m2.getRoot());
  }

  public MultipleConditionItem(ConditionItem c) {
    this.root = new Node<>(c);
  }

//  public MultipleConditionItem(ConditionItem c1, ConditionItem c2, String op) {
//    Node<ConditionItem> n1 = new Node<>(c1);
//    Node<ConditionItem> n2 = new Node<>(c2);
//    this.root = new Node<>(op, n1, n2);
//  }

  @Override
  protected ConditionItem merge(ConditionItem v1, ConditionItem v2, String op) {
    if (op.equals("and")) {
      return new ConditionItem(v1.evaluate() && v2.evaluate());
    } else if (op.equals("or")) {
      return new ConditionItem(v1.evaluate() || v2.evaluate());
    } else {
      return new ConditionItem(true); // 这种情况实际不会发生
    }
  }
}


