package view;

import controller.Controller;
import engine.fontMeshCreator.GUIText;
import engine.fontRendering.TextMaster;
import engine.guis.ButtonEnum;
import engine.guis.UiButton;
import engine.guis.UiTab;
import engine.renderEngine.Loader;
import model.GameModel;
import org.joml.Vector2f;

/**
 * Class responsible for showing the MoneyStatistics upon clicking on the money
 * extending the Menu Class
 */
public class MoneyStatistic extends Menu{

    private UiTab tab;
    private Loader loader = new Loader();
    private String tabTexture = "Test";
    private GUIText moneyBudget;
    private GUIText taxRate;
    private GUIText spend;
    private GUIText revenue;
    private UiButton increaseTax;
    private UiButton decreaseTax;

    public MoneyStatistic(Controller controller, GameModel gameModel) {
        super(controller, gameModel);
        loadComponents();
        moneyBudget = new GUIText("The Money Budget: "+String.valueOf(super.gameModel.getCityStatistics().getBudget().getBalance()), 1, new Vector2f(0.25f, 0.3f), 1f, false);
        moneyBudget.setColour(0,0,0);
        taxRate = new GUIText("Tax Rate: " + String.valueOf((super.gameModel.getCityStatistics().getBudget().getTaxRate())),1,new Vector2f(0.25f,0.35f),1f,false);
        taxRate.setColour(0,0,0);
        spend = new GUIText("Spend: " + String.valueOf((super.gameModel.calculateSpend())),1,new Vector2f(0.25f,0.40f),1f,false);
        spend.setColour(0,0,0);
        revenue = new GUIText("Revenue: " + String.valueOf((super.gameModel.calculateRevenue())),1,new Vector2f(0.25f,0.45f),1f,false);
        revenue.setColour(0,0,0);
        TextMaster.loadText(moneyBudget);
        TextMaster.loadText(taxRate);
        TextMaster.loadText(spend);
        TextMaster.loadText(revenue);
        super.texts.add(moneyBudget);
        super.texts.add(taxRate);
        super.texts.add(spend);
        super.texts.add(revenue);
        increaseTax = new UiButton(loader.loadTexture("Button"),new Vector2f(-0.2f,0.28f),new Vector2f(0.02f,0.03f),ButtonEnum.INCREASE_TAX );
        super.buttons.add(increaseTax);
        decreaseTax = new UiButton(loader.loadTexture("Button"),new Vector2f(-0.25f,0.28f),new Vector2f(0.02f,0.03f),ButtonEnum.DECREASE_TAX );
        super.buttons.add(decreaseTax);
    }
    /**
     * method needed to load the background Tab
     */
    @Override
    protected void loadComponents() {
        tab = new UiTab(loader.loadTexture(tabTexture),new Vector2f(0f,0f),new Vector2f(0.5f,0.5f));
        super.tabs.add(tab);
    }

    /**
     * method to update the text of the money and the tax rate within the time frame
     */
    @Override
    public void updateText() {


        spend = new GUIText("Spend: " + String.valueOf((super.gameModel.calculateSpend())),1,new Vector2f(0.25f,0.40f),1f,false);
        spend.setColour(0,0,0);
        revenue = new GUIText("Revenue: " + String.valueOf((super.gameModel.calculateRevenue())),1,new Vector2f(0.25f,0.45f),1f,false);
        moneyBudget.setTextString("The Money Budget: "+String.valueOf(super.gameModel.getCityStatistics().getBudget().getBalance()));
        taxRate.setTextString("Tax Rate: " +String.valueOf(super.gameModel.getCityStatistics().getBudget().getTaxRate()));
        TextMaster.loadText(moneyBudget);
        TextMaster.loadText(taxRate);
        TextMaster.loadText(spend);
        TextMaster.loadText(revenue);
        super.texts.add(moneyBudget);
        super.texts.add(taxRate);
        super.texts.add(spend);
        super.texts.add(revenue);
    }
    public UiButton getIncreaseTax() {
        return increaseTax;
    }
    public UiButton getDecreaseTax() {
        return decreaseTax;
    }
}

