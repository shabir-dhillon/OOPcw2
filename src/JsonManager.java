import dataframapackage.DataFrame;

import javax.swing.*;
import java.io.File;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JsonManager {
    DataFrame model;

    public JsonManager(DataFrame dataModel) {
        this.model = dataModel;
    }

    public void writeToJson(File fileToSave)
    {
        int response = createJsonFile(fileToSave);
        if (response == 1 || response == 0)
        {
            jsonWriter(fileToSave);
        }
        else
        {
            JFrame errorFrame = new JFrame();
            JOptionPane.showMessageDialog(errorFrame,"Data could not be saved.");
        }
    }

    private void jsonWriter(File fileToSave) {
        try {
            FileWriter myWriter = new FileWriter(fileToSave);
            ArrayList<String> colNames = model.getColumnNames();
            myWriter.write("{\n\"patients\": [\n");
            for (int i = 0; i < model.getRowCount(); i++)
            {
                if (i == model.getRowCount() - 1)
                {
                    writeLastRow(myWriter, colNames, i);
                    return;
                }
                myWriter.write("{\n");
                for (int j = 0; j < model.getColumnCount(); j++)
                {
                    String colName = colNames.get(j);
                    myWriter.write("\"" + colName + "\":");
                    myWriter.write("\"" + model.getValue(colName, i) + "\"");
                    if (j == model.getColumnCount() -1 ) {myWriter.write("\n");}
                    else {myWriter.write(",\n");}
                }
                myWriter.write("},\n");
            }
        } catch (IOException e) {
            JFrame errorFrame = new JFrame();
            JOptionPane.showMessageDialog(errorFrame,"Data could not be saved.");
        }
    }

    private void writeLastRow(FileWriter myWriter, ArrayList<String> colNames, int i) throws IOException {
        myWriter.write("{\n");
        for (int j = 0; j < model.getColumnCount(); j++) {
            String colName = colNames.get(j);
            myWriter.write("\"" + colName + "\":");
            myWriter.write("\"" + model.getValue(colName, i) + "\"");
            if (j == model.getColumnCount() - 1) {
                myWriter.write("\n");
            } else {
                myWriter.write(",\n");
            }
        }
        myWriter.write("}\n]\n}");
        myWriter.close();
    }

    private int createJsonFile(File fileToSave) {
        try {
            if (fileToSave.createNewFile()) {
                return 1;
            } else {
                return 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }


    public void readFromJson(File fileToLoad)
    {
    }
}