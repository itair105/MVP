package model;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class DijkstraSearcher extends AbstractSearcher
{
    private WeightGraphDomain domain;
    public Map<String, Node> previousNodeMap = new HashMap<String, Node>();
    public Map<String, Double> minDistances = new HashMap<String, Double>();

    public void computePaths()
    {
        Node source = domain.getStartNode();
        minDistances.put(source.getId(), 0.);
        LinkedBlockingQueue<Node> nodeQueue = new LinkedBlockingQueue<Node>();
        nodeQueue.add(source);

        while (!nodeQueue.isEmpty()) {
            Node node = nodeQueue.poll();

            // Visit each edge exiting u
            for (WeightedEdge e : domain.adjacencies.get(node.getId()))
            {
                Node v = e.getN2();
                double weight = e.weight;
                double distanceThroughU = minDistances.get(node.getId()) + weight;
                if (distanceThroughU < minDistances.get(v.getId())) {
                    nodeQueue.remove(v);
                    minDistances.put(v.getId(), distanceThroughU) ;
                    previousNodeMap.put(v.getId(), node);
                    nodeQueue.add(v);
                }
            }
        }
    }

    public List<Node> getShortestPath()
    {
        Node target = domain.getTargetNode();
        List<Node> path = new ArrayList<Node>();
        for (Node node = target; node != null; node = previousNodeMap.get(node.getId()))
            path.add(node);
        Collections.reverse(path);
        return path;
    }

    public DijkstraSearcher(WeightGraphDomain domain) {
        this.domain = domain;
        for (String s : domain.getNodeMap().keySet()) {
            minDistances.put(s, Double.POSITIVE_INFINITY);
        }

    }

    public List<Node> search() {
        previousNodeMap.clear();
        minDistances.clear();
        computePaths();
        return getShortestPath();
    }

    @Override
    public void setDomain(GraphDomain domain) {
        this.domain = (WeightGraphDomain)domain;
    }
}

