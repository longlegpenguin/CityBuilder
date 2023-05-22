package view;

import controller.Controller;
import engine.guis.UiButton;
import engine.guis.UiTab;
import model.GameModel;
import model.zone.ZoneStatistics;

import java.util.ArrayList;

public class ViewModel {

    private Controller controller;
    private BottomMenuBar bottomMenuBar;
    private StatisticsMenu statisticsMenu;
    private ZoneSelector zoneSelector;
    private ArrayList<UiButton> buttons = new ArrayList<UiButton>();
    private ArrayList<UiTab> tabs = new ArrayList<UiTab>();
    private MoneyStatistic moneyStatistic;
    private boolean moneyMenuActive = false;

    public ViewModel(Controller controller, GameModel gameModel) {
        this.controller = controller;
        this.bottomMenuBar = new BottomMenuBar(controller, gameModel);
        this.buttons.addAll(bottomMenuBar.getButtons());



        this.statisticsMenu = new StatisticsMenu(controller,gameModel);
        this.tabs.addAll(this.statisticsMenu.getTabs());
        this.tabs.addAll(this.bottomMenuBar.getTabs());

        this.zoneSelector = new ZoneSelector(controller, gameModel);
        this.tabs.addAll(this.zoneSelector.getTabs());


    }

    public void moneyDisplayManagement(Controller controller,GameModel gameModel, boolean moneyTab)
    {
        if (!moneyTab) {
            this.moneyStatistic = new MoneyStatistic(controller, gameModel);
            this.tabs.addAll(this.moneyStatistic.getTabs());

        } else {
            this.moneyStatistic.clearText();
            this.tabs.removeAll(this.moneyStatistic.getTabs());
            this.buttons.removeAll(moneyStatistic.getButtons());
        }
    }
    public void taxIncDecButtons(boolean moneyTab)
    {   if (moneyTab)
        this.buttons.addAll(moneyStatistic.getButtons());
        else
        this.buttons.removeAll(moneyStatistic.getButtons());
    }

    public void Update() {
        statisticsMenu.clearText();
        statisticsMenu.updateText();

        bottomMenuBar.clearText();
        bottomMenuBar.updateText();
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
