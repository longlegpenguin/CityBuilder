package view;

import controller.Controller;
import engine.guis.UiButton;
import engine.guis.UiTab;

import java.util.ArrayList;

public class ViewModel {

    private Controller controller;
    private BottomMenuBar bottomMenuBar;

    private ArrayList<UiButton> buttons = new ArrayList<UiButton>();
    private ArrayList<UiTab> tabs = new ArrayList<UiTab>();

    public ViewModel(Controller controller) {
        this.controller = controller;
        this.bottomMenuBar = new BottomMenuBar(controller);
        this.buttons.addAll(bottomMenuBar.getButtons());
    }

    public ArrayList<UiButton> getButtons() {
        return buttons;
    }

    public ArrayList<UiTab> getTabs() {
        return tabs;
    }

    public BottomMenuBar getBottomMenuBar() {
        return bottomMenuBar;
    }
}
