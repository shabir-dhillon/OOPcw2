import dataframapackage.Column;
import dataframapackage.DataFrame;
import dataframapackage.DataLoader;

import java.util.ArrayList;

public class DataManager implements Model {
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
}
