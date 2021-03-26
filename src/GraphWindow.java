import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.TreeMap;

public class GraphWindow extends JFrame {
    private JButton searchBtn;
    private JComboBox searchOptions;
    private JPanel backPanel;
    private JPanel topPanel;
    private JPanel centerPanel;
    private DataManager searchModel;
    private JPanel graph;

    GraphWindow(DataManager model) {
        super("Advanced Search");
        getModel(model);
        createSearchGUI();

        //-----
        pack();
        setSize(750,700);
        setLocationRelativeTo(null);
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
        String[] options = { "Population of each city", "Line Graph of Yearly Deaths", "Pie Chart of Patient Marital Status",
                            "Pie Chart of All Races", "Pie Chart of Gender Distribution"};
        searchOptions = new JComboBox(options);
        searchBtn = new JButton("Search");
        searchBtn.addActionListener((ActionEvent e) -> searchBtnClicked());
        topPanel.add(searchOptions);
        topPanel.add(searchBtn);
    }


    private void searchBtnClicked()
    {
        int searchIndex = searchOptions.getSelectedIndex();
        switch (searchIndex) {
            case 0 -> populationOfCities();
            case 1 -> peopleWhoDiedInTheSameYear();
            case 2 -> findMaritalStatusOfAllPatients();
            case 3 -> findAllRaces();
            case 4 -> findGenderDistribution();
        }
    }

    private void populationOfCities() {
        TreeMap<String, Integer> cityPopulation = searchModel.populationOfCities();

        for (String i : cityPopulation.keySet()) {
            System.out.println(i + " : " + cityPopulation.get(i));
        }
        System.out.println(cityPopulation.size());
    }


    private void findMaritalStatusOfAllPatients() {
        TreeMap<String, Integer> maritalStatus = searchModel.findMaritalStatusOfAllPatients();
        graph = new PieChart(maritalStatus);
        centerPanel.add(graph, BorderLayout.CENTER);
        centerPanel.revalidate();
        centerPanel.repaint();
        backPanel.updateUI();
    }

    private void peopleWhoDiedInTheSameYear() {
        graph = new BarChart(searchModel);
        centerPanel.add(graph, BorderLayout.CENTER);
        centerPanel.revalidate();
        centerPanel.repaint();
        backPanel.updateUI();
    }

    private void addPatientInfoToModel(String patientData)
    {
        String[] data = patientData.split(",", -1);
        for (String d : data)
        {
            System.out.println(d);
        }
    }

    private void findAllRaces() {
        TreeMap<String, Integer> patientRaces = searchModel.findAllRaces();
        graph = new PieChart(patientRaces);
        centerPanel.add(graph, BorderLayout.CENTER);
        centerPanel.revalidate();
        centerPanel.repaint();
        backPanel.updateUI();
    }

    private void findGenderDistribution() {
        TreeMap<String, Integer> genderData = searchModel.findGenderData();
        graph = new PieChart(genderData);
        centerPanel.add(graph, BorderLayout.CENTER);
        centerPanel.revalidate();
        centerPanel.repaint();
        backPanel.updateUI();
    }

    private void createBackPanel() {
        backPanel = new JPanel(new BorderLayout());
        backPanel.add(centerPanel, BorderLayout.CENTER);
        backPanel.add(topPanel, BorderLayout.NORTH);
        backPanel.setBorder(BorderFactory.createEmptyBorder(20,10, 10, 10));
        add(backPanel);
    }
}
