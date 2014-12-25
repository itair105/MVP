package model;

import java.util.List;

/**
 * Created by user on 12/13/2014.
 */
public abstract class AbstractSearcher {
    public abstract List<Node> search();

    public abstract void setDomain(GraphDomain domain);
}
