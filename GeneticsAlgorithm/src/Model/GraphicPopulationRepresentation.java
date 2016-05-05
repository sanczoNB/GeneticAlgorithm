package Model;

import Genetic.Individual;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by sanczo on 2016-05-03.
 */
public class GraphicPopulationRepresentation {

    private List<GraphicIndividualRepresentation> individuals;

    public GraphicPopulationRepresentation(){
        individuals = new LinkedList<>();
    }

    public GraphicPopulationRepresentation(List individuals)
    {
        this.individuals = individuals;
    }

    public void insertGraphicIndividualRepresentation(GraphicIndividualRepresentation graphicIndividualRepresentation){

        individuals.add(graphicIndividualRepresentation);

    }

    public int size() {
        return individuals.size();
    }

    public GraphicIndividualRepresentation getGraphicIndividualRepresentationAtPosition(int index){

        return individuals.get(index);

    }
}
