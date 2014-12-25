package view;

import common.Observable;
import model.GraphDomain;
import model.Solution;

public interface View extends Observable{
    void start();
    void displayCurrentState(GraphDomain domain);

    void displaySolution(Solution solution);
    String getUserAction();
}
