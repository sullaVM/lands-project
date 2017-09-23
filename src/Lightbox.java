import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PImage;

import java.awt.*;

public class Lightbox {

    private float x;
    private float xOriginal;
    private float dx;
    private float y;
    private float yOriginal;
    private float width;
    private float height;
    float xOffset = 0.0f;
    float yOffset = 0.0f;
    float lineHieght = 14;

    PFont header;
    PFont body;
    Constants c = new Constants();
    PApplet parent;
    LandData data;
    private Color lightboxColor;
    PImage drag;

    boolean isPopup = false;
    boolean isFixed = false;
    boolean isLocked = false;
    boolean isOver = false;

    Lightbox(PApplet p, float x, float y, int width, int height, Color c) {
        parent = p;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        lightboxColor = c;
        dx = 30;
    }

    Lightbox(PApplet p, float x, float y, int width, int height, Color c, LandData data) {
        parent = p;
        this.x = x;
        this.y = y;
        this.xOriginal = x;
        this.yOriginal = y;
        this.width = width;
        this.height = height;
        lightboxColor = c;
        dx = 30;
        this.data = data;
        drag = parent.loadImage("graphics/close.png");
        header = parent.loadFont("SourceSansPro-Semibold-35.vlw");
        body = parent.loadFont("SourceSansPro-Regular-12.vlw");
    }

    public void draw() {
        parent.noStroke();
        if (isPopup) {
            parent.fill(c.SHADOW.getRGB());
            parent.rect(x + 3, y + 3, width, height);
            if (!isFixed) {
                parent.triangle(x + (width / 2) + 3, y + height + 5 + 3,
                                x + (width / 2) - 5 + 3, y + height,
                                x + (width / 2) + 5 + 3, y + height + 3);
            }
        }
        parent.fill(lightboxColor.getRGB());
        parent.rect(x, y, width, height);
        if (!isFixed) {
            parent.triangle(x + (width / 2), y + height + 5,
                            x + (width / 2) - 5, y + height,
                            x + (width / 2) + 5, y + height);
        }
        if (isPopup) {
           parent.textSize(12);
           parent.fill(c.RED.getRGB(), 255);
           parent.rect(x, y, width, 40);
           if (isFixed) {
               parent.alpha(120);
               parent.image(drag, x + width - 25, y + 15, 10, 10);
           }
           parent.fill(250);
           parent.textFont(header);
           parent.textSize(20);
           parent.text("£" + data.getPrice(), x + 20, y + 27);
           parent.fill(0, 0, 0);
           parent.textFont(body);
           parent.text(data.getNumname() + ", " + data.getStreet()
                        + "\n" + data.getTown() + "\n" + data.getDistrict()
                        + "\n" + data.getCounty() + "\n" + "POSTCODE: " + data.getPostcode()
                        + "\n" + "DATE OF SALE: " + data.getDateofsale()
                        , x + 20, y + 10 + (lineHieght * 3), width - 40, height - 20);
        }
    }

    public boolean isFixed() {
        return isFixed;
    }

    public boolean getIsOver() {
        return isOver;
    }

    public void fix() {
        if (!isFixed) {
            isFixed = true;
        } else {
            isFixed = false;
        }
    }

    public void slide() {
        if (width < 260) {
            width = width + dx;
        }
    }

    public void close() {
        if (width > 0) {
            width = width - dx;
        }
    }

    public void makePopup() {
        isPopup = true;
    }

//    public void mouseClicked(boolean menuOut) {
//        menuOut = !menuOut;
//    }

    public void mousePressed(float mouseX, float mouseY) {
        if (isOver) {
            isLocked = true;
        } else {
            isLocked = false;
        }
        xOffset = mouseX - x;
        yOffset = mouseY - y;

        if (mouseX > x + width - 25 && mouseX < x + width - 15 &&
                mouseY > y + 15 && mouseY < y + 25) {
            isFixed = false;
            x = xOriginal;
            y = yOriginal;
        }
    }

    public void mouseDragged(float mouseX, float mouseY) {
        if (isLocked && isFixed) {
            x = mouseX - xOffset;
            y = mouseY - yOffset;
        }
    }

    public void mouseReleased() {
        isLocked = false;
    }

    public void mouseMoved(float mouseX, float mouseY) {
        if (mouseX > x && mouseX < x + width &&
                mouseY > y && mouseY < y + height) {
            isOver = true;
        } else {
            isOver = false;
        }

    }
}
