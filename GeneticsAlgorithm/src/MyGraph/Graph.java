package MyGraph;

import Genetic.AlgorithmParameters;
import Genetic.Individual;
import Helpers.TextFileWriter;
import org.javatuples.Pair;

import java.util.*;

/**
 * Created by sanczo on 2016-03-07.
 */
public class Graph implements IGraphService{

    public List<Vertex> vertexes;

    public List<Edge> edges;

    private TextFileWriter writer;

    public Graph() {
        vertexes = new ArrayList<>();
        edges = new ArrayList<>();
    }



    public int NumberOfVertex() {
        return vertexes.size();
    }

    public int getFitness(Individual colorShema) {

        /*SetColoring(colorShema);
        return countAverage();*/
        return -1;
    }

    public void SetColoring(Individual colorShema) {
       /* for (int i = 0; i < Individual.getNumberOfGens(); i++) {
            vertexes.get(i).setColors(colorShema.getColorOnPosition(i));
        }*/
    }



    public int getFitness() {

        //return countAverage();
        return -1;
    }

    /*private int countAverage() {
      return countConflicts(this.edges);
    }*/

    void addVertex(Vertex vertex) {
        vertexes.add(vertex);
    }

     boolean isVertexAlreadyExist(int indexOnTheVertexesList) {

        return indexOnTheVertexesList != -1;
    }

     Vertex getVertexByIndex(int indexOnTheVertexesList) {

        return vertexes.get(indexOnTheVertexesList);
    }

     int GetIndexOnTheVertexListByVertexId(long id) {
        int index = vertexes.size() - 1;
        while (index >= 0 && vertexes.get(index).getId() != id) {
            index--;
        }
        return index;
    }

    public int NumberOfEdges() {
        return edges.size();
    }


    public int[] giveColloringByGreedy()
    {
       /* List<Vertex> vertexInRandomOrder= new LinkedList<>(vertexes);
        Collections.shuffle(vertexInRandomOrder);
        GreedyColoring(Individual.colorsNumber, vertexInRandomOrder);
        int[] colloring = new int[NumberOfVertex()];
        for(int i = 0; i < NumberOfVertex(); i++)
        {
            colloring[i] = vertexes.get(i).getColors();
        }
        return colloring;*/
        return null;
    }



    public int GreedyColoring()
    {
        this.resetColors();
        clearBanedColors();

        int maxUsedColor = -1;

        for(Vertex vertex : vertexes )
        {
            for(int i = 0; i < vertex.getColorsNumber(); i++)
            {
                int color = vertex.getFirstNotUsedColor();
                vertex.addColor(color);
                if (color > maxUsedColor)
                    maxUsedColor = color;

                Pair<Integer, Integer> intervalToBan = computeIntervalToBan(color, vertex.getSelfBandwidth());
                vertex.banColorFromGivenInterval(intervalToBan.getValue0(), intervalToBan.getValue1());

                for(Vertex neighbor : vertex.neighbors)
                {
                    if(neighbor.isTotalyColored() == false) {
                        intervalToBan = computeIntervalToBan(color, vertex.neighborsBandwidth.get(neighbor.getId()));
                        neighbor.banColorFromGivenInterval(intervalToBan.getValue0(), intervalToBan.getValue1());
                    }
                }
            }
        }


        return maxUsedColor;
    }

    private void clearBanedColors() {
        vertexes.forEach(MyGraph.Vertex::clearBanedColors);
    }

    public int ColorGraph(int[] individual)
    {
        this.resetColors();
        initializeAvailableColors(AlgorithmParameters.getInstance().getMaxUsedColor());
        int maxUsedColor = -1;
        int index = 0;
        boolean wrongColoring = false;

        for(Vertex vertex : vertexes )
        {
            for(int i = 0; i < vertex.getColorsNumber() && wrongColoring == false; i++)
            {
                int color = vertex.getNAvailableColor(individual[index]);
                if (color != -1) {
                    //vertex.addColor(color);
                    vertex.addColor(color);
                    if (color > maxUsedColor)
                        maxUsedColor = color;

                    Pair<Integer, Integer> intervalToBan = computeIntervalToBan(color, vertex.getSelfBandwidth());
                    vertex.unavailableColorFromGivenInterval(intervalToBan.getValue0(), intervalToBan.getValue1());

                    for (Vertex neighbor : vertex.neighbors) {
                        if(neighbor.isTotalyColored() == false) {
                            intervalToBan = computeIntervalToBan(color, vertex.neighborsBandwidth.get(neighbor.getId()));
                            neighbor.unavailableColorFromGivenInterval(intervalToBan.getValue0(), intervalToBan.getValue1());
                        }
                    }
                    index++;
                    vertex.resetColor();
                }
                else {
                    wrongColoring = true;
                }
            }
            if (wrongColoring)
                break;
        }
        if (wrongColoring)
            maxUsedColor = AlgorithmParameters.getInstance().getMaxUsedColor()+1;
        return maxUsedColor;
    }

