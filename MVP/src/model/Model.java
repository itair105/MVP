package model;

import common.Observable;

/**
 * Created by user on 12/13/2014.
 */
public interface Model extends Observable{
    void selectDomain(String domainName);
    void selectAlgorithm(String algorithmName);
    void solveDomain();
    Solution getSolution();

    GraphDomain getDomain();
}
