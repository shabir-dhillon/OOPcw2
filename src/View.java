import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

public class View extends JFrame
{
    private JPanel topPanel;
    private JPanel centerPanel;
    private JPanel buttonPanel;
    private JPanel leftPanel;
    private JPanel mainPanel;
    private JButton loadBtn;
    private JButton submitBtn;
    private DataManager model;
    private  JScrollPane scrollPane;
    private JScrollPane scrollFilter;
    private JFileChooser fileLoader;
    private ArrayList<JCheckBox> filterBoxes;
    private JTable table = new JTable();

    public View()
    {
        super("Data Loader");
        setLayout(new BorderLayout());
        setModel();
        createGUI();

        //---------------------
        pack();
        setSize(800,620);
        setVisible(true);

        ImageIcon image = new ImageIcon("src/logo.png");
        setIconImage(image.getImage());
        // Teal
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(JOptionPane.getRootFrame(),
                        "Are you sure you want to exit?", "Confirm Exit",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.NO_OPTION)
                {
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                }
                else { System.exit(0); }
            }
        });
    }

    public void setModel() { model = new DataManager(); }

    public void createGUI() {
        createTopPanel();
        createCenterPanel();
        createMainPanel();
        add(mainPanel, BorderLayout.CENTER);
    }

    private void createMainPanel() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(topPanel, BorderLayout.NORTH);
    }

    private void createCenterPanel() {
        scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(scrollPane, BorderLayout.CENTER );
    }

    private void createTopPanel() {
        createButtonPanel();
        topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        topPanel.add(buttonPanel, BorderLayout.WEST);
    }

    private void createButtonPanel()
    {
        loadBtn = new JButton("Load");
        loadBtn.addActionListener((ActionEvent e) -> loadButtonClicked());
        buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(loadBtn, BorderLayout.CENTER);
    }

    // Hard Code JTable below
//    private void loadButtonClicked() {
//        String s = inputField.getText();
//        if (s.length() > 0 && s.endsWith("00.csv")) {
//            model.loadDataFrame(s);
//            DefaultTableModel tableModel = new DefaultTableModel();
//            table = new JTable(tableModel);
//            tableModel.setColumnIdentifiers(model.getFieldNames().toArray());
//            setColumnWidth(table);
//            int colSize = model.getFieldNames().size();
//            for (int i = 0; i < model.getNumberOfRows(); i++) {
//                String[] rowData = new String[colSize] ;
//                for (int k = 0; k < colSize; k++) {
//                    String data = model.getColumnValueAt((model.getFieldNames().get(k)), i);
//                    rowData[k] = data;
//                }
//                tableModel.addRow(rowData);
//            }
//            // TODO check which works
//            tableModel.fireTableDataChanged();
//            table.revalidate();
//            table.repaint();
//            // Get scroll pane to revalidate and repaint the table
//            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//            scrollPane.setViewportView(table);
//            scrollPane.revalidate();
//            scrollPane.repaint();
//        }
//        inputField.setText("");
//    }

    private void loadButtonClicked() {
        File fileToLoad = chooseFileToLoad();
        model.loadDataFrame(fileToLoad);
        if (fileToLoad != null) {
            table = new JTable(model);
            table.setDefaultRenderer(Object.class, new TableCell());
            model.fireTableStructureChanged();
            model.fireTableDataChanged();
            table.revalidate();
            table.repaint();
            setColumnWidth(table);
            // Get scroll pane to revalidate and repaint the table
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            scrollPane.setViewportView(table);
            scrollPane.revalidate();
            scrollPane.repaint();
        }
        if (leftPanel != null)
        {
            scrollFilter.remove(leftPanel);
            mainPanel.remove(scrollFilter);
        }
        createFilterPanel();
    }

    private void createFilterPanel() {
        createLeftPanel();
        getFilterList();
        addBoxesToPanel();
        scrollFilter = new JScrollPane(leftPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(scrollFilter, BorderLayout.WEST);
        mainPanel.updateUI();
    }

    private File chooseFileToLoad() {
        File fileToLoad;
        fileLoader = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".csv", "csv");
        fileLoader.setFileFilter(filter);
        fileLoader.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int response = fileLoader.showOpenDialog(null);

        if (response == JFileChooser.APPROVE_OPTION)
        {
            fileToLoad = fileLoader.getSelectedFile();
            return fileToLoad;
        }
        return null;
    }

    private void setColumnWidth(JTable table) {
        TableColumn column = null;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = this.table.getColumnModel().getColumn(i);
            column.setPreferredWidth(200);
        }
    }

    private void createLeftPanel()
    {
        leftPanel = new JPanel(new GridLayout(model.getColumnCount() + 1, 1, 5, 5));
        filterBoxes = new ArrayList<>();
    }

    private void addBoxesToPanel() {
        for (JCheckBox filterBox : this.filterBoxes) {
            this.leftPanel.add(filterBox);
        }
        submitBtn = new JButton("Submit");
        submitBtn.addActionListener((ActionEvent e) -> submitBtnClicked());
        leftPanel.add(submitBtn);
    }

    private void getFilterList() {
        ArrayList<String> boxID = model.getFieldNames();
        for (String s : boxID)
        {
            JCheckBox checkBox = new JCheckBox();
            checkBox.setText(s);
            filterBoxes.add(checkBox);
        }
    }

    private void submitBtnClicked() {
        System.out.println("CLICK");
    }

    public static void main(final String[] args) { SwingUtilities.invokeLater(View::new); }
}