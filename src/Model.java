import dataframapackage.DataFrame;

import java.util.ArrayList;

public interface Model {
    DataFrame loadDataFrame(String fileName);

    ArrayList<String> getFieldNames();

    String getColumnValueAt(String columnName, int row);

    default int getNumberOfRows() { return 0; }
}
