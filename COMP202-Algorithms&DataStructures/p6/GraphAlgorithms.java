package code;

import java.util.*;

/*
 * The class that will hold your graph algorithm implementations
 * Implement:
 * - Depth first search
 * - Breadth first search
 * - Dijkstra's single-source all-destinations shortest path algorithm
 *
 * Feel free to add any addition methods and fields as you like
 */
public class GraphAlgorithms<V extends Comparable<V>> {

    /*
     * YOU CAN ADD ANY FIELDS AND ADDITIONAL METHODS AS YOU LIKE
     *
     */
    private static class CycleFoundException extends Exception{
    }



    public static boolean usageCheck = false;

    /*
     * WARNING: MUST USE THIS FUNCTION TO SORT THE
     * NEIGHBORS (the adjacent call in the pseudocodes)
     * FOR DFS AND BFS
     *
     * THIS IS DONE TO MAKE AUTOGRADING EASIER
     */
    public Iterable<V> iterableToSortedIterable(Iterable<V> inIterable) {
        usageCheck = true;
        List<V> sorted = new ArrayList<>();
        for (V i : inIterable) {
            sorted.add(i);
        }
        Collections.sort(sorted);
        return sorted;
    }

    /*
     * Runs depth first search on the given graph G and
     * returns a list of vertices in the visited order,
     * starting from the startvertex.
     *
     */
    public List<V> DFS(BaseGraph<V> G, V startVertex) {
        usageCheck = false;
        //TODO
        return iterativeDFS(G,startVertex,new HashSet<>());
    }

    private List<V> iterativeDFS(BaseGraph<V> G, V startVertex, HashSet<V> visited){
        Stack<V> s = new Stack<>();
        LinkedList<V> traversal = new LinkedList<>();

        s.push(startVertex);
        while(!s.isEmpty()){
            V u = s.pop();
            if (!visited.contains(u)){
                visited.add(u);
                traversal.addLast(u);

                for(V w: iterableToSortedIterable(G.outgoingNeighbors(u))){
                    if(!visited.contains(w)){
                        s.push(w);
                    }
                }
            }

        }
        return traversal;
    }


    /*
     * Runs breadth first search on the given graph G and
     * returns a list of vertices in the visited order,
     * starting from the startvertex.
     *
     */
    public List<V> BFS(BaseGraph<V> G, V startVertex) {
        usageCheck = false;
        //TODO
        return iterativeBFS(G, startVertex, new HashSet<>());
    }

    private List<V> iterativeBFS(BaseGraph<V> G, V startVertex, HashSet<V> visited){
        LinkedList<V> q = new LinkedList<>();
        LinkedList<V> traversal = new LinkedList<>();

        q.push(startVertex);
        while (!q.isEmpty()){
            V u = q.pollFirst();
            if (!visited.contains(u)){
                visited.add(u);
                traversal.addLast(u);
                Iterable<V> neighbours = iterableToSortedIterable(G.outgoingNeighbors(u));
                for (V w: neighbours){
                    if(!visited.contains(w)){
                        q.addLast(w);
                    }
                }
            }
        }
        return traversal;
    }

    /*
     * Runs Dijkstras single source all-destinations shortest path
     * algorithm on the given graph G and returns a map of vertices
     * and their associated minimum costs, starting from the startvertex.
     *
     */
    public HashMap<V,Float> Dijkstras(BaseGraph<V> G, V startVertex) {
        usageCheck = false;
        //TODO
        return _Dijkstras(G,startVertex,new HashSet<>());
    }

    public HashMap<V, Float> _Dijkstras(BaseGraph<V> G, V startVertex, HashSet<V> visited){
        HashMap<V, Float> costs = new HashMap<>();
        for (V v: G.outgoingNeighbors(startVertex)){
            costs.put(v, Float.MAX_VALUE);
        }
        costs.put(startVertex,0f);
        PriorityQueue<V> pq = new PriorityQueue<V>(Comparator.comparing(e->costs.get(e)));
        pq.add(startVertex);
        while(!pq.isEmpty()){
            V u = pq.poll();
            if (!visited.contains(u)){
                visited.add(u);
                for(V w: G.outgoingNeighbors(u)){
                    if(!costs.containsKey(w))
                        costs.put(w, Float.MAX_VALUE);
                    if (!visited.contains(w) && costs.get(w) >
                            (costs.get(u) + G.getEdgeWeight(u,w))){
                        costs.put(w, costs.get(u) + G.getEdgeWeight(u,w));
                        pq.add(w);
                    }
                }
            }
        }

        return costs;
    }

    /*
     *  Returns true if the given graph is cyclic, false otherwise
     */


    public boolean isCyclic(BaseGraph<V> G) {
        V startVertex;
        HashSet<V> visited = new HashSet<>();
        for(V v: G.vertices()){
            if(!visited.contains(v)){
                startVertex = v;
                try{
                    visited.addAll(DFSCyclicDetection(G, startVertex, startVertex, new HashSet<>(), new HashSet<>()));
                }catch (CycleFoundException e) {
                    return true;
                }
            }
        }

        return false;
    }

    private HashSet<V> DFSCyclicDetection(BaseGraph<V> G, V v, V parent, HashSet<V> visited, HashSet<V> marked) throws CycleFoundException{
        if(marked.contains(v))
            throw new CycleFoundException();
        else
            marked.add(v);
        for(V u : iterableToSortedIterable(G.outgoingNeighbors(v))){
            if(!u.equals(parent) && !visited.contains(u)){
                DFSCyclicDetection(G,u,v,visited,marked);
            }
        }
        visited.add(v);
        marked.remove(v);
        return visited;
    }

}
