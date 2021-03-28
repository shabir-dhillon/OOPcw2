import dataframapackage.DataFrame;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JsonManager {
    DataFrame model;
    FileWriter writer;

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
            writer = new FileWriter(fileToSave);
            writer.write("{\n\"data\": [\n");
            for (int row = 0; row < model.getRowCount(); row++)
            {
                writer.write("{\n");
                addRowData(row);
                if (row == model.getRowCount() - 1)
                {
                    writer.write("}\n]\n}");
                    writer.close();
                }
                else
                {
                    writer.write("},\n");
                }
            }
        } catch (IOException e) {
            JFrame errorFrame = new JFrame();
            JOptionPane.showMessageDialog(errorFrame,"Data could not be saved.");
        }
    }

    private void addRowData(int row) throws IOException {
        ArrayList<String> colNames = model.getColumnNames();
        for (int j = 0; j < model.getColumnCount(); j++)
        {
            String colName = colNames.get(j);
            writer.write("\"" + colName + "\":");
            writer.write("\"" + model.getValue(colName, row) + "\"");
            if (j == model.getColumnCount() -1 ) {
                writer.write("\n");}
            else {
                writer.write(",\n");}
        }
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
}