package cn.edu.thssdb.schema;


import cn.edu.thssdb.exception.TableMetaFileNotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Meta<V> {
    private String folder_name;
    private String file_name;
    private ArrayList<String []> lines;
    public Meta(String folder_name, String file_name, boolean just_created) {
        this.folder_name = folder_name;
        this.file_name = file_name;
        if (!just_created) {
            File d = new File(folder_name);
            d.mkdirs();
            new File(folder_name + "\\" + file_name);
        }

    }

    /**
     * [method] 读取元数据
     * @param
     * @exception TableMetaFileNotFoundException
     * @return
     */
    public ArrayList<String[]> readFromFile() throws IOException {
        String str;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file_name));
            while ((str = reader.readLine()) != null) // 判断最后一行不存在，为空结束循环
            {
                System.out.println(str);//原样输出读到的内容
                lines.add(str.split(" "));
            }
        } catch (FileNotFoundException e) {
            throw new TableMetaFileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        }
        return lines;
    }

    /**
     * [method] 写入元数据
     * @param
     * @exception TableMetaFileNotFoundException
     * @return
     */
    public void writeToFile(ArrayList<String> meta_data) throws IOException {
        String str;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file_name));
            for (String line : meta_data) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new IOException();
        }
    }

    public void deleteFile() {

    }
}

