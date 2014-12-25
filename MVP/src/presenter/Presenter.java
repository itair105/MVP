package presenter;

import common.Command;
import common.Observable;
import common.Observer;
import common.Utils;
import model.Model;
import model.MyModel;
import view.MyConsoleView;
import view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 12/13/2014.
 */
public class Presenter implements Observer {
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
                model.selectDomain(param);
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
            command.doCommand(commandParam);
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
        view.start();
    }


}
