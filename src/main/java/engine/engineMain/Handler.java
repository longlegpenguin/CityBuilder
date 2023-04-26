package engine.engineMain;

import controller.Controller;
import controller.ICallBack;
import controller.util.GameMode;
import engine.display.DisplayManager;
import engine.entities.Camera;
import engine.entities.Entity;
import engine.entities.Light;
import engine.fontMeshCreator.FontType;
import engine.fontMeshCreator.GUIText;
import engine.fontRendering.TextMaster;
import engine.models.RawModel;
import engine.models.TexturedModel;
import engine.objConverter.ModelData;
import engine.objConverter.OBJFileLoader;
import engine.renderEngine.GuiRenderer;
import engine.renderEngine.Loader;
import engine.renderEngine.MasterRenderer;
import engine.terrain.Selector;
import engine.terrain.Terrain;
import engine.textures.TextureAttribute;
import engine.guis.UiButton;
import engine.tools.Mouse;
import engine.tools.MousePicker;
import engine.world.WorldGrid;
import model.GameModel;
import model.common.Budget;
import model.common.Buildable;
import model.common.Coordinate;
import model.util.Date;
import model.zone.ZoneStatistics;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

public class Handler implements ICallBack {

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

    private FontType font;
    private GUIText text;

    private int counter = 0;

    private float timer = 0;
    private float multiplier = 1;
    private String date = "";

    //private Entity entity;
    ArrayList<UiButton> guiButtons = new ArrayList<UiButton>();;

    public Handler(String saveFile) {

        this.saveFile = saveFile;
        this.loader = new Loader();
        this.assets = new AssetLoader();
        this.worldGrid = new WorldGrid(loader, new TextureAttribute(loader.loadTexture("grass")));
        this.selector = new Selector(0, 0, loader, new TextureAttribute(loader.loadTexture("selector")));

        float center = Terrain.getSize() * worldGrid.getWorldSize() / 2;
        this.camera = new Camera(new Vector3f(0,100, 0));
        this.light = new Light(new Vector3f(50, 1000, 50), new Vector3f(1,1,1));

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

        TextMaster.init(loader);
        font = new FontType(loader.loadFontTexture("tahoma"),new File("src/main/resources/textures/tahoma.fnt"));


    }

    public void render() {



        if (timer >= 3f / multiplier) {
            gameModel.timePassUpdate(1);
            timer -= 3f/multiplier;
        }



        camera.move();
        mousePicker.update();

        int coordsX = -1;
        int coordsY = -1;
        if (mousePicker.getCurrentTileCoords() != null) {
            coordsX = mousePicker.getCurrentTileCoords().x;
            coordsY = mousePicker.getCurrentTileCoords().y;
            selector.setX(coordsX);
            selector.setZ(coordsY);
        } else {
            selector.setX(-100);
            selector.setZ(-100);
        }

        if (Mouse.isLeftButtonPressed()) {
            if (coordsX < worldGrid.getWorldSize() && coordsX >= 0 && coordsY < worldGrid.getWorldSize() && coordsY >= 0) {
                controller.switchModeRequest(GameMode.ROAD_MODE);
                controller.mouseClickRequest(new Coordinate(coordsX, coordsY), this);
//                Entity road = new Entity(roadTexM, new Vector3f(coordsX * Terrain.getSize(),0,(coordsY + 1) *Terrain.getSize()), 0,0,0,5);
//                worldGrid.addBuildable(mousePicker.getCurrentTileCoords().x, mousePicker.getCurrentTileCoords().y, road);
            }
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
        date = gameModel.DateAsString();
        text = new GUIText(date,1,font,new Vector2f(10f,10f),1f,true);
        text.setColour(0,0,1);

        TextMaster.render();
        TextMaster.removeText(text);
        timer += DisplayManager.getFrameTimeSeconds();

    }

    public void cleanUp() {
        masterRenderer.cleanUp();
        TextMaster.cleanUp();
        loader.cleanUp();

    }

    @Override
    public void updateGridSystem(Coordinate coordinate, Buildable buildable) {
        Entity entity = null;
        switch (buildable.getBuildableType()) {
            case ROAD -> {
                entity = new Entity(assets.getRoad(), new Vector3f(coordinate.getRow() * Terrain.getSize(),0,(coordinate.getCol() + 1) *Terrain.getSize()), 0,0,0,5);
                break;
            }
            case COMMERCIAL -> {
//                entity = new Entity(roadTexM, new Vector3f(coordinate.getRow() * Terrain.getSize(),0,(coordinate.getCol() + 1) *Terrain.getSize()), 0,0,0,5);
                break;
            }
        }
        worldGrid.addBuildable(mousePicker.getCurrentTileCoords().x, mousePicker.getCurrentTileCoords().y, entity);
    }

    @Override
    public void updateBudgetPanel(Budget budget) {

    }

    @Override
    public void updateStatisticPanel(ZoneStatistics zoneStatistics) {

    }

    @Override
    public void updateDatePanel(Date date) {

    }
}
