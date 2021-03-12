import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

class TableCell extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        DataManager model = (DataManager) table.getModel();
        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        comp.setBackground(model.getRowColour(row));
        return comp;
    }
}