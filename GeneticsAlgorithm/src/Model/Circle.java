package Model;

/**
 * Created by sanczo on 2016-05-03.
 */
public class Circle {

    private double leftCornerX;

    private double leftCornerY;

    public Circle(double leftCornerX, double leftCornerY, double radius) {
        this.leftCornerX = leftCornerX;
        this.leftCornerY = leftCornerY;
        this.radius = radius;
    }

    private double radius;

    public double getLeftUpperCornerX() {
        return leftCornerX;
    }

    public void setLeftCornerX(double leftCornerX) {
        this.leftCornerX = leftCornerX;
    }

    public double getLeftUpperCornerY() {
        return leftCornerY;
    }

    public void setLeftCornerY(double leftCornerY) {
        this.leftCornerY = leftCornerY;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
