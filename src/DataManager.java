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
import java.util.HashMap;

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

    public DataFrame loadCSVDataFrame(File fileName)
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

    public HashMap<String, Integer> populationOfCities() {
        HashMap<String, Integer> cityPopulation = new HashMap<String, Integer>();
        for (int i = 0; i < dataFrame.getRowCount(); i++)
        {
            String city = dataFrame.getValue("CITY", i);
            if (cityPopulation.containsKey(city))
            {
                cityPopulation.put(city, cityPopulation.get(city) + 1);
            }
            else { cityPopulation.put(city, 1); }
        }

        return cityPopulation;
    }

    public HashMap<String, Integer> numberOfPeopleInTheSamePlace() {
        HashMap<String, Integer> birthPlaces = new HashMap<String, Integer>();
        for (int i = 0; i < dataFrame.getRowCount(); i++)
        {
            String city = dataFrame.getValue("BIRTHPLACE", i);
            if (birthPlaces.containsKey(city))
            {
                birthPlaces.put(city, birthPlaces.get(city) + 1);
            }
            else { birthPlaces.put(city, 1); }
        }

        return birthPlaces;
    }

    public HashMap<String, Integer> findMaritalStatusOfAllPatients() {
        HashMap<String, Integer> maritalStatus = new HashMap<String, Integer>();
        for (int i = 0; i < dataFrame.getRowCount(); i++)
        {
            String marital = setFieldName( dataFrame.getValue("MARITAL", i), dataFrame.getValue("GENDER", i));

            if (maritalStatus.containsKey(marital))
            {
                maritalStatus.put(marital, maritalStatus.get(marital) + 1);
            }
            else { maritalStatus.put(marital, 1); }
        }

        return maritalStatus;
    }

    private String setFieldName(String marital, String gender) {
        if (marital.equals("M") )
        {
            marital = "Married";
            if (gender.equals("M")) { marital = "Male-" + marital;}
            else if (gender.equals("F")) { marital = "Female-" + marital;}
            else { marital = "Unknown-" + marital;}
        }
        else if (marital.equals("S"))
        {
            marital = "Single";
            if (gender.equals("M")) { marital = "Male-" + marital;}
            else if (gender.equals("F")) { marital = "Female-" + marital;}
            else { marital = "Unknown-" + marital;}
        }
        else {
            marital = " Unknown ";
            if (gender.equals("M")) { marital = "Male-" + marital;}
            else if (gender.equals("F")) { marital = "Female-" + marital;}
            else { marital = "Unknown-" + marital;}
        }
        return marital;
    }

    public HashMap[] peopleWhoDiedInTheSameYear() {
        HashMap<String, Integer> yearlyDeaths = new HashMap<>();
        HashMap<String, String> deadPatients = new HashMap<>();
        for (int i = 0; i < dataFrame.getRowCount(); i++)
        {
            String date = dataFrame.getValue("DEATHDATE", i);
            if (date.equals("")) continue;
            int end = date.indexOf("-");
            String year = date.substring(0 , end);
            String name = dataFrame.getValue("FIRST", i) + " " + dataFrame.getValue("LAST", i);

            if (deadPatients.containsKey(year) && yearlyDeaths.containsKey(year))
            {
                yearlyDeaths.put(year, yearlyDeaths.get(year) + 1);
                deadPatients.put(year, deadPatients.get(year) + " , " + name);
            }
            else
                {
                    yearlyDeaths.put(year, 1);
                    deadPatients.put(year, name);
                }
        }
        HashMap[] deathsPerYear = new HashMap[]{yearlyDeaths, deadPatients};
        return deathsPerYear;

    }

    public HashMap[] peopleBornInTheSameYear() {
        HashMap<String, Integer> yearlyBirths = new HashMap<>();
        HashMap<String, String> patients = new HashMap<>();
        for (int i = 0; i < dataFrame.getRowCount(); i++)
        {
            String date = dataFrame.getValue("BIRTHDATE", i);
            int end = date.indexOf("-");
            String year = date.substring(0 , end);
            String name = dataFrame.getValue("FIRST", i) + " " + dataFrame.getValue("LAST", i);

            if (patients.containsKey(year) && yearlyBirths.containsKey(year))
            {
                yearlyBirths.put(year, yearlyBirths.get(year) + 1);
                patients.put(year, patients.get(year) + " , " + name);
            }
            else
            {
                yearlyBirths.put(year, 1);
                patients.put(year, name);
            }
        }
        HashMap[] birthsPerYear = new HashMap[]{yearlyBirths, patients};
        return birthsPerYear;
    }

    public void writeToJson(File fileToSave) {
        JsonManager json = new JsonManager(dataFrame);
        json.writeToJson(fileToSave);
    }


    // TODO

    public DataFrame loadJsonDataFrame(File fileToLoad) {
        dataFrame = loader.loadJsonData(fileToLoad);
        return dataFrame;

    }
}
