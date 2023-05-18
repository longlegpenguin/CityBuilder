package view;

import controller.Controller;
import engine.guis.UiTab;
import engine.renderEngine.Loader;
import model.GameModel;
import model.zone.Zone;
import org.joml.Vector2f;

public class ZoneSelector extends Menu{

    private UiTab tab;
    private String tabTexture = "Test";
    private Loader loader = new Loader();


    public ZoneSelector(Controller controller, GameModel gameModel) {
        super(controller, gameModel);
        loadComponents();
    }

    @Override
    protected void loadComponents() {
        tab = new UiTab(loader.loadTexture(tabTexture), new Vector2f(0.8f, 0.05f), new Vector2f(0.2f, 0.7f));
        super.tabs.add(tab);
    }

    @Override
    public void updateText() {

    }
}
