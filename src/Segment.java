import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;

import processing.core.PApplet;

public class Segment {
    PApplet parent;
    float xpos;
    float ypos;
    float radius;
    float sizeOfSegment;
    float positionInCircle;
    Color colorOfSegment;
    float newSize;
    String label;

    Segment(PApplet parent, float xpos, float ypos, float radius, float sizeOfSegment, float positionInCircle, Color segmentColor) {
        this.parent = parent;
        this.xpos = xpos;
        this.ypos = ypos;
        this.radius = radius;
        this.sizeOfSegment = sizeOfSegment;
        this.positionInCircle = positionInCircle;
        this.colorOfSegment = segmentColor;
    }

    public void draw() {
        parent.fill(colorOfSegment.getRed(), colorOfSegment.getGreen(), colorOfSegment.getBlue());
        parent.stroke(255);
        parent.arc(xpos, ypos, radius, radius, positionInCircle, (positionInCircle + sizeOfSegment));
        if (sizeOfSegment < newSize) {
            sizeOfSegment += .03;
        }
    }

    void setSize(float newSize) {
        this.newSize = newSize;
    }

    public void mouseOver() {
        float mouseAngle = parent.atan2(parent.mouseY - ypos, parent.mouseX - xpos) / (float) Math.PI;
        if (mouseAngle < 0) {
            mouseAngle += 2;
        }
        mouseAngle *= Math.PI;
        if (parent.mouseX > xpos - (radius / 2) && parent.mouseX < xpos + (radius / 2) && parent.mouseY > ypos - (radius / 2) && parent.mouseY < ypos + (radius / 2) && mouseAngle > positionInCircle && mouseAngle < positionInCircle + sizeOfSegment) {
            parent.fill(0);
            parent.textFont(Main.getMenuButtonFont());
            parent.text(label, xpos-(radius/2)+130, ypos+radius/2+70);
            parent.text("(" + Float.toString((float)( round(100 * (sizeOfSegment / (2 * Math.PI)),2)-.25)) + "%)", xpos - (radius / 2)+170, ypos + (radius/2)+130);
        }
    }
    
	public double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    BigDecimal bd = new BigDecimal(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
}

}
