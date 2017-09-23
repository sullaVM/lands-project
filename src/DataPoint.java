import java.awt.Color;

import processing.core.PApplet;
import processing.core.PFont;

public class DataPoint {
    private PFont largeFont;
    PApplet parent;
    private float xpos;
    private float ypos;
    private float height = 50;
    private int radius = 0;
    private float maxValue;
    private float maxHeight;
    private TrendLine testLine;

    public float getNextPointX() {
        return nextPointX;
    }

    public void setNextPointX(float nextPointX) {
        this.nextPointX = nextPointX;
    }

    public float getRise() {
        return rise;
    }

    public void setRise(float rise) {
        this.rise = rise;
    }

    public float getRun() {
        return run;
    }

    public void setRun(float run) {
        this.run = run;
    }

    private float nextPointX;
    private float nextPointY;
    private float yposOfPoint;
    private float rise;
    private float run;
    private float slope;
    private String tag;
    private int value;
    private Color circleColor = Constants.OLIVE;

    DataPoint(PApplet parent, float xpos, float ypos, float maxValue, float maxHeight, String tag) {
        this.parent = parent;
        this.xpos = xpos;
        this.ypos = ypos;
        this.maxValue = maxValue;
        this.maxHeight = maxHeight;
        this.largeFont = Main.getLargeFont();
        this.tag = tag;
    }

    public void draw() {
        if (testLine != null) {
            testLine.draw();
        }

        parent.strokeWeight(1);
        int r = circleColor.getRGB();
        parent.fill(r);
        parent.ellipse(xpos, yposOfPoint, radius, radius);
        parent.fill(255);
        parent.ellipse(xpos, yposOfPoint, (float) (radius - (radius * .6)), (float) (radius - (radius * .6)));
    }

    public void setHeight(int graphValue) {
        height = Math.round(((float) graphValue / maxValue) * maxHeight);

        yposOfPoint = (ypos + maxHeight) - height;
    }

    public void updatePosition(float maxHeight) {
        this.maxHeight = maxHeight;
        setHeight(this.value);

    }

    public void createLine() {
        testLine = new TrendLine(parent, xpos, (ypos + maxHeight) - height);
    }

    public void setNextPointXandY(float nextPointX, float nextPointY) {
        this.nextPointX = nextPointX;
        this.nextPointY = nextPointY;
    }

    public void calculateSlope() {
        rise = nextPointY - yposOfPoint;
        run = nextPointX - xpos;
        slope = rise / run;
    }

    public boolean mouseOver(float xMouse, float yMouse) {
        if (yMouse >= yposOfPoint - radius && yMouse <= yposOfPoint + radius
                && xMouse >= xpos - radius && xMouse <= xpos + radius) {
            parent.textFont(largeFont);
            int startOfYear = tag.indexOf('(');
            String houseCount = tag.substring(0, startOfYear);
            String year = tag.substring(startOfYear + 1, startOfYear + 5);
            parent.textFont(largeFont);
            parent.fill(Constants.OLIVE.getRGB());
            parent.text(year, Main.getMainGraph().getXpos(), 680);
            parent.text("HOUSE COUNT: " + houseCount, Main.getMainGraph().getXpos(), 700);
            return true;
        }
        return false;
    }

    public float getXpos() {
        return xpos;
    }

    public void setXpos(float xpos) {
        this.xpos = xpos;
    }

    public float getYposOfPoint() {
        return yposOfPoint;
    }

    public TrendLine getTestLine() {
        return testLine;
    }

    public void setTestLine(TrendLine testLine) {
        this.testLine = testLine;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }



}
