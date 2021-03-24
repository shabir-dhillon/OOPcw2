import java.awt.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.*;

public class LineGraph extends JPanel {
    private DataManager graphModel;
    final int WIDTH = 600;
    final int HEIGHT = 600;

    public LineGraph(DataManager model)
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
        int x = xWidth / 2;
        int y = 300;
        int i = 0;

        int offset;
        int maxValue = getMaxValue(deathsPerYear);

        // TODO
        if (maxValue < 10)
        {
            offset = 20;
        }
        else if (maxValue > 100)
        {
            // How to account for the smaller values?
            offset = 1;
        }
        else
        {
            offset = 2;
        }

        for (Integer year : deathsPerYear.keySet())
        {
            xPoints[i] = x;
            yPoints[i] = y - (deathsPerYear.get(year)*offset);
            x = x + xWidth;
            i++;
        }

        Graphics2D lineGraphData = (Graphics2D) g;

        lineGraphData.setColor(Color.GREEN);
        lineGraphData.drawPolyline(xPoints, yPoints, nPoints);

    }

    private int getMaxValue(TreeMap<Integer, Integer> deathsPerYear) {
        int max = 0;
        int i = 0;
        int size = deathsPerYear.size();
        for (Integer year : deathsPerYear.keySet()) {
            if (i == size - 1) {
                max = deathsPerYear.get(year);
            }
        }
        return max;
    }

    private TreeMap<Integer, Integer> getDeathData() {
        TreeMap[] deathData = graphModel.peopleWhoDiedInTheSameYear();
        TreeMap<Integer, Integer> deathsPerYear = deathData[0];
        return deathsPerYear;
    }
}

