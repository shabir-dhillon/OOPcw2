package dataframapackage;
import java.io.File;  // Import the File class
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files

public class DataLoader {
    DataFrame fileData = new DataFrame();
    Scanner scanner;
    List<String> columnNames = new ArrayList<>();
    public DataFrame loadCSVData(File csvFileName) {
        try {
            scanner = new Scanner(csvFileName);
            writeDataFromCSV();
            scanner.close();
        } catch (Exception e) {
            System.out.println("An error occurred, File could not be opened.");
            e.printStackTrace();
        }
        return fileData;
    }

    private void writeDataFromCSV() {
        int i = 0;
        List<String> colNames = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            String[] fieldData = data.split(",", -1);
            if (i == 0) {
                createColumn(fieldData, colNames);
                i += 1;
                continue;
            }
            addRowData(fieldData, colNames);
        }
    }

    private void addRowData(String[] fieldData,  List<String> colNames) {
        int j = 0;
        for (String value : fieldData) {
            fileData.addValue(colNames.get(j), value);
            j += 1;
        }
    }

    private void createColumn(String[] fieldData, List<String> colNames) {
        for (String fieldName : fieldData) {
            colNames.add(fieldName);
            fileData.addColumn(fieldName);
        }
    }


    public DataFrame loadJsonData(File fileToLoad) {
        try {
            // TODO CLEAN UP
            scanner = new Scanner(fileToLoad);
            // Structure of every JSON File is the same, first two lines can be ignored
            scanner.next();
            scanner.next();
            writeDataFromJSON();
            scanner.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return fileData;
    }

    private void writeDataFromJSON() {
        while (scanner.hasNextLine()) {
            if (scanner.nextLine().contains("{"))
            {
                String data = scanner.nextLine();
                while (!data.contains("}"))
                {
                    addToDataFrame(data);
                    data = scanner.nextLine();
                }
            }
        }
    }

    private void addToDataFrame(String data) {
        String rowValue;
        String colName;
        int indexOfFirstApostrophe = data.indexOf("\"");
        int start = data.indexOf(":") + 1;
        int end = data.length() - 2;
        colName = (data.substring(indexOfFirstApostrophe, start - 1)).replace("\"", "");
        checkColumnExists(colName);
        if (data.indexOf(",") == data.length() - 1)
        {
            rowValue = data.substring(start, end).replace("\"", "");
        }
        else
        {
            rowValue = data.substring(start, end + 1).replace("\"", "");
        }
        fileData.addValue(colName,rowValue);
    }

    private void checkColumnExists(String colName) {
        if (!columnNames.contains(colName))
        {
            columnNames.add(colName);
            fileData.addColumn(colName);
        }
    }

}