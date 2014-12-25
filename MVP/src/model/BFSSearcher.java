package model;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

public class BFSSearcher extends AbstractSearcher{
    private static final Integer INFINITY = Integer.MAX_VALUE;
    public Map<String, Boolean> markedNodes = new HashMap<String, Boolean>();
    public Map<Node, Node> edgeTo = new HashMap<Node, Node>();
    public Map<String, Integer> minDistances = new HashMap<String, Integer>();

    private GraphDomain graphDomain;

    /**
     * Computes the shortest path between the source vertex <tt>s</tt>
     * and every other vertex in the graph <tt>graphDomain</tt>.
     *
     * @param graphDomain the graph
     */
    public BFSSearcher(GraphDomain graphDomain) {
        this.graphDomain = graphDomain;
    }

    public List<Node> getShortestPath()
    {
        Node target = graphDomain.getTargetNode();
        List<Node> path = new ArrayList<Node>();
        Node currentNode = target;
        int timeToLive = 1000;
        while (currentNode != graphDomain.getStartNode()) {
            timeToLive--;
            path.add(edgeTo.get(currentNode));
            currentNode = edgeTo.get(currentNode);
            if (timeToLive == 0) {
                path.clear();
                return null;
            }
        }

        Collections.reverse(path);

        path.add(target);
        return path;
    }

    public List<Node> search() {
        findShortestPaths();
        return getShortestPath();
    }

    // breadth-first search from a single source
    public void findShortestPaths() {
        Node start = graphDomain.getStartNode();
        Queue<Node> q = new LinkedBlockingDeque<Node>();

        for (Node node : graphDomain.getNodeMap().values()) {
            minDistances.put(node.getId(), INFINITY);
        }

        minDistances.put(start.getId(), 0);

        markedNodes.put(start.getId(), true);
        q.add(start);

        while (!q.isEmpty()) {
            Node v = q.remove();
            for (AbstractEdge edge : graphDomain.getAdjacencies().get(v.getId())) {
                if (!Boolean.TRUE.equals(markedNodes.get(edge.getN2().getId()))) {
                    Node otherNode = edge.getN1() == v ? edge.getN2() : edge.getN1();
                    edgeTo.put(otherNode, v);
                    minDistances.put(otherNode.getId(), minDistances.get(v.getId()) + 1);
                    markedNodes.put(otherNode.getId(), true);
                    q.add(otherNode);
                }
            }
        }
    }

    /**
     * Is there a path between the source vertex <tt>s</tt> (or sources) and vertex <tt>v</tt>?
     *
     * @param v the vertex
     * @return <tt>true</tt> if there is a path, and <tt>false</tt> otherwise
     */
    public boolean hasPathTo(Node v) {
        return Boolean.TRUE.equals(markedNodes.get(v));
    }

    /**
     * Returns the number of edges in a shortest path between the source vertex <tt>s</tt>
     * (or sources) and vertex <tt>v</tt>?
     *
     * @param v the vertex
     * @return the number of edges in a shortest path
     */
    public int distTo(Node v) {
        return minDistances.get(v);
    }

    /**
     * Returns a shortest path between the source vertex <tt>s</tt> (or sources)
     * and <tt>v</tt>, or <tt>null</tt> if no such path.
     *
     * @param v the vertex
     * @return the sequence of vertices on a shortest path, as an Iterable
     */
    public Iterable<Node> pathTo(Node v) {
        if (!hasPathTo(v)) return null;
        Stack<Node> path = new Stack<Node>();
        Node x;
        for (x = v; minDistances.get(x) != 0; x = edgeTo.get(x))
            path.push(x);
        path.push(x);
        return path;
    }
}
