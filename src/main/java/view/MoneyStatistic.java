package view;

import controller.Controller;
import engine.fontMeshCreator.GUIText;
import engine.fontRendering.TextMaster;
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
        loadComponents();
        moneyBudget = new GUIText("The Money Budget: "+String.valueOf(gameModel.getCityStatistics().getBudget().getBalance()), 1, new Vector2f(0.25f, 0.3f), 1f, false);
        moneyBudget.setColour(0,0,0);
        taxRate = new GUIText("Tax Rate:" + String.valueOf((gameModel.getCityStatistics().getBudget().getTaxRate())),1,new Vector2f(0.25f,0.35f),1f,false);
        taxRate.setColour(0,0,0);
        TextMaster.loadText(moneyBudget);
        TextMaster.loadText(taxRate);
    }

    @Override
    protected void loadComponents() {
        tab = new UiTab(loader.loadTexture(tabTexture),new Vector2f(0f,0.f),new Vector2f(0.5f,0.5f));
        super.tabs.add(tab);
    }

    @Override
    public void initText(GameModel gameModel) {
        moneyBudget.setTextString(String.valueOf(gameModel.getCityStatistics().getBudget().getBalance()));
        taxRate.setTextString(String.valueOf(gameModel.getCityStatistics().getBudget().getTaxRate()));
        TextMaster.loadText(moneyBudget);
        TextMaster.loadText(taxRate);

        super.texts.add(moneyBudget);
        super.texts.add(taxRate);
    }

    public UiTab getTab() {
        return tab;
    }
}
