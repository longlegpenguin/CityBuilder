package view;

import controller.Controller;
import engine.fontMeshCreator.GUIText;
import engine.guis.UiTab;
import engine.renderEngine.Loader;
import model.GameModel;
import org.joml.Vector2f;

public class StatisticsMenu extends Menu {
    private UiTab tab;
    private Loader  loader = new Loader();
    private String tabTexture = "Tab";
    private GUIText cityPopulation;
    private GUIText citizenSatisfaction;
    private GameModel gameModel;
    public StatisticsMenu(Controller controller, GameModel gameModel) {
        super(controller);
        this.gameModel = gameModel;
        loadComponents();
    }

    @Override
    protected void loadComponents() {
        tab = new UiTab(loader.loadTexture(tabTexture),new Vector2f(-0.95f,0.95f),new Vector2f(0.3f,0.1f));
        cityPopulation = new GUIText("Population: "+ String.valueOf(gameModel.getCityStatistics().getPopulation(gameModel.getCityRegistry())),
                1,new Vector2f(0f,0f),1,false);
        cityPopulation.setColour(0,0,0);


    }

    public UiTab getTab() {
        return tab;
    }
}
