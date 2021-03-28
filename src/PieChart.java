import javax.swing.*;
import java.awt.*;
import java.util.TreeMap;

public class PieChart extends JPanel {
    // It is best practice to never have any more than 7 categories as it becomes harder for the eye to distinguish relativity of size between each section.
    // Pie chart will not be generated if you have more than 7 fields.
    final Color[] chartColors = {Color.GREEN, Color.BLUE, Color.RED, Color.PINK, Color.YELLOW, Color.MAGENTA, Color.PINK};
    final int WIDTH = 600;
    final int HEIGHT = 600;
    private final TreeMap<String,Integer> pieModel;


    public PieChart (TreeMap<String,Integer> pieChartData)
    {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.pieModel = pieChartData;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        this.setBackground(Color.white);

        Graphics2D pieChart = (Graphics2D) g;

        int[] statusData = new int[pieModel.size()];
        if (pieModel.size() > 7)
        {
            JOptionPane.showMessageDialog(getParent(), "Too many fields for a Pie Chart");
            return;
        }
        int totalSize = 0;

        int i = 0;
        int xCoor = 50;
        int yCoor = 20;
        for (String status: pieModel.keySet())
        {
            String dataDescription = status + " (" + pieModel.get(status) + ")";
            pieChart.setColor(Color.BLACK);
            pieChart.drawString(dataDescription, xCoor, yCoor );
            pieChart.setColor(chartColors[i]);
            pieChart.fillRect(xCoor - 20, yCoor - 10, 15, 15);
            int data = pieModel.get(status);
            statusData[i] = data;
            totalSize += data;
            i++;
            yCoor += 20;
        }

        i = 0;
        int[] arcAngles = new int[pieModel.size()];
        int currentAngle;
        int totalAngle = 0;
        for (Integer data : statusData)
        {
            currentAngle = data * 360 / totalSize;
            arcAngles[i] = currentAngle;
            totalAngle += currentAngle;
            i++;
        }

        // This would only be triggered due to an accumalated rounding error
        if (totalAngle < 360)
        {
            arcAngles[arcAngles.length - 1] = arcAngles[arcAngles.length-1] + (360 - totalAngle);
        }

        int startAngle = 0;
        for (int j = 0; j < arcAngles.length; j++)
        {
            pieChart.setColor(chartColors[j]);
            pieChart.fillArc(200, 150, 300 ,300 , startAngle ,arcAngles[j]);
            startAngle += arcAngles[j];
        }
    }
}
