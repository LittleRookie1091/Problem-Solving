import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Point;
import javax.swing.*;

public class Toothpicks extends JPanel {
    static int n;
    static double percent;
    static double firstLineSize;
    static int overallFrameSize = 700;

    public static void main(String[] args) {
        try {
            n = Integer.parseInt(args[0]);
        } catch (Exception e) {
            n = 3; // default to 3 iterations
        }
        try {
            percent = Double.parseDouble(args[1]);
        } catch (Exception e) {
            percent = 1;
        }

        double totalWidth = 0;
        double totalHeight = 0;
        double t = 1;
        for (int g = 0; g <= n; g++) {
            if (g % 2 == 0) {
                totalWidth += t;
            } else {
                totalHeight += t;
            }
            t = t * percent;
        }

        if (totalHeight > totalWidth) {
            firstLineSize = (overallFrameSize * 0.9) / totalHeight;
        } else {
            firstLineSize = (overallFrameSize * 0.9) / totalWidth;
        }
        getLines(0, new Point(overallFrameSize / 2, overallFrameSize / 2), true, (int) firstLineSize);

        JFrame frame = new JFrame("Toothpicks");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(overallFrameSize, overallFrameSize);
        frame.getContentPane().add(new Toothpicks());
        frame.setVisible(true);
    }

    private static class Line {
        int firstX;
        int firstY;
        int secondX;
        int secondY;

        public Line(int firstX, int firstY, int secondX, int secondY) {
            this.firstX = firstX;
            this.firstY = firstY;
            this.secondX = secondX;
            this.secondY = secondY;
        }
    }

    static ArrayList<Line> lines = new ArrayList<Line>();

    public static void getLines(int iFirst, Point endPoint, Boolean check, int lineSize) {
        int newLine = (int) (lineSize * percent) / 2;
        Point right = new Point();
        Point left = new Point();
        if (iFirst >= n +1) {
            return;
        } else if (iFirst == 0) {
            newLine = lineSize / 2;

            lines.add(new Line(endPoint.x, endPoint.y, endPoint.x + newLine, endPoint.y));
            right.move(endPoint.x + newLine, endPoint.y);

            // Add line from end of last toothpick LEFT to end of new toothpick
            lines.add(new Line(endPoint.x, endPoint.y, endPoint.x - newLine, endPoint.y));
            left.move(endPoint.x - newLine, endPoint.y);

        } else {

            if (check) {
                // Add line from end of last toothpick RIGHT to end of new toothpick
                lines.add(new Line(endPoint.x, endPoint.y, endPoint.x + newLine, endPoint.y));
                right.move(endPoint.x + newLine, endPoint.y);

                // Add line from end of last toothpick LEFT to end of new toothpick
                lines.add(new Line(endPoint.x, endPoint.y, endPoint.x - newLine, endPoint.y));
                left.move(endPoint.x - newLine, endPoint.y);

            } else {
                lines.add(new Line(endPoint.x, endPoint.y, endPoint.x, endPoint.y + newLine));
                right.move(endPoint.x, endPoint.y + newLine);

                lines.add(new Line(endPoint.x, endPoint.y, endPoint.x, endPoint.y - newLine));
                left.move(endPoint.x, endPoint.y - newLine);
            }
        }

        getLines(iFirst + 1, right, !check, newLine * 2);// *2 as its half the full line size
        getLines(iFirst + 1, left, !check, newLine * 2);
    }

    public void paint(Graphics g) {// Draw the lines
        for (Line line : lines) {
            g.drawLine(line.firstX, line.firstY, line.secondX, line.secondY);
        }
    }

}