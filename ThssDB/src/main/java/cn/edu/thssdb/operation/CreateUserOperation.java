package cn.edu.thssdb.operation;


public class CreateUserOperation {

  private String userName;
  private String passWord;

  /**
   * [method] 构造方法
   */
  public CreateUserOperation(String userName, String passWord) {
    this.userName = userName;
    this.passWord = passWord;
  }

  /**
   * [method] 执行操作
   */
  public void exec() {
    //todo sessionid?
  }


}
