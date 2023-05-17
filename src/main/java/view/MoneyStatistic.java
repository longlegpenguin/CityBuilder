package view;

import controller.Controller;
import engine.fontMeshCreator.GUIText;
import engine.guis.UiTab;
import engine.renderEngine.Loader;
import model.GameModel;
import org.joml.Vector2f;

public class MoneyStatistic extends Menu{

    private UiTab tab;
    private Loader loader = new Loader();
    private String tabTexture = "Test";

    private GUIText moneyBudget;
    private GUIText taxRate;
    private GameModel gameModel;


    public MoneyStatistic(Controller controller, GameModel gameModel) {
        super(controller);
        this.gameModel = gameModel;

    }

    @Override
    protected void loadComponents() {
        tab = new UiTab(loader.loadTexture(tabTexture),new Vector2f(0.95f,0.95f),new Vector2f(0.5f,0.5f));

    }

    @Override
    public void initText(GameModel gameModel) {
        moneyBudget = new GUIText(String.valueOf(gameModel.getCityStatistics().getBudget().getBalance()), 1, new Vector2f(0.9f, 0.885f), 1f, false);
        moneyBudget.setColour(0,0,0);
        taxRate = new GUIText(String.valueOf((gameModel.getCityStatistics().getBudget().getTaxRate())),1,new Vector2f(0.5f,0.5f),1f,true);
        moneyBudget.setColour(0,0,0);
    }
}
