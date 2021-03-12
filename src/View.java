import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class View extends JFrame
{
    private JPanel topPanel;
    private JPanel centerPanel;
    private JPanel buttonPanel;
//    private JPanel leftPanel;
    private JPanel mainPanel;
    private JButton loadBtn;
    private DataManager model;
    private JTextField inputField;
    private  JScrollPane scrollPane;
    private JTable table = new JTable();

    public View()
    {
        super("Data Loader");
        setLayout(new BorderLayout());
        setModel();
        createGUI();

        //---------------------
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(620,420);
        setVisible(true);

        ImageIcon image = new ImageIcon("src/logo.png");
        setIconImage(image.getImage());
        // Teal
        getContentPane().setBackground(new Color(0,80,80));
    }

    public void setModel()
    {
        model = new DataManager();

    }

    public void createGUI() {
        createTopPanel();
        createCenterPanel();
//        createLeftPanel();
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
        inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(100,20));
        topPanel.add(inputField, BorderLayout.WEST);
        topPanel.add(buttonPanel, BorderLayout.EAST);
    }

    private void createButtonPanel()
    {
        loadBtn = new JButton("Load");
        loadBtn.addActionListener((ActionEvent e) -> loadButtonClicked());
        buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(loadBtn, BorderLayout.CENTER);
    }

    private void loadButtonClicked() {
        String s = inputField.getText();
        if (s.length() > 0 && s.endsWith("00.csv")) {
            model.loadDataFrame(s);
            DefaultTableModel tableModel = new DefaultTableModel();
            table = new JTable(tableModel);
            tableModel.setColumnIdentifiers(model.getFieldNames().toArray());
            setColumnWidth(table);
            int colSize = model.getFieldNames().size();
            for (int i = 0; i < model.getNumberOfRows(); i++) {
                String[] rowData = new String[colSize] ;
                for (int k = 0; k < colSize; k++) {
                    String data = model.getColumnValueAt((model.getFieldNames().get(k)), i);
                    rowData[k] = data;
                }
                tableModel.addRow(rowData);
            }
            // TODO check which works
            tableModel.fireTableDataChanged();
            table.revalidate();
            table.repaint();
            // Get scroll pane to revalidate and repaint the table
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            scrollPane.setViewportView(table);
            scrollPane.revalidate();
            scrollPane.repaint();
        }
        inputField.setText("");
    }

    private void setColumnWidth(JTable table) {
        TableColumn column = null;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(200);
        }
    }


//    private void createLeftPanel()
//    {
//        leftPanel = new JPanel();
//
//    }

    public static void main(final String[] args) { SwingUtilities.invokeLater(View::new); }
}