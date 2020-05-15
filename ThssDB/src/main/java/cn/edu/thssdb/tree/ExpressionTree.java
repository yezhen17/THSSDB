package cn.edu.thssdb.tree;

public class ExpressionTree extends BaseTree<Expression> {

  @Override
  protected Expression merge(Expression v1, Expression v2, String op) {
    if (op.equals("and")) {
      return new Expression(v1.evaluate() && v2.evaluate());
    } else if (op.equals("or")) {
      return new Expression(v1.evaluate() || v2.evaluate());
    } else {
      return new Expression(true); // 这种情况实际不会发生
    }
  }
}

