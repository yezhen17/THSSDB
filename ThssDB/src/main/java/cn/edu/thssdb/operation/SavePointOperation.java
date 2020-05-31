package cn.edu.thssdb.operation;

public class SavePointOperation extends BaseOperation {

  private String savepoint;

  public SavePointOperation(String savepoint) {
    this.savepoint = savepoint;
  }

  public void exec(){

  }

  public String getSavepoint() {
    return savepoint;
  }

}
