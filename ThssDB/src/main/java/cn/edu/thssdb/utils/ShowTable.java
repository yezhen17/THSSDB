package cn.edu.thssdb.utils;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Enumeration;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/*
展示select和show的结果，弹出一个表格，可调节
 */
public class ShowTable {


  public ShowTable(List<List<String>> tableData, List<String> columnTitle, String title) {
    JFrame jframe = new JFrame(title);
    JTable table = new JTable();

    DefaultTableModel tableModel = new DefaultTableModel(){
      public boolean isCellEditable(int row, int column)
      {
        return false;
      }
    };

    for (String column_title: columnTitle) {
      tableModel.addColumn(column_title);
    }
    for (List<String> table_data: tableData) {
      tableModel.addRow(table_data.toArray());
    }
    table.setModel(tableModel);

    // 设置格式
    setTableStyle(table);

    jframe.add(new JScrollPane(table),BorderLayout.CENTER);
    jframe.pack();
    // jframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    // 屏幕中间显示
    double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    jframe.setLocation( (int) (width - jframe.getWidth()) / 2,(int) (height - jframe.getHeight()) / 2);

    jframe.setVisible(true);
  }

  public static void main(String[] args) {

    List<List<String>> l = new ArrayList<>();
    List<String> k = new ArrayList<>(Arrays.asList("fdafs","fdaf","fdafds"));
    l.add(k);
    new ShowTable(l, k,"一个表");
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