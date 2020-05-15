package cn.edu.thssdb.schema;


import cn.edu.thssdb.exception.CustomIOException;
import cn.edu.thssdb.exception.MetaFileNotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Meta {
  private String folder_name;
  private String file_name;
  private String full_path;

  public Meta(String folder_name, String file_name, boolean just_created) throws CustomIOException {
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
//    if (!exists(this.full_path)) {
//
//
//    }
//    if (just_created) {
//
//    }

  }

  /**
   * [method] 读取元数据
   * @param
   * @exception MetaFileNotFoundException, CustomIOException
   * @return 元数据
   */
  public ArrayList<String[]> readFromFile() throws MetaFileNotFoundException, CustomIOException {
    ArrayList<String []> lines = new ArrayList<>();
    String str;
    try {
      BufferedReader reader = new BufferedReader(new FileReader(full_path));
      while ((str = reader.readLine()) != null) {
        lines.add(str.split(" "));
      }
      reader.close();
    } catch (FileNotFoundException e) {
      throw new MetaFileNotFoundException();
    } catch (IOException e) {
      throw new CustomIOException();
    }
    return lines;
  }

  /**
   * [method] 写入元数据
   * @param meta_data
   * @exception CustomIOException
   * @return
   */
  public void writeToFile(ArrayList<String> meta_data) throws CustomIOException {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(full_path));
      for (String line : meta_data) {
        writer.write(line);
        writer.newLine();
      }
      writer.close();
    } catch (IOException e) {
      throw new CustomIOException();
    }
  }

  public void deleteFile() {
      File f = new File(this.full_path);
      f.delete();
  }
}

