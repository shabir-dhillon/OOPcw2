package dataframapackage;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files


//  ID,BIRTHDATE,DEATHDATE,SSN,DRIVERS,PASSPORT,PREFIX,FIRST,LAST,SUFFIX,MAIDEN,MARITAL,RACE,ETHNICITY,GENDER,BIRTHPLACE,ADDRESS,CITY,STATE,ZIP
public class DataLoader {
    public DataFrame loadData(File csvFileName) {
        DataFrame fileData = new DataFrame();
        try {
//            File inputFile = new File("COMP0004Data-master/COMP0004Data-master/" + csvFileName);
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
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return fileData;
    }
}