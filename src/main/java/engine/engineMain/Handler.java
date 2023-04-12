package engine.engineMain;

import controller.Controller;
import engine.entities.Camera;
import engine.entities.Entity;
import engine.entities.Light;
import engine.renderEngine.GuiRenderer;
import engine.renderEngine.Loader;
import engine.renderEngine.MasterRenderer;
import engine.terrain.Selector;
import engine.terrain.Terrain;
import engine.textures.TextureAttribute;
import engine.textures.UiButton;
import engine.tools.Mouse;
import engine.tools.MousePicker;
import engine.world.WorldGrid;
import model.GameModel;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;

public class Handler {

    private String saveFile;
    private Loader loader = new Loader();
    private AssetLoader assets;
    private WorldGrid worldGrid;
    private Selector selector;
    private Camera camera;
    private Light light;
    private MousePicker mousePicker;
    private GuiRenderer guiRenderer;
    private MasterRenderer masterRenderer;
    private GameModel gameModel;
    private Controller controller;

    private Entity entity;
    ArrayList<UiButton> guiButtons = new ArrayList<UiButton>();;



    public Handler(String saveFile) {

        this.saveFile = saveFile;
        this.loader = new Loader();
        this.assets = new AssetLoader();
        this.worldGrid = new WorldGrid(loader, new TextureAttribute(loader.loadTexture("grass")));
        this.selector = new Selector(0, 0, loader, new TextureAttribute(loader.loadTexture("selector")));

        float center = Terrain.getSize() * worldGrid.getWorldSize() / 2;
        this.camera = new Camera(new Vector3f(0,100, 0));
        this.light = new Light(new Vector3f(center, 1000, center), new Vector3f(1,1,1));

        this.guiRenderer = new GuiRenderer(loader);

        String buttonTexture = "Button";

        UiButton resZoneButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(-0.9f, -0.85f), new Vector2f(0.05f, 0.05f));
        guiButtons.add(resZoneButton);
        UiButton comZoneButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(-0.75f, -0.85f), new Vector2f(0.05f, 0.05f));
        guiButtons.add(comZoneButton);
        UiButton indZoneButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(-0.60f, -0.85f), new Vector2f(0.05f, 0.05f));
        guiButtons.add(indZoneButton);
        UiButton deZoneButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(-0.45f, -0.85f), new Vector2f(0.05f, 0.05f));
        guiButtons.add(deZoneButton);

        UiButton roadButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(-0.2f, -0.85f), new Vector2f(0.05f, 0.05f));
        guiButtons.add(roadButton);
        UiButton policeButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(-0.05f, -0.85f), new Vector2f(0.05f, 0.05f));
        guiButtons.add(policeButton);
        UiButton stadiumButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(0.1f, -0.85f), new Vector2f(0.05f, 0.05f));
        guiButtons.add(stadiumButton);
        UiButton schoolButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(0.25f, -0.85f), new Vector2f(0.05f, 0.05f));
        guiButtons.add(schoolButton);
        UiButton universityButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(0.4f, -0.85f), new Vector2f(0.05f, 0.05f));
        guiButtons.add(universityButton);
        UiButton forestsButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(0.55f, -0.85f), new Vector2f(0.05f, 0.05f));
        guiButtons.add(forestsButton);

        UiButton destroyButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(0.9f, -0.85f), new Vector2f(0.05f, 0.05f));
        guiButtons.add(destroyButton);

        UiButton timePauseButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(-0.74f, -0.7f), new Vector2f(0.03f, 0.03f));
        guiButtons.add(timePauseButton);
        UiButton timeOneButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(-0.66f, -0.7f), new Vector2f(0.03f, 0.03f));
        guiButtons.add(timeOneButton);
        UiButton timeTwoButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(-0.58f, -0.7f), new Vector2f(0.03f, 0.03f));
        guiButtons.add(timeTwoButton);
        UiButton timeThreeButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(-0.50f, -0.7f), new Vector2f(0.03f, 0.03f));
        guiButtons.add(timeThreeButton);

        UiButton moneyButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(0.87f, -0.7f), new Vector2f(0.08f, 0.03f));
        guiButtons.add(moneyButton);

        UiButton dateButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(-0.87f, -0.7f), new Vector2f(0.08f, 0.03f));
        guiButtons.add(dateButton);

        UiButton cityStatsButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(0.83f, 0.93f), new Vector2f(0.17f, 0.07f));
        guiButtons.add(cityStatsButton);
        UiButton cellStatsButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(0.83f, 0.65f), new Vector2f(0.17f, 0.2f));
        guiButtons.add(cellStatsButton);

        this.masterRenderer = new MasterRenderer();
        this.mousePicker = new MousePicker(camera, masterRenderer.getProjectionMatrix(), worldGrid);

        this.gameModel = new GameModel(worldGrid.getWorldSize(), worldGrid.getWorldSize());
        this.gameModel.initialize();
        this.controller = new Controller(gameModel);
    }

    public void render() {
        camera.move();
        mousePicker.update();
        if (mousePicker.getCurrentTileCoords() != null) {
            selector.setX(mousePicker.getCurrentTileCoords().x);
            selector.setZ(mousePicker.getCurrentTileCoords().y);
        } else {
            selector.setX(-100);
            selector.setZ(-100);
        }




        Mouse.update();

        for (Terrain terrain: worldGrid.getTerrainList()) {
            masterRenderer.processTerrain(terrain);
        }

        for (Entity zone: worldGrid.getZoneList()) {
            masterRenderer.processEntities(zone);
        }

        for (Entity buildable: worldGrid.getBuildableList()) {
            masterRenderer.processEntities(buildable);
        }


        masterRenderer.render(selector, camera, light);
        guiRenderer.render(guiButtons);
    }

    public void cleanUp() {
        masterRenderer.cleanUp();
        loader.cleanUp();
    }

}
