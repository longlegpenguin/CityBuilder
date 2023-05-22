package view;

import controller.Controller;
import engine.fontMeshCreator.GUIText;
import engine.fontRendering.TextMaster;
import engine.guis.UiTab;
import engine.renderEngine.Loader;
import model.GameModel;
import org.joml.Vector2f;

/**
 * Class for showing the statistic Menu during the game
 */
public class StatisticsMenu extends Menu {
    private UiTab tab;
    private Loader  loader = new Loader();
    private String tabTexture = "Test";
    private GUIText cityPopulation;
    private GUIText citizenSatisfaction;
    private GUIText money;

    public StatisticsMenu(Controller controller, GameModel gameModel) {
        super(controller, gameModel);
        loadComponents();
    }

    /**
     * loadComponents to load the tabs and the texts to the menu to show them
     */
    @Override
    protected void loadComponents() {
        tab = new UiTab(loader.loadTexture(tabTexture),new Vector2f(0.95f,0.95f),new Vector2f(0.3f,0.1f));
        super.tabs.add(tab);

        cityPopulation = new GUIText("Population: "+ String.valueOf(super.gameModel.getCityStatistics().getPopulation(gameModel.getCityRegistry())),
                1,new Vector2f(0.83f,0f),1,false);
        cityPopulation.setColour(0,0,0);
        TextMaster.loadText(cityPopulation);
        super.texts.add(cityPopulation);

        citizenSatisfaction = new GUIText("Satifaction: "+ String.valueOf(Math.round(super.gameModel.getCityStatistics().getCitySatisfaction()*100)/100.0),
                1,new Vector2f(0.83f,0.04f),1f,false);
        citizenSatisfaction.setColour(0, 0, 0);
        TextMaster.loadText(citizenSatisfaction);
        super.texts.add(citizenSatisfaction);

        money = new GUIText(String.valueOf(super.gameModel.getCityStatistics().getBudget().getBalance()), 1, new Vector2f(0.9f, 0.885f), 1f, false);
        money.setColour(0,0,0);
        TextMaster.loadText(money);
        super.texts.add(money);
    }

    /**
     * method updateText to update the text during the game time frame
     */

    @Override
    public void updateText() {
        cityPopulation.setTextString("Population: "+ String.valueOf(super.gameModel.getCityStatistics().getPopulation(gameModel.getCityRegistry())));
        TextMaster.loadText(cityPopulation);

        citizenSatisfaction.setTextString("Satifaction: "+ String.valueOf(Math.round(super.gameModel.getCityStatistics().getCitySatisfaction()*100)/100.0));
        TextMaster.loadText(citizenSatisfaction);

        money.setTextString(String.valueOf(super.gameModel.getCityStatistics().getBudget().getBalance()));
        TextMaster.loadText(money);

        super.texts.add(cityPopulation);
        super.texts.add(citizenSatisfaction);
        super.texts.add(money);
    }

}
