package Model;

import java.util.List;

/**
 * Created by sanczo on 2016-05-03.
 */
public class GraphicGenRepresentation {

    private Circle circle;

    private DrawString drawString;

    public GraphicGenRepresentation(Circle circle, DrawString drawString) {
        this.circle = circle;
        this.drawString = drawString;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public DrawString getDrawString() {
        return drawString;
    }

    public void setDrawString(DrawString drawString) {
        this.drawString = drawString;
    }
}
