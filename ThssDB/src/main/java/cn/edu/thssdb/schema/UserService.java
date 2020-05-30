package cn.edu.thssdb.schema;
import cn.edu.thssdb.log.Logger;
import cn.edu.thssdb.operation.BaseOperation;
import cn.edu.thssdb.parser.MyParser;
import cn.edu.thssdb.rpc.thrift.*;
import cn.edu.thssdb.transaction.TransactionManager;
import cn.edu.thssdb.utils.Global;

import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/***************
 * [class] 用户服务
 ***************/
public class UserService {
    public User user;                                          // 用户数据
    
    // TODO
    private Logger logger;
    private TransactionManager transactionManager;
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
        try {
            logger = new Logger("", "");
            transactionManager= new TransactionManager(logger);
        } catch (Exception e) {
            throw new RuntimeException();
        }

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
        ExecuteStatementResp resp = new ExecuteStatementResp();
        try {
            ArrayList<BaseOperation> operations = MyParser.getOperations(statement);
            for (BaseOperation operation: operations) {
                if (operation.isTransactionType()) {
                    // TODO 放到事务里
                    System.out.println("yes");
                } else {
                    operation.exec();
                    // TODO 加log
                }
            }
        } catch (Exception e) {
            resp.setStatus(new Status(Global.FAILURE_CODE));
            resp.setInformation(e.getMessage());
        }



        // TODO 可根据类变量判断是否为完整事务，返回何值

        resp.setStatus(new Status(Global.SUCCESS_CODE));
        resp.setInformation(statement);
        resp.setIsAbort(true);
        resp.setHasResult(false);
        return resp;
    }
}
