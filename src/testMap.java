import processing.core.PApplet;
import processing.core.PFont;
import java.awt.Color;
import java.util.List;

public class testMap extends PApplet {

    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 800;
    public static int MENU_BUTTON_X = 10;
    public static int MENU_BUTTON_Y = 10;
    public static int MARGIN = 50;
    public static int GRAPH_WIDTH = 500;
    public static int GRAPH_HEIGHT = 540;

    // MAIN BOX
    private Lightbox white;

    // MENU
    private Lightbox menu;
    private Menu mainMenu;
    private Widget menuButton;
    boolean menuOut = false;
    private Lightbox shadow;

    // MAP
    private Map UKMap;

    // QUERY
    String url = DBConstants.EMBEDDED_URL;
    DBQuery queryInstance = new DBQuery(url);
    String data = DBConstants.DATA_COLUMNS[0];
    String order = DBConstants.SORT_ORDERS[2];
    String where = "where price > '0'";
    int limit = 10;
    List<String> dataList;

    PFont font;
    float listMargin;

    float mapX;
    float mapY;
    int menuButtonSize = 25;

    public static void main(String[] args) {
        PApplet.main("testMap");
    }

    public void settings() {
        size(SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    public void setup() {
        // UNFOLDING MAP
        UKMap = new Map(this);
        UKMap.setup(this, mainMenu);

        // LIGHTBOX
        white = new Lightbox(this, 0, 0, 600, SCREEN_HEIGHT, new Color(250, 250, 250));

        // MENU
        menu = new Lightbox(this, 0, 0, 0, SCREEN_HEIGHT, new Color(103, 129, 162));
        mainMenu = new Menu(this);
        menuButton = new Widget(this, "", color(0), MENU_BUTTON_X, MENU_BUTTON_Y, menuButtonSize, menuButtonSize);
        shadow = new Lightbox(this, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, new Color(0, 0, 0, 120));

        // LIST
        dataList = queryInstance.fetchColumn(DBConstants.YEARLY_TABLE[0], data, where, data, order, limit);

        // TYPOGRAPHY
        font = createFont("Georgia", 14);
    }

    public void draw() {
        background(161, 202, 254);

        UKMap.draw();
        white.draw();

        // TYPOGRAPHY
        textFont(font);
        fill(0);

        // LIST
        listMargin = MARGIN + 30;
        for (int i = 0; i < dataList.size(); i++) {
            listMargin = listMargin + 18;
            text("" + dataList.get(i), 80, listMargin);
        }

        // MENU
        if (menuOut) {
            shadow.draw();
            menu.slide();
            menu.draw();
            mainMenu.draw();
            fill(250);
            strokeWeight(1);
            stroke(200);
            rect(MENU_BUTTON_X, MENU_BUTTON_Y, menuButtonSize, menuButtonSize);
        } else {
            menu.close();
            menu.draw();
            fill(250);
            strokeWeight(1);
            stroke(200);
            rect(MENU_BUTTON_X, MENU_BUTTON_Y, menuButtonSize, menuButtonSize);
        }


    }


    public void mouseClicked() {

        if (menuButton.click(mouseX, mouseY)) {
            menuOut = !menuOut;
        }
    }

}