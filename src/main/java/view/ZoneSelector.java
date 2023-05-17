package view;

import controller.Controller;
import engine.guis.UiTab;
import engine.renderEngine.Loader;
import model.GameModel;

public class ZoneSelector extends Menu{

    private UiTab tab;
    private Loader loader = new Loader();


    public ZoneSelector(Controller controller, GameModel gameModel) {
        super(controller, gameModel);
    }

    @Override
    protected void loadComponents() {

    }

    @Override
    public void updateText() {

    }
}
