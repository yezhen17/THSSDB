package cn.edu.thssdb.exception;

/***************
 * [Exception] 计算类型错误
 ***************/
public class CalculationTypeException extends RuntimeException {
  @Override
  public String getMessage() {
    return "Exception: Wrong type for calculation or aggregation.";
  }
}
