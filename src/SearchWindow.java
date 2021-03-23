import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.TreeMap;

public class SearchWindow extends JFrame {
    private JButton searchBtn;
    private JComboBox searchOptions;
    private JTextArea searchResults;
    private JPanel backPanel;
    private JPanel topPanel;
    private JPanel centerPanel;
    private DataManager searchModel;
    private JScrollPane scroller;
    private JList<String> list;
    private DefaultListModel<String> listModel;

    SearchWindow(DataManager model) {
        super("Advanced Search");
        getModel(model);
        createSearchGUI();
        setTextAreaModel();

        //-----
        pack();
        setSize(420,420);
        setVisible(true);
    }

    private void setTextAreaModel() {
        listModel = new DefaultListModel<String>();
        list.setModel(listModel);
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
        searchResults = new JTextArea();
        searchResults.setLayout(new FlowLayout());
        searchResults.setMinimumSize(new Dimension(100, 100));
        searchResults.setMaximumSize(new Dimension(800,800));
        searchResults.setPreferredSize(new Dimension(250,400));
        createScrollerPanel();
    }

    private void createTopPanel() {
        topPanel = new JPanel(new FlowLayout());
        String[] options = { "Oldest Living Person", "Youngest Living Person", "Find the population of each city", "Number of People born in the same city"
                , "Number of people born in the same year", "Number of People who died in the same year", "Marital Status of people"};
        searchOptions = new JComboBox(options);
        searchBtn = new JButton("Search");
        searchBtn.setSize(new Dimension(200,100));
        searchBtn.addActionListener((ActionEvent e) -> runSearchOption());
        topPanel.add(searchOptions);
        topPanel.add(searchBtn);
    }

    private void createScrollerPanel()
    {
        centerPanel = new JPanel();
        list = new JList<String>();
        scroller = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller.setPreferredSize(new Dimension(300,200));
        centerPanel.add(scroller, BorderLayout.CENTER);
    }


    private void runSearchOption()
    {
        int searchIndex = searchOptions.getSelectedIndex();
        switch (searchIndex) {
            case 0 -> findOldestPerson();
            case 1 -> findYoungestPerson();
            case 2 -> populationOfCities();
            case 3 -> numberOfPeopleInTheSamePlace();
            case 4 -> peopleBornInTheSameYear();
            case 5 -> peopleWhoDiedInTheSameYear();
            case 6 -> findMaritalStatusOfAllPatients();
        }
    }

    private void clearTextArea()
    {
        listModel.clear();
    }

    // TODO WHAT IF MULTIPLE PEOPLE ARE YOUNGEST?
    private void findYoungestPerson() {
        clearTextArea();
        int result = searchModel.findYoungestPerson();
        if (result == -1)
        {
            JOptionPane.showMessageDialog(getParent(), "No matches found");
            return ;
        }
        // TODO SHOW MORE DATA
        String data = searchModel.getColumnValueAt("FIRST", result);
        listModel.addElement(data);
    }

    private void findOldestPerson() {
        clearTextArea();
        int result = searchModel.findOldestPerson();
        if (result == -1)
        {
            JOptionPane.showMessageDialog(getParent(), "No matches found");
            return ;
        }
        String data = searchModel.getColumnValueAt("FIRST", result);
        // TODO SHOW MORE DATA
        listModel.addElement(data);
    }

    // TODO TREEMAP
    private void populationOfCities() {
        clearTextArea();
        TreeMap<String, Integer> cityPopulation = searchModel.populationOfCities();

        for (String i : cityPopulation.keySet()) {
            listModel.addElement(i + " : " + cityPopulation.get(i));
        }

    }

    private void numberOfPeopleInTheSamePlace() {
        clearTextArea();
        TreeMap<String, Integer> birthPlaces = searchModel.numberOfPeopleInTheSamePlace();

        for (String i : birthPlaces.keySet()) {
            listModel.addElement(i + " : " + birthPlaces.get(i));
        }

    }

    private void findMaritalStatusOfAllPatients() {
        clearTextArea();
        TreeMap<String, Integer> maritalStatus = searchModel.findMaritalStatusOfAllPatients();

        for (String i : maritalStatus.keySet()) {
            listModel.addElement(i + " : " + maritalStatus.get(i));
        }
    }

    private void peopleWhoDiedInTheSameYear() {
        clearTextArea();
        TreeMap[] deathsPerYear = searchModel.peopleWhoDiedInTheSameYear();
        TreeMap<Integer, Integer> yearlyDeaths = deathsPerYear[0];
        TreeMap<Integer, String> deadPatients =  deathsPerYear[1];

        for (Integer i : yearlyDeaths.keySet()) {
            listModel.addElement(i + " : " + yearlyDeaths.get(i));
            addPatientInfoToModel(deadPatients.get(i));
            listModel.addElement("-------------------------------------------");
        }
    }

    private void peopleBornInTheSameYear() {
        clearTextArea();
        TreeMap[] birthsPerYear = searchModel.peopleBornInTheSameYear();
        TreeMap<String, Integer> yearlyBirths = birthsPerYear[0];
        TreeMap<String, String> patients =  birthsPerYear[1];

        for (String i : yearlyBirths.keySet()) {
            listModel.addElement(i + " : " + yearlyBirths.get(i));
            addPatientInfoToModel(patients.get(i));
            listModel.addElement("-------------------------------------------");
        }
    }

    private void addPatientInfoToModel(String patientData)
    {
        String[] data = patientData.split(",", -1);
        for (String d : data)
        {
            listModel.addElement(d);
        }
    }

    private void createBackPanel() {
        backPanel = new JPanel(new BorderLayout());
        backPanel.add(centerPanel, BorderLayout.CENTER);
        backPanel.add(topPanel, BorderLayout.NORTH);
        add(backPanel);
    }
}
