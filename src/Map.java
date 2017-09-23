import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.utils.ScreenPosition;
import processing.core.PApplet;
import processing.core.PShape;

import java.util.concurrent.ThreadLocalRandom;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Map {
    private static final int SCREEN_WIDTH = 1280;
    private static final int MARGIN = 50;
    private static final int MAP_HEIGHT = 750;
    private static final int MAP_WIDTH = 540;
    private static final int POP_WIDTH = 270;
    private static final int POP_HEIGHT = 150;
    private static final int SAMPLE_LIMIT = 30;

    private static String[] county, latitudeLongtitude;
    private static double[] latitude, longitude;
    private static UnfoldingMap map;
    private Location centre = new Location(54.75, -11.50);
    static PApplet parent;
    private PShape mapSVG;
    private float scaleVal = 1;
    private boolean hasInitialised = false;
    private static boolean isComplete = false;
    private Constants c = new Constants();

    private static List<LandData> randomLand;
    private static ArrayList<Location> locations;
    private static ArrayList<SimplePointMarker> markers;
    private static ArrayList<ScreenPosition> positions;
    private static ArrayList<Lightbox> popups;

    Map(PApplet p) {
        this.parent = p;
        In coordinates = new In("data/counties_coordinates.csv");
        latitudeLongtitude = coordinates.readAll().split("\\n");
        county = new String[latitudeLongtitude.length];
        latitude = new double[latitudeLongtitude.length];
        longitude = new double[latitudeLongtitude.length];
    }

    public void setup(PApplet p, Menu mainMenu) {
        map = new UnfoldingMap(p, "map");
//        MapUtils.createDefaultEventDispatcher(p, map);
        map.zoomToLevel(6);
        map.panTo(centre);
        map.setZoomRange(6, 7);
//        map.setPanningRestriction(centre, 1000);

        String year = "" + mainMenu.getCurrentYear();
        int yearToQuery = Character.getNumericValue(year.charAt(3));

        // SHAPE
        mapSVG = parent.loadShape("graphics/UK_Outline_and_Flag.svg");

        // QUERY / DATA
        for (int i = 0; i < latitudeLongtitude.length; i++) {
            String[] vectorInfo = latitudeLongtitude[i].split(",");
            county[i] = vectorInfo[0];
            latitude[i] = Double.parseDouble(vectorInfo[1]);
            longitude[i] = Double.parseDouble(vectorInfo[2]);
        }

        locations = new ArrayList<>();
        markers = new ArrayList<>();
        positions = new ArrayList<>();
        randomLand = new ArrayList<>();

        new Thread(() -> {
            List<LandData> land = new DBQuery(DBConstants.EMBEDDED_URL).fetchAllColumns(DBConstants.YEARLY_TABLE[yearToQuery],
                    DBConstants.DATA_COLUMNS[0], "d", 1000);
            setupDataMarkers(land);
            hasInitialised = true;
        }).start();
    }

    public void draw() {
        mapSVG.scale(scaleVal);
        parent.shape(mapSVG, SCREEN_WIDTH - MAP_WIDTH + 10, MARGIN - 25, MAP_WIDTH, MAP_HEIGHT);
        parent.noStroke();

        if (hasInitialised) {
            for (int i = 0; i < positions.size(); i++) {
                parent.fill(209, 70, 47, 100);
                parent.ellipse(positions.get(i).x, positions.get(i).y, 10, 10);
            }
            if (isComplete) {
                for (int i = 0; i < positions.size(); i++) {
                    if (positions.get(i) != null) {
                        if (markers.get(i).isInside(parent.mouseX, parent.mouseY, positions.get(i).x, positions.get(i).y)) {
                            parent.fill(c.RED.getRGB(), 255);
                            parent.ellipse(positions.get(i).x, positions.get(i).y, 10, 10);
                            popups.get(i).makePopup();
                            popups.get(i).draw();
                        }
                    }
                }
            }

            for (int i = 0; i < popups.size(); i++) {
                if (popups != null) {
                    if (popups.get(i).isFixed()) {
                        popups.get(i).draw();
                    }
                }
            }
        }
    }

    public void zoom(float x, float y) {
        if (hasInitialised) {
            parent.translate(x, y);
            if (scaleVal < 2) {
                scaleVal = scaleVal + (float) 0.1;
            } else {
                scaleVal = 2;
            }
        }
    }

    public void mouseClicked() {
        if (hasInitialised) {
            for (int i = 0; i < positions.size(); i++) {
                if (popups != null) {
                    if (markers.get(i).isInside(parent.mouseX, parent.mouseY, positions.get(i).x, positions.get(i).y)) {
                        popups.get(i).fix();
                    }
                }
            }
        }
    }

    public void mouseMoved(float mouseX, float mouseY) {
        if (hasInitialised) {
            for (int i = 0; i < popups.size(); i++) {
                if (!popups.get(i).isLocked) {
                    popups.get(i).mouseMoved(mouseX, mouseY);
                }
            }
        }
    }

    public void mousePressed() {
        if (hasInitialised) {
            for (int i = 0; i < popups.size(); i++) {
                if (popups != null) {
                    if (popups.get(i).getIsOver()) {
                        popups.get(i).mousePressed(parent.mouseX, parent.mouseY);
                    }
                }
            }
        }
    }

    public void mouseDragged() {
        if (hasInitialised) {
            for (int i = 0; i < positions.size(); i++) {
                if (popups != null) {
                    if (popups.get(i).isLocked) {
                        popups.get(i).mouseDragged(parent.mouseX, parent.mouseY);
                    }
                }
            }
        }
    }

    public void mouseReleased() {
        if (hasInitialised) {
            for (int i = 0; i < popups.size(); i++) {
                if (popups != null) {
                    popups.get(i).mouseReleased();
                }
            }
        }
    }

    public static void setupDataMarkers(List<LandData> land) {
        isComplete = false;
        int randomNumber;
        String[] sampleCounties = new String[SAMPLE_LIMIT];
        randomLand = new ArrayList<>();
        locations = new ArrayList<>();
        markers = new ArrayList<>();
        positions = new ArrayList<>();
        popups = new ArrayList<>();

        for (int i = 0; i < SAMPLE_LIMIT; i++) {
            randomNumber = ThreadLocalRandom.current().nextInt(0, land.size());
            sampleCounties[i] = land.get(randomNumber).getCounty();
            randomLand.add(land.get(randomNumber));
            Boolean found = false;
            int j = 0;
            while (!found && j < county.length) {
                if (sampleCounties[i].equals(county[j])) {
                    locations.add(new Location(latitude[j], longitude[j]));
                    found = true;
                }
                j++;
            }
        }
        for (int i = 0; i < locations.size(); i++) {
            markers.add(new SimplePointMarker(locations.get(i)));
            positions.add(markers.get(i).getScreenPosition(map));
            popups.add(new Lightbox(parent, positions.get(i).x - (POP_WIDTH / 2), positions.get(i).y - (POP_HEIGHT + 18),
                    POP_WIDTH, POP_HEIGHT, new Color(250, 250, 250), randomLand.get(i)));
        }
        isComplete = true;
    }
}