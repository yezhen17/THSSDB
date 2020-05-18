package cn.edu.thssdb.exception;

public class WrongCreateTableException extends RuntimeException {
  public WrongCreateTableException(String msg){ super(msg); }
}
