package presenter;

import common.Command;
import common.Observable;
import common.Observer;
import common.Utils;
import model.Model;
import model.MyModel;
import model.Solution;
import view.MyConsoleView;
import view.View;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Presenter implements Observer {
    public static final String SOLUTION_FILE = "c:/solutions/solutions.txt";
    private Model model;
    private View view;
    Map<String, Command> stringCommandMap = new HashMap<String, Command>();

    public Presenter(final View view, final Model model) {
        this.view = view;
        this.model = model;

        stringCommandMap.put("algorithm", new Command() {
            @Override
            public void doCommand(String param) {
                model.selectAlgorithm(param);
            }
        });
        stringCommandMap.put("domain", new Command() {
            @Override
            public void doCommand(String param) {
                try {
                    model.selectDomain(param);
                } catch (IllegalArgumentException e) {
                    view.displayError(e.getMessage());
                }
            }
        });

        stringCommandMap.put("start", new Command() {
            @Override
            public void doCommand(String param) {
                model.solveDomain();
            }
        });

        stringCommandMap.put("displayState", new Command() {
            @Override
            public void doCommand(String param) {
                view.displayCurrentState(model.getDomain());
            }
        });

        stringCommandMap.put("is_calculating", new Command() {
            @Override
            public void doCommand(String param) {
                view.displayIsCalculating(model.isCalculationRunning());
            }
        });
    }

    @Override
    public void update(Observable observable) {
        if (observable == view) {
            String userAction = view.getUserAction();
            String commandType = userAction.substring(0, userAction.indexOf("=") > 0 ? userAction.indexOf("=") : userAction.length());
            String commandParam = "";
            if (userAction.indexOf("=") >= 0) {
                commandParam = userAction.substring(userAction.indexOf("=") + 1);
            }

            Command command = stringCommandMap.get(commandType);
            if (command != null) {
                command.doCommand(commandParam);
            } else {
                view.displayError("No such command");
            }
        } else  if (observable == model) {
            view.displaySolution(model.getSolution());
        }
    }

    public static void main( String [] args ) {
        View view = new MyConsoleView();
        Model model = new MyModel();

        Presenter presenter = new Presenter(view, model);
        view.addObserver(presenter);
        model.addObserver(presenter);

        presenter.loadSolutions();
        view.start();
        model.stopCalculating();
        presenter.saveSolutions();
    }

    private void loadSolutions() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(SOLUTION_FILE);
            while (true) {
                ObjectInputStream ois = new ObjectInputStream(fis);
                Solution solution = (Solution) ois.readObject();
                model.getSolutionMap().put(String.valueOf(solution.getProblem().hashCode()), solution);
            }
        } catch (EOFException ignored) {
            // as expected
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null)
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

    }

    private void saveSolutions() {

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(SOLUTION_FILE);
            for (Solution solution : model.getSolutionMap().values()) {
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(solution);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null)
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
