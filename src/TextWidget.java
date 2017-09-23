import processing.core.PApplet;

public class TextWidget extends Widget {
    private int maxLength = 14;
    private int inputLength = 0;

    public void setTypedMessage(String typedMessage) {
        this.typedMessage = typedMessage;
    }

    private String typedMessage = "";

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    private String indicator = "";
    private boolean indicatorOn;
    private int timer = 0;

    TextWidget(PApplet parent, String text, int textColor, int myButtonColor, int xPosition, int yPosition, int width, int height) {
        super(parent, text, myButtonColor, xPosition, yPosition, width, height);
        this.textColor = textColor;
        text = "";
        textShiftX = 16;
    }

    void draw() {
        if (mouseOver)
            parent.stroke(180);
        else
            parent.noStroke();
        parent.fill(buttonColor);
        parent.rect(xPos, yPos, width, height);
        parent.fill(textColor);
        parent.text(buttonText + indicator, xPos + width / textShiftX, yPos + height / textShiftY);
    }

    public void focus() {
        timer++;
        if (timer > 50) {
            indicatorOn = !indicatorOn;
            timer = 0;
        }
        if (indicatorOn)
            indicator = "|";
        else
            indicator = "";
    }

    public void displayKey(char key) {
        if (key >= 32 && key <= 126) {
            typedMessage += key;
            inputLength++;
        } else if (key == 8 && inputLength > 0) {
            inputLength--;
            typedMessage = typedMessage.substring(0, inputLength);
            if (typedMessage == null)
                typedMessage = "";
        } else if (key == 10 || key == 176) {
            inputLength = 0;
            buttonText = "";
            Main.setInputQuery(typedMessage);
            typedMessage = "";

            switch (Main.currentButton) {
                case Main.DATEOFSALE:
                    Main.getMainGraph().displayMenuData(Main.getInputQuery(), Main.currentYear, Main.DATEOFSALE);
                    Main.getMainGraph().displayData(Graph.BAR);
                    if (!Main.getMainGraph().isVisible()) {
                        Main.getMainGraph().fadeIn();
                        Main.getMainPieChart().setVisible(false);
                    }
                    break;
                case Main.POSTCODE:
                    Main.getMainGraph().displayMenuData(Main.getInputQuery(), Main.currentYear, Main.POSTCODE);
                    Main.getMainGraph().displayData(Graph.BAR);
                    if (!Main.getMainGraph().isVisible()) {
                        Main.getMainGraph().fadeIn();
                        Main.getMainPieChart().setVisible(false);
                    }
                    break;
                case Main.STREET:
                    Main.getMainGraph().displayMenuData(Main.getInputQuery(), Main.currentYear, Main.STREET);
                    Main.getMainGraph().displayData(Graph.BAR);
                    if (!Main.getMainGraph().isVisible()) {
                        Main.getMainGraph().fadeIn();
                        Main.getMainPieChart().setVisible(false);
                    }
                    break;
                case Main.TOWN:
                    Main.getMainGraph().displayMenuData(Main.getInputQuery(), Main.currentYear, Main.TOWN);
                    Main.getMainGraph().displayData(Graph.BAR);
                    if (!Main.getMainGraph().isVisible()) {
                        Main.getMainGraph().fadeIn();
                        Main.getMainPieChart().setVisible(false);
                    }
                    break;
                case Main.DISTRICT:
                    Main.getMainGraph().displayMenuData(Main.getInputQuery(), Main.currentYear, Main.DISTRICT);
                    Main.getMainGraph().displayData(Graph.BAR);
                    if (!Main.getMainGraph().isVisible()) {
                        Main.getMainGraph().fadeIn();
                        Main.getMainPieChart().setVisible(false);
                    }
                    break;
                case Main.COUNTY:
                    Main.getMainGraph().displayMenuData(Main.getInputQuery(), Main.currentYear, Main.COUNTY);
                    Main.getMainGraph().displayData(Graph.BAR);
                    if (!Main.getMainGraph().isVisible()) {
                        Main.getMainGraph().fadeIn();
                        Main.getMainPieChart().setVisible(false);
                    }
                    break;
            }
        }

        if (inputLength > maxLength) {
            buttonText = typedMessage.substring(inputLength - maxLength, inputLength);
        } else if (key != 10 && key != 176) {
            buttonText = typedMessage;
        }
    }
}


