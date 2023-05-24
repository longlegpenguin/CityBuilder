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
        tab = new UiTab(loader.loadTexture(tabTexture),new Vector2f(0f,0f),new Vector2f(0.3f,0.5f));
        super.tabs.add(tab);
        resumeButton = new UiButton(loader.loadTexture("button/resume"),
                new Vector2f(0f,0.27f),new Vector2f(0.1f,0.1f), ButtonEnum.RESUME_GAME);
        super.buttons.add(resumeButton);

        saveGameButton = new UiButton(loader.loadTexture("button/saveGame"),
                new Vector2f(0f,0.03f),new Vector2f(0.1f,0.1f), ButtonEnum.SAVE_GAME);
        super.buttons.add(saveGameButton);

        exitGameButton = new UiButton(loader.loadTexture("button/ExitGame"),
                new Vector2f(0f,-0.23f),new Vector2f(0.1f,0.1f), ButtonEnum.EXIT_GAME);
        super.buttons.add(exitGameButton);


    }


    @Override
    public void updateText() {

    }



    public UiButton getResumeButton() {
        return resumeButton;
    }

    public UiButton getSaveGameButton() {
        return saveGameButton;
    }

    public UiButton getExitGameButton() {
        return exitGameButton;
    }
}
