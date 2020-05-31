package cn.edu.thssdb.operation;

public class RollbackOperation extends BaseOperation {

  private String savepoint;

  public RollbackOperation() {
    savepoint = null;
  }

  public RollbackOperation(String savepoint) {
    this.savepoint = savepoint;
  }

  public String getSavepoint() {
    return savepoint;
  }
}
