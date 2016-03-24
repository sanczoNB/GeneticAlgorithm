package MyGraph;

import Comperators.LessConflictedComparator;
import Genetic.Colloring;
import Helpers.TextFileWriter;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by sanczo on 2016-03-07.
 */
public class Graph {
    public List<Vertex> vertexes;

    public List<Edge> edges;

    private TextFileWriter writer;

    public Graph() {
        vertexes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void addEdge(EdgeParameters edgeParameters) {
        Vertex firstVertex = null;
        Vertex secondVertex = null;

        int indexOnTheVertexesList = GetIndexOnTheListByVertexId(edgeParameters.getFirstVertexId());

        if (isVertexAlreadyExist(indexOnTheVertexesList)) {
            firstVertex = getVertexByIndex(indexOnTheVertexesList);
        } else {
            firstVertex = new Vertex(edgeParameters.getFirstVertexId());
            addVertex(firstVertex);
        }

        indexOnTheVertexesList = GetIndexOnTheListByVertexId(edgeParameters.getSecondVertexId());

        if (isVertexAlreadyExist(indexOnTheVertexesList)) {
            secondVertex = getVertexByIndex(indexOnTheVertexesList);
        } else {
            secondVertex = new Vertex(edgeParameters.getSecondVertexId());
            addVertex(secondVertex);
        }

        //Dodaje wzajemnie do listy sasiadow
        secondVertex.addNeighbor(firstVertex);
        firstVertex.addNeighbor(secondVertex);
        edges.add(new Edge(firstVertex, secondVertex, edgeParameters.getBandwith()));

    }

    public int NumberOfVertex() {
        return vertexes.size();
    }

    public int getFitness(Colloring colorShema) {

        SetColoring(colorShema);
        return countFitness();
    }

    public void SetColoring(Colloring colorShema) {
        for (int i = 0; i < Colloring.getNumberOfVertex(); i++) {
            vertexes.get(i).setColor(colorShema.getColorOnPosition(i));
        }
    }

    public int getFitness() {

        return countFitness();
    }

    private int countFitness() {
      return countConflicts(this.edges);
    }

    private void addVertex(Vertex vertex) {
        vertexes.add(vertex);
    }

    private boolean isVertexAlreadyExist(int indexOnTheVertexesList) {

        return indexOnTheVertexesList != -1;
    }

    private Vertex getVertexByIndex(int indexOnTheVertexesList) {

        return vertexes.get(indexOnTheVertexesList);
    }

    private int GetIndexOnTheListByVertexId(long id) {
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
        List<Vertex> vertexInRandomOrder= new LinkedList<>(vertexes);
        Collections.shuffle(vertexInRandomOrder);
        GreedyColoring(Colloring.colorsNumber, vertexInRandomOrder);
        int[] colloring = new int[NumberOfVertex()];
        for(int i = 0; i < NumberOfVertex(); i++)
        {
            colloring[i] = vertexes.get(i).getColor();
        }
        return colloring;
    }


    public int GreedyColoring(int colorNumber)
    {
        return GreedyColoring(colorNumber, vertexes);
    }

     int GreedyColoring(int colorNumber, Collection<Vertex> edgeToColor)
    {
        TreeSet<Integer> usedColors = new TreeSet<>();

        ColorChoose[] colorChooses = new ColorChoose[colorNumber];

        InitiateColorChooses(colorChooses);

        SetVertexColorAsNotInitiate();

        for(Vertex currentVertex : edgeToColor)
        {
            List<Edge> currentVertexEdges = getEdgesIncludedSpecificVertex(currentVertex.getId());

            for (ColorChoose colorChoose : colorChooses)
            {
                currentVertex.setColor(colorChoose.getColor());
                int conflicts = countConflicts(currentVertexEdges);
                colorChoose.setConflict(conflicts);
            }

            ColorChoose lessConflicted = GetTheLeastConflictedColorChoose(colorChooses);

            currentVertex.setColor(lessConflicted.getColor());
            usedColors.add(lessConflicted.getColor());
        }
        return usedColors.last()+1;
    }

    private ColorChoose GetTheLeastConflictedColorChoose(ColorChoose[] colorChooses) {
        List<ColorChoose> listToSort = java.util.Arrays.asList(colorChooses);
        Collections.sort(listToSort);
        return colorChooses[0];
    }

    private void SetVertexColorAsNotInitiate() {
        for(Vertex vertex : vertexes)
        {
            vertex.setColor(-1);
        }
    }

    private void InitiateColorChooses(ColorChoose[] colorChooses) {
        for (int i = 0; i < colorChooses.length; i++)
        {
            colorChooses[i] = new ColorChoose(i);
        }
    }

    private int countConflicts(List<Edge> edges) {
        int fitness = 0;
        for (Edge edge : edges) {
            if(isInitiate(edge))
            {
                fitness += 0;
            }
            else {
                int firstColor = edge.getFirstVertex().getColor();
                int secondColor = edge.getSecondVertex().getColor();

                int colorsDifference = Math.abs(firstColor - secondColor);

                int bandwidthFitness = edge.getBandwidth() - colorsDifference;

                if (bandwidthFitness <= 0)
                    fitness += 0;
                else
                    fitness += bandwidthFitness;
            }
        }
        return fitness;
    }

    private boolean isInitiate(Edge edge) {
        return edge.getFirstVertex().getColor() == -1 || edge.getSecondVertex().getColor() == -1;
    }

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

    public void print()
    {
        writer.CreatePrintWriter();
        for(Vertex v : vertexes)
        {
            writer.writeColoring(v.getId(), v.getColor());
        }
        writer.FlushData();
        writer.EndConnection();
    }

    public void setWriter(TextFileWriter writer) {
        this.writer = writer;
    }

}
