import processing.core.PApplet;
import processing.core.PFont;

public class Bar extends PApplet {
    private PApplet parent;
    private float xpos;
    private float ypos;
    private float height;
    private float width;
    private float newHeight = 0;
    private int maxValue;
    private float maxHeight;
    private String tag = "";
    private PFont largeFont = Main.getLargeFont();
    private int color;
    private Graph mainGraph;
    private Constants c = new Constants();
    private int value = 0;

    Bar(PApplet parent, float xpos, float ypos, float maxHeight, float width, int maxValue, String tag, Graph mainGraph) {
        this.parent = parent;
        this.height = 0;
        this.maxHeight = maxHeight;
        this.width = width;
        this.xpos = xpos;
        this.ypos = ypos + maxHeight;
        this.maxValue = maxValue;
        this.tag = tag;
        this.mainGraph = mainGraph;
    }

    public void draw() {
        parent.fill(color);
        parent.stroke(255);
        parent.rect(xpos, ypos, width, height);
    }

    public void setHeight(int newValue) {
        newHeight = Math.round(((float) newValue / (float) this.maxValue) * maxHeight);
        this.value = newValue;
    }

    public void transition() {
    	
        if (height > newHeight) {
            height--;
            ypos++;
        } else if (height < newHeight) {
            height++;
            ypos--;
        }
        if (height < 100) {
            color = c.SILVER.getRGB();
        } else if (height >= 100 && height < 200) {
            color = c.TAUPE.getRGB();
        } else if (height >= 200 && height < 300) {
            color = c.RIFLE.getRGB();
        } else if (height >= 300 && height < 400) {
            color = c.OLIVE.getRGB();
        } else {
            color = c.BLACK.getRGB();
        }

    }

    public boolean mouseOver(float xMouse, float yMouse) {
        if (yMouse >= ypos && yMouse <= ypos + height
                && xMouse >= xpos && xMouse <= xpos + width) {
            parent.textFont(largeFont, 16);
            parent.text(tag, mainGraph.getXpos(), mainGraph.getYpos() + mainGraph.getHeight() + 40);
            parent.textFont(largeFont, 12);
            parent.text("HOUSE COUNT: "+ this.value, mainGraph.getXpos(), mainGraph.getYpos() + mainGraph.getHeight() + 55);
            return true;
        }
        return false;
    }

    public void fadeOut(float height, float width) {
        maxHeight = height;
        setHeight((int) newHeight);
        this.width = width;
    }
    public void fadeIn(float height, float width) {
        maxHeight = height;
        setHeight((int) newHeight);
        this.width = width;
        transition();
    }
    public void setXpos(float xpos) {
        this.xpos = xpos;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
    public float getNewHeight()
    {
    	return this.newHeight;
    }
}
