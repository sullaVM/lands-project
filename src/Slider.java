import processing.core.PApplet;

public class Slider {

    PApplet parent;

    // properties: Main line, highlight line, pin1 and pin2
    public static float SLIDER_HEIGHT = 20;

    float lineWidth;
    float highlightWidth;
    float lineX;
    float lineY;
    float highlightX;
    float highlightY;

    Pin start;
    Pin end;
    Pin focus;

    Slider(PApplet p, float lineWidth, float lineX, float lineY) {
        parent = p;
        this.lineWidth = lineWidth;
        this.lineX = lineX;
        this.lineY = lineY;
        highlightWidth = lineWidth;
        highlightX = lineX;
        highlightY = lineY;
        start = new Pin(parent, lineX, lineY, SLIDER_HEIGHT);
        end = new Pin(parent, lineX + lineWidth - SLIDER_HEIGHT, lineY, SLIDER_HEIGHT);
    }

    public void draw() {
        parent.fill(211, 208, 203);
        parent.rect(lineX, lineY, lineWidth, SLIDER_HEIGHT);
        start.draw();
        end.draw();
    }
    
    public void mouseOver(int mouseX, int mouseY){
    	if(mouseX >= start.xPos && mouseX <= start.xPos + start.pinWidthHeight && mouseY >= start.yPos && mouseY <= start.yPos + start.pinWidthHeight)
    		focus = start;
    	else if(mouseX >= end.xPos && mouseX <= end.xPos + end.pinWidthHeight && mouseY >= end.yPos && mouseY <= end.yPos + end.pinWidthHeight)
    		focus = end;
    	else
    		focus = null;
    }
    
    public void movePins(int mouseX, int mouseY){
    	if(focus != null)
    	{
    		if(mouseX >= lineX && mouseX <= lineX + lineWidth - SLIDER_HEIGHT)
        		focus.xPos = mouseX;
    		else if(mouseX < lineX)
    			focus.xPos = lineX;
    		else if(mouseX > lineX + lineWidth - SLIDER_HEIGHT)
    			focus.xPos = lineX + lineWidth - SLIDER_HEIGHT;
    	}
    }
}
