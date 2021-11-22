package code;

import java.util.HashMap;

public class DirectedUnweightedGraph<V> extends BaseGraph<V> {

    /*
     * YOU CAN ADD ANY FIELDS AND ADDITIONAL METHODS AS YOU LIKE
     *
     */
    private HashMap<V, DirectedVertex> vertices;

    public DirectedUnweightedGraph() {
        vertices = new HashMap<>();
    }

    @Override
    public String toString() {
        String tmp = "Directed Unweighted Graph";
        return tmp;
    }

    @Override
    public void insertVertex(V v) {
        vertices.putIfAbsent(v, new DirectedVertex(v));
    }

    @Override
    public V removeVertex(V v) {
        if(vertices.get(v) != null){
            for(V incoming: this.incomingNeighbors(v)){
                this.removeEdge(incoming,v);
            }
            for(V outgoing: this.outgoingNeighbors(v)){
                vertices.get(outgoing).getIncomingEdges().remove(v);
            }
            vertices.remove(v);
            return v;
        }
        return null;
    }

    @Override
    public boolean areAdjacent(V v1, V v2) {
        // TODO Auto-generated method stub
        if(vertices.get(v1) == null)
            return false;
        return vertices.get(v1).getOutgoingEdges().get(v2) != null;
    }

    @Override
    public void insertEdge(V source, V target) {
        insertVertex(source);
        insertVertex(target);
        vertices.get(source).getOutgoingEdges().putIfAbsent(target, new Edge(0,source,target));
        vertices.get(target).getIncomingEdges().putIfAbsent(source, new Edge(0,source,target));
    }

    @Override
    public void insertEdge(V source, V target, float weight) {
        insertEdge(source, target);
    }

    @Override
    public boolean removeEdge(V source, V target) {
        if(vertices.get(source) == null){
            return false;
        }
       if(vertices.get(source).getOutgoingEdges().get(target) != null){
           vertices.get(source).getOutgoingEdges().remove(target);
           vertices.get(target).getIncomingEdges().remove(source);
           return true;
       }
        return false;
    }

    @Override
    public float getEdgeWeight(V source, V target) {
        Edge result = vertices.get(source).getOutgoingEdges().get(target);
        if (result != null)
            return 1;
        else
            return 0;
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
            sum += vertices.get(v).getOutgoingEdges().size();
        }
        return sum;
    }

    @Override
    public boolean isDirected() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isWeighted() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int outDegree(V v) {
        // TODO Auto-generated method stub
        try {
            return vertices.get(v).getOutgoingEdges().size();
        }catch (NullPointerException e){
            return -1;
        }
    }

    @Override
    public int inDegree(V v) {
        // TODO Auto-generated method stub
        try{
            return vertices.get(v).getIncomingEdges().size();
        }catch (NullPointerException e){
            return -1;
        }
    }

    @Override
    public Iterable<V> outgoingNeighbors(V v) {
        // TODO Auto-generated method stub
        try{
            return vertices.get(v).getOutgoingEdges().keySet();
        }catch (NullPointerException e){
            return null;
        }
    }

    @Override
    public Iterable<V> incomingNeighbors(V v) {
        // TODO Auto-generated method stub
        try{
            return vertices.get(v).getIncomingEdges().keySet();
        }catch (NullPointerException e){
            return null;
        }
    }
}
