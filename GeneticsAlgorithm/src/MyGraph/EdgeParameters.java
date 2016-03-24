package MyGraph;

/**
 * Created by sanczo on 2016-03-08.
 */
public class EdgeParameters {

    public EdgeParameters(int firstVertexId, int secondVertexId, int bandwith) {
        this.firstVertexId = firstVertexId;
        this.secondVertexId = secondVertexId;
        this.bandwith = bandwith;
    }

    private int firstVertexId;

    private int secondVertexId;

    private int bandwith;

    public int getFirstVertexId() {
        return firstVertexId;
    }

    public int getSecondVertexId() {
        return secondVertexId;
    }

    public int getBandwith() {
        return bandwith;
    }


}
