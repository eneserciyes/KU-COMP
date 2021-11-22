package code;

import given.iGraph;

import java.util.HashMap;
import java.util.Objects;

/*
 * A class given to you to handle common operations. 
 * Intentionally left empty for you to fill as you like.
 * You do not have to use this at all!
 */

public abstract class BaseGraph<V> implements iGraph<V>{

/*
 * Fill as you like!
 *   
 */
    protected class Edge {
        float weight;
        V destination;
        V origin;

        public Edge(float weight, V origin, V destination) {
            this.weight = weight;
            this.destination = destination;
            this.origin = origin;
        }

        public float getWeight() {
            return weight;
        }

        public void setWeight(float weight) {
            this.weight = weight;
        }

        public V getDestination() {
            return destination;
        }

        public void setDestination(V destination) {
            this.destination = destination;
        }

        public V getOrigin() {
            return origin;
        }

        public void setOrigin(V origin) {
            this.origin = origin;
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Float.compare(edge.weight, weight) == 0 &&
                Objects.equals(destination, edge.destination) &&
                Objects.equals(origin, edge.origin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, destination, origin);
    }
}

    protected class Vertex{
        V element;
        public Vertex(V element) {
            this.element = element;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vertex vertex = (Vertex) o;
            return Objects.equals(element, vertex.element);
        }

        @Override
        public int hashCode() {
            return Objects.hash(element);
        }
    }

    protected class UndirectedVertex extends Vertex{
        HashMap<V, Edge> incidenceEdges = new HashMap<>();
        public UndirectedVertex(V element) {
            super(element);
        }
        public HashMap<V, Edge> getIncidenceEdges(){
            return incidenceEdges;
        }
    }

    protected class DirectedVertex extends Vertex{
        HashMap<V,Edge> outgoingEdges = new HashMap<>();
        HashMap<V,Edge> incomingEdges = new HashMap<>();
        public DirectedVertex(V element) {
            super(element);
        }

        public HashMap<V, Edge> getOutgoingEdges() {
            return outgoingEdges;
        }

        public HashMap<V, Edge> getIncomingEdges() {
            return incomingEdges;
        }
    }

}
