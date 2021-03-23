import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.TreeMap;

public class GraphWindow extends JFrame {
    private JButton searchBtn;
    private JComboBox searchOptions;
    private JPanel backPanel;
    private JPanel topPanel;
    private JPanel centerPanel;
    private DataManager searchModel;
    private GraphicsPanel graph;

    GraphWindow(DataManager model) {
        super("Advanced Search");
        getModel(model);
        createSearchGUI();

        //-----
        pack();
        setSize(800,600);
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
        String[] options = { "Find the population of each city", "Number of People who died in the same year", "Marital Status of people"};
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
        }
    }

    private void populationOfCities() {
        TreeMap<String, Integer> cityPopulation = searchModel.populationOfCities();

        for (String i : cityPopulation.keySet()) {
            System.out.println(i + " : " + cityPopulation.get(i));
        }
    }


    private void findMaritalStatusOfAllPatients() {
        TreeMap<String, Integer> maritalStatus = searchModel.findMaritalStatusOfAllPatients();

        for (String i : maritalStatus.keySet()) {
            System.out.println(i + " : " + maritalStatus.get(i));
        }
    }

    private void peopleWhoDiedInTheSameYear() {
        graph = new GraphicsPanel(searchModel);
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

    private void createBackPanel() {
        backPanel = new JPanel(new BorderLayout());
        backPanel.add(centerPanel, BorderLayout.CENTER);
        backPanel.add(topPanel, BorderLayout.NORTH);
        backPanel.setBorder(BorderFactory.createEmptyBorder(20,10, 10, 10));
        add(backPanel);
    }
}
