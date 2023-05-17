package view;

import controller.Controller;
import engine.fontMeshCreator.GUIText;
import engine.guis.UiButton;
import engine.guis.UiTab;
import model.GameModel;
import model.city.CityStatistics;

import java.util.ArrayList;
import java.util.Date;

public class ViewModel {

    private Controller controller;
    private BottomMenuBar bottomMenuBar;
    private StatisticsMenu statisticsMenu;
    private ArrayList<UiButton> buttons = new ArrayList<UiButton>();
    private ArrayList<UiTab> tabs = new ArrayList<UiTab>();
    private ArrayList<GUIText> texts = new ArrayList<GUIText>();

    public ViewModel(Controller controller, GameModel gameModel) {
        this.controller = controller;
        this.bottomMenuBar = new BottomMenuBar(controller, gameModel);
        this.buttons.addAll(bottomMenuBar.getButtons());

        this.statisticsMenu = new StatisticsMenu(controller,gameModel);
        this.tabs.addAll(this.statisticsMenu.getTabs());
        this.tabs.addAll(this.bottomMenuBar.getTabs());
        this.texts.addAll(statisticsMenu.getTexts());
    }

    public void init(GameModel gameModel) {
        texts.clear();
        statisticsMenu.clearText();

        statisticsMenu.initText(gameModel);
        texts.addAll(statisticsMenu.getTexts());
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

    public ArrayList<GUIText> getTexts() {
        return texts;
    }

    public BottomMenuBar getBottomMenuBar() {
        return bottomMenuBar;
    }

    public void updateDate(Date date) {
        bottomMenuBar.updateDateText(date);
    }

}
