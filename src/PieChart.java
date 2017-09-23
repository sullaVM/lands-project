import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import processing.core.PApplet;

public class PieChart {
    private float radius;
    private float xpos;
    private float ypos;
    private int[] dataToDisplay = {2, 3, 4, 5};
    private int numberOfSegments;
    PApplet parent;
    private float sumOfData = 14;
    private Segment[] segmentArray;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    private boolean visible = false;
    private Color[] colors = {new Color(255, 86, 238), new Color(255, 255, 255), new Color(76, 255, 240),
            new Color(247, 247, 155), new Color(255, 130, 130), new Color(150, 60, 255)};
    private String[] labelList = {"Detached", "Semi-Detached", "Terraced", "Flat", "Other"};

    PieChart(PApplet parent, float xpos, float ypos, float radius) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.radius = radius;
        this.parent = parent;
    }

    public void setData(int[] data) {
        this.dataToDisplay = data;
        this.numberOfSegments = data.length;
        segmentArray = new Segment[numberOfSegments];
        float sumOfData = 0;
        for (int i = 0; i < dataToDisplay.length; i++) {
            sumOfData += dataToDisplay[i];

        }
        this.sumOfData = sumOfData;
        float startPointOfSegment = 0;

        for (int i = 0; i < dataToDisplay.length; i++) {
            float sizeOfArc = ((float) (2 * (Math.PI))) * (dataToDisplay[i] / sumOfData);
            segmentArray[i] = new Segment(parent, xpos, ypos, radius, 0, startPointOfSegment, colors[i]);
            startPointOfSegment += sizeOfArc;
            segmentArray[i].setSize(sizeOfArc);
            segmentArray[i].label = Integer.toString(dataToDisplay[i]);
        }
    }

    public void draw() {
        if (visible) {
            if (segmentArray != null) {
                if (segmentArray[segmentArray.length - 1].sizeOfSegment + (segmentArray[segmentArray.length - 1].positionInCircle) >= 2)
                    parent.fill(200, 200, 200);
                parent.ellipse(xpos + 5, ypos + 1, radius, radius);
                for (int i = 0; i < segmentArray.length; i++) {
                    if (segmentArray[i] != null) {
                        segmentArray[i].draw();
                        segmentArray[i].mouseOver();
                        parent.textFont(Main.getLargeFont());
                        parent.stroke(0);
                        parent.fill(colors[i].getRed(), colors[i].getGreen(), colors[i].getBlue());

                        parent.rect(xpos + (radius / 2) + 60, 200 + (50 * i), 30, 30);
                        parent.fill(0);
                        String fullLabel = segmentArray[i].label;
                        int findBracket = fullLabel.indexOf('(');
                        String shortenedString = fullLabel.substring(0, findBracket);
                        parent.text(shortenedString, xpos + (radius / 2) + 100, 222 + (50 * i));
                    }
                }
            }
        }
    }

    public void displayOldNewData(int currentYear) {
        Main.getMainGraph().fadeOut();

        int[] oldVsNew = new int[2];
        final int y = getCurrentYearIndex(currentYear);
        new Thread(() -> {
            List<String> land = new DBQuery(DBConstants.EMBEDDED_URL).fetchCustomQuery(
                    "select count(*) from " + DBConstants.YEARLY_TABLE[y] + " where oldnew = 'N' union " +
                            "select count(*) from " + DBConstants.YEARLY_TABLE[y]);
            oldVsNew[0] = Integer.parseInt(land.get(0));
            oldVsNew[1] = Integer.parseInt(land.get(1)) - Integer.parseInt(land.get(0));
            setData(oldVsNew);
            segmentArray[0].label = "old (" + segmentArray[0].label + " )";
            segmentArray[1].label = "new (" + segmentArray[1].label + " )";
        }).start();
    }

    public void displayPropertyTypeData(int currentYear) {
        Main.getMainGraph().fadeOut();

        int[] propertyTypes = new int[5];

        final int y = getCurrentYearIndex(currentYear);
        new Thread(() -> {
            List<String> land = new DBQuery(DBConstants.EMBEDDED_URL).fetchCustomQuery(
                    "select count(*) from " + DBConstants.YEARLY_TABLE[y] + " where propertytype = 'D' union " +
                            "select count(*) from " + DBConstants.YEARLY_TABLE[y] + " where propertytype = 'S' union " +
                            "select count(*) from " + DBConstants.YEARLY_TABLE[y] + " where propertytype = 'T' union " +
                            "select count(*) from " + DBConstants.YEARLY_TABLE[y] + " where propertytype = 'F' union " +
                            "select count(*) from " + DBConstants.YEARLY_TABLE[y] + " where propertytype = 'O'");

            for (int i = 0; i < propertyTypes.length; i++) {

                propertyTypes[i] = Integer.parseInt(land.get(land.size() - (1 + i)));
            }
            System.out.println((land.toString()));
            setData(propertyTypes);
            segmentArray[0].label = "Detached" + " ( " + segmentArray[0].label + " ) ";
            segmentArray[1].label = "Semi-Detached" + " ( " + segmentArray[1].label + " ) ";
            segmentArray[2].label = "Terraced" + " ( " + segmentArray[2].label + " ) ";
            segmentArray[3].label = "Flat" + " ( " + segmentArray[3].label + " ) ";
            segmentArray[4].label = "Other" + " ( " + segmentArray[4].label + " ) ";
        }).start();
    }

    public int getCurrentYearIndex(int currentYear) {
        switch (currentYear) {
            case 2010:
                return 0;
            case 2011:
                return 1;
            case 2012:
                return 2;
            case 2013:
                return 3;
            case 2014:
                return 4;
            case 2015:
                return 5;
            case 2016:
                return 6;
            case 2017:
                return 7;
        }
        return -1;
    }
}
