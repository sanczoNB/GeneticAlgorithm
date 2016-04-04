package MyGraph;

import Genetic.Colloring;
import Helpers.TextFileReader;
import org.javatuples.Pair;
import org.javatuples.Tuple;

import java.util.List;

/**
 * Created by sanczo on 2016-03-08.
 */
public class GraphFactory {

    Graph graph;

    String filePath;



    public GraphFactory(String filePath) {

        this.filePath = filePath;
    }

    public Graph getGraph()
    {
        if(graph == null)
            graph = createGraph();
        return graph;
    }

    private Graph createGraph()
    {
        List<String> rows = PossesListOfRows();

        Graph graph =new Graph();

        for(String row : rows)
        {
            EdgeParameters edgeParameters = GetEdegeParametrsFromLettering(row);

            if(IsLoopEdge(edgeParameters) == false)
            {
                graph.addEdge(edgeParameters);
            }
        }
        InformColloringShemaAboutVertexNumber(graph.NumberOfVertex());
        return graph;
    }

    private void InformColloringShemaAboutVertexNumber(int numberOfVertex) {
        Colloring.setNumberOfVertex(numberOfVertex);
    }


    private List<String> PossesListOfRows() {
        TextFileReader textFileReader = new TextFileReader(filePath);
        List<String> listOfRows = textFileReader.ReadFileAndGiveListOfRows();
        textFileReader.EndConnection();
        return listOfRows;
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
