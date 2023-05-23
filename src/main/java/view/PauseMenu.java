package view;

import controller.Controller;
import engine.fontMeshCreator.GUIText;
import engine.guis.UiButton;
import engine.guis.UiTab;
import engine.renderEngine.Loader;
import model.GameModel;
import org.joml.Vector2f;

public class PauseMenu extends Menu{
    private Loader loader = new Loader();
    private UiTab tab;
    private GUIText resume;
    private GUIText leaveGame;
    private GUIText exitGame;
    private String tabTexture = "Test";

    public PauseMenu(Controller controller, GameModel gameModel) {
        super(controller, gameModel);
        loadComponents();
    }

    @Override
    protected void loadComponents() {
        tab = new UiTab(loader.loadTexture(tabTexture),new Vector2f(0f,0f),new Vector2f(0.5f,0.5f));
        super.tabs.add(tab);

    }
    

    @Override
    public void updateText() {

    }
}
