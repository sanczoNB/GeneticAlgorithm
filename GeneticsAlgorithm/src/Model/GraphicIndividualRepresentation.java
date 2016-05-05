package Model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by sanczo on 2016-05-04.
 */
public class GraphicIndividualRepresentation {

    private List<GraphicGenRepresentation> gens;

    public GraphicIndividualRepresentation(){

        gens = new LinkedList<>();

    }

    public void insertGraphicGenRepresentation(GraphicGenRepresentation graphicGenRepresentation){

        gens.add(graphicGenRepresentation);

    }

    public int gensLength() {
        return gens.size();
    }

    public GraphicGenRepresentation getGraphicGenRepresentationAtPosition(int index){

       return gens.get(index);

    }
}
