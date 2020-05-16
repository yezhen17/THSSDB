package cn.edu.thssdb.service;

import cn.edu.thssdb.exception.UserNotExistException;
import cn.edu.thssdb.exception.WrongPasswordException;
import cn.edu.thssdb.rpc.thrift.*;
import cn.edu.thssdb.schema.Manager;
import cn.edu.thssdb.schema.UserManager;
import cn.edu.thssdb.utils.Global;
import org.apache.thrift.TException;

import java.util.Date;

public class IServiceHandler implements IService.Iface {
  Manager dataManager = Manager.getInstance();
  UserManager userManager = UserManager.getInstance();

  /**
   * [method] 接口 - 打印时间
   * @param req {GetTimeReq} 请求
   * @return {GetTimeResp} 响应
   */
  @Override
  public GetTimeResp getTime(GetTimeReq req) throws TException {
    // 响应创建
    GetTimeResp resp = new GetTimeResp();
    resp.setTime(new Date().toString());
    resp.setStatus(new Status(Global.SUCCESS_CODE));
    return resp;
  }

  /**
   * [method] 接口 - 进行连接
   * @param req {ConnectReq} 请求
   * @return {ConnectResp} 响应
   */
  @Override
  public ConnectResp connect(ConnectReq req) throws TException {
    // 请求解析 & 响应创建
    String username = req.getUsername();
    String password = req.getPassword();
    ConnectResp resp = new ConnectResp();
    // 操作执行
    try {
      long sessionId = userManager.login(username, password);
      // 成功
      resp.setSessionId(sessionId);
      resp.setStatus(new Status(Global.SUCCESS_CODE));
      resp.setInformation(Global.SUCCESS_CONNECT);
    } catch (UserNotExistException e) {
      // 失败
      resp.setSessionId(-1);
      resp.setStatus(new Status(Global.FAILURE_CODE));
      resp.setInformation(Global.FAILURE_CONNECT_1);
    } catch (WrongPasswordException e) {
      // 失败
      resp.setSessionId(-1);
      resp.setStatus(new Status(Global.FAILURE_CODE));
      resp.setInformation(Global.FAILURE_CONNECT_2);
    }
    // 响应回复
    return resp;
  }

  /**
   * [method] 接口 - 关闭连接
   * @param req {DisconnetReq} 请求
   * @return {DisconnetResp} 响应
   */
  @Override
  public DisconnetResp disconnect(DisconnetReq req) throws TException {
    // 请求解析 & 响应创建
    long sessionId = req.getSessionId();
    DisconnetResp resp = new DisconnetResp();
    // 操作执行
    if (userManager.logout(sessionId)) {
      // 成功
      resp.setStatus(new Status(Global.SUCCESS_CODE));
      resp.setInformation(Global.SUCCESS_DISCONNECT);
    } else {
      // 失败
      resp.setStatus(new Status(Global.FAILURE_CODE));
      resp.setInformation(Global.FAILURE_DISCONNECT);
    }
    // 响应回复
    return resp;
  }

  /**
   * [method] 接口 - 执行操作
   * @param req {ExecuteStatementReq} 请求
   * @return {ExecuteStatementResp} 响应
   */
  @Override
  public ExecuteStatementResp executeStatement(ExecuteStatementReq req) throws TException {
    // 请求解析 & 响应创建
    long sessionId = req.getSessionId();
    String statement = req.getStatement();
    ExecuteStatementResp resp = new ExecuteStatementResp();
    // 操作执行 TODO 调用接口
    Manager manager = Manager.getInstance();
    resp.setIsAbort(false);
    resp.setHasResult(false);
    resp.setStatus(new Status(Global.FAILURE_CODE));
    resp.setInformation(Global.FAILURE_EXECUTE);
    // 响应回复
    return resp;
  }
}
