package code;

import java.util.HashMap;

public class UndirectedWeightedGraph<V> extends BaseGraph<V> {

    /*
     * YOU CAN ADD ANY FIELDS AND ADDITIONAL METHODS AS YOU LIKE
     *
     */
    private HashMap<V, UndirectedVertex> vertices;

    public UndirectedWeightedGraph() {
        this.vertices = new HashMap<>();
    }

    @Override
    public String toString() {
        String tmp = "Undirected Weighted Graph";
        return tmp;
    }

    @Override
    public void insertVertex(V v) {
        vertices.putIfAbsent(v, new UndirectedVertex(v));
    }

    @Override
    public V removeVertex(V v) {
        if(vertices.get(v) != null){
            for(V incoming: this.incomingNeighbors(v)){
                vertices.get(incoming).getIncidenceEdges().remove(v);
            }
            vertices.remove(v);
            return v;
        }
        return null;
    }

    @Override
    public boolean areAdjacent(V v1, V v2) {
        return _areAdjacent(v1, v2) || _areAdjacent(v2,v1);
    }

    private boolean _areAdjacent(V v1, V v2){
        if(vertices.get(v1) == null)
            return false;
        return vertices.get(v1).getIncidenceEdges().get(v2) != null;
    }

    @Override
    public void insertEdge(V source, V target) {
        insertVertex(source);
        insertVertex(target);
        vertices.get(source).getIncidenceEdges().put(target, new Edge(1, source,target));
        vertices.get(target).getIncidenceEdges().put(source, new Edge(1,target,source));
    }

    @Override
    public void insertEdge(V source, V target, float weight) {
        insertVertex(source);
        insertVertex(target);
        vertices.get(source).getIncidenceEdges().put(target, new Edge(weight,source,target));
        vertices.get(target).getIncidenceEdges().put(source, new Edge(weight,target,source));
    }

    @Override
    public boolean removeEdge(V source, V target) {
        if(vertices.get(source) == null || vertices.get(target) == null){
            return false;
        }
        Edge result = vertices.get(source).getIncidenceEdges().remove(target);
        vertices.get(target).getIncidenceEdges().remove(source);
        return result != null;
    }

    @Override
    public float getEdgeWeight(V source, V target) {
        Edge result = vertices.get(source).getIncidenceEdges().get(target);
        if (result != null)
            return result.getWeight();
        else
            return Float.MAX_VALUE;
    }

    @Override
    public int numVertices() {
        return vertices.size();
    }

    @Override
    public Iterable<V> vertices() {
        return vertices.keySet();
    }

    @Override
    public int numEdges() {
        int sum = 0;
        for(V v: vertices.keySet()){
            sum += vertices.get(v).getIncidenceEdges().size();
        }
        return sum/2;
    }

    @Override
    public boolean isDirected() {
        return false;
    }

    @Override
    public boolean isWeighted() {
        return true;
    }

    @Override
    public int outDegree(V v) {
        try {
            return vertices.get(v).getIncidenceEdges().size();
        }catch (NullPointerException e){
            return -1;
        }
    }

    @Override
    public int inDegree(V v) {
        return this.outDegree(v);
    }

    @Override
    public Iterable<V> outgoingNeighbors(V v) {
        try {
            return vertices.get(v).getIncidenceEdges().keySet();
        }catch (NullPointerException e){
            return null;
        }
    }

    @Override
    public Iterable<V> incomingNeighbors(V v) {
        return this.outgoingNeighbors(v);
    }
}
