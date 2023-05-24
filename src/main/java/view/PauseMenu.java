package view;

import controller.Controller;
import engine.fontMeshCreator.GUIText;
import engine.fontRendering.TextMaster;
import engine.guis.ButtonEnum;
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
    private UiButton resumeButton;
    private UiButton saveGameButton;
    private UiButton exitGameButton;
    private String tabTexture = "Test";

    public PauseMenu(Controller controller, GameModel gameModel) {
        super(controller, gameModel);
        loadComponents();

    }

    @Override
    protected void loadComponents() {
        tab = new UiTab(loader.loadTexture(tabTexture),new Vector2f(0f,0f),new Vector2f(0.5f,0.5f));
        super.tabs.add(tab);
        resumeButton = new UiButton(loader.loadTexture("Button"),
                new Vector2f(0f,0.17f),new Vector2f(0.1f,0.05f), ButtonEnum.RESUME_GAME);
        super.buttons.add(resumeButton);
        resume   = new GUIText("Resume Game",1,new Vector2f(0f,0.4f),1,true);
        saveGameButton = new UiButton(loader.loadTexture("Button"),
                new Vector2f(0f,-0.04f),new Vector2f(0.1f,0.05f), ButtonEnum.SAVE_GAME);
        super.buttons.add(saveGameButton);
        saveGame = new GUIText("Save Game",1,new Vector2f(0f,0.5f),1,true);
        exitGameButton = new UiButton(loader.loadTexture("Button"),
                new Vector2f(0f,-0.23f),new Vector2f(0.1f,0.05f), ButtonEnum.EXIT_GAME);
        super.buttons.add(exitGameButton);
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
