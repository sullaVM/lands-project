/*Lands Main Line*/
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import java.awt.Color;

public class Main extends PApplet {
    public static final int DATEOFSALE = 1;
    public static final int POSTCODE = 2;
    public static final int STREET = 3;
    public static final int TOWN = 4;
    public static final int DISTRICT = 5;
    public static final int COUNTY = 6;

    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 800;
    public static final int MENU_WIDTH = 270;
    public static final int MENU_BUTTON_X = 15;
    public static final int MENU_BUTTON_Y = 15;
    public static final int MARGIN = 50;
    public static final int GRAPH_WIDTH = 490;
    public static final int GRAPH_HEIGHT = 490;
    public static final int MAIN_WIDTH = 750;
    public static int currentButton = 0;
    public static int currentYear = 0;

    // MAIN BOX
    private static Lightbox white;
    private static Lightbox mainShadow;
    private static Lightbox footer;

    // TYPOGRAPHY
    private static PFont largeFont;
    private static PFont menuButtonFont;
    private static PFont myFont;

    // QUERY
    private static String inputQuery = "";
    private static int yearToQuery = 2016;
    private int[] previousData;
    private static DBQuery queryInstance = new DBQuery(DBConstants.EMBEDDED_URL);

    //MENU
    private Menu mainMenu;
    private boolean menuOut = false;
    private Widget menuButton;
    private static Widget backButton;
    private Lightbox menu;
    private Lightbox shadow;
    private int menuButtonSize = 40;

    // GRAPH
    private static Graph mainGraph;
    private static String title = "HOUSES SOLD IN THE UK: " + yearToQuery;
    private static boolean displayYear = true;

    // PIE CHART
    private static PieChart mainPieChart;

    // MAP
    private Map UKMap;

    // SLIDER
    private Slider testSlider;

    private Constants c = new Constants();
    private PImage logo;

    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public void setup() {
        frameRate(120);

        // TYPOGRAPHY
        myFont = createFont("data/OpenSans-Regular.ttf", 12);
        menuButtonFont = loadFont("SourceSansPro-Regular-25.vlw");
        largeFont = createFont("data/OpenSans-Bold.ttf", 18);
//        myFont = loadFont("ArialMT-12.vlw");

        // LIGHTBOX
        white = new Lightbox(this, 0, 0, MAIN_WIDTH, SCREEN_HEIGHT, new Color(250, 250, 250));
        mainShadow = new Lightbox(this, MAIN_WIDTH, 0, 3, SCREEN_HEIGHT, c.SHADOW);
        footer = new Lightbox(this, 0, SCREEN_HEIGHT - 40, MAIN_WIDTH, 40, c.SILVER);

        // SLIDER
        testSlider = new Slider(this, 300, 150, 750);

        // GRAPH
        mainGraph = new Graph(this, MARGIN * 3, MARGIN * 3, GRAPH_WIDTH, GRAPH_HEIGHT, new int[10]);

        // PIE CHART
        mainPieChart = new PieChart(this, 290, 320, 430);

        // MENU
        menu = new Lightbox(this, 0, 0, 0, SCREEN_HEIGHT, c.TAUPE);
        mainMenu = new Menu(this);
        menuButton = new Widget(this, "", color(0), MENU_BUTTON_X, MENU_BUTTON_Y, menuButtonSize, menuButtonSize);
        shadow = new Lightbox(this, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, new Color(0, 0, 0, 120));

        // UNFOLDING MAP
        UKMap = new Map(this);
        UKMap.setup(this, mainMenu);

        fetchAndDisplayQuery(yearToQuery);

        logo = loadImage("graphics/logo.png");
    }

