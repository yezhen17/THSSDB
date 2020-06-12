package cn.edu.thssdb.operation;

import cn.edu.thssdb.schema.User;
import cn.edu.thssdb.schema.UserManager;

public class DropUserOperation {
  private String userName;

  /**
   * [method] 构造方法
   */
  public DropUserOperation(String userName) {
    this.userName = userName;
  }

  /**
   * [method] 执行操作
   */
  public void exec(Long sessionId) {
    // UserManager.getInstance().logon(sessionId, userName, passWord, User.Permission.USER);
  }
}
