package dataframapackage;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files

public class DataLoader {
    public DataFrame loadData(File csvFileName) {
        DataFrame fileData = new DataFrame();
        try {
            // TODO CLEAN UP
            Scanner scanner = new Scanner(csvFileName);
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

}