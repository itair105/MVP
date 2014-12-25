package model;

import common.Observable;

import java.util.Map;

/**
 * Created by user on 12/13/2014.
 */
public interface Model extends Observable{
    Map<String, Solution> getSolutionMap();

    boolean isCalculationRunning();

    void selectDomain(String domainName);
    void selectAlgorithm(String algorithmName);
    void solveDomain();
    Solution getSolution();

    GraphDomain getDomain();

    void stopCalculating();
}
