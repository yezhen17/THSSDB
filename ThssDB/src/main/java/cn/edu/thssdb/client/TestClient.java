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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestClient {

  private static final Logger logger = LoggerFactory.getLogger(Client.class);

  private static final PrintStream SCREEN_PRINTER = new PrintStream(System.out);

  private static TTransport transport;
  private static TProtocol protocol;
  private static IService.Client client;

  private static final Scanner SCANNER = new Scanner(System.in);

  public static void main(String[] args) {
    try {
      transport = new TSocket(Global.DEFAULT_SERVER_HOST, Global.DEFAULT_SERVER_PORT);
      transport.open();
      protocol = new TBinaryProtocol(transport);
      client = new IService.Client(protocol);

      List<String> insertStatements = loadInsertStatements();

      long sessionId = connect();
      createDatabase(sessionId);
      useDatabase(sessionId);
      createTable(sessionId);
      insertData(sessionId, insertStatements);
      queryData(sessionId);

      insertTest(sessionId);
      deleteTest(sessionId);
      updateTest(sessionId);
      showTest(sessionId);

      alterTest(sessionId);
      whereTest(sessionId);
      joinTest(sessionId);
      selectTest(sessionId);
      aggrTest(sessionId);
      orderByTest(sessionId);
      rollbackTest(sessionId);

      dropDatabase(sessionId);
      disconnect(sessionId);

      transport.close();
    } catch (TException | IOException e) {
      logger.error(e.getMessage());
    }
  }

  private static void showResult(String[] statements, Long sessionId) throws TException {
    println("Continue:");
    SCANNER.nextLine();
    for (String statement : statements) {
      println("\n-----------------------------------------");
      println(statement);
      ExecuteStatementReq req = new ExecuteStatementReq(sessionId, statement);
      ExecuteStatementResp resp = client.executeStatement(req);
      if (resp.getStatus().code == Global.SUCCESS_CODE) {
        if (resp.getRowList() != null) {
          new ShowTable(resp.getRowList(), resp.getColumnsList(), statement);
        }
        println(resp.getInformation());
      } else {
        println("Warning! Statement caused the following exception:");
        println(resp.getInformation());
      }
      println("-----------------------------------------");
    }
  }

  private static void insertTest(Long sessionId) throws TException {
    String[] statements = {
            "insert into student(s_id, dept_name, tot_cred) values ('1', 'Math', 50);",
            "insert into student values('66008', 'Szczerban', 'Languages', 25);",
    };
    showResult(statements, sessionId);
  }

  private static void deleteTest(Long sessionId) throws TException {
    String[] statements = {
            "select s_name from student where s_id = '66008';",
            "delete from student where s_id = '66008';",
            "select s_name from student where s_id = '66008';",
    };
    showResult(statements, sessionId);
  }

  private static void updateTest(Long sessionId) throws TException {
    String[] statements = {
            "select s_name from student where s_id = '76291';",
            "update student set s_name = 'Dell' where s_id = '76291';",
            "select s_name from student where s_id = '76291';",
    };
    showResult(statements, sessionId);
  }

  private static void showTest(Long sessionId) throws TException {
    String[] statements = {
            "show table advisor;",
            "drop table advisor;",
            "select s_id from advisor;",
    };
    showResult(statements, sessionId);
  }

  private static void alterTest(Long sessionId) throws TException {
    String[] statements = {
            "alter table department add id Int;",
            "select * from department",
            "alter table department alter column BUdget Int;",
            "select * from department",
            "alter table department drop column id;",
            "select * from department",
            "alter table department alter column building String(1);",
            "select * from department",
    };
    showResult(statements, sessionId);
  }

  private static void joinTest(Long sessionId) throws TException {
    String[] statements = {
            "select * from t, r",
            "select a, b, c from t natural join r",
            "select * from t inner join r on t.a = r.a;",
            "select * from t join r on t.a = r.x;",
            "select * from t join r on t.a = s.a;",
            "select * from t join r on t.a = r.a and t.b = r.c;",
            "select * from t full outer join r on t.a = r.a;",
            "select * from t left outer join r on t.a = r.a;",
            "select * from t right outer join r on t.a = r.a;",
            "select course_id, title, building from department natural join course"
    };
    showResult(statements, sessionId);
  }

  private static void orderByTest(Long sessionId) throws TException {
    String[] statements = {
            "select * from department order by budget;",
            "select * from department order by building, budget desc;",
    };
    showResult(statements, sessionId);
  }

  private static void aggrTest(Long sessionId) throws TException {
    String[] statements = {
            "select avg(budget) from department;",
            "select max(budget), min(budget) from department;",
            "select sum(budget) from department;",
            "select count(budget) from department;",
    };
    showResult(statements, sessionId);
  }

  private static void whereTest(Long sessionId) throws TException {
    String[] statements = {
            "select building, credits from department, course where (building = 'Palmer' or building = 'Lambeau') and credits = 4;",
            "select building, credits from department, course where building = 'Palmer' or building = 'Lambeau' and credits = 4;",
            "select building, credits from department natural join course " +
                    "where building = 'Palmer' or building = 'Lambeau' or building = 'Mercer';",
            "insert into department(dept_name, building) values('test', 'test')",
            "select * from department where budget is null",
            "delete from department where budget is null",
            "select * from department where budget is null",
    };
    showResult(statements, sessionId);
  }

  private static void selectTest(Long sessionId) throws TException {
    String[] statements = {
            "show table q",
            "select 1 + a, 2/b, c * 10, 100 - d, 2.7 + 2.2, g from q",
            "select distinct 100 - 1 from q",
            "select all 100 - 1 from q",
            "select distinct building, credits from course, department"
    };
    showResult(statements, sessionId);
  }

  private static void rollbackTest(Long sessionId) throws TException {
    String[] statements = {
            "commit",
            "begin transaction",
            "begin transaction",
            "commit",
            "INSERT INTO r(a, c) VALUES (6, 6);",
            "select max(c) from r;",
            "savepoint test",
            "INSERT INTO r(a, c) VALUES (7, 7);",
            "select max(c) from r;",
            "rollback to savepoint test",
            "select max(c) from r;",
            "DELETE FROM r where a = 1;",
            "UPDATE r SET A = 22 where a = 2;",
            "select * from r;",
            "rollback to savepoint test",
            "select * from r;",
            "rollback",
            "select max(c) from r;",
    };
    showResult(statements, sessionId);
  }

  private static List<String> loadInsertStatements() throws IOException {
    List<String> statements = new ArrayList<>();
    File file = new File("insert_into.sql");
    if (file.exists() && file.isFile()){
      FileInputStream fileInputStream = new FileInputStream(file);
      InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        statements.add(line);
      }
      bufferedReader.close();
      inputStreamReader.close();
      fileInputStream.close();
    }
    return statements;
  }

  private static long connect() throws TException {
    String username = "username";
    String password = "password";
    ConnectReq req = new ConnectReq(username, password);
    ConnectResp resp = client.connect(req);
    if (resp.getStatus().code == Global.SUCCESS_CODE) {
      println("Connect Successfully!");
    } else {
      println("Connect Unsuccessfully!");
    }
    return resp.getSessionId();
  }

  private static void createDatabase(long sessionId) throws TException {
    String statement = "create database test;";
    ExecuteStatementReq req = new ExecuteStatementReq(sessionId, statement);
    ExecuteStatementResp resp = client.executeStatement(req);
    if (resp.getStatus().code == Global.SUCCESS_CODE) {
      println("Create Database Successfully!");
    } else {
      println("Create Database Unsuccessfully!");
    }
  }

  private static void useDatabase(long sessionId) throws TException {
    String statement = "use test;";
    ExecuteStatementReq req = new ExecuteStatementReq(sessionId, statement);
    ExecuteStatementResp resp = client.executeStatement(req);
    if (resp.getStatus().code == Global.SUCCESS_CODE) {
      println("Use Database Successfully!");
    } else {
      println("Use Database Unsuccessfully!");
    }
  }

  private static void createTable(long sessionId) throws TException {
    String[] statements = {
            "create table department (dept_name String(20), building String(15), budget Double, primary key(dept_name));",
            "create table course (course_id String(8), title String(50), dept_name String(20), credits Int, primary key(course_id));",
            "create table instructor (i_id String(5), i_name String(20) not null, dept_name String(20), salary Float, primary key(i_id));",
            "create table student (s_id String(5), s_name String(20) not null, dept_name String(20), tot_cred Int, primary key(s_id));",
            "create table advisor (s_id String(5), i_id String(5), primary key (s_id));",
            "CREATE TABLE t(a int primary key, b int, primary key(a));",
            "CREATE TABLE r(a int primary key, c int, primary key(a));",
            "CREATE TABLE q(a int primary key, b long, c float, d double, e string(10), " +
                    "f int not null, g string(100) not null, primary key(a));",
    };
    for (String statement : statements) {
      ExecuteStatementReq req = new ExecuteStatementReq(sessionId, statement);
      ExecuteStatementResp resp = client.executeStatement(req);
      if (resp.getStatus().code == Global.SUCCESS_CODE) {
        println("Create Table Successfully!");
      } else {
        println("Create Table Unsuccessfully!");
      }
    }
  }

  private static void insertData(long sessionId, List<String> statements) throws TException {
    long startTime = System.currentTimeMillis();
    boolean success = true;
    for (String statement : statements) {
      ExecuteStatementReq req = new ExecuteStatementReq(sessionId, statement);
      ExecuteStatementResp resp = client.executeStatement(req);
      if (resp.getStatus().code == Global.FAILURE_CODE) {
        success = false;
      }
    }
    String[] statements2 = {
            "INSERT INTO t(a, b) VALUES (0, 1);",
            "INSERT INTO t(a, b) VALUES (1, 1);",
            "INSERT INTO t(a, b) VALUES (2, 1);",
            "INSERT INTO t(a, b) VALUES (3, 3);",
            "INSERT INTO t(a, b) VALUES (4, 3);",
            "INSERT INTO r(a, c) VALUES (1, 1);",
            "INSERT INTO r(a, c) VALUES (2, 2);",
            "INSERT INTO r(a, c) VALUES (3, 3);",
            "INSERT INTO r(a, c) VALUES (4, 4);",
            "INSERT INTO r(a, c) VALUES (5, 5);",
            "insert into q(a,b,c,d,e,f,g) values (1,1,1,-1,'x',10,'y');",
            "insert into q(a,b,c,d,e,f,g) values (2,2,2,-2,'X',20,'Y');",
    };
    for (String statement : statements2) {
      ExecuteStatementReq req = new ExecuteStatementReq(sessionId, statement);
      ExecuteStatementResp resp = client.executeStatement(req);
      if (resp.getStatus().code == Global.FAILURE_CODE) {
        success = false;
      }
    }
    if (success) {
      println("Insert Data Successfully!");
    } else {
      println("Insert Data Unsuccessfully!");
    }
    println("It costs " + (System.currentTimeMillis() - startTime) + "ms.");
  }

  private static void queryData(long sessionId) throws TException {
    long startTime = System.currentTimeMillis();
    String[] statements = {
            "select s_id, s_name, dept_name, tot_cred from student;",
            "select course_id, title from course where credits >= 4;",
            "select s_id, s_name from student where dept_name = 'Physics';",
            "select course_id, title from course join department on course.dept_name = department.dept_name where building <> 'Palmer';",
            "select s_id from instructor join advisor on instructor.i_id = advisor.i_id where i_name = 'Luo';"
    };
    int[] results = {2000, 92, 96, 182, 44};
    for (int i = 0; i < statements.length; i++) {
      ExecuteStatementReq req = new ExecuteStatementReq(sessionId, statements[i]);
      ExecuteStatementResp resp = client.executeStatement(req);
      if (resp.getStatus().code == Global.SUCCESS_CODE) {
        println("Query Data Successfully!");
      } else {
        println("Query Data Unsuccessfully!");
      }
      if (resp.getRowList() != null) {
        if (resp.getRowList().size() == results[i]) {
          println("The Result Set is Correct!");
        } else {
          new ShowTable(resp.getRowList(), resp.getColumnsList(), statements[i]);
          println("The Result Set is Wrong!");
        }
      }

    }
    println("It costs " + (System.currentTimeMillis() - startTime) + "ms.");
  }

  private static void disconnect(long sessionId) throws TException {
    DisconnectReq req = new DisconnectReq(sessionId);
    DisconnectResp resp = client.disconnect(req);
    if (resp.getStatus().code == Global.SUCCESS_CODE) {
      println("Disconnect Successfully!");
    } else {
      println("Disconnect Unsuccessfully!");
    }
  }

  private static void dropDatabase(long sessionId) throws TException {
    String statement = "drop database test;";
    ExecuteStatementReq req = new ExecuteStatementReq(sessionId, statement);
    ExecuteStatementResp resp = client.executeStatement(req);
    if (resp.getStatus().code == Global.SUCCESS_CODE) {
      println("Drop Database Successfully!");
    }
  }

  static void println(String msg) {
    SCREEN_PRINTER.println(msg);
  }
}

