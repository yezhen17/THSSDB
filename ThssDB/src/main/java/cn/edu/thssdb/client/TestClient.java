package cn.edu.thssdb.client;

import cn.edu.thssdb.rpc.thrift.ConnectReq;
import cn.edu.thssdb.rpc.thrift.ConnectResp;
import cn.edu.thssdb.rpc.thrift.DisconnectReq;
import cn.edu.thssdb.rpc.thrift.DisconnectResp;
import cn.edu.thssdb.rpc.thrift.ExecuteStatementReq;
import cn.edu.thssdb.rpc.thrift.ExecuteStatementResp;
import cn.edu.thssdb.rpc.thrift.IService;
import cn.edu.thssdb.utils.Global;
import cn.edu.thssdb.utils.ShowTable;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestClient {

  private static final Logger logger = LoggerFactory.getLogger(Client.class);

  private static final PrintStream SCREEN_PRINTER = new PrintStream(System.out);

  private static TTransport transport;
  private static TProtocol protocol;
  private static IService.Client client;

  public static void main(String[] args) {
    try {
      transport = new TSocket(Global.DEFAULT_SERVER_HOST, Global.DEFAULT_SERVER_PORT);
      transport.open();
      protocol = new TBinaryProtocol(transport);
      client = new IService.Client(protocol);

      long sessionId = connect();
      createDatabase(sessionId);
      useDatabase(sessionId);
      createTable(sessionId);
      insertData(sessionId);
      // queryData(sessionId);
      // queryDataJoin(sessionId);
      // transaction(sessionId);
      dropDatabase(sessionId);
      disconnect(sessionId);

      transport.close();
    } catch (TException e) {
      logger.error(e.getMessage());
    }
  }

  private static long connect() throws TException {
    String username = "username";
    String password = "password";
    ConnectReq req = new ConnectReq(username, password);
    ConnectResp resp = client.connect(req);
    if (resp.getStatus().code == Global.SUCCESS_CODE) {
      println("Connect Successfully!");
    }
    return resp.getSessionId();
  }

  private static void createDatabase(long sessionId) throws TException {
    String statement = "create database test;";
    ExecuteStatementReq req = new ExecuteStatementReq(sessionId, statement);
    ExecuteStatementResp resp = client.executeStatement(req);
    if (resp.getStatus().code == Global.SUCCESS_CODE) {
      println("Create Database Successfully!");
    }
  }

  private static void useDatabase(long sessionId) throws TException {
    String statement = "use test;";
    ExecuteStatementReq req = new ExecuteStatementReq(sessionId, statement);
    ExecuteStatementResp resp = client.executeStatement(req);
    if (resp.getStatus().code == Global.SUCCESS_CODE) {
      println("Use Database Successfully!");
    }
  }

  private static void createTable(long sessionId) throws TException {
    String statement = "create table person (name String(256), ID Int not null, primary key(ID));" +
            "CREATE TABLE t(a int primary key, b int, primary key(a));" +
            "CREATE TABLE r(a int primary key, b int, primary key(a));" +
            "CREATE TABLE q(a int primary key, b long, c float, d double, e string(10), f int not null, g string(100) not null, primary key(a));";
    ExecuteStatementReq req = new ExecuteStatementReq(sessionId, statement);
    ExecuteStatementResp resp = client.executeStatement(req);
    if (resp.getStatus().code == Global.SUCCESS_CODE) {
      println("Create Table Successfully!");
    } else {
      println(resp.getInformation());
    }
  }

  private static void insertData(long sessionId) throws TException {
    long startTime = System.currentTimeMillis();
    String[] statements = {"insert into person values ('Anna', 20);",
            "insert into person values ('Bob', 22);",
            "insert into person values ('Cindy', 30);",
            "INSERT INTO t(a, b) VALUES (0, 0);",
            "INSERT INTO t(a, b) VALUES (1, 2);",
            "INSERT INTO t(a, b) VALUES (2, 2);",
            "INSERT INTO t(a, b) VALUES (3, 4);",
                    "INSERT INTO t(a, b) VALUES (4, 4);",
            "INSERT INTO r(a, b) VALUES (1, 2);",
            "INSERT INTO r(a, b) VALUES (5, 5);",
                    "INSERT INTO r(a, b) VALUES (2, 2);",
                    "INSERT INTO r(a, b) VALUES (3, 3);",
                    "INSERT INTO r(a, b) VALUES (4, 4);",
            "insert into q(a,b,c,d,e,f,g) values (1,1,1,-1,'x',10,'y');",};
    for (String statement : statements) {
      ExecuteStatementReq req = new ExecuteStatementReq(sessionId, statement);
      ExecuteStatementResp resp = client.executeStatement(req);
      if (resp.getStatus().code == Global.SUCCESS_CODE) {
        println("Insert Data Successfully!");
      } else {
        println(resp.getInformation());
      }
    }
    println("It costs " + (System.currentTimeMillis() - startTime) + "ms.");
  }

  private static void queryDataJoin(long sessionId) throws TException {
    long startTime = System.currentTimeMillis();
    String[] statements = {
            "select * from t, r",
            "select * from t natural join r",
            "select * from t inner join r on t.a = r.a;",
            "select * from t join r on t.a = r.x;",
            "select * from t join r on t.a = r.a and t.a = r.b;",
            "select * from t full outer join r on t.a = r.a;",
            "select * from t left outer join r on t.a = r.a;",
            "select * from t right outer join r on t.a = r.a;",
            };
    for (String statement : statements) {
      ExecuteStatementReq req = new ExecuteStatementReq(sessionId, statement);
      ExecuteStatementResp resp = client.executeStatement(req);

      if (resp.getStatus().code == Global.SUCCESS_CODE) {
        new ShowTable(resp.getRowList(), resp.getColumnsList(), statement);
        println("Query Data Successfully!");
      }
    }
    println("It costs " + (System.currentTimeMillis() - startTime) + "ms.");
  }

  private static void queryData(long sessionId) throws TException {
    long startTime = System.currentTimeMillis();
    String[] statements = {"select * from person;",
            "select * from t where a = 2 or b > 2;",
            "select 1+a, 2/b, c * 10, 100-d, 2.7 + 2.2 from q",
            "select 1 + a, 2 * b from r;",
            "select max(a) from r;",
            "select min(g) from q;",
            "select distinct b from t;",
            "select * from t, r where t.a = 2 and r.a = 2 or t.b = 4 and r.b = 5;",
            "select * from t, r where (t.a = 0 or r.a = 1) and (t.b = 4 or r.b = 5);",
            "select a from r order by a desc;",
            "select * from r where b <> 2 and a >= 3;",
            "select * from r, t where r.b <> 2 and r.a >= 3;",
            "select * from r, t where r.b < 3 and t.a >= 3;",
            "select * from r, t where r.b < 3 and t.a = r.a;",};
    for (String statement : statements) {
      ExecuteStatementReq req = new ExecuteStatementReq(sessionId, statement);
      ExecuteStatementResp resp = client.executeStatement(req);
      println(resp.getInformation());
      if (resp.getStatus().code == Global.SUCCESS_CODE) {
        new ShowTable(resp.getRowList(), resp.getColumnsList(), statement);
        println("Query Data Successfully!");
      }
    }
    println("It costs " + (System.currentTimeMillis() - startTime) + "ms.");
  }

  private static void transaction(long sessionId) throws TException {
    long startTime = System.currentTimeMillis();
    String[] statements = {
            "commit",
            "begin transaction",
            "begin transaction",
            "commit",
            "INSERT INTO r(a, b) VALUES (6, 6);",
            "select max(b) from r;",
            "savepoint test",
            "INSERT INTO r(a, b) VALUES (7, 7);",
            "select max(b) from r;",
            "rollback test",
            "select max(b) from r;",
            "DELETE FROM r where a = 1;",
            "UPDATE  r SET A = 111 where a = 2;",
            "select * from r;",
            "rollback test",
            "select * from r;",
            "rollback",
            "select max(b) from r;"};
    for (String statement : statements) {
      ExecuteStatementReq req = new ExecuteStatementReq(sessionId, statement);
      ExecuteStatementResp resp = client.executeStatement(req);

      if (resp.getStatus().code == Global.SUCCESS_CODE) {
        if (resp.getRowList() != null) new ShowTable(resp.getRowList(), resp.getColumnsList(), statement);
        println("Query Data Successfully!");
      } else {
        println(resp.getInformation());
      }
    }
    println("It costs " + (System.currentTimeMillis() - startTime) + "ms.");
  }

  private static void dropDatabase(long sessionId) throws TException {
    String statement = "drop database test;";
    ExecuteStatementReq req = new ExecuteStatementReq(sessionId, statement);
    ExecuteStatementResp resp = client.executeStatement(req);
    if (resp.getStatus().code == Global.SUCCESS_CODE) {
      println("Drop Database Successfully!");
    }
  }

  private static void disconnect(long sessionId) throws TException {
    DisconnectReq req = new DisconnectReq(sessionId);
    DisconnectResp resp = client.disconnect(req);
    if (resp.getStatus().code == Global.SUCCESS_CODE) {
      println("Disconnect Successfully!");
    }
  }

  static void println(String msg) {
    SCREEN_PRINTER.println(msg);
  }
}

