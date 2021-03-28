import dataframapackage.DataFrame;

import java.util.ArrayList;

public class CompareBirthdays {
    public static int compareBirthDays(String searchFor, ArrayList<Integer> alivePatients, DataFrame dataFrame) {
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
}
