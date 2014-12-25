package model;

/**
 * Created by user on 12/13/2014.
 */
public class Node {

    private final String id;

    public Node(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public synchronized String toString() {
        return id;
    }
}
