import dataframapackage.DataFrame;

import java.time.LocalDate;
import java.time.*;
import java.util.HashMap;
import java.util.TreeMap;

public class AgeDistributionFinder {
    DataFrame model;
    final String[] ageLimits = {"0-10", "11-20", "21-30", "31-40", "41-50", "51-60", "61-70", "71-80", "81-90", "91+"};
    final int currentYear = 2021;
    final int currentMonth = 3;
    final int currentDay = 29;
    private LocalDate endDate;

    public AgeDistributionFinder(DataFrame model)
    {
        this.model = model;
    }

    public TreeMap<String, Integer> findAgeDistribution()
    {
        TreeMap<String, Integer> ages = new TreeMap<>();

        intialiseAgesData(ages);
        // Key is Birthdate and Value is DeathDate
        HashMap<String, String> patientBirthdates = new HashMap<>();
        getPatientBirthdates(patientBirthdates);
        int[] patientAges = calculateAges(patientBirthdates);

        addAgesToTreeMap(ages, patientAges);

        return ages;
    }

    private void addAgesToTreeMap(TreeMap<String, Integer> ages, int[] patientAges) {
        String key;
        for (int age : patientAges)
        {
            if (age < 11)
            {
                key = ageLimits[0];
                ages.put(key, ages.get(key) + 1);
            }
            else if (age < 21)
            {
                key = ageLimits[1];
                ages.put(key, ages.get(key) + 1);
            }
            else if (age < 31)
            {
                key = ageLimits[2];
                ages.put(key, ages.get(key) + 1);
            }
            else if (age < 41)
            {
                key = ageLimits[3];
                ages.put(key, ages.get(key) + 1);
            }
            else if (age < 51)
            {
                key = ageLimits[4];
                ages.put(key, ages.get(key) + 1);
            }
            else if (age < 61)
            {
                key = ageLimits[5];
                ages.put(key, ages.get(key) + 1);
            }
            else if (age < 71)
            {
                key = ageLimits[6];
                ages.put(key, ages.get(key) + 1);
            }
            else if (age < 81)
            {
                key = ageLimits[7];
                ages.put(key, ages.get(key) + 1);
            }
            else if (age < 91)
            {
                key = ageLimits[8];
                ages.put(key, ages.get(key) + 1);
            }
            else
            {
                key = ageLimits[9];
                ages.put(key, ages.get(key) + 1);
            }
        }
    }

    private int[] calculateAges(HashMap<String, String> patientBirthdates) {
        int[] patientAges = new int[patientBirthdates.size()];
        int i = 0;
        for (String currentDate : patientBirthdates.keySet())
        {
            String[] date = currentDate.split("-");
            int birthYear = Integer.parseInt(date[0]);
            int birthMonth = Integer.parseInt(date[1]);
            int birthDay = Integer.parseInt(date[2]);

            LocalDate startDate = LocalDate.of(birthYear, birthMonth, birthDay);
            String deathDate = patientBirthdates.get(currentDate);
            if (deathDate.equals(""))
            {
                endDate = LocalDate.of(currentYear, currentMonth, currentDay);
            }
            else
            {
                String[] deathDateVariables = deathDate.split("-");
                int deathYear = Integer.parseInt(deathDateVariables[0]);
                int deathMonth = Integer.parseInt(deathDateVariables[1]);
                int deathDay = Integer.parseInt(deathDateVariables[2]);
                endDate = LocalDate.of(deathYear, deathMonth, deathDay);
            }

            int patientAge = Period.between(startDate, endDate).getYears();
            patientAges[i] = patientAge;
            i++;
        }
        return patientAges;
    }

    private void getPatientBirthdates(HashMap<String, String> patientBirthdates) {
        String keyColumnName = "BIRTHDATE";
        String valueColumnName = "DEATHDATE";
        for (int i = 0; i < model.getRowCount(); i++)
        {
            patientBirthdates.put(model.getValue(keyColumnName, i), model.getValue(valueColumnName, i));
        }
    }

    private void intialiseAgesData(TreeMap<String, Integer> ages) {
        for (String ageLimit : ageLimits) {
            ages.put(ageLimit, 0);
        }
    }
}

