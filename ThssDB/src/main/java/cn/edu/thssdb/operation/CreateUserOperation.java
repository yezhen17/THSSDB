package cn.edu.thssdb.operation;

import cn.edu.thssdb.schema.User;
import cn.edu.thssdb.schema.UserManager;

public class CreateUserOperation extends BaseOperation {

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
  public void exec(Long sessionId) {
    UserManager.getInstance().logon(sessionId, userName, passWord, User.Permission.USER);
  }
}
