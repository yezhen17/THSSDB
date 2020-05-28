package cn.edu.thssdb.schema;
import cn.edu.thssdb.rpc.thrift.*;
import cn.edu.thssdb.utils.Global;

import java.time.temporal.Temporal;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/***************
 * [class] 用户服务
 ***************/
public class UserService {
    public User user;                                          // 用户数据
    // TODO
    /*
    private Thread serviceThread;                               // 服务线程
    private ReentrantLock lock = new ReentrantLock();           // 可重入锁
    private Condition condition = lock.newCondition();          // 条件对象
    */

    /**
     * [method] 构造方法
     */
    public UserService(User user) {
        this.user = user;

        /*
        // 创建服务线程
        serviceThread = new Thread(this::handle);
        // 开始服务线程
        serviceThread.start();
         */
    }


    /**
     * [method] 服务处理方法 —— 主方法
     */
    public synchronized ExecuteStatementResp handle(String statement) {
        // TODO 可根据类变量判断是否为完整事务，返回何值
        ExecuteStatementResp resp = new ExecuteStatementResp();
        resp.setStatus(new Status(Global.SUCCESS_CODE));
        resp.setInformation(Global.SUCCESS_EXECUTE);
        resp.setIsAbort(true);
        resp.setHasResult(false);
        return resp;
    }
}
