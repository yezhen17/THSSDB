package cn.edu.thssdb.utils;

import javafx.embed.swing.JFXPanel;

import javax.swing.*;
import javax.swing.plaf.TableHeaderUI;
import javax.swing.table.*;
import java.awt.*;
import java.util.Enumeration;

public class ShowTable {


  public ShowTable(Object[][] tableData, Object[] columnTitle) {
    JFrame jframe = new JFrame("result");
    JTable table = new JTable();

    // 设置表格不可编辑
    DefaultTableModel t = new DefaultTableModel(tableData, columnTitle){
      public boolean isCellEditable(int row, int column)
      {
        return false;
      }
    };
    table.setModel(t);

    // 设置格式
    setTableStyle(table);

    jframe.add(new JScrollPane(table),BorderLayout.CENTER);
    jframe.pack();
    jframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    jframe.setVisible(true);
  }

  public ShowTable(Object[][] tableData, Object[] columnTitle, String title) {
    JFrame jframe = new JFrame(title);
    JTable table = new JTable();

    // 设置表格不可编辑
    DefaultTableModel t = new DefaultTableModel(tableData, columnTitle){
      public boolean isCellEditable(int row, int column)
      {
        return false;
      }
    };
    table.setModel(t);

    // 设置格式
    setTableStyle(table);

    jframe.add(new JScrollPane(table),BorderLayout.CENTER);
    jframe.pack();
    jframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    jframe.setVisible(true);
  }

  public static void main(String[] args) {

    //定义二维数组作为表格数据
    Object[][] tableData =
            {
                    new Object[]{"李清照", 29, "女","大概防守打法","规范规定","高考宽容","绕宽容","热看完评论","比热容","佛马蜂窝","放假哦跟金融if热积分佛门 "},
                    new Object[]{"李清照", 29, "女","大概防守打法","规范规定","高考宽容","绕宽容","热看完评论","比热容","佛马蜂窝","放假哦跟金融if热积分佛门 "},
                    new Object[]{"李清照", 29, "女","大概防守打法","规范规定","高考宽容","绕宽容","热看完评论","比热容","佛马蜂窝","放假哦跟金融if热积分佛门 "},
                    new Object[]{"李清照", 29, "女","大概防守打法","规范规定","高考宽容","绕宽容","热看完评论","比热容","佛马蜂窝","放假哦跟金融if热积分佛门 "},
                    new Object[]{"李清照", 29, "女","大概防守打法","规范规定","高考宽容","绕宽容","热看完评论","比热容","佛马蜂窝","放假哦跟金融if热积分佛门 "},
                    new Object[]{"李清照", 29, "女","大概防守打法","规范规定","高考宽容","绕宽容","热看完评论","比热容","佛马蜂窝","放假哦跟金融if热积分佛门 "},
                    new Object[]{"李清照", 29, "女","大概防守打法","规范规定","高考宽容","绕宽容","热看完评论","比热容","佛马蜂窝","放假哦跟金融if热积分佛门 "},
                    new Object[]{"李清照", 29, "女","大概防守打法","规范规定","高考宽容","绕宽容","热看完评论","比热容","佛马蜂窝","放假哦跟金融if热积分佛门 "},
            };
    //定义一维数据作为列标题
    Object[] columnTitle = {"姓名", "年龄", "性别","国家","省","市","县","镇","村子","道路","家"};


    ShowTable showTable =  new ShowTable(tableData, columnTitle,"一个表");

  }



  public static void setTableStyle(JTable jtb) {

    // 设置选中行的背景色
    jtb.setSelectionBackground(new Color(224, 242, 255));

    //设置表格每行的高度
    jtb.setRowHeight(35);

    // 设置点击表头自动实现排序
    jtb.setAutoCreateRowSorter(true);


    // 设置表头文字居中显示
    DefaultTableCellRenderer  renderer = (DefaultTableCellRenderer) jtb.getTableHeader().getDefaultRenderer();
    renderer.setHorizontalAlignment(renderer.CENTER);
    jtb.getTableHeader().setFont(new Font("黑体", Font.PLAIN, 18));
    jtb.getTableHeader().setBackground(new Color(150, 195, 255));

    // 设置表格中的数据居中显示
    DefaultTableCellRenderer r=new DefaultTableCellRenderer();
    r.setHorizontalAlignment(JLabel.CENTER);
    jtb.setDefaultRenderer(Object.class,r);

    jtb.setFocusable(false);

    jtb.setFont(new Font("新宋体", Font.PLAIN, 18));
    fitTableColumns(jtb);
  }

  // 根据内容自动调节表格的列宽度
  @SuppressWarnings("rawtypes")
  private static void fitTableColumns(JTable myTable)
  {
    myTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    JTableHeader header = myTable.getTableHeader();
    int rowCount = myTable.getRowCount();
    Enumeration columns = myTable.getColumnModel().getColumns();
    while(columns.hasMoreElements())
    {
      TableColumn column = (TableColumn)columns.nextElement();
      int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
      int width = (int)header.getDefaultRenderer().getTableCellRendererComponent
              (myTable, column.getIdentifier(), false, false, -1, col).getPreferredSize().getWidth();
      for(int row = 0; row < rowCount; row++)
      {
        int preferedWidth = (int)myTable.getCellRenderer(row, col).getTableCellRendererComponent
                (myTable, myTable.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
        width = Math.max(width, preferedWidth);
      }
      header.setResizingColumn(column);
      column.setWidth(width+myTable.getIntercellSpacing().width);
    }
  }

}