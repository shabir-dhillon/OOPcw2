import com.sun.source.tree.Tree;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import javax.swing.*;

public class GraphicsPanel extends JPanel {
    private DataManager graphModel;

    public GraphicsPanel(DataManager model)
    {
        setPreferredSize(new Dimension(600, 400));
        this.graphModel = model;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        this.setBackground(Color.GRAY);

        TreeMap<Integer, Integer> deathsPerYear = getDeathData();
        int nPoints = deathsPerYear.size();
        int[] xPoints = new int[nPoints];
        int[] yPoints = new int[nPoints];

        int x = 20;
        int y = 300;
        int i = 0;
        for (Integer key : deathsPerYear.keySet())
        {
            xPoints[i] = x;
            yPoints[i] = y - (deathsPerYear.get(key)*20);
            x = x + 50;
            i++;
        }

        Graphics2D lineGraphData = (Graphics2D) g;

        lineGraphData.setColor(Color.GREEN);
        lineGraphData.drawPolyline(xPoints, yPoints, nPoints);

    }

    private TreeMap<Integer, Integer> getDeathData() {
        TreeMap[] deathData = graphModel.peopleWhoDiedInTheSameYear();
        TreeMap<Integer, Integer> deathsPerYear = deathData[0];
        return deathsPerYear;
    }
}
