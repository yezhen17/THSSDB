package cn.edu.thssdb.log;

import cn.edu.thssdb.exception.CustomIOException;
import cn.edu.thssdb.exception.MetaFileNotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Logger {

  // 加锁

  private String folder_name;
  private String file_name;
  private String full_path;

  public Logger(String folder_name, String file_name) throws CustomIOException {
    this.folder_name = folder_name;
    this.file_name = file_name;
    this.full_path = folder_name + "\\" + file_name;

    File d = new File(this.folder_name);
    if (!d.isDirectory()) {
      d.mkdirs();
    }
    File f = new File(this.full_path);
    if (!f.isFile()) {
      try {
        f.createNewFile();
      } catch (IOException e) {
        throw new CustomIOException();
      }
    }
  }

  public ArrayList<String> readLog() throws CustomIOException {
    ArrayList<String> lines = new ArrayList<>();
    String str;
    try {
      BufferedReader reader = new BufferedReader(new FileReader(full_path));
      while ((str = reader.readLine()) != null) {
        lines.add(str);
      }
      reader.close();
    } catch (IOException e) {
      throw new CustomIOException();
    }
    return lines;
  }

  public void eraseFile() throws CustomIOException {
    try {
      FileWriter writer = new FileWriter(full_path, false);
      writer.close();
    } catch (IOException e) {
      throw new CustomIOException();
    }
  }

  private void appendToFile(String content) throws CustomIOException {
    try {
      FileWriter writer = new FileWriter(full_path, true);
      writer.write(content);
      writer.close();
    } catch (IOException e) {
      throw new CustomIOException();
    }
  }

  public void writeLines(List<String> lines) throws CustomIOException {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(full_path, true));
      for (String line : lines) {
        writer.write(line);
        writer.newLine();
      }
      writer.close();
    } catch (IOException e) {
      throw new CustomIOException();
    }
  }

  public void writeSQL(String sql) throws CustomIOException {
    appendToFile(sql + "\n");
  }

  public void writeCommit() throws CustomIOException {
    appendToFile("COMMIT\n");
  }



  public void writeLog(String type, int transactionId) throws CustomIOException {
    appendToFile(type + " " + String.valueOf(transactionId) + "\n");
  }

  public void writeLog(int transactionId, Comparable oldValue, Comparable newValue) throws CustomIOException {
    appendToFile(String.valueOf(transactionId) + " " +
            String.valueOf(oldValue) + " " + String.valueOf(newValue) + "\n");
  }
}
