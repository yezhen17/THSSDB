package cn.edu.thssdb.utils;

public class Global {
  public static int fanout = 129;


  public static String DEFAULT_SERVER_HOST = "127.0.0.1";
  public static int DEFAULT_SERVER_PORT = 6667;

  public enum ISOLATION_LEVEL {
    READ_UNCOMMITTED, READ_COMMITTED, SERIALIZABLE
  }
    public static ISOLATION_LEVEL DATABASE_ISOLATION_LEVEL = ISOLATION_LEVEL.READ_COMMITTED;
  public static final boolean ISOLATION_STATUS = true;

  public static final String ROOT_USERNAME = "username";
  public static final String ROOT_PASSWORD = "password";

  public static final String CLI_PREFIX = "ThssDB>";
  public static final String INPUT_USERNAME = "Username:";
  public static final String INPUT_PASSWORD = "Password:";
  public static final String INPUT_STATEMENT = "Statement:";
  public static final String SHOW_TIME = "show time;";
  public static final String CONNECT = "connect;";
  public static final String DISCONNECT = "disconnect;";
  public static final String EXECUTE = "execute;";
  public static final String QUIT = "quit;";

  public static int SUCCESS_CODE = 0;
  public static int FAILURE_CODE = -1;

  public static final String ERROR_INVALID_STATEMENT = "[Error] Invalid statement!";
  public static final String ERROR_HAVE_CONNECTED = "[Error] You are connected, please disconnect first.";
  public static final String ERROR_NOT_CONNECTED = "[Error] You are not connected, please connect first.";
  public static final String SUCCESS_CONNECT = "[Success] Connect succeed.";
  public static final String SUCCESS_DISCONNECT = "[Success] Disconnect succeed.";
  public static final String SUCCESS_EXECUTE = "[Success] Execute succeed.";
  public static final String FAILURE_CONNECT_1 = "[Failure] Connect failed: user doesn't exist!";
  public static final String FAILURE_CONNECT_2 = "[Failure] Connect failed: wrong password!";
  public static final String FAILURE_CONNECT_3 = "[Failure] Connect failed: User already online!";
  public static final String FAILURE_DISCONNECT = "[Failure] Disconnect failed: user is not online!";
  public static final String FAILURE_EXECUTE = "[Failure] Execute failed!";
  public static final String FAILURE_FORBIDDEN = "[Failure] Forbidden!";


  public static final String S_URL_INTERNAL = "jdbc:default:connection";

  public static final String DATA_ROOT_FOLDER = "data";

  public static final String DATABASE_NAME_META = "DATABASE_NAME";
  public static final String TABLE_NAME_META = "TABLE_NAME";
  public static final String PRIMARY_KEY_INDEX_META = "PRIMARY_KEY_INDEX";
}
