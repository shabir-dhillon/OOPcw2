import javax.swing.*;
import java.awt.*;
import java.util.TreeMap;

public class PieChart extends JPanel {
    final Color[] chartColors = {new Color(0x3232FF), new Color(0x6666FF), new Color(0x9999FF), new Color(0xFF3232), new Color(0xFF6666), new Color(0xFF9999) };
    final int WIDTH = 600;
    final int HEIGHT = 600;
    private DataManager pieModel;


    public PieChart (DataManager model)
    {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.pieModel = model;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        this.setBackground(Color.white);

        Graphics circle = (Graphics) g;

        TreeMap<String, Integer> maritalStatus = pieModel.findMaritalStatusOfAllPatients();
        int[] statusData = new int[maritalStatus.size()];
        int totalSize = 0;

        int i = 0;
        int xCoor = 50;
        int yCoor = 20;
        for (String status: maritalStatus.keySet())
        {
            String dataDescription = status + " (" + maritalStatus.get(status) + ")";
            circle.setColor(Color.black);
            circle.drawString(dataDescription, xCoor, yCoor );
            circle.setColor(chartColors[i]);
            circle.fillRect(xCoor - 20, yCoor - 10, 15, 15);
            int data = maritalStatus.get(status);
            statusData[i] = data;
            totalSize += data;
            i++;
            yCoor += 20;
        }

        i = 0;
        int[] arcAngles = new int[maritalStatus.size()];
        int currentAngle;
        int totalAngle = 0;
        for (Integer data : statusData)
        {
            currentAngle = data * 360 / totalSize;
            arcAngles[i] = currentAngle;
            totalAngle += currentAngle;
            i++;
        }

        // Todo How to deal with this?
//        if (totalAngle < 360)
//        {
//            arcAngles[arcAngles.length - 1] = arcAngles[arcAngles.length-1] + (360 - totalAngle);
//        }
        System.out.println(totalAngle);

        int startAngle = 0;
        for (int j = 0; j < arcAngles.length; j++)
        {
            circle.setColor(chartColors[j]);
            circle.fillArc(200, 150, 300 ,300 ,startAngle ,arcAngles[j]);
            startAngle += arcAngles[j];
        }
    }
}
