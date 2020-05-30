package cn.edu.thssdb.log;

import cn.edu.thssdb.exception.CustomIOException;
import cn.edu.thssdb.schema.Manager;

import java.util.ArrayList;

public class LoggerBuffer {

  private Logger logger;
  private ArrayList<String> buffer;

  public LoggerBuffer(Logger logger) {
    this.logger = logger;
    this.buffer = new ArrayList<>();
  }

//  public static LoggerBuffer getInstance() {
//    return LoggerBuffer.BufferHolder.INSTANCE;
//  }

  public void emptyBuffer() throws CustomIOException {
    logger.writeLines(buffer);
    buffer.clear();
  }

  public void writeBuffer(ArrayList<String> records) throws CustomIOException {
    buffer.addAll(records);
  }

//  /**
//   * [class] Holder类
//   */
//  private static class BufferHolder {
//    private static final LoggerBuffer INSTANCE = new LoggerBuffer();
//  }
}
