import processing.core.PApplet;

public class Pin {

    PApplet parent;

    float pinWidthHeight;
    float xPos;
    float yPos;

    Pin(PApplet p, float lineX, float lineY, float sliderHeight) {
        parent = p;
        xPos = lineX;
        yPos = lineY;
        pinWidthHeight = sliderHeight;
    }

    public void draw() {
        parent.fill(46, 82, 102);
        parent.rect(xPos, yPos, pinWidthHeight, pinWidthHeight);
    }
}
