import java.awt.*;
import java.awt.*;
import java.util.ArrayList;
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
        super.paintComponent(g);
        this.setBackground(Color.white);


        int nPoints = graphModel.size();
        int[] xPoints = new int[nPoints];
        int[] yPoints = new int[nPoints];
        String[] xAxisVariables = new String[nPoints];

        int xWidth = WIDTH / nPoints;
        int x = 100;
        int y = HEIGHT - 150;
        int maxYScaleHeight = 50;

        int maxValue = getMaxValue();
        double offset = (double) 400 / maxValue;
        System.out.println(offset);

        int i = 0;
        for (String ageGroup : graphModel.keySet())
        {
            xAxisVariables[i] = ageGroup;
            xPoints[i] = x;
            yPoints[i] = (int) ( y - (graphModel.get(ageGroup)*offset));
            x = x + xWidth + 10;
            i++;
        }

        Graphics2D barChart = (Graphics2D) g;


        for (int row = 0; row < nPoints; row++){
            barChart.setColor(barColor);
            barChart.fillRect(xPoints[row], y-yPoints[row], xWidth, yPoints[row]);
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

