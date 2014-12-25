package model;

/**
 * Created by user on 12/13/2014.
 */
public final class WeightedEdge extends AbstractEdge {
    double weight;
    public WeightedEdge(Node n1, Node n2, double weight) {
        super(n1, n2);
        this.weight = weight;
    }

    public String toString() {
        return "Edge id: " + id + " n1: " + n1.getId() + " n2: " + n2.getId() + " weight: " + weight;
    }
}
