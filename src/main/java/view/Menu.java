package view;

import engine.guis.UiButton;
import engine.guis.UiTab;

import java.util.ArrayList;

public abstract class Menu {

    protected ArrayList<UiButton> buttons = new ArrayList<UiButton>();
    protected ArrayList<UiTab> tabs = new ArrayList<UiTab>();

    public Menu() {

    }

    protected abstract void loadComponents();

    public ArrayList<UiButton> getButtons() {
        return buttons;
    }

    public ArrayList<UiTab> getTabs() {
        return tabs;
    }
}
