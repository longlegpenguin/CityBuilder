package view;

import controller.Controller;
import engine.guis.UiButton;
import engine.guis.UiTab;
import model.GameModel;

import java.util.ArrayList;

public class ViewModel {

    private Controller controller;
    private BottomMenuBar bottomMenuBar;
    private StatisticsMenu statisticsMenu;
    private ArrayList<UiButton> buttons = new ArrayList<UiButton>();
    private ArrayList<UiTab> tabs = new ArrayList<UiTab>();

    public ViewModel(Controller controller, GameModel gameModel) {
        this.controller = controller;
        this.bottomMenuBar = new BottomMenuBar(controller);
        this.buttons.addAll(bottomMenuBar.getButtons());
        this.statisticsMenu = new StatisticsMenu(controller,gameModel);
        this.tabs.add(this.statisticsMenu.getTab());
    }

    public ArrayList<UiButton> getButtons() {
        return buttons;
    }

    public StatisticsMenu getStatisticsMenu() {
        return statisticsMenu;
    }

    public ArrayList<UiTab> getTabs() {
        return tabs;
    }

    public BottomMenuBar getBottomMenuBar() {
        return bottomMenuBar;
    }
}
