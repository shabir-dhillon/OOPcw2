import dataframapackage.Column;
import dataframapackage.DataFrame;
import dataframapackage.DataLoader;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class DataManager extends AbstractTableModel implements Model {
// TODO add override
// TODO RENAME CLASS
    private DataFrame dataFrame;
    private DataLoader loader;

    public DataManager()
    {
        dataFrame = new DataFrame();
        loader = new DataLoader();
    }

    public DataFrame loadDataFrame(String fileName)
    {
        dataFrame = loader.loadData(fileName);
        return dataFrame;
    }

    public ArrayList<String> getFieldNames()
    {
        return dataFrame.getColumnNames();
    }

    @Override
    public String getColumnValueAt(String columnName, int row) {
        return dataFrame.getValue(columnName, row);
    }
//
//    public String getColumnValueAt(String columnName, int row)
//    {
//        return dataFrame.getValue(columnName, row);
//    }

    public int getNumberOfRows()
    {
        return dataFrame.getRowCount();
    }

    @Override
    public int getRowCount() {
        return dataFrame.getRowCount();
    }

    @Override
    public int getColumnCount() {
        return dataFrame.getColumnNames().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return dataFrame.getValue(dataFrame.getColumnName(columnIndex), rowIndex);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return dataFrame.getColumnName(columnIndex);
    }
}
