package MyGraph;

/**
 * Created by sanczo on 2016-04-14.
 */
public interface IGraphService {
    int ColorGraph(int[] individual);
    void Print();
    int GreedyColoring();
    void orderVertexBy(VertexOrder order);
    void establishVertexPriority(int[] priorities);

}
