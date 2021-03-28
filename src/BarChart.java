import java.awt.*;
import java.util.Arrays;
import java.util.TreeMap;
import javax.swing.*;

public class BarChart extends JPanel {
    private Color barColor = Color.PINK;
    private  TreeMap<String, Integer> graphModel;
    final int WIDTH = 600;
    final int HEIGHT = 600;

    public BarChart(TreeMap<String, Integer> model)
    {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.graphModel = model;
    }

    public void paintComponent(Graphics g)
    {
//        super.paintComponent(g);
//        this.setBackground(Color.white);


        int nPoints = graphModel.size();
        int[] xPoints = new int[nPoints];
        int[] scaleData = new int[nPoints];
        int[] yPoints = new int[nPoints];
        String[] xAxisVariables = new String[nPoints];

        int xWidth = WIDTH / nPoints;
        int x = 100;
        int y = HEIGHT - 150;
        int maxYScaleHeight = -1000;

        int maxValue = getMaxValue();
        double offset = (double) 400 / maxValue;

        int i = 0;
        for (String ageGroup : graphModel.keySet())
        {
            xAxisVariables[i] = ageGroup;
            int currentValue = graphModel.get(ageGroup);
            xPoints[i] = x;
            scaleData[i] = currentValue;
            yPoints[i] = (int) (currentValue*offset);
            if (yPoints[i] > maxYScaleHeight)
            {
                maxYScaleHeight = yPoints[i];
            }
            x = x + xWidth + 10;
            i++;
        }

        Arrays.sort(scaleData);
        int minValue = scaleData[0];
        Graphics2D barChart = (Graphics2D) g;
        barChart.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));

        for (int row = 0; row < nPoints; row++){
            barChart.setColor(barColor);
            barChart.fillRect(xPoints[row], y-yPoints[row], xWidth, yPoints[row]);
            barChart.setColor(Color.black);
            barChart.drawString(xAxisVariables[row],xPoints[row] + 20, y + 25);
        }

        drawYAxisScale(maxValue, y, offset, maxYScaleHeight, barChart);
        barChart.setColor(Color.black);
        barChart.drawLine(75,y,x + 25,y);
        barChart.drawString("Age Group (Years)", x + 40, y);
        barChart.drawLine(75,y,75,y - maxYScaleHeight - 20);
        barChart.drawString("Frequency", 50, y - maxYScaleHeight - 25);
    }

    private void drawYAxisScale( int maxValue ,int y, double offset, int maxYScaleHeight, Graphics2D barChart) {

        int maxValueRange = Integer.toString(maxValue).length();
        double roundingValue = Math.pow(10, maxValueRange-1);
        maxValue = (int) (((Math.round( maxValue / roundingValue) * roundingValue)));

        int steps = 4;
        int stepValue = maxValue / steps;


        for (int i = 0; i < steps; i++)
        {
            maxValue = maxValue - stepValue;
            barChart.drawString(Integer.toString(maxValue), 40, y - (int)((maxValue)*offset));
        }
    }

    private int getMaxValue() {
        int max = -10000;
        for (String ageGroup : graphModel.keySet()) {
            int currentValue = graphModel.get(ageGroup);
            if (currentValue > max)
            {
                max = currentValue;
            }
        }
        return max;
    }

}

