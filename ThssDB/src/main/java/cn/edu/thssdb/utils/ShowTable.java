package cn.edu.thssdb.utils;

import javafx.embed.swing.JFXPanel;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ShowTable {


  public ShowTable(Object[][] tableData, Object[] columnTitle) {
    JFrame jframe = new JFrame("result");
    JTable table = new JTable(tableData, columnTitle);
    table.setEnabled(false);
    jframe.add(new JScrollPane(table));
    jframe.pack();
    // jf.setSize(200,200);
    jframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    jframe.setVisible(true);
  }

  public ShowTable(Object[][] tableData, Object[] columnTitle, String title) {
    JFrame jframe = new JFrame(title);
    JTable table = new JTable(tableData, columnTitle);
    table.setEnabled(false);
    jframe.add(new JScrollPane(table));
    jframe.pack();
    jframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    jframe.setVisible(true);
  }

  public static void main(String[] args) {

    //定义二维数组作为表格数据
    Object[][] tableData =
            {
                    new Object[]{"李清照", 29, "女"},
                    new Object[]{"苏格拉底", 56, "男"},
                    new Object[]{"李白", 35, "男"},
                    new Object[]{"虎头", 2, "男"}
            };
    //定义一维数据作为列标题
    Object[] columnTitle = {"姓名", "年龄", "性别"};


    new ShowTable(tableData, columnTitle);

  }
}