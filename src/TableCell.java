import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

class TableCell extends DefaultTableCellRenderer {

    /**
     *  user (2010). Change the background color of a row in a JTable. [online]
     *  Stack Overflow. Available at: https://stackoverflow.com/questions/3875607/change-the-background-color-of-a-row-in-a-jtable
     *  [Accessed 26 Mar. 2021].
     * ‌
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Model model = (Model) table.getModel();
        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        comp.setBackground(model.getRowColour(row));
        return comp;
    }
}