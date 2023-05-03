package view;

import controller.Controller;
import controller.util.GameMode;
import engine.fontMeshCreator.GUIText;
import engine.guis.UiButton;
import engine.guis.UiTab;
import model.GameModel;

import java.util.ArrayList;

public abstract class Menu {

    protected Controller controller;
    protected ArrayList<UiButton> buttons = new ArrayList<UiButton>();
    protected ArrayList<UiTab> tabs = new ArrayList<UiTab>();
    protected ArrayList<GUIText> texts = new ArrayList<GUIText>();

    public Menu(Controller controller) {
        this.controller = controller;
    }

    public void buttonAction(UiButton button,GameMode gameMode) {
        if (!button.isEnabled()) {
            controller.switchGameModeRequest(gameMode);
        } else {
            controller.switchGameModeRequest(GameMode.SELECTION_MODE);
        }
    }

    protected abstract void loadComponents();

    public abstract void initText(GameModel gameModel);

    public ArrayList<UiButton> getButtons() {
        return buttons;
    }

    public ArrayList<GUIText> getTexts() {
        return texts;
    }

    public ArrayList<UiTab> getTabs() {
        return tabs;
    }

    public void clearText() {
        this.texts.clear();
    }
}
