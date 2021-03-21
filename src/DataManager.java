import dataframapackage.Column;
import dataframapackage.DataFrame;
import dataframapackage.DataLoader;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public DataFrame loadDataFrame(File fileName)
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

    @Override
    public Color getRowColour(int row) {
        if (row % 2 == 0)
        {
            return new Color(0xC3D8E5);
        }
        return new Color(0xE5D0C3);
    }

    public int findOldestPerson() {
        ArrayList<Integer> alivePatients = getIndexOfPatientsAlive();

        return compareBirthDays("old", alivePatients);
    }

    private int compareBirthDays(String searchFor, ArrayList<Integer> alivePatients) {
        int row = -1;
        String colName = "BIRTHDATE";
        if (searchFor.equals("old"))
        {
            String oldest = "9999-12-31";
            for (int i = 0; i < alivePatients.size(); i++)
            {
                String currentDate = dataFrame.getValue(colName, alivePatients.get(i));
                if (currentDate.compareTo(oldest) < 0)
                {
                    oldest = currentDate;
                    row = alivePatients.get(i);
                }
            }
        }
        else
        {
            String youngest = "1111-01-01";
            for (int i = 1; i < alivePatients.size(); i++)
            {
                String currentDate = dataFrame.getValue(colName, alivePatients.get(i));
                if (currentDate.compareTo(youngest) > 0)
                {
                    youngest = currentDate;
                    row = alivePatients.get(i);
                }
            }
        }
        return row;
    }

    private ArrayList<Integer> getIndexOfPatientsAlive() {
        ArrayList<Integer> alivePatients = new ArrayList<>();
        for (int i = 0; i < dataFrame.getRowCount(); i++)
        {
            String deathDate = dataFrame.getValue("DEATHDATE", i);
            if (deathDate.equals("")) { alivePatients.add(i); }
        }
        return alivePatients;
    }

    public int findYoungestPerson() {
        ArrayList<Integer> alivePatients = getIndexOfPatientsAlive();

        return compareBirthDays("young", alivePatients);
    }

    public void writeToJson(File fileToSave) {
        JsonManager json = new JsonManager(dataFrame);
        json.writeToJson(fileToSave);
    }
}
