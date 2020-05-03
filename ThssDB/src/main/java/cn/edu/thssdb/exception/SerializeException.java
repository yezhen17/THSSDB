package cn.edu.thssdb.exception;

import java.io.IOException;


// ignore
public class SerializeException extends IOException {
  @Override
  public String getMessage() {
    return "Exception: File not found during serialization!";
  }
}
