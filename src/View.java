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
    private JPanel searchPanel;
    private JPanel leftPanel;
    private JPanel mainPanel;
    private JButton loadBtn;
    private JButton submitBtn;
    private JButton searchBtn;
    private JTextField inputField;
    private JComboBox columnSelection;
    private DataManager model;
    private JScrollPane scrollPane;
    private JScrollPane scrollFilter;
    private JFileChooser fileLoader;
    private ArrayList<JCheckBox> filterBoxes;
    private JTable table = new JTable();
    private ArrayList<Boolean>  columnFilter;

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
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.NO_OPTION != JOptionPane.showConfirmDialog(JOptionPane.getRootFrame(),
                        "Are you sure you want to exit?", "Confirm Exit",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)) {
                    System.exit(0);
                } else {
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                }
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
        createSearchPanel();
        topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        topPanel.add(buttonPanel, BorderLayout.WEST);
        topPanel.add(searchPanel, BorderLayout.EAST);
    }

    private void createSearchPanel() {
        searchPanel = new JPanel(new FlowLayout());
        searchBtn = new JButton("Search");
        searchBtn.addActionListener((ActionEvent e) -> searchButtonClicked());
        searchBtn.setEnabled(false);
        inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(90, 25));
        columnSelection = new JComboBox();
        searchPanel.add(columnSelection);
        searchPanel.add(inputField);
        searchPanel.add(searchBtn);
    }

    private void updateComboBox() {
        searchPanel.remove(columnSelection);
        searchPanel.remove(inputField);
        searchPanel.remove(searchBtn);
        columnSelection = new JComboBox(getStringColArray());
        searchPanel.add(columnSelection);
        searchPanel.add(inputField);
        searchBtn.setEnabled(true);
        searchPanel.add(searchBtn);
    }

    private String[] getStringColArray() {
        String[] fields = new String[model.getColumnCount()];
        for (int i = 0; i < fields.length; i++)
        {
            fields[i] = model.getColumnName(i);
        }
        return fields;
    }

    private void searchButtonClicked() {
        String selectedCol = (String) columnSelection.getSelectedItem();
        int colIndex = columnSelection.getSelectedIndex();
//        System.out.println(selectedCol);
//        System.out.println(colIndex);
    }

    private void createButtonPanel()
    {
        loadBtn = new JButton("Load");
        loadBtn.addActionListener((ActionEvent e) -> loadButtonClicked());
        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(loadBtn, BorderLayout.CENTER);
        submitBtn = new JButton("Submit");
        submitBtn.addActionListener((ActionEvent e) -> submitBtnClicked());
        submitBtn.setEnabled(false);
        buttonPanel.add(submitBtn);
    }

    private void loadButtonClicked() {
        File fileToLoad = chooseFileToLoad();
        model.loadDataFrame(fileToLoad);
        if (fileToLoad != null) {
            table = new JTable(model);
            table.setDefaultRenderer(Object.class, new TableCell());
            revalidateTable();
        }
        if (leftPanel != null)
        {
            scrollFilter.remove(leftPanel);
            mainPanel.remove(scrollFilter);
        }
        createFilterPanel();
        submitBtn.setEnabled(true);
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

    private void createFilterPanel() {
        createLeftPanel();
        getFilterList();
        addBoxesToPanel();
//        System.out.println("Done");
        scrollFilter = new JScrollPane(leftPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        // Has limitations due to comparison and sorting of strings
        table.setAutoCreateRowSorter(true);
        updateComboBox();
        mainPanel.add(scrollFilter, BorderLayout.WEST);
        mainPanel.updateUI();
//        System.out.println("out");
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
            try
            {
                fileToLoad = fileLoader.getSelectedFile();
                return fileToLoad;
            } catch (Exception e)
            {
                JOptionPane.showMessageDialog(getParent(), e.toString());
            }
        }
        else
        {
            JOptionPane.showMessageDialog(getParent(), "No File Selected.");
        }
        return null;
    }

    private void setColumnWidth() {
        TableColumn column = null;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
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
        System.out.println("Done with this");
    }

    private void getFilterList() {
        ArrayList<String> boxID = model.getFieldNames();
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
        // TODO Little big of a lag over here
        System.out.println("CLICK");
        columnFilter = new ArrayList<>();
        int colCount = model.getColumnCount();

        for (int i = 0; i < colCount; i++)
        {
            columnFilter.add(filterBoxes.get(i).isSelected());
        }
//        System.out.println("____________________________");
        hideFilteredColumns();
        System.out.println("Exit here");
    }

    private void hideFilteredColumns() {
        System.out.println(columnFilter.toString());
        for (int i = 0; i < table.getColumnCount(); i++) {
            if (columnFilter.get(i).equals(false))
            {
                // TODO
                table.getColumnModel().getColumn(i).setMinWidth(0);
                table.getColumnModel().getColumn(i).setMaxWidth(0);
                table.getColumnModel().getColumn(i).setWidth(0);
            }
            else
            {
                table.getColumnModel().getColumn(i).setMinWidth(200);
                table.getColumnModel().getColumn(i).setMaxWidth(200);
                table.getColumnModel().getColumn(i).setWidth(200);
            }
        }
    }

    public static void main(final String[] args) { SwingUtilities.invokeLater(View::new); }
}