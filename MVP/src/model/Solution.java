package model;

import java.util.List;

/**
 * Created by user on 12/13/2014.
 */
public class Solution {
    List<Node> path;

    public Solution(List<Node> path) {
        this.path = path;
    }

    public List<Node> getPath() {
        return path;
    }

    @Override
    public String toString() {
        if (path == null) {
            return "No Path found";
        }

        return "Solution{" +
                "path=" + path +
                '}';
    }
}