    private void initializeAvailableColors(int allowedNumberOfColors) {
        for(Vertex vertex : vertexes)
        {
            vertex.initializeAvailableColors(allowedNumberOfColors);
        }
    }

    private Pair<Integer, Integer> computeIntervalToBan(int color, int bandwidth) {
        int a = Math.max(0, color - bandwidth +1);
        int b = color + bandwidth -1;
        return new Pair<>(a,b);
    }

    private ColorChoose GetTheLeastConflictedColorChoose(ColorChoose[] colorChooses) {
        List<ColorChoose> listToSort = java.util.Arrays.asList(colorChooses);
        Collections.sort(listToSort);
        return colorChooses[0];
    }

   /* private void SetVertexColorAsNotInitiate() {
        for(Vertex vertex : vertexes)
        {
            vertex.setColors(-1);
        }
    }*/

    private void InitiateColorChooses(ColorChoose[] colorChooses) {
        for (int i = 0; i < colorChooses.length; i++)
        {
            colorChooses[i] = new ColorChoose(i);
        }
    }

   /* private int countConflicts(List<Edge> edges) {
        int fitness = 0;
        for (Edge edge : edges) {
            if(isInitiate(edge))
            {
                fitness += 0;
            }
            else {
                int firstColor = edge.getFirstVertex().getColors();
                int secondColor = edge.getSecondVertex().getColors();

                int colorsDifference = Math.abs(firstColor - secondColor);

                int bandwidthFitness = edge.getBandwidth() - colorsDifference;

                if (bandwidthFitness <= 0)
                    fitness += 0;
                else
                    fitness += bandwidthFitness;
            }
        }
        return fitness;
    }*/

    /*private boolean isInitiate(Edge edge) {
        return edge.getFirstVertex().getColors() == -1 || edge.getSecondVertex().getColors() == -1;
    }*/

    private List<Edge> getEdgesIncludedSpecificVertex(int vertexId)
    {
        ArrayList<Edge> results = new ArrayList<>();
        for (Edge e : edges)
        {
            if (e.include(vertexId))
                results.add(e);
        }
        return results;
    }

    public void Print()
    {
        for(Vertex v : vertexes)
        {
            for (Integer c : v.colors)
            {
                writer.writeColoring(v.getId(), c);
            }
        }
        writer.FlushData();
        writer.EndConnection();
    }

    @Override
    public String toString() {
        StringBuilder bob = new StringBuilder();
        for(Vertex v : vertexes)
        {
            bob.append(v.toString());
            bob.append("\n");
        }
        return bob.toString();
    }

    public void setWriter(TextFileWriter writer) {
        this.writer = writer;
    }

    public void resetColors()
    {
        for(Vertex v : vertexes)
        {
            v.resetColor();
        }
    }

    public void sortVertexById()
    {
        vertexes.stream().forEach(v -> v.setPriority(v.getId()));

        Collections.sort(vertexes);
    }

    public void sortVertexByDegreeAscent(){
        vertexes.stream().forEach(v -> v.setPriority(v.neighborNumber()));

        Collections.sort(vertexes);
    }

    public void sortVertexByDegreeDescent() {
        vertexes.stream().forEach(v -> v.setPriority(v.neighborNumber()));

        Collections.sort(vertexes, Collections.reverseOrder());
    }

    public void shuffleVertex() {
        Collections.shuffle(vertexes);
    }

    public void orderVertexBy(VertexOrder order) {
            switch (order){

                case ById:
                    sortVertexById();
                    break;
                case ByDegreeAscent:
                    sortVertexByDegreeAscent();
                    break;
                case ByDegreeDescent:
                    sortVertexByDegreeDescent();
                    break;
                case ByRandom:
                    shuffleVertex();
                    break;
            }
    }

}