    public void settings() {
        size(SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    public void draw() {
        // MAP
        background(161, 202, 254);
        UKMap.draw();

        // GRAPH
        mainShadow.draw();
        white.draw();
        footer.draw();

        textFont(largeFont);
        fill(c.RED.getRGB());
        rect(0, 0, MAIN_WIDTH, 70);
        fill(c.FLORAL_WHITE.getRGB());

        textSize(20);
        fill(250);
        text(title, MARGIN + 170, MARGIN - 8);

        textFont(myFont);
        noStroke();
        textFont(myFont);
        mainGraph.draw();
        mainGraph.transition();

        textFont(largeFont);
        if (!mainGraph.isVisible()) {
            mainGraph.fadeOut();
        }
        if (backButton != null) {
            backButton.draw();
        }
        //pieChart
        if (mainPieChart != null && mainPieChart.isVisible()) {
            mainPieChart.draw();
        }
        // price.draw();
        // testSlider.draw();
        // MENU
        textFont(menuButtonFont);
        noStroke();
        if (menuOut) {
            shadow.draw();
            menu.slide();
            menu.draw();
            mainMenu.slideWidgets();
            mainMenu.draw();
        } else {
            image(loadImage("graphics/menu 2.png"), MENU_BUTTON_X, MENU_BUTTON_Y, menuButtonSize, menuButtonSize);
            menu.close();
            menu.draw();
            mainMenu.slideWidgets();
            if (menuButton.isOver(mouseX, mouseY)) {
                fill(c.SILVER.getRGB());
                rect(MENU_BUTTON_X - 1, MENU_BUTTON_Y + menuButtonSize, 40, 16);
                triangle(MENU_BUTTON_X + 19, MENU_BUTTON_Y + menuButtonSize - 6,
                        MENU_BUTTON_X + 15, MENU_BUTTON_Y + menuButtonSize,
                        MENU_BUTTON_X + 23, MENU_BUTTON_Y + menuButtonSize);
                textFont(myFont, 10);
                fill(0);
                text("Menu", MENU_BUTTON_X + 5, MENU_BUTTON_Y + menuButtonSize + 12);
            }
        }

        // LOGO
        tint(250, 150);
        image(logo, SCREEN_WIDTH - 100 - MARGIN / 2, MARGIN / 2, 100, 35);
        tint(250, 250);

        // RIGHT FOOTER
        textFont(myFont);
        textSize(9);
        fill(c.OLIVE.getRGB());
        text("© 2017 Programming Project Group 18", SCREEN_WIDTH - textWidth("© 2017 Programming Project Group 18") - 25,
                SCREEN_HEIGHT - 20);
    }

    public void keyPressed() {
        mainMenu.displayKey(key);
    }

    public void mouseClicked() {
        UKMap.mouseClicked();
        mainMenu.mouseClicked(mouseX, mouseY);

        if (menuButton.click(mouseX, mouseY) || (mouseX > MENU_WIDTH && menuOut)) {
            menuOut = !menuOut;
            if (menuOut)
                mainMenu.setWidgetPosition(20);
            else
                mainMenu.setWidgetPosition(-230);
        }

        if (menuOut) {
            if (mouseX > MENU_WIDTH) {
                menuOut = !menuOut;
            }
        } else {
            if (mainGraph.isClickable()) {
                for (int i = 0; i < mainGraph.getBarArray().length; i++) {
                    if (mainGraph.getBarArray()[i].mouseOver(mouseX, mouseY)) {
                        if (backButton == null) {
                            mainGraph.setSavedLabels(new String[mainGraph.getIntDataToDisplay().length]);
                            for (int j = 0; j < mainGraph.getBarArray().length; j++) {
                                //       mainGraph.savedLabels[j] = mainGraph.barArray[j].tag;
                            }
                            previousData = mainGraph.getIntDataToDisplay();
                            mainGraph.specificYearDataQuery(i);
                            mainGraph.displayData(Graph.BAR);
                            backButton = new Widget(this, "BACK", new Color(250, 250, 250).getRGB(), MAIN_WIDTH - 70,
                                    SCREEN_HEIGHT - 35, 65, 30, 5, c.RED);
                        }
                    }
                }
            }
        }

        if (backButton != null) {
            if (backButton.click(mouseX, mouseY)) {
                for (int i = 0; i < mainGraph.getSavedLabels().length; i++) {
                    mainGraph.getBarArray()[i].setTag(mainGraph.getSavedLabels()[i]);

                }
                mainGraph.label = "£0 - £550,000";
                backButton = null;
                mainGraph.setIntDataToDisplay(previousData);
                mainGraph.displayData(Graph.BAR);
            }
        }
    }

    public void mouseMoved() {
        mainMenu.mouseOver(mouseX, mouseY);
        testSlider.mouseOver(mouseX, mouseY);
        UKMap.mouseMoved(mouseX, mouseY);
    }

    public void mouseDragged() {
        testSlider.movePins(mouseX, mouseY);
        UKMap.mouseDragged();
    }

    public void mousePressed() {
        UKMap.mousePressed();
    }

    public void mouseReleased() {
        UKMap.mouseReleased();
    }

    public static void fetchAndDisplayQuery(int year) {
        new Thread(() -> {
            mainGraph.yearDataQuery(year);
            mainGraph.displayData(Graph.BAR);
        }).start();

    }

    public static void setTitle(String newTitle) {
        title = newTitle;
    }

    public static PFont getLargeFont() {
        return largeFont;
    }

    public static PFont getMenuButtonFont() {
        return menuButtonFont;
    }

    public static PFont getMyFont() {
        return myFont;
    }

    public static String getInputQuery() {
        return inputQuery;
    }

    public static void setInputQuery(String inputQuery) {
        Main.inputQuery = inputQuery;
    }

    public static int getYearToQuery() {
        return yearToQuery;
    }

    public static void setYearToQuery(int yearToQuery) {
        Main.yearToQuery = yearToQuery;
    }

    public static DBQuery getQueryInstance() {
        return queryInstance;
    }

    public static Widget getBackButton() {
        return backButton;
    }

    public static void setBackButton(Widget backButton) {
        Main.backButton = backButton;
    }

    public static Graph getMainGraph() {
        return mainGraph;
    }
    public static void setDisplayYear(boolean displayYear) {
        Main.displayYear = displayYear;
    }

    public static PieChart getMainPieChart() {
        return mainPieChart;
    }
}
