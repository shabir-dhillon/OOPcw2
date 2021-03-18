import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class SearchWindow extends JFrame {
    JLabel resutlLabel;
    JButton searchBtn;
    JComboBox searchOptions;
    JTextArea searchResults;
    JPanel backPanel;
    JPanel topPanel;
    JPanel centerPanel;
    DataManager searchModel;

    SearchWindow(DataManager model) {
        super("Advanced Search");
        getModel(model);
        createSearchGUI();

        //-----
        pack();
        setSize(420,420);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.NO_OPTION != JOptionPane.showConfirmDialog(JOptionPane.getRootFrame(),
                        "Are you sure you want to exit?", "Confirm Exit",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)) { dispose(); }
                else { setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); }
            }
        });
    }

    private void getModel(DataManager model) {
        // TODO Should I make a copy?
        searchModel = model;
        System.out.println("Model copy");
    }

    private void createSearchGUI() {
        createTopPanel();
        createCenterPanel();
        createBackPanel();
    }

    private void createCenterPanel() {
        centerPanel = new JPanel();
        searchResults = new JTextArea();
        searchResults.setLayout(new FlowLayout());
        searchResults.setMinimumSize(new Dimension(100, 100));
        searchResults.setMaximumSize(new Dimension(800,800));
        searchResults.setPreferredSize(new Dimension(250,400));
        centerPanel.add(searchResults);
    }

    private void createTopPanel() {
        topPanel = new JPanel(new FlowLayout());
        String[] options = { "Oldest Living Person", "Youngest Living Person", "Number of People Living in the same city", "Number of People born in the same city"
                , "People born in the same year", "People who died in the same year", "Marital Status of people"};
        searchOptions = new JComboBox(options);
        searchBtn = new JButton("Search");
        searchBtn.setSize(new Dimension(200,100));
        searchBtn.addActionListener((ActionEvent e) -> runSearchOption());
        topPanel.add(searchOptions);
        topPanel.add(searchBtn);
    }

    private void runSearchOption()
    {
        int searchIndex = searchOptions.getSelectedIndex();
        switch (searchIndex) {
            case 0 -> findOldestPerson();
            case 1 -> findYoungestPerson();
            case 2 -> System.out.println(2);
            case 3 -> System.out.println(3);
            case 4 -> System.out.println(4);
            case 5 -> System.out.println(5);
            case 6 -> System.out.println(6);
            default -> System.out.println(7);
        }
    }

    // TODO WHAT IF MULTIPLE PEOPLE ARE YOUNGEST?
    private void findYoungestPerson() {
        int result = searchModel.findYoungestPerson();
        if (result == -1)
        {
            JOptionPane.showMessageDialog(getParent(), "No matches found");
            return ;
        }
        String data = searchModel.getColumnValueAt("FIRST", result);
        //TODO Better display
        searchResults.append(data);
    }

    private void findOldestPerson() {
        int result = searchModel.findOldestPerson();
        if (result == -1)
        {
            JOptionPane.showMessageDialog(getParent(), "No matches found");
            return ;
        }
        String data = searchModel.getColumnValueAt("FIRST", result);
        //TODO Better display
        searchResults.append(data);
    }

    private void createBackPanel() {
        backPanel = new JPanel(new BorderLayout());
        backPanel.add(centerPanel, BorderLayout.CENTER);
        backPanel.add(topPanel, BorderLayout.NORTH);
        add(backPanel);
    }
}
