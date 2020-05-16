package cn.edu.thssdb.client;

import cn.edu.thssdb.rpc.thrift.ConnectReq;
import cn.edu.thssdb.rpc.thrift.DisconnetReq;
import cn.edu.thssdb.rpc.thrift.GetTimeReq;
import cn.edu.thssdb.rpc.thrift.IService;
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

  private static final Logger logger = LoggerFactory.getLogger(Client.class);     // 日志

  static final String HOST_ARGS = "h";
  static final String HOST_NAME = "host";

  static final String HELP_ARGS = "help";
  static final String HELP_NAME = "help";

  static final String PORT_ARGS = "p";
  static final String PORT_NAME = "port";

  private static final PrintStream SCREEN_PRINTER = new PrintStream(System.out);
  private static final Scanner SCANNER = new Scanner(System.in);

  private static TTransport transport;
  private static TProtocol protocol;
  private static IService.Client client;
  private static CommandLine commandLine;

  /**
   * [method] 入口方法
   */
  public static void main(String[] args) {
    commandLine = parseCmd(args);
    if (commandLine.hasOption(HELP_ARGS)) {
      showHelp();
      return;
    }
    try {
      echoStarting();
      String host = commandLine.getOptionValue(HOST_ARGS, Global.DEFAULT_SERVER_HOST);    // 获取 host
      int port = Integer.parseInt(commandLine.getOptionValue(PORT_ARGS, String.valueOf(Global.DEFAULT_SERVER_PORT)));   // 获取 port
      // 创建连接
      transport = new TSocket(host, port);
      transport.open();
      protocol = new TBinaryProtocol(transport);
      client = new IService.Client(protocol);
      // ***** MAIN LOOP *****
      boolean open = true;
      while (open) {
        print(Global.CLI_PREFIX);
        String msg = SCANNER.nextLine();
        // *** START ***
        long startTime = System.currentTimeMillis();
        // TODO 命令添加
        switch (msg.trim()) {
          case Global.SHOW_TIME:
            getTime();
            break;
          case Global.QUIT:
            open = false;
            break;
          default:
            println("Invalid statements!");
            break;
        }
        long endTime = System.currentTimeMillis();
        // *** END ***
        println("It costs " + (endTime - startTime) + " ms.");
      }
      // ***** LOOP END *****
      // 关闭连接
      transport.close();
    } catch (TTransportException e) {
      logger.error(e.getMessage());
    }
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

  /**
   * [method] 请求 - 打印时间
   */
  private static void getTime() {
    GetTimeReq req = new GetTimeReq();
    try {
      println(client.getTime(req).getTime());
    } catch (TException e) {
      logger.error(e.getMessage());
    }
  }

  /**
   * [method] 请求 - 进行连接
   */
  private static void connect() {
    ConnectReq req = new ConnectReq();
    try {
      println(String.valueOf(client.connect(req).getSessionId()));
    } catch (TException e) {
      logger.error(e.getMessage());
    }
  }


  /**
   * [method] 请求 - 关闭连接
   */
  private static void disconnect() {
    DisconnetReq req = new DisconnetReq();
    try {
      println(String.valueOf(client.disconnect(req)));
    } catch (TException e) {
      logger.error(e.getMessage());
    }
  }
}
