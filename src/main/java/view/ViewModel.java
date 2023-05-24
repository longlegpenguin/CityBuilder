package view;

import controller.Controller;
import engine.display.DisplayManager;
import engine.guis.UiButton;
import engine.guis.UiTab;
import engine.tools.Keyboard;
import engine.tools.Mouse;
import model.GameModel;
import model.zone.Zone;
import model.zone.ZoneStatistics;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class ViewModel {

    private Controller controller;
    private BottomMenuBar bottomMenuBar;
    private StatisticsMenu statisticsMenu;
    private ZoneSelector zoneSelector;
    private ArrayList<UiButton> buttons = new ArrayList<UiButton>();
    private ArrayList<UiTab> tabs = new ArrayList<UiTab>();
    private MoneyStatistic moneyStatistic;
    private PauseMenu pauseMenu;
    private boolean selectionMenuActive = false;
    private boolean moneyMenuActive = false;
    private boolean pauseMenuActive = false;

    public ViewModel(Controller controller, GameModel gameModel) {
        this.controller = controller;
        this.bottomMenuBar = new BottomMenuBar(controller, gameModel);
        this.buttons.addAll(bottomMenuBar.getButtons());



        this.statisticsMenu = new StatisticsMenu(controller,gameModel);
        this.tabs.addAll(this.statisticsMenu.getTabs());
        this.tabs.addAll(this.bottomMenuBar.getTabs());
    }

    public void moneyDisplayManagement(Controller controller,GameModel gameModel, boolean moneyTab)
    {
        if (!moneyTab) {
            this.moneyStatistic = new MoneyStatistic(controller, gameModel);
            this.tabs.addAll(this.moneyStatistic.getTabs());

        } else {
            this.moneyStatistic.clearText();
            this.tabs.removeAll(this.moneyStatistic.getTabs());

        }
    }
    public boolean pause(Controller controller,GameModel gameModel){
        if (Keyboard.isKeyDown(GLFW_KEY_ESCAPE)) {
            this.pauseMenu = new PauseMenu(controller, gameModel);
            this.tabs.addAll((this.pauseMenu.getTabs()));
            this.buttons.addAll(this.pauseMenu.getButtons());

        return true;
        }else return false;

    }
    public boolean unpause()
    {

        if(this.pauseMenu.getResumeButton().isClicked())
        {
            this.tabs.removeAll(this.pauseMenu.getTabs());
            this.buttons.removeAll((this.pauseMenu.getButtons()));

            return true;
        }
        else return false;
        }


    public boolean checkExitGame(){
        return this.pauseMenu.getExitGameButton().isClicked();
    }

    public void taxIncDecButtons(boolean moneyTab,GameModel gameModel)
    {
            if (moneyTab){
            this.buttons.addAll(moneyStatistic.getButtons());
            this.moneyMenuActive = true;
            if (moneyStatistic.getIncreaseTax().isClicked())
            {
                gameModel.getCityStatistics().getBudget().setTaxRate(gameModel.getCityStatistics().getBudget().getTaxRate()+0.1);

            }
            if (moneyStatistic.getDecreaseTax().isClicked()){
                gameModel.getCityStatistics().getBudget().setTaxRate(gameModel.getCityStatistics().getBudget().getTaxRate()-0.1);


        }
            }
        else if (moneyStatistic != null) {
            this.buttons.removeAll(moneyStatistic.getButtons());

            }
        }

    public void update() {
        statisticsMenu.clearText();
        statisticsMenu.updateText();
        if (moneyMenuActive) {
            moneyStatistic.clearText();
            moneyStatistic.updateText();
        }
        bottomMenuBar.clearText();
        bottomMenuBar.updateText();

        if (selectionMenuActive) {
            zoneSelector.updateText();
        }
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

    public void createZoneSelector(GameModel gameModel, Zone zone) {
        if (selectionMenuActive) {
            deleteZoneSelector();
        }

        this.zoneSelector = new ZoneSelector(controller, gameModel, zone);
        this.tabs.addAll(this.zoneSelector.getTabs());
        this.selectionMenuActive = true;
    }

    public void deleteZoneSelector() {
        if (selectionMenuActive) {
            this.tabs.removeAll(this.zoneSelector.getTabs());
            this.zoneSelector.clearText();
            this.zoneSelector = null;
            selectionMenuActive = false;
        }
    }

    public boolean isMoneyMenuActive() {
        return moneyMenuActive;
    }

    public void setMoneyMenuActive(boolean moneyMenuActive) {
        this.moneyMenuActive = moneyMenuActive;
    }

    public PauseMenu getPauseMenu() {
        return pauseMenu;
    }
}
