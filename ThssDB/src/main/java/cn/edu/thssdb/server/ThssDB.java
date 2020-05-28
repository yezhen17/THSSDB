package cn.edu.thssdb.server;

import cn.edu.thssdb.rpc.thrift.IService;
import cn.edu.thssdb.schema.Manager;
import cn.edu.thssdb.service.IServiceHandler;
import cn.edu.thssdb.utils.Global;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***************
 * [class] 服务端 - ThssDB
 * [note] 单例模式（Holder）
 ***************/
public class ThssDB {

  private static final Logger logger = LoggerFactory.getLogger(ThssDB.class);     // 日志

  private static IServiceHandler handler;
  private static IService.Processor processor;
  private static TServerSocket transport;
  private static TServer server;

  public static String test = "AAA";

  private Manager manager;      // 管理器

  /**
   * [method] 获取服务端实例
   * @return 服务端实例
   */
  public static ThssDB getInstance() {
    return ThssDBHolder.INSTANCE;
  }

  /**
   * [method] 入口方法
   */
  public static void main(String[] args) {
    ThssDB server = ThssDB.getInstance();
    server.start();
  }
  private void start() {
    handler = new IServiceHandler();
    processor = new IService.Processor(handler);
    Runnable setup = () -> setUp(processor);
    new Thread(setup).start();
  }
  private static void setUp(IService.Processor processor) {
    try {
      transport = new TServerSocket(Global.DEFAULT_SERVER_PORT);
      server = new TThreadPoolServer(new TThreadPoolServer.Args(transport).processor(processor));
      // server = new TSimpleServer(new TServer.Args(transport).processor(processor));
      logger.info("Starting ThssDB ...");
      server.serve();
    } catch (TTransportException e) {
      logger.error(e.getMessage());
    }
  }

  /**
   * [class] Holder类
   */
  private static class ThssDBHolder {
    private static final ThssDB INSTANCE = new ThssDB();
  }
}
