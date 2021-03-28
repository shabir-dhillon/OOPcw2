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
        setSize(1000,750);
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
        centerPanel = new JPanel(new BorderLayout());
    }

    private void createTopPanel() {
        topPanel = new JPanel(new FlowLayout());
        String[] options = { "Bar Chart Age Distribution", "Pie Chart of Patient Marital Status",
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
            case 0 -> displayAgeDistribution();
            case 1 -> findMaritalStatusOfAllPatients();
            case 2 -> findAllRaces();
            case 3 -> findGenderDistribution();
        }
    }

    private void displayAgeDistribution() {
        checkCenterPanel();
        TreeMap<String, Integer> ages = searchModel.findAgeDistribution();
        graph = new BarChart(ages);
        centerPanel.add(graph, BorderLayout.CENTER);
        updateGraphWindow();
    }

    private void updateGraphWindow() {
        centerPanel.revalidate();
        centerPanel.repaint();
        backPanel.updateUI();
    }

    private void checkCenterPanel() {
        if (centerPanel != null)
        {
            centerPanel.removeAll();
        }
    }

    private void findMaritalStatusOfAllPatients() {
        checkCenterPanel();
        TreeMap<String, Integer> maritalStatus = searchModel.findMaritalStatusOfAllPatients();
        graph = new PieChart(maritalStatus);
        centerPanel.add(graph, BorderLayout.CENTER);
        updateGraphWindow();
    }

    private void findAllRaces() {
        checkCenterPanel();
        TreeMap<String, Integer> patientRaces = searchModel.findAllRaces();
        graph = new PieChart(patientRaces);
        centerPanel.add(graph, BorderLayout.CENTER);
        updateGraphWindow();
    }

    private void findGenderDistribution() {
        checkCenterPanel();
        TreeMap<String, Integer> genderData = searchModel.findGenderData();
        graph = new PieChart(genderData);
        centerPanel.add(graph, BorderLayout.CENTER);
        updateGraphWindow();
    }

    private void createBackPanel() {
        backPanel = new JPanel(new BorderLayout());
        backPanel.add(centerPanel, BorderLayout.CENTER);
        backPanel.add(topPanel, BorderLayout.NORTH);
        backPanel.setBorder(BorderFactory.createEmptyBorder(20,10, 10, 10));
        add(backPanel);
    }
}
