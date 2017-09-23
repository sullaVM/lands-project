import processing.core.PApplet;
import java.awt.Color;

class Widget {
    PApplet parent;
    int height = 50;
    int width = 110;
    int yPos = 150;
    int xPos = 400;
    int buttonColor = Color.RED.getRGB();
    int textColor = 255;
    boolean mouseOver = false;
    String buttonText;
    int textShiftX = 4;
    float textShiftY = (float) 1.5;
    Constants c = new Constants();
    int textSize = 18;

    Widget(PApplet parent, String text, Color myButtonColor, int xPosition, int yPosition) {
        this.buttonColor = myButtonColor.getRGB();
        this.yPos = yPosition;
        this.xPos = xPosition;
        this.buttonText = text;
        this.parent = parent;
    }

    Widget(PApplet parent, String text, int myButtonColor, int xPosition, int yPosition, int width, int height) {
        this.buttonColor = myButtonColor;
        this.yPos = yPosition;
        this.xPos = xPosition;
        this.buttonText = text;
        this.parent = parent;
        this.width = width;
        this.height = height;
    }

    Widget(PApplet parent, String text, int myButtonColor, int xPosition, int yPosition, int width, int height, int textShiftX, Color textColor) {
        this.buttonColor = myButtonColor;
        this.yPos = yPosition;
        this.xPos = xPosition;
        this.buttonText = text;
        this.parent = parent;
        this.width = width;
        this.height = height;
        this.textShiftX = textShiftX;
        this.textColor = textColor.getRGB();
        this.textSize = 14;
    }

    Widget(PApplet parent, String text, int myButtonColor, int xPosition, int yPosition, int width, int height, int textShiftX) {
        this.buttonColor = myButtonColor;
        this.yPos = yPosition;
        this.xPos = xPosition;
        this.buttonText = text;
        this.parent = parent;
        this.width = width;
        this.height = height;
        this.textShiftX = textShiftX;
    }

    void draw() {
        if (mouseOver)
            parent.stroke(180);
        else
            parent.noStroke();
        parent.fill(buttonColor);
        parent.rect(xPos, yPos, width, height, 5);
        parent.fill(textColor);
        parent.textSize(textSize);
        parent.text(buttonText, xPos + width / textShiftX, (yPos + height/textShiftY) - 2);
    }

    boolean click(int xMouse, int yMouse) {
        return (yMouse >= yPos && yMouse <= yPos + height && xMouse >= xPos && xMouse <= xPos + width);
    }

    void mouseOver(int xMouse, int yMouse) {
        if (yMouse >= yPos && yMouse <= yPos + height && xMouse >= xPos && xMouse <= xPos + width)
            mouseOver = true;
        else
            mouseOver = false;
    }

    public boolean isOver(int xMouse, int yMouse) {
        if (yMouse >= yPos && yMouse <= yPos + height && xMouse >= xPos && xMouse <= xPos + width)
            return true;
        else
            return false;
    }
}

