package engine.engineMain;

import controller.Controller;
import controller.ICallBack;
import controller.util.GameMode;
import engine.display.DisplayManager;
import engine.entities.Camera;
import engine.entities.Entity;
import engine.entities.Light;
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
import view.ViewModel;

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
    private ViewModel viewModel;

    private float mouseDelay = 0f;


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

        this.masterRenderer = new MasterRenderer();
        this.mousePicker = new MousePicker(camera, masterRenderer.getProjectionMatrix(), worldGrid);

        this.gameModel = new GameModel(worldGrid.getWorldSize(), worldGrid.getWorldSize());
        this.gameModel.initialize();
        this.controller = new Controller(gameModel);

        viewModel = new ViewModel(controller);
    }

    public void render() {
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

        if (mouseDelay == 0f && Mouse.isLeftButtonPressed()) {
            mouseDelay = 0.1f;
            for (UiButton button: viewModel.getButtons()) {
                if (button.isClicked()) {
                    switch (button.getButtonEnum()) {
                        case RESIDENTIAL_ZONE -> viewModel.getBottomMenuBar().resZoneButtonAction();
                        case COMMERICAL_ZONE -> viewModel.getBottomMenuBar().comZoneButtonAction();
                        case INDUSTRIAL_ZONE -> viewModel.getBottomMenuBar().indZoneButtonAction();
                        case DE_ZONE -> viewModel.getBottomMenuBar().deZoneButtonAction();
                        case ROAD -> viewModel.getBottomMenuBar().roadButtonAction();
                        case FOREST -> viewModel.getBottomMenuBar().forestButtonAction();
                        case POLICE -> viewModel.getBottomMenuBar().policeButtonAction();
                        case SCHOOL -> viewModel.getBottomMenuBar().schoolButtonAction();
                        case UNIVERSITY -> viewModel.getBottomMenuBar().universityButton();
                        case DESTROY -> viewModel.getBottomMenuBar().destroyButtonAction();
                    }
                }
            }
            if (coordsX < worldGrid.getWorldSize() && coordsX >= 0 && coordsY < worldGrid.getWorldSize() && coordsY >= 0 && controller.getGameMode() != GameMode.SELECTION_MODE) {
                controller.mouseClickRequest(new Coordinate(coordsX, coordsY), this);
//                Entity road = new Entity(roadTexM, new Vector3f(coordsX * Terrain.getSize(),0,(coordsY + 1) *Terrain.getSize()), 0,0,0,5);
//                worldGrid.addBuildable(mousePicker.getCurrentTileCoords().x, mousePicker.getCurrentTileCoords().y, road);
            }
        } else {
            mouseDelay -= DisplayManager.getFrameTimeSeconds();
            if (mouseDelay < 0f) {
                mouseDelay = 0f;
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
        guiRenderer.render(viewModel.getButtons());
    }

    public void cleanUp() {
        masterRenderer.cleanUp();
        loader.cleanUp();
    }

    @Override
    public void updateGridSystem(Coordinate coordinate, Buildable buildable) {
        Entity entity = null;
        switch (buildable.getBuildableType()) {
            case RESIDENTIAL -> {
                entity = new Entity(assets.getResidentialBuilding(), new Vector3f(coordinate.getRow() * Terrain.getSize(),0,(coordinate.getCol() + 1) *Terrain.getSize()), 0,0,0,5);
                break;
            }
            case COMMERCIAL -> {
                entity = new Entity(assets.getCommercialBuilding(), new Vector3f(coordinate.getRow() * Terrain.getSize(),0,(coordinate.getCol() + 1) *Terrain.getSize()), 0,0,0,5);
                break;
            }
            case INDUSTRIAL -> {
                entity = new Entity(assets.getIndustrialBuilding(), new Vector3f(coordinate.getRow() * Terrain.getSize(),0,(coordinate.getCol() + 1) *Terrain.getSize()), 0,0,0,5);
                break;
            }
            case ROAD -> {
                entity = new Entity(assets.getRoad(), new Vector3f(coordinate.getRow() * Terrain.getSize(),0,(coordinate.getCol() + 1) *Terrain.getSize()), 0,0,0,5);
                break;
            }
            case FOREST -> {
                entity = new Entity(assets.getForest(), new Vector3f(coordinate.getRow() * Terrain.getSize(),0,(coordinate.getCol() + 1) *Terrain.getSize()), 0,0,0,5);
                break;
            }
            case POLICE -> {
                entity = new Entity(assets.getPolice(), new Vector3f(coordinate.getRow() * Terrain.getSize(),0,(coordinate.getCol() + 1) *Terrain.getSize()), 0,0,0,5);
                break;
            }
            case STADIUM -> {
                entity = new Entity(assets.getStadium(), new Vector3f(coordinate.getRow() * Terrain.getSize(),0,(coordinate.getCol() + 1) *Terrain.getSize()), 0,0,0,5);
                break;
            }
            case SCHOOL -> {
                entity = new Entity(assets.getSchool(), new Vector3f(coordinate.getRow() * Terrain.getSize(),0,(coordinate.getCol() + 1) *Terrain.getSize()), 0,0,0,5);
                break;
            }
            case UNIVERSITY -> {
                entity = new Entity(assets.getUniversity(), new Vector3f(coordinate.getRow() * Terrain.getSize(),0,(coordinate.getCol() + 1) *Terrain.getSize()), 0,0,0,5);
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
