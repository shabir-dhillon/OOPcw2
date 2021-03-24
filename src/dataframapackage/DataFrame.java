package dataframapackage;

import dataframapackage.Column;

import java.util.ArrayList;

public class DataFrame { ;
    public ArrayList<Column> dataFrame;

    public DataFrame() {
        this.dataFrame = new ArrayList<>(); }

    public boolean isEmpty()
    {
        return dataFrame.size() == 0;
    }

    // TODO IS THIS CORRECT?
    public void addColumn(String columnName)
    {
        Column new_column = new Column(columnName);
        this.dataFrame.add(new_column);
    }

    public String getColumnName(int index) {
        return dataFrame.get(index).getName();
    }

    public ArrayList<String> getColumnNames() {
        ArrayList<String> columnNames = new ArrayList<>();
        if (dataFrame.size() > 0)
        {
            for (Column col:dataFrame){ columnNames.add(col.getName()); }
            return columnNames;
        }
        return null;
    }

    public int getColumnCount()
    {
        return dataFrame.size();
    }
    /*
    the number of rows in a column, all columns should have the same number of
    rows when the frame is fully loaded with data
     */
    public int getRowCount() {
        if (dataFrame.size() == 0) return 0;
        int rowCount = dataFrame.get(0).getSize();
        for (Column column : dataFrame) {
            if (rowCount != column.getSize()) {
                // This is to prevent using a DataFrame that has columns of different row counts.
                rowCount = 0;
                break;
            }
        }
        return rowCount;
    }

    public String getValue(String columnName,int row) {
        for (Column col: dataFrame){
            if (col.getName().equals(columnName))
            {
                return col.getRowValue(row);
            }
        }
        return null;
    }

    public void putValue(String columnName,int row,String value) {
        for (Column col: dataFrame){
            if (col.getName().equals(columnName))
            {
                col.setRowValue(row, value);
            }
        }
    }

    public void addValue(String columnName, String value) {
        for (Column col: dataFrame){
            if (col.getName().equals(columnName))
            {
                col.addRowValue(value);
            }
        }
    }

}