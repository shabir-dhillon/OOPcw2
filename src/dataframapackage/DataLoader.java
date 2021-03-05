package dataframapackage;

import dataframapackage.DataFrame;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files

public class DataLoader {
    public static void main(String[] args) {
        DataFrame fileData = new DataFrame();
        try {
            File inputFile = new File("COMP0004Data-master/COMP0004Data-master/patients100.csv");
            Scanner scanner = new Scanner(inputFile);
            int i = 0;
            ArrayList<String> colNames = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] fieldData = data.split(",");
                if ( i == 0) {
                    for (String fieldName : fieldData){
                        colNames.add(fieldName);
                        fileData.addColumn(fieldName);
                    }
                    i += 1;
                    continue;
                }
                int j = 0;
                for (String value : fieldData){
                    fileData.addValue(colNames.get(j), value);
                    j += 1;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        ArrayList<String> columnNames = fileData.getColumnNames();

        for (String name: columnNames)
        {
            System.out.print(name + ", ");
        }
        System.out.println();

        // S99995087
        System.out.println(fileData.getValue("DRIVERS", 4));

        System.out.println(fileData.getRowCount());
    }
}
