import dataframapackage.DataFrame;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public interface Model {
    // TODO needed?
    DataFrame loadDataFrame(File fileName);

    ArrayList<String> getFieldNames();

    String getColumnValueAt(String columnName, int row);

    default int getNumberOfRows() { return 0; }

    Color getRowColour(int row);
}
