package MyGraph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sanczo on 2016-03-07.
 */
public class Vertex {

    private int Id;

   private int color;

    private List<Vertex> neighbors;

    public Vertex(int id) {
        neighbors = new LinkedList<>();
        Id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getId() {
        return Id;
    }

    public void addNeighbor(Vertex neighbor)
    {
        neighbors.add(neighbor);
    }
    public Vertex getNeighborOnPosition(int position)
    {
        return neighbors.get(position);
    }
    public int neighborNumber()
    {
        return neighbors.size();
    }

}
