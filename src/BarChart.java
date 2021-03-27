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
        super.paintComponent(g);
        this.setBackground(Color.white);


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
        System.out.print("Max : " + maxValue);
        System.out.println(" , Min : " + minValue);
        Graphics2D barChart = (Graphics2D) g;


//        barChart.drawLine(0,0,600,0);
//        barChart.drawLine(100,0,100,600);
//        barChart.drawLine(0,100,600,100);
//        barChart.drawLine(0,200,600,200);
//        barChart.drawLine(0,300,600,300);
//        barChart.drawLine(0,400,600,400);
//        barChart.drawLine(0,500,600,500);
//        barChart.drawLine(0,600,600,600);
//        barChart.drawLine(0,0,600,600);

        for (int row = 0; row < nPoints; row++){
            barChart.setColor(barColor);
            barChart.fillRect(xPoints[row], y-yPoints[row], xWidth, yPoints[row]);
            barChart.setColor(Color.black);
            barChart.drawString(xAxisVariables[row],xPoints[row] + 20, y + 25);
        }

        drawYAxisScale(scaleData, minValue, maxValue);
        barChart.setColor(Color.black);
        barChart.drawLine(75,y,x + 25,y);
        barChart.drawString("Age Group (Years)", x + 40, y);
        barChart.drawLine(75,y,75,y - maxYScaleHeight - 20);
        barChart.drawString("Frequency", 50, y - maxYScaleHeight - 25);
    }

    private void drawYAxisScale(int[] scaleData, int minValue, int maxValue) {




        /**
         * for loop
         * barChart.drawLine(75,y,x + 25,y);
         */
    }

    private int getMinValue() {
        int min = 10000;
        for (String ageGroup : graphModel.keySet()) {
            int currentValue = graphModel.get(ageGroup);
            if (currentValue < min)
            {
                min = currentValue;
            }
        }
        return min;
    }

    private int getMaxValue() {
        int max = -10000;
        for (String ageGroup : graphModel.keySet()) {
            int currentValue = graphModel.get(ageGroup);
            System.out.print(currentValue + " , ");
            if (currentValue > max)
            {
                max = currentValue;
            }
        }
        System.out.println();
        return max;
    }

}

