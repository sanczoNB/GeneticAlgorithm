package Model;

/**
 * Created by sanczo on 2016-05-03.
 */
public class DrawString {

    private double leftUpperCornerX;

    private double leftUpperCornerY;

    private String content;

    public DrawString(double leftUpperCornerX, double leftUpperCornerY, String content) {
        this.leftUpperCornerX = leftUpperCornerX;
        this.leftUpperCornerY = leftUpperCornerY;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getLeftUpperCornerX() {
        return leftUpperCornerX;
    }

    public void setLeftUpperCornerX(double leftUpperCornerX) {
        this.leftUpperCornerX = leftUpperCornerX;
    }

    public double getLeftUpperCornerY() {
        return leftUpperCornerY;
    }

    public void setLeftUpperCornerY(double leftUpperCornerY) {
        this.leftUpperCornerY = leftUpperCornerY;
    }
}
