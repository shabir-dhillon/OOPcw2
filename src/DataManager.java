import dataframapackage.DataFrame;
import dataframapackage.DataLoader;

import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.io.File;
import java.util.*;

public class DataManager extends AbstractTableModel {
    private DataFrame dataFrame;
    private DataLoader loader;

    public DataManager()
    {
        dataFrame = new DataFrame();
        loader = new DataLoader();
    }

    public void loadCSVDataFrame(File fileName)
    {
        dataFrame = loader.loadCSVData(fileName);
    }

    public void loadJsonDataFrame(File fileToLoad)
    {
        dataFrame = loader.loadJsonData(fileToLoad);
    }

    public ArrayList<String> getColumnNames()
    {
        return dataFrame.getColumnNames();
    }

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

    public Color getRowColour(int row) {
        if (row % 2 == 0)
        {
            return new Color(0xC3D8E5);
        }
        return new Color(0xE5D0C3);
    }

    public int findOldestPerson() {
        ArrayList<Integer> alivePatients = getIndexOfPatientsAlive();

        return CompareBirthdays.compareBirthDays("old", alivePatients, dataFrame);
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

        return CompareBirthdays.compareBirthDays("young", alivePatients, dataFrame);
    }

    public TreeMap<String, Integer> populationOfCities() {
        TreeMap<String, Integer> cityPopulation = new TreeMap<String, Integer>();
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

    public TreeMap<String, Integer> numberOfPeopleInTheSamePlace() {
        TreeMap<String, Integer> birthPlaces = new TreeMap<String, Integer>();
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

    public TreeMap<String, Integer> findMaritalStatusOfAllPatients() {
        TreeMap<String, Integer> maritalStatus = new TreeMap<>();
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
            marital = "Unknown";
            if (gender.equals("M")) { marital = "Male-" + marital;}
            else if (gender.equals("F")) { marital = "Female-" + marital;}
            else { marital = "Unknown-" + marital;}
        }
        return marital;
    }

    public TreeMap[] peopleWhoDiedInTheSameYear() {
        TreeMap<Integer, Integer> yearlyDeaths = new TreeMap<>();
        TreeMap<Integer, String> deadPatients = new TreeMap<>();

        for (int i = 0; i < dataFrame.getRowCount(); i++)
        {
            String date = dataFrame.getValue("DEATHDATE", i);
            if (date.equals("")) continue;
            int end = date.indexOf("-");
            int year = Integer.parseInt(date.substring(0 , end));
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

        TreeMap[] deathsPerYear = new TreeMap[]{yearlyDeaths, deadPatients};
        return deathsPerYear;
    }

    public TreeMap[] peopleBornInTheSameYear() {
        TreeMap<String, Integer> yearlyBirths = new TreeMap<>();
        TreeMap<String, String> patients = new TreeMap<>();
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
        TreeMap[] birthsPerYear = new TreeMap[]{yearlyBirths, patients};
        return birthsPerYear;
    }

    public void writeToJson(File fileToSave) {
        JsonManager json = new JsonManager(dataFrame);
        json.writeToJson(fileToSave);
    }


    public TreeMap<String, Integer> findAllRaces() {
        TreeMap<String, Integer> patientRaces = new TreeMap<>();
        for (int i = 0; i < dataFrame.getRowCount(); i++)
        {
            String race = dataFrame.getValue("RACE", i);

            if (patientRaces.containsKey(race))
            {
                patientRaces.put(race, patientRaces.get(race) + 1);
            }
            else { patientRaces.put(race, 1); }
        }

        return patientRaces;
    }

    public TreeMap<String, Integer> findAgeDistribution()
    {
        AgeDistributionFinder ages = new AgeDistributionFinder(dataFrame);
        return ages.findAgeDistribution();
    }

    public TreeMap<String, Integer> findGenderData() {
        TreeMap<String, Integer> gendersData = new TreeMap<>();
        for (int i = 0; i < dataFrame.getRowCount(); i++)
        {
            String gender = dataFrame.getValue("GENDER", i);

            if (gendersData.containsKey(gender))
            {
                gendersData.put(gender, gendersData.get(gender) + 1);
            }
            else { gendersData.put(gender, 1); }
        }

        return gendersData;
    }
}
