import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class GraphWindow extends JFrame {
    private JButton searchBtn;
    private JComboBox searchOptions;
    private JPanel backPanel;
    private JPanel topPanel;
    private JPanel centerPanel;
    private DataManager searchModel;
    private DefaultListModel<String> listModel;

    GraphWindow(DataManager model) {
        super("Advanced Search");
        getModel(model);
        createSearchGUI();

        //-----
        pack();
        setSize(800,600);
        setVisible(true);
    }


    private void getModel(DataManager model) {
        searchModel = model;
        System.out.println("Model copy");
    }

    private void createSearchGUI() {
        createTopPanel();
        createCenterPanel();
        createBackPanel();
    }

    private void createCenterPanel() {
        // TODO new to change layout?
        centerPanel = new JPanel(new BorderLayout());

    }

    private void createTopPanel() {
        topPanel = new JPanel(new FlowLayout());
        String[] options = { "Find the population of each city", "Number of People born in the same city", "Number of people born in the same year",
                "Number of People who died in the same year", "Marital Status of people"};
        searchOptions = new JComboBox(options);
        searchBtn = new JButton("Search");
        searchBtn.setSize(new Dimension(200,100));
        searchBtn.addActionListener((ActionEvent e) -> searchBtnClicked());
        topPanel.add(searchOptions);
        topPanel.add(searchBtn);
    }


    private void searchBtnClicked()
    {
        int searchIndex = searchOptions.getSelectedIndex();
        switch (searchIndex) {
            case 0 -> populationOfCities();
            case 1 -> numberOfPeopleInTheSamePlace();
            case 2 -> peopleBornInTheSameYear();
            case 3 -> peopleWhoDiedInTheSameYear();
            case 4 -> findMaritalStatusOfAllPatients();
        }
    }

    private void populationOfCities() {
        HashMap<String, Integer> cityPopulation = searchModel.populationOfCities();

        for (String i : cityPopulation.keySet()) {
            System.out.println(i + " : " + cityPopulation.get(i));
        }

    }

    private void numberOfPeopleInTheSamePlace() {
        HashMap<String, Integer> birthPlaces = searchModel.numberOfPeopleInTheSamePlace();

        for (String i : birthPlaces.keySet()) {
            System.out.println(i + " : " + birthPlaces.get(i));
        }

    }

    private void findMaritalStatusOfAllPatients() {
        HashMap<String, Integer> maritalStatus = searchModel.findMaritalStatusOfAllPatients();

        for (String i : maritalStatus.keySet()) {
            System.out.println(i + " : " + maritalStatus.get(i));
        }
    }

    private void peopleWhoDiedInTheSameYear() {
        HashMap[] deathsPerYear = searchModel.peopleWhoDiedInTheSameYear();
        HashMap<String, Integer> yearlyDeaths = deathsPerYear[0];
        HashMap<String, String> deadPatients =  deathsPerYear[1];

        for (String i : yearlyDeaths.keySet()) {
            System.out.println(i + " : " + yearlyDeaths.get(i));
            addPatientInfoToModel(deadPatients.get(i));
            System.out.println("-------------------------------------------");
        }
    }

    private void peopleBornInTheSameYear() {
        HashMap[] birthsPerYear = searchModel.peopleBornInTheSameYear();
        HashMap<String, Integer> yearlyBirths = birthsPerYear[0];
        HashMap<String, String> patients =  birthsPerYear[1];

        for (String i : yearlyBirths.keySet()) {
            System.out.println(i + " : " + yearlyBirths.get(i));
            addPatientInfoToModel(patients.get(i));
            System.out.println("-------------------------------------------");
        }
    }

    private void addPatientInfoToModel(String patientData)
    {
        String[] data = patientData.split(",", -1);
        for (String d : data)
        {
            System.out.println(d);
        }
    }

    private void createBackPanel() {
        backPanel = new JPanel(new BorderLayout());
        backPanel.add(centerPanel, BorderLayout.CENTER);
        backPanel.add(topPanel, BorderLayout.NORTH);
        add(backPanel);
    }
}
