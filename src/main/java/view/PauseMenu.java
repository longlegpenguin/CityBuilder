package view;

import controller.Controller;
import engine.fontMeshCreator.GUIText;
import engine.fontRendering.TextMaster;
import engine.guis.UiButton;
import engine.guis.UiTab;
import engine.renderEngine.Loader;
import model.GameModel;
import org.joml.Vector2f;

public class PauseMenu extends Menu{
    private Loader loader = new Loader();
    private UiTab tab;
    private GUIText menuTitle;
    private GUIText resume;
    private GUIText saveGame;
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
        resume   = new GUIText("Resume Game",1,new Vector2f(0f,0.4f),1,true);
        saveGame = new GUIText("Save Game",1,new Vector2f(0f,0.5f),1,true);
        exitGame = new GUIText("Exit",1,new Vector2f(0f,0.6f),1,true);
        TextMaster.loadText(resume);
        TextMaster.loadText(saveGame);
        TextMaster.loadText(exitGame);
        super.texts.add(resume);
        super.texts.add(saveGame);
        super.texts.add(exitGame);
    }


    @Override
    public void updateText() {

    }
}
