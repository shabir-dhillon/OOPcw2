import java.awt.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.*;

public class BarChart extends JPanel {
    private Color[] barColors = {Color.BLUE, Color.GREEN, Color.CYAN, Color.RED, Color.ORANGE, Color.PINK, Color.YELLOW};
    private DataManager graphModel;
    final int WIDTH = 600;
    final int HEIGHT = 600;

    public BarChart(DataManager model)
    {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.graphModel = model;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        this.setBackground(Color.white);
        TreeMap<Integer, Integer> deathsPerYear = getDeathData();
        int nPoints = deathsPerYear.size();
        int[] xPoints = new int[nPoints];
        int[] yPoints = new int[nPoints];

        int xWidth = WIDTH / nPoints;
        int x = 100;
        int y = HEIGHT - 150;
        int maxYScaleHeight = 50;
        int i = 0;

        int maxValue = getMaxValue(deathsPerYear);
        double offset = (double) 400 / maxValue;
        System.out.println(offset);

        for (Integer year : deathsPerYear.keySet())
        {
            xPoints[i] = x;
            yPoints[i] = (int) ( y - (deathsPerYear.get(year)*offset));
            x = x + xWidth;
            i++;
        }

        Graphics2D barChart = (Graphics2D) g;

        barChart.drawLine(0,0,600,0);
        barChart.drawLine(100,0,100,600);
        barChart.drawLine(0,100,600,100);
        barChart.drawLine(0,200,600,200);
        barChart.drawLine(0,300,600,300);
        barChart.drawLine(0,400,600,400);
        barChart.drawLine(0,500,600,500);
        barChart.drawLine(0,600,600,600);
        barChart.drawLine(0,0,600,600);


        int j = 0;
        for (int row = 0; row < nPoints; row++){
            barChart.setColor(barColors[j]);
            barChart.fillRect(xPoints[row], y-yPoints[row], xWidth, yPoints[row]);
            if ( j == barColors.length -1) { j = -1;}
            j++;
        }
        //barChart.drawPolyline(xPoints, yPoints, nPoints);

    }

    private int getMaxValue(TreeMap<Integer, Integer> deathsPerYear) {
        int max = -10000;
        for (Integer year : deathsPerYear.keySet()) {
            int currentValue = deathsPerYear.get(year);
            if (currentValue > max) max = currentValue;
        }
        return max;
    }

    private TreeMap<Integer, Integer> getDeathData() {
        TreeMap[] deathData = graphModel.peopleWhoDiedInTheSameYear();
        TreeMap<Integer, Integer> deathsPerYear = deathData[0];
        return deathsPerYear;
    }
}

