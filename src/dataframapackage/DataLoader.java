package dataframapackage;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files

public class DataLoader {
    DataFrame fileData = new DataFrame();
    Scanner scanner;
    String[] columnNames = { "ID", "BIRTHDATE", "DEATHDATE", "SSN", "DRIVERS","PASSPORT" ,"PREFIX" , "FIRST", "LAST", "SUFFIX","MAIDEN" ,"MARITAL" , "RACE" , "ETHNICITY", "GENDER", "BIRTHPLACE", "ADDRESS", "CITY" ,"STATE", "ZIP"};
    public DataFrame loadData(File csvFileName) {
        try {
            // TODO CLEAN UP
            scanner = new Scanner(csvFileName);
            int i = 0;
            ArrayList<String> colNames = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] fieldData = data.split(",", -1);
                if (i == 0) {
                    for (String fieldName : fieldData) {
                        colNames.add(fieldName);
                        fileData.addColumn(fieldName);
                    }
                    i += 1;
                    continue;
                }
                int j = 0;
                for (String value : fieldData) {
                    fileData.addValue(colNames.get(j), value);
                    j += 1;
                }
            }
            scanner.close();
        } catch (Exception e) {
            // TODO
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return fileData;
    }


    // TODO BUG all chracters return one less char
    public DataFrame loadJsonData(File fileToLoad) {
        fileData = intialiseDataFrame();
        try {
            // TODO CLEAN UP
            scanner = new Scanner(fileToLoad);
            // Structure of every JSON File is the same, first two lines can be ignored
            scanner.next();
            scanner.next();
            while (scanner.hasNextLine()) {
                if (scanner.nextLine().contains("{"))
                {
                    for (int j = 0; j < columnNames.length; j++)
                    {
                        String data = scanner.nextLine();
                        addToDataFrame(data, j);
                    }
                }
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return fileData;
    }

    // TODO CHECK
    private void addToDataFrame(String data, int j) {
        String rowValue;
        int start = data.indexOf(":") + 1;
        int end = data.length() - 2;
        if (data.indexOf(",") == data.length() - 1)
        {
            rowValue = data.substring(start, end).replace("\"", "");
            fileData.addValue(columnNames[j],rowValue);
        }
        else
        {
            rowValue = data.substring(start, end + 1).replace("\"", "");
            fileData.addValue(columnNames[j],rowValue);
        }
    }

    private DataFrame intialiseDataFrame() {
        for (String fieldName : columnNames) {
            fileData.addColumn(fieldName);
        }
        return fileData;
    }

}

/*
                String data = scanner.nextLine();
                String rowValue;
                if (i == fileData.getColumnCount() - 1) { i = 0; }
                int start = data.indexOf(": ") + 1;
                int end = data.length() - 3;
                if (data.indexOf(",") == data.length() - 1)
                {
                    rowValue = data.substring(start, end);
                    fileData.addValue(columnNames[i],rowValue);
                    i++;
                }
                else
                {
                    rowValue = data.substring(start, end + 1);
                    fileData.addValue(columnNames[i],rowValue);
                    i++;
                }
* */