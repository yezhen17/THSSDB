package cn.edu.thssdb.client;

import cn.edu.thssdb.rpc.thrift.*;
import cn.edu.thssdb.utils.Global;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;
import java.util.Scanner;

/***************
 * [class] 客户端
 ***************/
public class Client {
  private static IService.Client client;  // 客户端实例
  private static final Logger logger = LoggerFactory.getLogger(Client.class);     // 日志
  private static final Scanner SCANNER = new Scanner(System.in);                  // 输入流
  private static final PrintStream SCREEN_PRINTER = new PrintStream(System.out);  // 输出流


  // 选项定义
  static final String HOST_ARGS = "h";
  static final String HOST_NAME = "host";
  static final String HELP_ARGS = "help";
  static final String HELP_NAME = "help";
  static final String PORT_ARGS = "p";
  static final String PORT_NAME = "port";

  private static boolean isRunning = true;      // 运行状态
  private static boolean isConnected = false;   // 连接状态
  private static long sessionId = -1;           // Session ID

  /**
   * [method] 入口方法
   */
  public static void main(String[] args) {
    CommandLine commandLine = parseCmd(args);
    if (commandLine.hasOption(HELP_ARGS)) {
      showHelp();
      return;
    }
    try {
      echoStarting();
      String host = commandLine.getOptionValue(HOST_ARGS, Global.DEFAULT_SERVER_HOST);    // 获取 host
      int port = Integer.parseInt(commandLine.getOptionValue(PORT_ARGS, String.valueOf(Global.DEFAULT_SERVER_PORT)));   // 获取 port
      // 创建连接
      TTransport transport = new TSocket(host, port);
      transport.open();
      TProtocol protocol = new TBinaryProtocol(transport);
      client = new IService.Client(protocol);
      // ***** MAIN LOOP *****
      isRunning = true;
      isConnected = false;
      sessionId = -1;
      String msg, username, password, statement;
      while (isRunning) {
        print(Global.CLI_PREFIX);
        msg = SCANNER.nextLine().trim();
        // *** MAIN SWITCH ***
        switch (msg) {
          case Global.SHOW_TIME:
            // * 显示时间 *
            getTime();
            break;
          case Global.CONNECT:
            // * 进行连接 *
            if (isConnected) {
              println(Global.ERROR_HAVE_CONNECTED);
            } else {
              print(Global.INPUT_USERNAME);
              username = SCANNER.nextLine().trim();
              print(Global.INPUT_PASSWORD);
              password = SCANNER.nextLine().trim();
              connect(username, password);
            }
            break;
          case Global.DISCONNECT:
            // * 关闭连接 *
            if (!isConnected) {
              println(Global.ERROR_NOT_CONNECTED);
            } else {
              disconnect(sessionId);
            }
            break;
          case Global.EXECUTE:
            // * 执行操作 *
            if (!isConnected) {
              println(Global.ERROR_NOT_CONNECTED);
            } else {
              print(Global.INPUT_STATEMENT);
              statement = SCANNER.nextLine().trim();
              execute(sessionId, statement);
            }
            break;
          case Global.QUIT:
            // * 退出程序 *
            if (isConnected) {
              println(Global.ERROR_HAVE_CONNECTED);
            } else {
              isRunning = false;
            }
            break;
          default:
            println(Global.ERROR_INVALID_STATEMENT);
            break;
        }
        // *** SWITCH END ***
      }
      // ***** LOOP END *****
      // 关闭连接
      transport.close();
    } catch (TTransportException e) {
      logger.error(e.getMessage());
    }
  }

  /**
   * [method] 请求 - 打印时间
   */
  private static void getTime() {
    long startTime = System.currentTimeMillis();
    // 请求创建
    GetTimeReq req = new GetTimeReq();
    try {
      // 请求发送 & 响应获取
      GetTimeResp resp = client.getTime(req);
      // 响应处理
      println(resp.getTime());
    } catch (TException e) {
      logger.error(e.getMessage());
    }
    long endTime = System.currentTimeMillis();
    println("It costs " + (endTime - startTime) + " ms.");
  }

