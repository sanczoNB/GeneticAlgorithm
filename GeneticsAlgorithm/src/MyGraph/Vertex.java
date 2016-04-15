package MyGraph;

import Genetic.AlgorithmParameters;

import java.util.*;

/**
 * Created by sanczo on 2016-03-07.
 */
public class Vertex {


    public static int[] allAllowedColors;

    private int Id;

   public Integer[] colors;

    public List<Vertex> neighbors;

    public Map<Integer, Integer> neighborsBandwidth;

    private int selfBandwidth;

    private List<Integer> availableColors;

    private Set<Integer> banedColors;

    static {
        int maxAllowedColor = AlgorithmParameters.getInstance().getMaxUsedColor();
        allAllowedColors = new int[maxAllowedColor];
        for(int i = 0; i < maxAllowedColor; i++)
        {
            allAllowedColors[i] = i+1;
        }
    }

    public Vertex(){
        neighbors = new LinkedList<>();
        neighborsBandwidth = new HashMap<>();
        availableColors = new ArrayList<>();
        banedColors = new HashSet<>();
    }

    public Vertex(int id, int colorsNumber) {
        this();
        this.Id = id;
        colors = new Integer[colorsNumber];

    }

    public Integer[] getColors() {
      return colors;
    }

    /*
    Wstawia w piersze wolne miejsce na tablicy
    kolorow wybrany kolor
     */
    public void setColor(int color) {
        int i;
        for (i = 0; colors[i] != null &&  i < colors.length ; i++)
        {

        }
        if (i < colors.length)
            colors[i] = color;
    }

    public void setColorAtPosition(int color, int position) {
        colors[position] = color;
    }

    public int getId() {
        return Id;
    }

    public int getSelfBandwidth() {
        return selfBandwidth;
    }

    public void addNeighbor(Vertex neighbor)
    {
        neighbors.add(neighbor);
    }

    public void putNeighborBandwidth(int neighborId,int bandwidth)
    {
        neighborsBandwidth.put(neighborId, bandwidth);
    }

    public Vertex getNeighborOnPosition(int position)
    {
        return neighbors.get(position);
    }

    public int neighborNumber()
    {
        return neighbors.size();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vertex)
        {
            Vertex other = (Vertex)obj;
            return this.Id == other.Id;
        }
        else return false;
    }

    public void setSelfBandwidth(int selfBandwidth) {
        this.selfBandwidth = selfBandwidth;
    }

    public int getFirstNotUsedColor()
    {
        int color = 1;
        while (banedColors.contains(color))
        {
            color++;
        }
        return color;
    }

    public int getNAvailableColor(int n)
    {
        if(availableColors.size() == 0)
            return -1;
        int searchIndex = (n-1)%availableColors.size();
       return availableColors.get(searchIndex);
    }

    public void banColorFromGivenInterval(int a, int b)
    {
        for(int banedColor = a; banedColor <= b ;banedColor++)
        {
            banedColors.add(banedColor);
        }
    }

    public void unavailableColorFromGivenInterval(int a, int b)
    {
        for(int banedColor = a; banedColor <= b ;banedColor++)
        {
            availableColors.remove((Integer)banedColor);
        }
    }

    public int getColorsNumber() {
        return colors.length;
    }

    @Override
    public String toString() {
        StringBuilder bob = new StringBuilder("<");
        bob.append(this.Id);
        bob.append("> {");
        for (int c : colors)
        {
            bob.append(c);
            bob.append(", ");
        }
        bob.delete(bob.length() -2, bob.length());
        bob.append("}");
        return bob.toString();
    }

    public void initializeAvailableColors(int allowedNumberOfColors) {
        availableColors.clear();
        for(int color = 1; color <= allowedNumberOfColors; color++)
        {
            availableColors.add(color);
        }
    }

    public void resetColor()
    {
        for (int i = 0;i < colors.length ;i++)
        {
            colors[i] = null;
        }
    }
}
