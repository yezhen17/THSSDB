package cn.edu.thssdb.query;

import cn.edu.thssdb.exception.CalculationTypeException;
import cn.edu.thssdb.schema.Column;
import cn.edu.thssdb.schema.Entry;
import cn.edu.thssdb.schema.Row;
import cn.edu.thssdb.type.ColumnType;

import java.util.ArrayList;

/*
 这个类别存储一个列对应的所有数据
 */
public class QueryColumnPlusData {
  private String columnName;
  private int idx;
  private ColumnType columnType;
  private double num1;
  private double num2;
  private String op;
  private String aggr;
  private int type;
  private boolean isDistinct;

  private String title;
  private ArrayList<String> data;

  // 不同初始化方式对应不同类型
  public QueryColumnPlusData(double num1) {
    init();
    this.num1 = num1;
    this.type = 0;
  }

  public QueryColumnPlusData(String columnName, int idx, ColumnType columnType) {
    init();
    this.idx = idx;
    this.columnName = columnName;
    this.type = 1;
    this.columnType = columnType;
  }

  public QueryColumnPlusData(double num1, String columnName, String op, int idx, ColumnType columnType) {
    init();
    this.op = op;
    this.idx = idx;
    this.columnName = columnName;
    this.num1 = num1;
    this.type = 2;
    this.columnType = columnType;
  }

  public QueryColumnPlusData(String columnName, double num2, String op, int idx, ColumnType columnType) {
    init();
    this.op = op;
    this.idx = idx;
    this.columnName = columnName;
    this.num2 = num2;
    this.type = 3;
    this.columnType = columnType;
  }

  public QueryColumnPlusData(double num1, double num2, String op) {
    init();
    this.op = op;
    this.num1 = num1;
    this.num2 = num2;
    this.type = 4;
  }

  public QueryColumnPlusData(String columnName, String aggr, int idx, ColumnType columnType) {
    init();
    this.idx = idx;
    this.columnName = columnName;
    this.aggr = aggr;
    this.type = 5;
    this.columnType = columnType;
  }

  private void init() {
    data = new ArrayList<>();
  }

  // 不同聚集函数求值
  private Double aggregate(ArrayList<Entry> entries) {
    try {
      Double res = 0.0;
      switch (aggr) {
        case "sum": {
          for (Entry entry: entries) {
            if (entry.value == null) continue;
            res += Double.valueOf(entry.toString());
          }
          return res;
        }
        case "avg": {
          double count = 0.0;
          for (Entry entry: entries) {
            if (entry.value == null) continue;
            res += Double.valueOf(entry.toString());
            count++;
          }
          if (count == 0.0) return 0.0;
          return res / count;
        }
        case "max": {
          Double max = -Double.MAX_VALUE;
          for (Entry entry: entries) {
            if (entry.value == null) continue;
            Double tmp = Double.valueOf(entry.toString());
            if (tmp.compareTo(max) == 1) {
              max = tmp;
            }
          }
          if (max == -Double.MAX_VALUE) return null;
          return max;
        }
        case "min": {
          Double min = Double.MAX_VALUE;
          for (Entry entry: entries) {
            if (entry.value == null) continue;
            Double tmp = Double.valueOf(entry.toString());
            if (tmp.compareTo(min) == -1) {
              min = tmp;
            }
          }
          if (min == Double.MAX_VALUE) return null;
          return min;
        }
        default: {
          int count = 0;
          for (Entry entry: entries) {
            if (entry.value == null) continue;
            count++;
          }
          return (double) count;
        }
      }
    } catch (Exception e) {
      throw new CalculationTypeException();
    }

  }

  // 不同运算求值
  private Double calculate(Double a, Double b) {
    switch (op) {
      case "+": {
        return a + b;
      }
      case "-": {
        return a - b;
      }
      case "*": {
        return a * b;
      }
      default: {
        return a / b;
      }
    }
  }

  // 生成列标题
  public void generateTitle() {
    switch (type) {
      case 0: {
        title = String.valueOf(num1); break;
      }
      case 1: {
        title = columnName; break;
      }
      case 2: {
        title = ColumnType.getColumnTypeValue(columnType, String.valueOf(num1)) + " " + op + " " + columnName; break;
      }
      case 3: {
        title = columnName + " " + op + " " + ColumnType.getColumnTypeValue(columnType, String.valueOf(num2)); break;
      }
      case 4: {
        title = num1 + " " + op + " " + num2; break;
      }
      default: {
        title = aggr + "(" + columnName + ")"; break;
      }
    }
  }

  // 产生该列对应的数据
  public void generateData(ArrayList<Row> rows) {
    switch (type) {
      case 0: {
        for (int i = 0; i < rows.size(); i++) {
          data.add(String.valueOf(num1));
        }
        break;
      }
      case 1: {
        for (Row row : rows) {
          data.add(row.getEntries().get(idx).toString());
        }
        break;
      }
      case 2: {
        for (Row row : rows) {
          Entry tmp = row.getEntries().get(idx);
          if (tmp.value == null) data.add("null");
          else {
            String val = tmp.toString();
            data.add(String.valueOf(ColumnType.getColumnTypeValue(columnType, calculate(num1, Double.valueOf(val)))));
          }
        }
        break;
      }
      case 3: {
        for (Row row : rows) {
          Entry tmp = row.getEntries().get(idx);
          if (tmp.value == null) data.add("null");
          else {
            String val = tmp.toString();
            data.add(String.valueOf(ColumnType.getColumnTypeValue(columnType, calculate(Double.valueOf(val), num2))));
          }
        }
        break;
      }
      case 4: {
        for (int i = 0; i < rows.size(); i++) {
          data.add(String.valueOf(calculate(num1, num2)));
        }
        break;
      }
      default: {
        if (columnType == ColumnType.STRING && (aggr.equals("sum") || aggr.equals("avg"))) throw new CalculationTypeException();
        ArrayList<Entry> entries = new ArrayList<>();
        for (Row row : rows) {
          entries.add(row.getEntries().get(idx));
        }
        Double res = aggregate(entries);
        if (res == null) data.add("null");
        else {
          if (aggr.equals("count")) data.add(String.valueOf(res.intValue()));
          else if (aggr.equals("avg")) data.add(String.valueOf(res));
          else data.add(String.valueOf(ColumnType.getColumnTypeValue(columnType, res)));
        }
      }
    }
  }

  public String getTitle() {
    return title;
  }

  public ArrayList<String> getData() {
    return data;
  }

  public int getDataSize() { return data.size(); }
}
