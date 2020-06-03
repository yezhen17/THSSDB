package cn.edu.thssdb.exception;

public class CalculationTypeException extends RuntimeException {
  @Override
  public String getMessage() {
    return "Exception: Wrong type for calculation or aggregation.";
  }
}
