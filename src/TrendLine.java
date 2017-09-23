import processing.core.PApplet;

public class TrendLine {
    PApplet parent;
    float xpos;
    float ypos;
    float run;
    float rise;

    public TrendLine(PApplet parent, float xpos, float ypos) {
        this.parent = parent;
        this.xpos = xpos;
        this.run = xpos;
        this.rise = ypos;
        this.ypos = ypos;
    }

    public void draw() {
        parent.fill(0);
        parent.strokeWeight(7);
        parent.stroke(Constants.RED.getRGB());
        parent.line(xpos, ypos, run, rise);
        parent.fill(255);
        parent.strokeWeight(1);
        parent.stroke(255);
        parent.line(xpos, ypos, run, rise);
    }
    
    

}

