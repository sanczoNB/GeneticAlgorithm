package MyGraph;

/**
 * Created by sanczo on 2016-03-07.
 */
public class Edge {

    public Edge(Vertex firstVertex, Vertex secondVertex, int bandwich) {
        this.firstVertex = firstVertex;
        this.secondVertex = secondVertex;
        this.bandwidth = bandwich;
    }

    private Vertex firstVertex;

    private Vertex secondVertex;

    private int bandwidth;

    public int getBandwidth() {
        return bandwidth;
    }

    public Vertex getFirstVertex() {
        return firstVertex;
    }

    public Vertex getSecondVertex() {
        return secondVertex;
    }

    public boolean include(int vertexId) {

        return firstVertex.getId() == vertexId || secondVertex.getId() == vertexId;
    }
}
