import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class Graph extends PApplet {
    public static final int BAR = 0;
    public static final int TREND = 1;
    PApplet parent;
    private float xpos;
    private float ypos;
    private float height;
    private float width;

    public Bar[] getBarArray() {
        return barArray;
    }

    public void setBarArray(Bar[] barArray) {
        this.barArray = barArray;
    }

    private Bar[] barArray;

    public DataPoint[] getTrendArray() {
        return trendArray;
    }

    public void setTrendArray(DataPoint[] trendArray) {
        this.trendArray = trendArray;
    }

    private DataPoint[] trendArray;

    public int[] getIntDataToDisplay() {
        return intDataToDisplay;
    }

    public void setIntDataToDisplay(int[] intDataToDisplay) {
        this.intDataToDisplay = intDataToDisplay;
    }

    private int[] intDataToDisplay;
    private boolean trendFinished = false;
    private int maxValueOnChart;

    public String[] getSavedLabels() {
        return savedLabels;
    }

    public void setSavedLabels(String[] savedLabels) {
        this.savedLabels = savedLabels;
    }

    private String[] savedLabels;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    private boolean visible = true;
    private float originalHeight;
    private float originalWidth;
    private float originalXpos;
    private float originalYpos;
    private boolean clickable = true;
    int graphType = BAR;
    String label = "";
    String xAxisLabel = "PRICE RANGE: ";


    Graph(PApplet parent, float xpos, float ypos, float width, float height, int[] dataToDisplay) {
        this.parent = parent;
        this.xpos = xpos;
        this.ypos = ypos;
        this.originalXpos = xpos;
        this.originalYpos = ypos;
        this.height = height;
        this.width = width;
        this.originalHeight = height;
        this.originalWidth = width;
        this.intDataToDisplay = dataToDisplay;
        barArray = new Bar[dataToDisplay.length];
        this.trendArray = new DataPoint[dataToDisplay.length];
        int maxValue = 0;

        for (int i = 0; i < intDataToDisplay.length; i++) {
            if (intDataToDisplay[i] > maxValue) {
                maxValue = dataToDisplay[i];
            }
            maxValueOnChart = maxValue;
        }

        for (int i = 0; i < barArray.length; i++) {
            barArray[i] = new Bar(parent, xpos + 1 + ((width / barArray.length) * i), ypos - 1, height, width / barArray.length, maxValueOnChart,
                    (i == 0 ? "< £100000" : (i == 9 ? "> £550000" : "£" + (100000 + 50000 * (i) - 50000) + " - £" + (100000 + 50000 * (i + 1) - 50000))), this);
        }


        for (int i = 0; i < trendArray.length; i++) {
            trendArray[i] = new DataPoint(parent, xpos + ((width / trendArray.length) * i), ypos, maxValueOnChart, height, "house" + "  (" + intDataToDisplay + ")");
            trendArray[i].setHeight(intDataToDisplay[i]);
        }

        for (int i = 0; i < trendArray.length; i++) {
            if (i < trendArray.length - 1) {
                trendArray[i].setNextPointXandY(trendArray[i + 1].getXpos(), trendArray[i + 1].getYposOfPoint());
                trendArray[i].calculateSlope();
            }
        }
    }

    public void draw() {
        if (noDataToDisplay() && visible) {
            parent.fill(0);
            parent.textFont(Main.getLargeFont());
            parent.text("Sorry we were unable to find any results", xpos + 50, ypos + (height / 2));
        }
        parent.fill(0);
        parent.stroke(0);
        parent.strokeWeight(1);
        parent.line(xpos, ypos, xpos, ypos + height);
        parent.line(xpos, ypos + height, xpos + width, ypos + height);
        parent.textFont(Main.getMenuButtonFont());
        parent.textFont(Main.getLargeFont(), 12);
        parent.fill(Constants.BLACK.getRGB());
        parent.text("CURRENT RANGE: " + label, 20, Main.SCREEN_HEIGHT - 15);
        parent.textFont(Main.getMyFont());
        //write graduations
        if (visible) {
            fadeIn();
            for (int i = 1; i <= 10; i++) {
                parent.line(xpos - 10, (ypos + height) - ((height / 10) * i), xpos, (ypos + height) - ((height / 10) * i));
                if (!noDataToDisplay()) {
                    parent.text((maxValueOnChart / 10) * i, xpos - 75, (ypos + height) - ((height / 10) * i) + 5);
                }
            }
            parent.textFont(Main.getLargeFont(), 12);
            parent.fill(Constants.OLIVE.getRGB());
            parent.text(xAxisLabel, xpos, ypos + height + 20);
            parent.text("HOUSES SOLD", xpos - parent.textWidth("HOUSES SOLD"), ypos - 15);
        } else {
            fadeOut();
        }

        //draw bars or trend graph
        if (barArray != null) {
            for (int i = 0; i < barArray.length; i++) {
                barArray[i].draw();
                barArray[i].mouseOver(parent.mouseX, parent.mouseY);
            }
        }

        for (int i = 0; i < trendArray.length; i++) {
            trendArray[i].draw();
            trendArray[i].mouseOver(parent.mouseX, parent.mouseY);
        }
    }

    public boolean noDataToDisplay() {
        if (graphType != BAR) {
            return false;
        }
        if (barArray != null) {
            for (int i = 0; i < barArray.length; i++) {
                if (barArray[i].getNewHeight() != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public float getXpos() {
        return xpos;
    }

    public float getYpos() {
        return ypos;
    }

    public float getHeight() {
        return height;
    }

    public void displayRatingsOnGraph() {
        for (int i = 0; i < barArray.length; i++) {
            barArray[i].setHeight(intDataToDisplay[i]);
        }
    }

    public void transition() {
        transitionTrend();
        for (int i = 0; i < barArray.length; i++) {
            barArray[i].transition();
        }
    }

    public void displayData(int graphType) {
        int maxValue = 0;

        for (int i = 0; i < intDataToDisplay.length; i++) {
            if (intDataToDisplay[i] > maxValue) {
                maxValue = intDataToDisplay[i];
            }
        }

        maxValueOnChart = maxValue;
        setMaxHeightOfBars(maxValue);

        if (graphType == BAR) {
            displayDataOnBarGraph();
        } else if (graphType == TREND) {
            displayDataOnTrendGraph();
        }
    }

    public void displayDataOnTrendGraph() {
        graphType = TREND;
        if (barArray != null) {
            for (int i = 0; i < barArray.length; i++) {
                barArray[i].setHeight(0);
            }
        }

        trendArray = new DataPoint[intDataToDisplay.length];

        for (int i = 0; i < trendArray.length; i++) {
            trendArray[i] = new DataPoint(this.parent, this.xpos + ((this.width / trendArray.length) * i), ypos, this.maxValueOnChart, this.height, intDataToDisplay[i] + "  (" + (2010 + i) + ")");
            trendArray[i].setHeight(intDataToDisplay[i]);
        }

        for (int i = 0; i < trendArray.length; i++) {
            if (i < trendArray.length - 1) {
                trendArray[i].setNextPointXandY(trendArray[i + 1].getXpos(), trendArray[i + 1].getYposOfPoint());
                trendArray[i].calculateSlope();
            }
        }

        if (trendArray[0].getTestLine() == null) {
            trendArray[0].createLine();
        }
    }

    public void displayDataOnBarGraph() {
        graphType = BAR;
        if (barArray != null) {
            for (int i = 0; i < barArray.length; i++) {
                displayRatingsOnGraph();
            }
        }
        if (trendArray != null) {
            for (int i = 0; i < trendArray.length; i++) {
                trendArray[i].setRadius(0);
                if (trendArray[i].getTestLine() != null) {
                    trendArray[i].setTestLine(null);
                }
            }
        }
    }

    public void transitionTrend() {
        trendFinished = false;
        for (int i = 0; i < trendArray.length - 1; i++) {
            if (trendArray[i].getTestLine() != null) {
                if (trendArray[i].getTestLine().run >= trendArray[i].getNextPointX() && trendArray[i + 1].getTestLine() == null) {
                    trendArray[i + 1].createLine();
                } else if (!trendFinished && trendArray[trendArray.length - 2].getTestLine() != null &&
                        trendArray[trendArray.length - 2].getTestLine().run >= trendArray[trendArray.length - 2].getNextPointX()) {
                    for (int j = 0; j < trendArray.length; j++) {
                        trendArray[j].setRadius(20);
                    }
                    trendFinished = true;
                }
            }
            if (trendArray[i].getTestLine() != null) {
                if (trendArray[i].getTestLine().run < trendArray[i + 1].getXpos()) {
                    if (trendArray[i] != null) {
                        trendArray[i].getTestLine().run += (trendArray[i].getRun()) / 20;
                        trendArray[i].getTestLine().rise += (trendArray[i].getRise()) / 20;
                    }
                }
            }
        }
    }

    public void setMaxHeightOfBars(float graphMaxValue) {
        for (int i = 0; i < barArray.length; i++) {
            barArray[i].setMaxValue((int) graphMaxValue);
        }
    }

    public void displayAveragePriceTrend() {
        trendFinished = false;
        Main.setBackButton(null);
        this.xpos = originalXpos;
        this.ypos = originalYpos;
        this.height = originalHeight;
        this.width = originalWidth;
        int[] averageArray = new int[7];

        new Thread(() -> {
            List<Integer> averages = Main.getQueryInstance().fetchAveragePrice();

            for (int i = 0; i < 7; i++) {
                averageArray[i] = averages.get(i);
            }

            intDataToDisplay = averageArray;
            displayData(TREND);
        }).start();
    }

    public void displayHousesSold() {
        if (Main.getBackButton() != null) {
            Main.setBackButton(null);
        }
        this.xpos = originalXpos;
        this.ypos = originalYpos;
        this.height = originalHeight;
        this.width = originalWidth;
        trendFinished = false;
        Main.setBackButton(null);
        int[] housesSoldPerYear = new int[7];

        new Thread(() -> {
            List<Integer> housesSold = Main.getQueryInstance().fetchHousesSold();

            for (int i = 0; i < 7; i++) {
                housesSoldPerYear[i] = housesSold.get(i);
            }

            intDataToDisplay = housesSoldPerYear;
            this.height = originalHeight;
            this.width = originalWidth;
            if (this.height >= originalHeight) {
                displayData(TREND);
            }
        }).start();
    }

    public void fadeOut() {
        this.visible = false;
        if (trendArray != null) {
            for (int i = 0; i < trendArray.length; i++) {
                trendArray[i].setRadius(0);
                if (trendArray[i].getTestLine() != null) {
                    trendArray[i].setTestLine(null);
                }
            }
        }
        if (this.height > 0 || this.width > 0) {
            if (this.height > 0) {
                this.height--;
            }
            if (this.width > 0) {
                this.width--;
            }
            //fade bars
            this.ypos++;
            for (int i = 0; i < barArray.length; i++) {
                barArray[i].fadeOut(height, (width / barArray.length));
                barArray[i].setXpos(xpos + 1 + ((width / barArray.length) * i));
            }
        }
    }

    public void fadeIn() {
        visible = true;
        if (this.height < originalHeight || this.width < originalWidth) {
            if (this.height < originalHeight) {
                this.height++;
            }
            if (this.width < originalWidth) {
                this.width++;
            }
            //fade bars
            this.ypos--;
            if (graphType == BAR) {
                for (int i = 0; i < barArray.length; i++) {

                    barArray[i].fadeIn(height, (width / barArray.length));
                    barArray[i].setXpos(xpos + 1 + ((width / barArray.length) * i));
                    barArray[i].setMaxValue(maxValueOnChart);
                    barArray[i].setHeight(intDataToDisplay[i]);
                    barArray[i].transition();

                }

            } else if (graphType == TREND) {
                for (int i = 0; i < trendArray.length; i++) {
                    trendArray[i].updatePosition(height);
                }
            }
        }
    }

    public void makeClickable() {
        this.clickable = true;
    }

    public void makeUnClickable() {
        this.clickable = false;
    }

    public boolean isClickable() {
        return this.clickable;
    }

    public int[] yearDataQuery(int year) {
        this.label = "£0  -   £550,000";
        int columnToQuery = 0;
        Main.getMainGraph().makeClickable();
        switch (year) {
            case 2010:
                columnToQuery = 0;
                break;
            case 2011:
                columnToQuery = 1;
                break;
            case 2012:
                columnToQuery = 2;
                break;
            case 2013:
                columnToQuery = 3;
                break;
            case 2014:
                columnToQuery = 4;
                break;
            case 2015:
                columnToQuery = 5;
                break;
            case 2016:
                columnToQuery = 6;
                break;
            default:
                columnToQuery = 0;
        }
        int[] graphTestData = new int[10];
        String data = DBConstants.DATA_COLUMNS[0];
        String order = DBConstants.SORT_ORDERS[0];
        String where;
        int number = 100000;
        int limit = 0;
        int count;
        for (int i = 0; i < graphTestData.length; i++) {
            if (i == 0) {
                where = "where price < '" + number + "' ";
            } else if (i == graphTestData.length - 1) {
                where = "where price > '" + ((i * 50000) + number) + "' ";
            } else {
                where = "where price > '" + ((i * 50000) + number) + "' AND price < '" + (((i + 1) * 50000) + number) + "'";
            }
            List<String> dataList = Main.getQueryInstance().fetchColumn(DBConstants.YEARLY_TABLE[columnToQuery], data,
                    where, data, order, limit);
            count = dataList.size();
            graphTestData[i] = count;
        }

        if (this != null) {
            intDataToDisplay = graphTestData;
        }
        savedLabels = new String[barArray.length];
        for (int i = 0; i < barArray.length; i++) {
            barArray[i].setTag(i == 0 ? "< £100000" : (i == 9 ? "> £550000" : "£" + (100000 + 50000 * (i) - 50000) + " - £" + (100000 + 50000 * (i + 1) - 50000)));
            savedLabels[i] = barArray[i].getTag();

        }
        return graphTestData;
    }

    public int[] specificYearDataQuery(int barClicked) {
        int[] graphTestData = new int[10];
        String[] labels = new String[10];
        StringBuilder sb = new StringBuilder();
        String select = "select count(*) from data" + Main.getYearToQuery();
        label = "";

        for (int i = 0; i < graphTestData.length; i++) {
            if (barClicked == 0) {
                int number = 50000 * barClicked;
                label = "£0 - £100,000";
                sb.append(select + " where price > '" + ((i * 10000) + number) + "' AND price < '" +
                        (((i + 1) * 10000) + number) + "'");
                labels[i] = "£" + ((i * 10000) + number) + " - £" + (((i + 1) * 10000) + number);
            } else if (barClicked > 0 && barClicked < barArray.length - 1) {
                int number = 50000 * barClicked + 50000;
                label = "£" + Integer.toString(number / 1000) + ",000 - £" + Integer.toString((number + 50000) / 1000) + ",000";
                sb.append(select + " where price > '" + ((i * 5000) + number) + "' AND price < '" +
                        (((i + 1) * 5000) + number) + "'");
                labels[i] = "£" + ((i * 5000) + number) + " - £" + (((i + 1) * 5000) + number);
            } else if (barClicked == barArray.length - 1) {
                int number = 50000 * barClicked + 50000;
                this.label = "> £550,000";
                sb.append(select + " where price > '" + ((i * 100000) + number) + "' AND price < '" +
                        (((i + 1) * 100000) + number) + "'");
                labels[i] = "£" + ((i * 100000) + number) + " - £" + (((i + 1) * 100000) + number);
            }

            if (i != graphTestData.length - 1) {
                sb.append(" union ");
            }
        }

        List<Integer> dataList = new DBQuery(DBConstants.EMBEDDED_URL).fetchCustomScalarList(sb.toString());

        new Thread(() -> {
            String query = "select * from data" + Main.getYearToQuery() + " where price " + ((barClicked == 0) ?
                    " < 100000" : " > '" + (100000 + (barClicked - 1) * 50000) + "'" + " AND price < '" +
                    ((100000 + barClicked * 50000)) + "'");
            List<LandData> landList = new DBQuery(DBConstants.EMBEDDED_URL).fetchCustomAllColumns(query);
            Map.setupDataMarkers(landList);
        }).start();

        for (int i = 0; i < graphTestData.length; i++) {
            graphTestData[i] = dataList.get(i);
        }

        for (int i = 0; i < barArray.length; i++) {
            savedLabels[i] = barArray[i].getTag();
        }

        for (int i = 0; i < labels.length; i++) {
            if (barArray[i] != null) {
                barArray[i].setTag(labels[i]);
            }
        }

        if (this != null) {
            this.intDataToDisplay = graphTestData;
        }
        return graphTestData;
    }


    public void displayMenuData(String colParameter, int currentYear, int queryType) {
        if (Main.getBackButton() != null) {
            Main.setBackButton(null);
        }
        Main.getMainGraph().xAxisLabel = "PRICE RANGE: ";
        String year = "";
        String column = "";
        String where = colParameter.toUpperCase();
        List<Integer> dataList;
        int[] prices = new int[10];
        Main.getMainGraph().makeUnClickable();
        switch (currentYear) {
            case 2010:
                year = DBConstants.YEARLY_TABLE[0];
                break;
            case 2011:
                year = DBConstants.YEARLY_TABLE[1];
                break;
            case 2012:
                year = DBConstants.YEARLY_TABLE[2];
                break;
            case 2013:
                year = DBConstants.YEARLY_TABLE[3];
                break;
            case 2014:
                year = DBConstants.YEARLY_TABLE[4];
                break;
            case 2015:
                year = DBConstants.YEARLY_TABLE[5];
                break;
            case 2016:
                year = DBConstants.YEARLY_TABLE[6];
                break;
        }
        switch (queryType) {
            case Main.DATEOFSALE:
                column = DBConstants.DATA_COLUMNS[1];
                where += " 00:00";
                break;
            case Main.POSTCODE:
                column = DBConstants.DATA_COLUMNS[2];
                break;
            case Main.STREET:
                column = DBConstants.DATA_COLUMNS[6];
                break;
            case Main.TOWN:
                column = DBConstants.DATA_COLUMNS[8];
                break;
            case Main.DISTRICT:
                column = DBConstants.DATA_COLUMNS[9];
                break;
            case Main.COUNTY:
                column = DBConstants.DATA_COLUMNS[10];
        }

        dataList = getData(column, where, year);
        Main.setTitle("Houses sold in " + where + " in " + currentYear);

        for (int i = 0; i < 10; i++) {
            prices[i] = dataList.get(i).intValue();
        }

        this.intDataToDisplay = prices;
        displayData(BAR);
    }

    private List<Integer> getData(String column, String where, String year) {
        DBQuery queryObj = new DBQuery(DBConstants.EMBEDDED_URL);
        List<Integer> dataList = new ArrayList<>();

        dataList.add(queryObj.fetchCustomScalar("select count(*) from " + year + " where price < '100000' AND "
                + column + " = '" + where + "'"));

        for (int i = 100000; i <= 500000; i += 50000) {
            dataList.add(queryObj.fetchCustomScalar("select count(*) from " + year + " where price > '" +
                    i + "' AND price < '" + (i + 50000) + "' AND " + column + " = '" + where + "'"));
        }

        dataList.add(queryObj.fetchCustomScalar("select count(*) from " + year + " where price > '550000' AND " +
                column + " = '" + where + "'"));

        return dataList;
    }
}
