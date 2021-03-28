import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

public class View extends JFrame
{
    private JPanel topPanel;
    private JPanel centerPanel;
    private JPanel buttonPanel;
    private JPanel searchPanel;
    private JPanel leftPanel;
    private JPanel mainPanel;
    private JButton loadBtn;
    private JButton filterBtn;
    private JButton searchBtn;
    private JButton saveBtn;
    private JButton graphBtn;
    private JTextField inputField;
    private JComboBox columnSelection;
    private DataManager model;
    private JScrollPane scrollPane;
    private JScrollPane scrollFilter;
    private JFileChooser fileLoader;
    private ArrayList<JCheckBox> filterBoxes;
    private JTable table = new JTable();
    private TableRowSorter<DataManager> sorter;

    public View(DataManager model)
    {
        super("Data Loader");
        setLayout(new BorderLayout());
        setModel(model);
        createGUI();

        pack();
        setSize(800,620);
        setVisible(true);
        ImageIcon image = new ImageIcon("src/logo.png");
        setIconImage(image.getImage());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.NO_OPTION != JOptionPane.showConfirmDialog(JOptionPane.getRootFrame(),
                        "Are you sure you want to exit?", "Confirm Exit",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)) { System.exit(0); }
                else { setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); }
            }
        });
    }

    public void setModel(DataManager model)
    { this.model = model; }

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
        createSearchPanel();
        topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        topPanel.add(buttonPanel, BorderLayout.WEST);
        topPanel.add(searchPanel, BorderLayout.EAST);
    }

    private void createSearchPanel() {
        searchPanel = new JPanel(new FlowLayout());
        createSearchPanelComponents();
        searchPanel.add(columnSelection);
        searchPanel.add(inputField);
        searchPanel.add(searchBtn);
    }

    private void createSearchPanelComponents() {
        searchBtn = new JButton("Advanced Search");
        searchBtn.setMaximumSize(new Dimension(200,200));
        searchBtn.setMinimumSize(new Dimension(100, 100));
        searchBtn.addActionListener((ActionEvent e) -> searchButtonClicked());
        searchBtn.setEnabled(false);
        inputField = new JTextField();
        setInputFieldSettings();
        columnSelection = new JComboBox();
    }

    private void setInputFieldSettings() {
        inputField.setEnabled(false);
        inputField.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        newFilter();
                    }
                    public void insertUpdate(DocumentEvent e) {
                        newFilter();
                    }
                    public void removeUpdate(DocumentEvent e) {
                        newFilter();
                    }
                });
        inputField.setPreferredSize(new Dimension(90, 25));
    }

    /**
     * Oracle.com. (2020). How to Use Tables (The JavaTM Tutorials > Creating a GUI With JFC/Swing > Using Swing Components). [online]
     * Available at: https://docs.oracle.com/javase/tutorial/uiswing/components/table.html#sorting
     * [Accessed 28 Mar. 2021].
     * ‌
     */
    private void newFilter() {
        RowFilter<DataManager, Object> rf = null;
        int colIndex = columnSelection.getSelectedIndex();
        //If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter(inputField.getText(), colIndex);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }

    private void updateComboBox() {
        removeSearchPanelComponents();
        columnSelection = new JComboBox(getColNamesArray());
        searchPanel.add(columnSelection);
        searchPanel.add(inputField);
        searchBtn.setEnabled(true);
        searchPanel.add(searchBtn);
    }

    private void removeSearchPanelComponents() {
        searchPanel.remove(columnSelection);
        searchPanel.remove(inputField);
        searchPanel.remove(searchBtn);
    }

    private String[] getColNamesArray() {
        String[] fields = new String[model.getColumnCount()];
        for (int i = 0; i < fields.length; i++)
        {
            fields[i] = model.getColumnName(i);
        }
        return fields;
    }

    private void searchButtonClicked() {
        SearchWindow searchWindow = new SearchWindow(model);
        newWindowBtnEvent(0);
        searchWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(JOptionPane.getRootFrame(),
                        "Are you sure you want to exit?", "Confirm Exit",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE))
                {
                    newWindowBtnEvent(1);
                    searchWindow.dispose();
                }
                else { searchWindow.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); }
            }
        });
    }

    private void createButtonPanel()
    {
        buttonPanel = new JPanel(new FlowLayout());
        createLoadBtn();
        createFilterBtn();
        createGraphBtn();
        createSaveBtn();
    }

    private void createSaveBtn() {
        saveBtn = new JButton("Save to JSON");
        saveBtn.addActionListener((ActionEvent e) -> saveBtnClicked());
        buttonPanel.add(saveBtn);
    }

    private void createGraphBtn() {
        graphBtn = new JButton("Generate Graph");
        graphBtn.addActionListener((ActionEvent e) -> graphButtonClicked());
        graphBtn.setEnabled(false);
        buttonPanel.add(graphBtn);
    }

    private void createFilterBtn() {
        filterBtn = new JButton("Filter");
        filterBtn.addActionListener((ActionEvent e) -> submitBtnClicked());
        filterBtn.setEnabled(false);
        buttonPanel.add(filterBtn);
    }

    private void createLoadBtn() {
        loadBtn = new JButton("Load");
        loadBtn.addActionListener((ActionEvent e) -> loadButtonClicked());
        buttonPanel.add(loadBtn);
    }

    private void graphButtonClicked() {
        GraphWindow graphWindow = new GraphWindow(model);
        newWindowBtnEvent(0);
        graphWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(JOptionPane.getRootFrame(),
                        "Are you sure you want to exit?", "Confirm Exit",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                    newWindowBtnEvent(1);
                    graphWindow.dispose();
                } else {
                    graphWindow.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }

    private void newWindowBtnEvent(int option)
    {
        if (option == 1)
        {
            loadBtn.setEnabled(true);
            graphBtn.setEnabled(true);
            searchBtn.setEnabled(true);
        }
        else
        {
            loadBtn.setEnabled(false);
            graphBtn.setEnabled(false);
            searchBtn.setEnabled(false);
        }
    }

    private void saveBtnClicked() {
        fileLoader = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".json", "json");
        fileLoader.setFileFilter(filter);
        fileLoader.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int response = fileLoader.showSaveDialog(null);

        if (response == JFileChooser.APPROVE_OPTION)
        {
            try
            {
                File fileToSave = fileLoader.getSelectedFile();
                model.writeToJson(fileToSave);
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(getParent(), e.toString());
            }
        }
        else
            {
                JOptionPane.showMessageDialog(getParent(), "No File Selected.");
            }
    }

    private void loadButtonClicked() {
        File fileToLoad = chooseFileToLoad();
        if (fileToLoad == null)
        {
            return;
        }
        if (loadModel(fileToLoad) == -1)
        {
            return;
        }
        table = new JTable(model);
        table.setDefaultRenderer(Object.class, new TableCell());
        revalidateTable();
        if (leftPanel != null)
        {
            scrollFilter.remove(leftPanel);
            mainPanel.remove(scrollFilter);
        }
        createFilterPanel();
        reEnableBtns();
    }

    private void reEnableBtns() {
        filterBtn.setEnabled(true);
        inputField.setEnabled(true);
        graphBtn.setEnabled(true);
    }

    /**
     *
     * @param fileToLoad
     * @return -1 if File does not exists and 1 if File exists
     */
    private int loadModel(File fileToLoad) {
        if (!fileToLoad.exists())
        {
            JOptionPane.showMessageDialog(getParent(), "File does not exists.");
            return -1;
        }
        this.model = new DataManager();
        if (fileToLoad.getName().endsWith(".csv"))
        {
            model.loadCSVDataFrame(fileToLoad);
        }
        else
        {
            model.loadJsonDataFrame(fileToLoad);
        }
        return 1;
    }

    private void revalidateTable() {
        model.fireTableStructureChanged();
        model.fireTableDataChanged();
        table.revalidate();
        table.repaint();
        setColumnWidth();
        // Get scroll pane to revalidate and repaint the table
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scrollPane.setViewportView(table);
        scrollPane.revalidate();
        scrollPane.repaint();
    }

    private File chooseFileToLoad() {
        fileLoader = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".csv or .json", "csv", "json");
        fileLoader.setFileFilter(filter);
        fileLoader.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int response = fileLoader.showOpenDialog(null);

        if (response == JFileChooser.APPROVE_OPTION)
        {
            try
            {
                File fileToLoad = fileLoader.getSelectedFile();
                return fileToLoad;
            } catch (Exception e) { JOptionPane.showMessageDialog(getParent(), e.toString()); }
        }
        else { JOptionPane.showMessageDialog(getParent(), "No File Selected."); }
        return null;
    }

    private void setColumnWidth() {
        TableColumn column = null;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(300);
        }
    }

    private void createFilterPanel() {
        createLeftPanel();
        getFilterList();
        addBoxesToPanel();
        scrollFilter = new JScrollPane(leftPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        createAutoTableSorter();
        updateComboBox();
        mainPanel.add(scrollFilter, BorderLayout.WEST);
        mainPanel.updateUI();
    }

    private void createAutoTableSorter() {
        table.setAutoCreateRowSorter(true);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
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
    }

    private void getFilterList() {
        ArrayList<String> boxID = model.getColumnNames();
        for (String s : boxID)
        {
            JCheckBox checkBox = new JCheckBox();
            checkBox.setText(s);
            checkBox.setSelected(true);
            filterBoxes.add(checkBox);
        }
    }

    private void submitBtnClicked()
    {
        ArrayList<Boolean>  columnFilter = new ArrayList<>();
        int colCount = model.getColumnCount();
        for (int i = 0; i < colCount; i++) { columnFilter.add(filterBoxes.get(i).isSelected()); }
        hideFilteredColumns(columnFilter);
    }

    private void hideFilteredColumns(ArrayList<Boolean>  columnFilter) {
        for (int i = 0; i < table.getColumnCount(); i++) {
            if (columnFilter.get(i).equals(false))
            {
                adjustColumnWidth(i, 0);
            }
            else
            {
                adjustColumnWidth(i, 300);
            }
        }
    }

    private void adjustColumnWidth(int i, int width) {
        table.getColumnModel().getColumn(i).setMinWidth(width);
        table.getColumnModel().getColumn(i).setMaxWidth(width);
        table.getColumnModel().getColumn(i).setPreferredWidth(width);
    }

    public static void main(final String[] args, DataManager mainModel)
    {
        SwingUtilities.invokeLater(() -> new View(mainModel));
    }
}