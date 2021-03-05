import dataframapackage.DataFrame;

import java.util.ArrayList;

public class testDF {

    public static void main(String[] args) {
        String[] fieldNames = {"ID","BIRTHDATE","DEATHDATE","SSN","DRIVERS","PASSPORT","PREFIX","FIRST","LAST","SUFFIX","MAIDEN","MARITAL","RACE","ETHNICITY","GENDER","BIRTHPLACE","ADDRESS","CITY","STATE","ZIP"};
        DataFrame fileData = new DataFrame();
        for (int i = 0; i < fieldNames.length;i++)
        {
            fileData.addColumn(fieldNames[i]);
        }

        for (int i = 0; i < fieldNames.length; i++)
        {
            fileData.addValue(fieldNames[i], fieldNames[i]);
        }

        ArrayList<String> columnNames = fileData.getColumnNames();

        for (String name: columnNames)
        {
            System.out.print(name + ", ");
        }
        System.out.println();

        System.out.println(fileData.getValue("SSN", 0));
    }

}
