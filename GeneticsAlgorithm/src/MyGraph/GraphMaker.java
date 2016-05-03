package MyGraph;

import Genetic.Individual;
import Helpers.TextFileReader;
import org.javatuples.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sanczo on 2016-03-08.
 */
public class GraphMaker {

    Graph graph;

    String filePath;

    private TextFileReader textFileReader;

    private Map<Integer, Integer> colorsNumbers;

    private int howManyColorsToFind;


    public GraphMaker(String filePath) {

        this.filePath = filePath;
        colorsNumbers = new HashMap<>();
        textFileReader = new TextFileReader(filePath);
    }

    public IGraphService getGraph()
    {
        if(graph == null)
            createGraph();
        return graph;
    }

    private void createGraph()
    {

        List<String> listOfColorsNumberForEachVertex = textFileReader.giveListOfColorsNumberForEachVertex();

        fillColorsNumberBaseOnStringList(listOfColorsNumberForEachVertex);

        List<String> rows = textFileReader.ReadFileAndGiveListOfRows();



        this.graph =new Graph();

        for(String row : rows)
        {
            EdgeParameters edgeParameters = GetEdegeParametrsFromLettering(row);
            addEdge(edgeParameters);

        }
        InformIndividualHowLongShouldBeGenotyp(howManyColorsToFind);

    }

    public void addEdge(EdgeParameters edgeParameters) {
        Vertex firstVertex = null;
        Vertex secondVertex = null;

        firstVertex = getVertex(edgeParameters.getFirstVertexId());


        if(IsLoopEdge(edgeParameters))
            firstVertex.setSelfBandwidth(edgeParameters.getBandwith());
        else {

            secondVertex = getVertex(edgeParameters.getSecondVertexId());

            firstVertex.addNeighbor(secondVertex);
            firstVertex.putNeighborBandwidth(secondVertex.getId(), edgeParameters.getBandwith());

            secondVertex.addNeighbor(firstVertex);
            secondVertex.putNeighborBandwidth(firstVertex.getId(), edgeParameters.getBandwith());

        }

    }

    private Vertex getVertex(int vertexId) {
        Vertex vertex;
        int indexOnTheVertexesList = graph.GetIndexOnTheVertexListByVertexId(vertexId);

        if (graph.isVertexAlreadyExist(indexOnTheVertexesList)) {
            vertex = graph.getVertexByIndex(indexOnTheVertexesList);
        } else {
            vertex = CreateVertex(vertexId);
            graph.addVertex(vertex);
        }
        return vertex;
    }

    private Vertex CreateVertex(int vertexId) {
        Vertex vertex;
        int colorsNumber = colorsNumbers.get(vertexId);
        vertex = new Vertex(vertexId, colorsNumber);
        return vertex;
    }

    private void fillColorsNumberBaseOnStringList(List<String> listOfColorsNumberForEachVertex) {
        for (String lettering : listOfColorsNumberForEachVertex)
        {
            Pair<Integer, Integer> vertexIdAndNumberOfColors = GetVertexIdAndColorNumberFromLettering(lettering);
            colorsNumbers.put(vertexIdAndNumberOfColors.getValue0(), vertexIdAndNumberOfColors.getValue1());
            howManyColorsToFind += vertexIdAndNumberOfColors.getValue1();
        }
    }

    private void InformIndividualHowLongShouldBeGenotyp(int lengthOfGenotyp) {
        Individual.setNumberOfGens(lengthOfGenotyp);
    }




    private EdgeParameters GetEdegeParametrsFromLettering(String row) {
        String[] arguments = row.split(" ");

        String[] notEmpty = selectNotNull(arguments);

        int firstVertexId = Integer.parseInt(notEmpty[1].trim());
        int secondVertexId = Integer.parseInt(notEmpty[2].trim());
        int bandwidth = Integer.parseInt(notEmpty[3].trim());

        return new EdgeParameters(firstVertexId, secondVertexId, bandwidth);
    }

    private Pair<Integer,Integer> GetVertexIdAndColorNumberFromLettering(String lettering)
    {
        String[] afterFirstSplit = lettering.split(" ");
        String[] arguments = selectNotNull(afterFirstSplit);

        int id = Integer.parseInt(arguments[1]);
        int colorNumber = Integer.parseInt(arguments[2]);

        return new Pair<>(id, colorNumber);
    }

    private String[] selectNotNull(String[] arguments) {
        String[] result = new String[4];
        int index =0;
        for (String s : arguments)
        {
            if (!s.trim().isEmpty()) {
                result[index] = s;
                index ++;
            }

        }
        return result;
    }


    private boolean IsLoopEdge(EdgeParameters edgeParameters)
    {
        return edgeParameters.getFirstVertexId() == edgeParameters.getSecondVertexId();
    }
}
