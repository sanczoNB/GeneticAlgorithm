package MyGraph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by sanczo on 2016-03-17.
 */
public class ColorChoose implements Comparable<ColorChoose>{

    private int color;

    private int conflict;

    public ColorChoose(int color) {
        this.color = color;
    }

    public ColorChoose(int color, int conflict) {
        this.color = color;
        this.conflict = conflict;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setConflict(int conflict) {
        this.conflict = conflict;
    }

    public int getConflict() {
        return conflict;
    }

    @Override
    public int compareTo(ColorChoose other) {

        int firstCompare = Integer.compare(this.conflict, other.conflict);
        if (firstCompare == 0) {
            return Integer.compare(this.color,other.color);
        }
        else
            return firstCompare;
    }
}
