package cn.edu.thssdb.operation;

public class SavepointOperation extends BaseOperation {

  private String savepoint;

  public SavepointOperation(String savepoint) {
    this.savepoint = savepoint;
  }

  public String getSavepoint() {
    return savepoint;
  }

}
