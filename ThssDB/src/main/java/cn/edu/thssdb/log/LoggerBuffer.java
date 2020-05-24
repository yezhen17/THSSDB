package cn.edu.thssdb.log;

import cn.edu.thssdb.exception.CustomIOException;

import java.util.ArrayList;

public class LoggerBuffer {

  private ArrayList<String> buffer;

  public LoggerBuffer() {}

  public void emptyBuffer(Logger logger) throws CustomIOException {
    logger.writeLines(buffer);
    buffer.clear();
  }
}
