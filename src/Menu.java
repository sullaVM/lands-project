import processing.core.PApplet;
import processing.core.PConstants;

public class Menu {
    private int currentSection = 0;
    private int currentYear = Main.getYearToQuery();
    private int widgetPosition = -230;
    PApplet parent;
    private String searchField = "";
    private TextWidget inputBox;
    private TextWidget selectedInputBox;
    private Widget backButton;
    private Widget[] yearWidgets = new Widget[8];
    private Widget[] selectionWidgets = new Widget[10];
    private Widget[] comparisonWidgets = new Widget[3];
    private Widget[] currentWidgets = yearWidgets;

    //  HEADING
    private float titleXpos = widgetPosition;
    private float titleYpos = 30;

    private float queryLabelXpos = 20;
    private float queryLabelXYpos = titleYpos + 10;

    Menu(PApplet parent) {
        this.parent = parent;
        intialiseWidgets();
    }

    public void draw() {
//		parent.textFont(Main.myFont);
        // parent.rect(0, 0, menu_width, menu_height);
        parent.textFont(Main.getLargeFont(), 12);
        parent.fill(Constants.BLACK.getRGB());
        parent.text("UK LAND REGISTRY VISUALISED DATA", titleXpos, titleYpos);
        parent.stroke(Constants.RIFLE.getRGB());
        parent.line(titleXpos - 20, titleYpos + 7, titleXpos - 20 + Main.MENU_WIDTH, titleYpos + 7);
        parent.line(titleXpos - 20, titleYpos + 35, titleXpos - 20 + Main.MENU_WIDTH, titleYpos + 35);
        parent.noStroke();
        parent.textFont(Main.getMyFont(), 18);
        parent.fill(255);
        parent.textFont(Main.getMyFont(), 16);
        if (currentSection == 1)
            parent.text(currentYear + " - ", queryLabelXpos, queryLabelXYpos, 230, 65);
        else if (currentSection == 2)
            parent.text(currentYear + " - " + searchField, queryLabelXpos, queryLabelXYpos, 230, 65);
        else if (currentSection == 3)
            parent.text(searchField, queryLabelXpos, queryLabelXYpos, 230, 65);
        parent.fill(0);
        parent.textFont(Main.getLargeFont(), 12);
        if (currentSection != 2) {
            for (int count = 0; count < currentWidgets.length; count++) {
                currentWidgets[count].draw();
            }
        } else {
            backButton.draw();
            if (selectedInputBox != null)
                selectedInputBox.focus();
            inputBox.draw();
        }
        parent.noStroke();
        parent.textFont(Main.getMyFont(), 12);
        parent.textLeading(13);
        parent.fill(Constants.BLACK.getRGB());
        String group = "Group 18\nConor Heffernan\nHieu Nguyen\nPhilip Bradish\nSulla Montes";
        parent.text(group, titleXpos, Main.SCREEN_HEIGHT - ((parent.textAscent() + parent.textDescent()) * 4));
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public void displayKey(char key) {
        if (currentSection == 2) {
            if (selectedInputBox != null) {
                selectedInputBox.displayKey(key);
            }
        }
    }

    public void slideWidgets() {
        if (backButton.xPos != widgetPosition) {
            int widgetSlider;
            if (widgetPosition > 0) {
                widgetSlider = 25;
            } else {
                widgetSlider = -25;
            }
            inputBox.xPos += widgetSlider;
            backButton.xPos += widgetSlider;
            titleXpos += widgetSlider;
            for (int count = 0; count < currentWidgets.length; count++)
                currentWidgets[count].xPos += widgetSlider;
        }
    }

    public void intialiseWidgets() {
        //int widgetColor = color (0);
        String text = "";
        int textShift = 3;
        for (int count = 0; count < yearWidgets.length; count++) {
            switch (count) {
                case 0:
                    text = "2010";
                    break;
                case 1:
                    text = "2011";
                    break;
                case 2:
                    text = "2012";
                    break;
                case 3:
                    text = "2013";
                    break;
                case 4:
                    text = "2014";
                    break;
                case 5:
                    text = "2015";
                    break;
                case 6:
                    text = "2016";
                    break;
                case 7:
                    text = "Year Comparison";
                    textShift = 8;
                    break;
            }
            yearWidgets[count] = new Widget(parent, text, 0, widgetPosition, 75 + 57 * (count), 230, 50, textShift + 5);
        }
        textShift = 10;
        for (int count = 0; count < selectionWidgets.length; count++) {
            switch (count) {
                case 0:
                    text = "Price";
                    break;
                case 1:
                    text = "Date of Sale";
                    break;
                case 2:
                    text = "Postcode";
                    break;
                case 3:
                    text = "Property Type";
                    break;
                case 4:
                    text = "Old/New";
                    break;
                case 5:
                    text = "Street";
                    break;
                case 6:
                    text = "Town";
                    break;
                case 7:
                    text = "District";
                    break;
                case 8:
                    text = "County";
                    break;
                case 9:
                    text = "Back";
            }
            selectionWidgets[count] = new Widget(parent, text, 0, 20, 75 + 57 * (count), 230, 50, textShift);
        }

        for (int count = 0; count < comparisonWidgets.length; count++) {
            switch (count) {
                case 0:
                    text = "Yearly Averages";
                    break;
                case 1:
                    text = "Houses Sold";
                    break;
                default:
                    text = "Back";
            }
            comparisonWidgets[count] = new Widget(parent, text, 0, 20, 75 + 57 * (count), 230, 50, textShift);
        }
        inputBox = new TextWidget(parent, "Search", 0, 255, widgetPosition, 75, 230, 50);
        backButton = new Widget(parent, "Back", 0, widgetPosition, 75 + 60, 230, 50, textShift);

    }

    public void mouseClicked(int mouseX, int mouseY) {
        if (currentSection == 0) {
            for (int count = 0; count < currentWidgets.length; count++) {
                if (currentWidgets[count].click(mouseX, mouseY)) {
                    currentSection = 1;
                    currentWidgets = selectionWidgets;
                    switch (count) {
                        case 0:
                            currentYear = 2010;
                            break;
                        case 1:
                            currentYear = 2011;
                            break;
                        case 2:
                            currentYear = 2012;
                            break;
                        case 3:
                            currentYear = 2013;
                            break;
                        case 4:
                            currentYear = 2014;
                            break;
                        case 5:
                            currentYear = 2015;
                            break;
                        case 6:
                            currentYear = 2016;
                            break;
                        case 7:
                            searchField = "Year Comparison";
                            currentWidgets = comparisonWidgets;
                            currentSection = 3;
                        default:
                    }
                }
            }
        } else if (currentSection == 1) {
            for (int count = 0; count < currentWidgets.length; count++) {
                if (currentWidgets[count].click(mouseX, mouseY)) {
                    switch (count) {
                        case 0:
                            Main.fetchAndDisplayQuery(currentYear);
                            Main.setYearToQuery(currentYear);
                            for (int i = 0; i < Main.getMainGraph().getTrendArray().length; i++) {
                                Main.getMainGraph().getTrendArray()[i].setRadius(0);
                                Main.getMainGraph().getTrendArray()[i].setTestLine(null);
                            }
                            Main.setDisplayYear(true);
                            Main.setTitle(("Houses Sold In The UK: " + Main.getYearToQuery()).toUpperCase());
                            if (!Main.getMainGraph().isVisible()) {
                                Main.getMainGraph().fadeIn();
                                Main.getMainPieChart().setVisible(false);
                            }
                            break;
                        case 1:
                            Main.currentYear = currentYear;
                            Main.currentButton = Main.DATEOFSALE;
                            searchField = currentWidgets[count].buttonText;
                            currentSection = 2;

                            break;
                        case 2:
                            Main.currentYear = currentYear;
                            Main.currentButton = Main.POSTCODE;
                            searchField = currentWidgets[count].buttonText;
                            currentSection = 2;

                            break;
                        case 3:
                            Main.getMainGraph().xAxisLabel = "";
                            Main.getMainPieChart().displayPropertyTypeData(currentYear);
                            Main.getMainPieChart().setVisible(true);
                            Main.setTitle(("Ratio of Property Types Sold - " + currentYear).toUpperCase());
                            if (Main.getBackButton() != null) {
                                Main.setBackButton(null);
                            }
                            break;
                        case 4:
                            Main.getMainGraph().xAxisLabel = "";
                            Main.getMainPieChart().displayOldNewData(currentYear);
                            Main.getMainPieChart().setVisible(true);
                            Main.setTitle(("Ratio of Old:New Properties - " + currentYear).toUpperCase());
//                            if(Main.mainGraph.visible==false)
//                            {
//                            	Main.mainGraph.fadeIn();
//                            	Main.mainPieChart.visible = false;
//                            }
                            break;
                        case 5:
                            Main.currentYear = currentYear;
                            Main.currentButton = Main.STREET;
                            searchField = currentWidgets[count].buttonText;
                            currentSection = 2;

                            break;
                        case 6:
                            Main.currentYear = currentYear;
                            Main.currentButton = Main.TOWN;
                            searchField = currentWidgets[count].buttonText;
                            currentSection = 2;

                            break;
                        case 7:
                            Main.currentYear = currentYear;
                            Main.currentButton = Main.DISTRICT;
                            searchField = currentWidgets[count].buttonText;
                            currentSection = 2;

                            break;
                        case 8:
                            Main.currentYear = currentYear;
                            Main.currentButton = Main.COUNTY;
                            searchField = currentWidgets[count].buttonText;
                            currentSection = 2;

                            break;
                        case 9:
                            currentSection = 0;
                            currentWidgets = yearWidgets;
                            break;
                        default:
                            searchField = currentWidgets[count].buttonText;
                            currentSection = 2;
                    }
                }
            }
        } else if (currentSection == 2) {
            if (inputBox.click(mouseX, mouseY))
                selectedInputBox = inputBox;
            else if (backButton.click(mouseX, mouseY)) {
                currentSection = 1;
                Main.setInputQuery("");
                inputBox.setTypedMessage("");
                inputBox.buttonText = "Search";
            } else {
                if (selectedInputBox != null)
                    selectedInputBox.setIndicator("");
                selectedInputBox = null;
            }
        } else if (currentSection == 3) {
            for (int count = 0; count < currentWidgets.length; count++) {
                if (currentWidgets[count].click(mouseX, mouseY)) {
                    switch (count) {
                        case 0:            //yearlyAverages
                            Main.getMainGraph().displayAveragePriceTrend();
                            Main.setTitle("Yearly Price Averages".toUpperCase());
                            Main.setDisplayYear(false);
                            Main.getMainGraph().xAxisLabel = "Current Year: ";
                            if (!Main.getMainGraph().isVisible()) {
                                Main.getMainGraph().fadeIn();
                                Main.getMainPieChart().setVisible(false);
                            }

                            break;
                        case 1:            // house sales
                            Main.getMainGraph().displayHousesSold();
                            Main.setTitle("Yearly House Sales".toUpperCase());
                            Main.setDisplayYear(false);
                            Main.getMainGraph().xAxisLabel = "Current Year: ";
                            if (!Main.getMainGraph().isVisible()) {
                                Main.getMainGraph().fadeIn();
                                Main.getMainPieChart().setVisible(false);
                                Main.getMainGraph().graphType = Graph.TREND;
                            }

                            break;
                        case 2:
                            currentSection = 0;
                            currentWidgets = yearWidgets;
                            break;
                        default:

                    }
                }
            }
        }

    }

    public void mouseOver(int mouseX, int mouseY) {
        if (currentSection == 2)
            backButton.mouseOver(mouseX, mouseY);
        for (int count = 0; count < currentWidgets.length; count++) {
            currentWidgets[count].mouseOver(mouseX, mouseY);
        }
    }


    public void setWidgetPosition(int widgetPosition) {
        this.widgetPosition = widgetPosition;
    }
}