  /**
   * [method] 请求 - 进行连接
   * @param username {String} 用户名
   * @param password {String} 密码
   */
  private static void connect(String username, String password) {
    long startTime = System.currentTimeMillis();
    // 请求创建
    ConnectReq req = new ConnectReq();
    req.setUsername(username);
    req.setPassword(password);
    try {
      // 请求发送 & 响应获取
      ConnectResp resp = client.connect(req);
      // 响应处理 TODO
      println(String.valueOf(resp));
    } catch (TException e) {
      logger.error(e.getMessage());
    }
    long endTime = System.currentTimeMillis();
    println("It costs " + (endTime - startTime) + " ms.");
  }

  /**
   * [method] 请求 - 关闭连接
   * @param sessionId {long} session ID
   */
  private static void disconnect(long sessionId) {
    long startTime = System.currentTimeMillis();
    // 请求创建
    DisconnetReq req = new DisconnetReq();
    req.setSessionId(sessionId);
    try {
      // 请求发送 & 响应获取
      DisconnetResp resp = client.disconnect(req);
      // 响应处理 TODO
      println(String.valueOf(resp));
    } catch (TException e) {
      logger.error(e.getMessage());
    }
    long endTime = System.currentTimeMillis();
    println("It costs " + (endTime - startTime) + " ms.");
  }

  /**
   * [method] 请求 - 执行操作
   * @param sessionId {long} Session ID
   * @param statement {String} 操作语句
   */
  private static void execute(long sessionId, String statement) {
    long startTime = System.currentTimeMillis();
    // 请求创建
    ExecuteStatementReq req = new ExecuteStatementReq();
    req.setSessionId(sessionId);
    req.setStatement(statement);
    try {
      // 请求发送 & 响应获取
      ExecuteStatementResp resp = client.executeStatement(req);
      // 响应处理 TODO
      println(String.valueOf(resp));
    } catch (TException e) {
      logger.error(e.getMessage());
    }
    long endTime = System.currentTimeMillis();
    println("It costs " + (endTime - startTime) + " ms.");
  }


  /**
   * [method] 创建命令行选项
   */
  static Options createOptions() {
    Options options = new Options();
    options.addOption(Option.builder(HELP_ARGS)
        .argName(HELP_NAME)
        .desc("Display help information(optional)")
        .hasArg(false)
        .required(false)
        .build()
    );
    options.addOption(Option.builder(HOST_ARGS)
        .argName(HOST_NAME)
        .desc("Host (optional, default 127.0.0.1)")
        .hasArg(false)
        .required(false)
        .build()
    );
    options.addOption(Option.builder(PORT_ARGS)
        .argName(PORT_NAME)
        .desc("Port (optional, default 6667)")
        .hasArg(false)
        .required(false)
        .build()
    );
    return options;
  }

  /**
   * [method] 解析命令行
   */
  static CommandLine parseCmd(String[] args) {
    Options options = createOptions();
    CommandLineParser parser = new DefaultParser();
    CommandLine cmd = null;
    try {
      cmd = parser.parse(options, args);
    } catch (ParseException e) {
      logger.error(e.getMessage());
      println("Invalid command line argument!");
      System.exit(-1);
    }
    return cmd;
  }

  /**
   * [method] 打印信息
   */
  static void print(String msg) {
    SCREEN_PRINTER.print(msg);
  }
  static void println() {
    SCREEN_PRINTER.println();
  }
  static void println(String msg) {
    SCREEN_PRINTER.println(msg);
  }

  /**
   * [method] 打印开始信息
   */
  static void echoStarting() {
    println("----------------------");
    println("Starting ThssDB Client");
    println("----------------------");
  }

  /**
   * [method] 打印帮助信息
   */
  static void showHelp() {
    // TODO 添加帮助信息
    println("DO IT YOURSELF");
  }
}
