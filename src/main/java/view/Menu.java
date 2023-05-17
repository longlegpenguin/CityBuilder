package view;

import controller.Controller;
import controller.util.GameMode;
import engine.fontMeshCreator.GUIText;
import engine.fontRendering.TextMaster;
import engine.guis.UiButton;
import engine.guis.UiTab;
import model.GameModel;

import java.util.ArrayList;

public abstract class Menu {

    protected Controller controller;
    protected GameModel gameModel;
    protected ArrayList<UiButton> buttons = new ArrayList<UiButton>();
    protected ArrayList<UiTab> tabs = new ArrayList<UiTab>();
    protected ArrayList<GUIText> texts = new ArrayList<GUIText>();

    public Menu(Controller controller, GameModel gameModel) {
        this.controller = controller;
        this.gameModel = gameModel;
    }

    public void buttonAction(UiButton button,GameMode gameMode, GUIText text) {
        if (!button.isEnabled()) {
            button.setEnabled(true);
            controller.switchGameModeRequest(gameMode);
        } else {
            button.setEnabled(false);
            controller.switchGameModeRequest(GameMode.SELECTION_MODE);
        }
        TextMaster.removeText(text);
        text.setTextString(button.getButtonEnum().toString());
        TextMaster.loadText(text);
    }

    protected abstract void loadComponents();

    public abstract void updateText();

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
        for (GUIText text: texts) {
            TextMaster.removeText(text);
        }
        this.texts.clear();
    }
}
