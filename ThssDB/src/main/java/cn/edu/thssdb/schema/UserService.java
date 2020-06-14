package cn.edu.thssdb.schema;
import cn.edu.thssdb.operation.*;
import cn.edu.thssdb.parser.MyParser;
import cn.edu.thssdb.rpc.thrift.*;
import cn.edu.thssdb.transaction.TransactionManager;
import cn.edu.thssdb.transaction.TransactionStatus;
import cn.edu.thssdb.utils.Global;

import java.util.ArrayList;
import java.util.List;

/***************
 * [class] 用户服务
 ***************/
public class UserService {
  public User user;   // 用户数据
  private Long sessionId;

  private TransactionManager transactionManager;   // 事务管理

  /**
   * [method] 构造方法
   */
  public UserService(User user, Long sessionId) {
    this.user = user;
    this.sessionId = sessionId;
    transactionManager = new TransactionManager(user.database, null);
  }

  public void disconnect() {
    forceCommit();
    if (user.database != null) {
      Manager.getInstance().quitDatabase(user.database);
    }
  }

  // 强制提交
  private void forceCommit() {
    if (user.database != null) {
      if (transactionManager.isUnderTransaction()) {
        TransactionStatus status = transactionManager.exec(new CommitOperation());
        if (!status.getStatus()) {
          throw new RuntimeException(status.getMessage());
        }
      }
    }
  }

  /**
   * [method] 服务处理方法 —— 主方法
   */
  public synchronized ExecuteStatementResp handle(String statement) {
    ExecuteStatementResp resp = new ExecuteStatementResp();
    boolean has_select = false;
    List<List<String>> data_all = new ArrayList<>();
    List<String> columns_all = new ArrayList<>();
    // String title_all;
    try {
      ArrayList<BaseOperation> operations = MyParser.getOperations(statement);
      for (BaseOperation operation: operations) {
        // 先设置operation操作的当前数据库
        operation.setCurrentUser(user.username, user.database);
        if (operation instanceof UseOperation) {
          forceCommit();
          operation.exec();
          String databaseName = ((UseOperation) operation).getName();
          user.database = databaseName;
          transactionManager.setDatabase(databaseName);
        } else if (operation instanceof CreateUserOperation) {
          ((CreateUserOperation) operation).exec(sessionId);
        }
        else {
          TransactionStatus status = transactionManager.exec(operation);
          if (!status.getStatus()) {
            throw new RuntimeException(status.getMessage());
          }
          TransactionStatus.Table result = status.getRes();
          if (result != null) {
            // title_all = result.title;
            columns_all = result.columns;
            data_all = result.data;
            has_select = true;
          }
        }
      }
      // 如有select语句
      if (has_select) {
        resp.setColumnsList(columns_all);
        resp.setRowList(data_all);
      }
      resp.setStatus(new Status(Global.SUCCESS_CODE));
      resp.setIsAbort(false);
      resp.setHasResult(false);
      resp.setInformation(Global.SUCCESS_EXECUTE);
      return resp;
    } catch (Exception e) {
      // 有报错则返回信息
      if (e.getMessage() == null) {
        resp.setInformation(e.toString());
      } else {
        resp.setInformation(e.getMessage());
      }
      resp.setStatus(new Status(Global.FAILURE_CODE));
      resp.setIsAbort(true);
      resp.setHasResult(false);
      return resp;
    }
  }
}
