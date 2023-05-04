package view;

import controller.Controller;
import engine.fontMeshCreator.GUIText;
import engine.guis.UiTab;
import engine.renderEngine.Loader;
import model.GameModel;
import model.city.CityStatistics;
import org.joml.Vector2f;

public class StatisticsMenu extends Menu {
    private UiTab tab;
    private Loader  loader = new Loader();
    private String tabTexture = "Test";
    private GUIText cityPopulation;
    private GUIText citizenSatisfaction;
    private GUIText money;
    private GameModel gameModel;
    public StatisticsMenu(Controller controller, GameModel gameModel) {
        super(controller);
        this.gameModel = gameModel;
        loadComponents();
    }

    @Override
    protected void loadComponents() {
        tab = new UiTab(loader.loadTexture(tabTexture),new Vector2f(0.95f,0.95f),new Vector2f(0.3f,0.1f));
        super.tabs.add(tab);

        initText(gameModel);
    }

    @Override
    public void initText(GameModel gameModel) {
        cityPopulation = new GUIText("Population: "+ String.valueOf(gameModel.getCityStatistics().getPopulation(gameModel.getCityRegistry())),
                1,new Vector2f(0.83f,0f),1,false);

        cityPopulation.setColour(0,0,0);
        citizenSatisfaction = new GUIText("Satifaction: "+ String.valueOf(gameModel.getCityStatistics().getCitySatisfaction()),
                1,new Vector2f(0.83f,0.04f),1f,false);
        citizenSatisfaction.setColour(0, 0, 0);

        money = new GUIText(String.valueOf(gameModel.getCityStatistics().getBudget().getBalance()), 1, new Vector2f(0.9f, 0.885f), 1f, false);
        money.setColour(0,0,0);

        System.out.println("Test");
        super.texts.add(cityPopulation);
        super.texts.add(citizenSatisfaction);
        super.texts.add(money);
    }

}
