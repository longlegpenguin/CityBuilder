package engine.engineMain;

import controller.Controller;
import controller.ICallBack;
import controller.util.TimeMode;
import engine.display.DisplayManager;
import engine.entities.Camera;
import engine.entities.Entity;
import engine.entities.Light;
import engine.fontMeshCreator.GUIText;
import engine.fontRendering.TextMaster;
import engine.guis.UiButton;
import engine.models.TexturedModel;
import engine.renderEngine.GuiRenderer;
import engine.renderEngine.Loader;
import engine.renderEngine.MasterRenderer;
import engine.terrain.Selector;
import engine.terrain.Terrain;
import engine.terrain.ZoneTile;
import engine.textures.TextureAttribute;
import engine.tools.Keyboard;
import engine.tools.Mouse;
import engine.tools.MousePicker;
import engine.world.WorldGrid;
import model.GameModel;
import model.city.CityStatistics;
import model.common.Budget;
import model.common.Buildable;
import model.common.Citizen;
import model.common.Coordinate;
import model.facility.Forest;
import model.util.Date;
import model.zone.Zone;
import model.zone.ZoneStatistics;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import persistence.Database;
import view.MoneyStatistic;
import view.ViewModel;

import java.util.List;
import static org.lwjgl.glfw.GLFW.*;

/**
 * Handler class is responsible for the communication between the Game Model, Controller, View Model and Game Engine
 * Main Render function located in this class and delegates to other renderers.
 */
public class Handler implements ICallBack {

    private String saveFile;
    private Loader loader;
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
    private int counter = 0;
    private float baseTime = 3f;
    private float timeMultiplier = 1;
    private float timer = 0;
    private float timer2 = 0;
    private boolean paused = false;
    private GUIText framerate;
    private GUIText frametime;
    private GUIText gameStatus;
    private boolean zoneState = false;
    private boolean moneyTab = false;
    private boolean isGameOver = false;
    private boolean exitGame = false;
    private boolean HUD = true;
    private Boolean pausedByMenu = false;

    /**
     * Constructor initializes all renderers, calls asset load functions and initializes the game model, view model and controller.
     * @param saveFile
     */
    public Handler(String saveFile) {

        this.saveFile = saveFile;
        this.loader = new Loader();
        this.assets = new AssetLoader();
        this.worldGrid = new WorldGrid(loader, new TextureAttribute(loader.loadTexture("zones/grass")));
        this.selector = new Selector(Terrain.getSize(), Terrain.getSize(),0, 0, loader, new TextureAttribute(loader.loadTexture("selector")));

        this.camera = new Camera(new Vector3f(Terrain.getSize() * worldGrid.getWorldSize() / 2, 40, Terrain.getSize() * worldGrid.getWorldSize() / 2 + 5));
        this.light = new Light(new Vector3f(50, 1000, 50), new Vector3f(1, 1, 1));

        this.guiRenderer = new GuiRenderer(loader);

        this.masterRenderer = new MasterRenderer();
        this.mousePicker = new MousePicker(camera, masterRenderer.getProjectionMatrix(), worldGrid);


        if ((gameModel = Database.read()) == null) {
            this.gameModel = new GameModel(worldGrid.getWorldSize(), worldGrid.getWorldSize());
            this.gameModel.initialize();
        }

        this.controller = new Controller(gameModel);

        TextMaster.init(loader);
        viewModel = new ViewModel(controller, gameModel);

        frametime = new GUIText("FT (ms): ", 0.8f, new Vector2f(0.01f, 0.01f), 1, false);
        frametime.setColour(0, 0, 0);
        TextMaster.loadText(frametime);

        framerate = new GUIText("FPS: ", 0.8f, new Vector2f(0.01f, 0.03f), 1, false);
        framerate.setColour(0, 0, 0);
        TextMaster.loadText(framerate);

        gameStatus = new GUIText("Game Over: " + isGameOver, 0.8f, new Vector2f(0.01f, 0.05f), 1, false);
        gameStatus.setColour(0, 0, 0);
        TextMaster.loadText(gameStatus);

        setWorldGrid();
    }

    /**
     * Main Render function
     * Everything that needs to be updated in the game is called from this function in some form or another.
     * Controller updates, Text, GUI and asset rendering is done here as well as input handeling.
     */
    public boolean render() {
        if (DisplayManager.isRESIZED()) {
            DisplayManager.resize();
            masterRenderer = new MasterRenderer();
            DisplayManager.setRESIZED(false);
        }

        if (timer2 >= 0.1f) {
            TextMaster.removeText(frametime);
            TextMaster.removeText(framerate);
            TextMaster.removeText(gameStatus);

            frametime.setTextString("FT (ms): " + DisplayManager.getFrameTimeSeconds() * 1000);
            TextMaster.loadText(frametime);

            framerate.setTextString("FPS: " + Math.round(1 / DisplayManager.getFrameTimeSeconds()));
            TextMaster.loadText(framerate);

            gameStatus.setTextString("Game Over: " + isGameOver);
            TextMaster.loadText(gameStatus);
            timer2 -= 0.1f;
        }

        if (timer >= baseTime / timeMultiplier) {
            controller.regularUpdateRequest(1, this);


            setWorldGrid();
            viewModel.update();
            timer -= baseTime / timeMultiplier;
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
            selector.setX(-1000);
            selector.setZ(-1000);
        }

        if (viewModel.pause(controller, gameModel)) {
                pausedByMenu = true;
                paused = pausedByMenu;
                controller.switchTimeModeRequest(TimeMode.PAUSE);
        }

        boolean buttonPressed = false;
        if (Mouse.isLeftButtonClicked()) {
            for (UiButton button : viewModel.getButtons()) {
                if (button.isClicked()) {
                    selector = new Selector(Terrain.getSize(), Terrain.getSize(),0, 0, loader, new TextureAttribute(loader.loadTexture("selector")));
                    zoneState = false;
                    buttonPressed = true;
                    viewModel.deleteZoneSelector();
                    switch (button.getButtonEnum()) {
                        case RESIDENTIAL_ZONE -> {viewModel.getBottomMenuBar().resZoneButtonAction();zoneState = true;}
                        case COMMERICAL_ZONE -> {viewModel.getBottomMenuBar().comZoneButtonAction();zoneState = true;}
                        case INDUSTRIAL_ZONE -> {viewModel.getBottomMenuBar().indZoneButtonAction();zoneState = true;}
                        case DE_ZONE -> viewModel.getBottomMenuBar().deZoneButtonAction();
                        case ROAD -> viewModel.getBottomMenuBar().roadButtonAction();
                        case FOREST -> viewModel.getBottomMenuBar().forestButtonAction();
                        case STADIUM -> {
                            viewModel.getBottomMenuBar().stadiumButtonAction();
                            selector = new Selector(Terrain.getSize() * 2, Terrain.getSize() * 2,0, 0, loader, new TextureAttribute(loader.loadTexture("selector")));
                        }
                        case POLICE -> viewModel.getBottomMenuBar().policeButtonAction();
                        case SCHOOL -> {
                            viewModel.getBottomMenuBar().schoolButtonAction();
                            selector = new Selector(Terrain.getSize() * 2, Terrain.getSize(),0, 0, loader, new TextureAttribute(loader.loadTexture("selector")));
                        }
                        case UNIVERSITY -> {
                            viewModel.getBottomMenuBar().universityButton();
                            selector = new Selector(Terrain.getSize() * 2, Terrain.getSize() * 2,0, 0, loader, new TextureAttribute(loader.loadTexture("selector")));
                        }
                        case MONEY -> {
                            viewModel.moneyDisplayManagement(controller, gameModel, moneyTab);
                            if (moneyTab) {
                                moneyTab = false;
                                viewModel.setMoneyMenuActive(false);
                            } else {
                                moneyTab = true;
                                viewModel.setMoneyMenuActive(true);
                            }
                        }
                        case SELECT -> viewModel.getBottomMenuBar().selectButtonAction();
                        case SPEED_PAUSE -> {
                            controller.switchTimeModeRequest(TimeMode.PAUSE);
                            paused = true;
                        }
                        case SPEED_ONE -> {
                            controller.switchTimeModeRequest(TimeMode.DAILY);
                            paused = false;
                        }
                        case SPEED_TWO -> {
                            controller.switchTimeModeRequest(TimeMode.WEEKLY);
                            paused = false;
                        }
                        case SPEED_THREE -> {
                            controller.switchTimeModeRequest(TimeMode.MONTHLY);
                            paused = false;
                        }
                    }
                }
            }
            viewModel.taxIncDecButtons(moneyTab,gameModel);


            if(pausedByMenu)
            {
                if (viewModel.getPauseMenu() != null) {
                    if (viewModel.getPauseMenu().getSaveGameButton().isClicked())
                    {
                        Database.save(gameModel);

                    }
                    exitGame = viewModel.checkExitGame();
                    if (exitGame == true)
                        return true;

                    if (viewModel.checkNewGame())
                    {
                        this.gameModel = new GameModel(worldGrid.getWorldSize(), worldGrid.getWorldSize());
                        this.gameModel.initialize();
                        this.controller = new Controller(gameModel);
                        setWorldGrid();
                    }

                    pausedByMenu = !(viewModel.unpause());
                    paused = pausedByMenu;
                    if (!paused)
                        controller.switchTimeModeRequest(TimeMode.DAILY);



                }
            }

            if (buttonPressed == false && coordsX < worldGrid.getWorldSize() && coordsX >= 0 && coordsY < worldGrid.getWorldSize() && coordsY >= 0) {
                controller.mouseClickRequest(new Coordinate(coordsX, coordsY), this);
            }
        }

        if (Keyboard.isClicked(GLFW_KEY_F3)) {
            if (HUD) {HUD = false;}
            else {HUD = true;}
        }

        if (isGameOver && viewModel.getExit() == null)
        {
            viewModel.gameOverScreen(loader);
            controller.switchTimeModeRequest(TimeMode.PAUSE);

        }
        if (viewModel.getExit() !=null )
        {
            if(viewModel.getExit().isClicked() && Mouse.isLeftButtonPressed())
            {
                return true;
            }
        }

        Mouse.update();
        processAllAssets();

        masterRenderer.render(selector, camera, light);

        if (HUD) {
            guiRenderer.render(viewModel.getButtons(), viewModel.getTabs());
            TextMaster.render();
        }

        loader.clearTextVaos();
        if (paused == false) {
            timer += DisplayManager.getFrameTimeSeconds();
        }
        timer2 += DisplayManager.getFrameTimeSeconds();


        return false;
    }

    /**
     * Helper function which does the preprocessing for all assets and terrains before it is rendered.
     */
    private void processAllAssets() {
        for (Terrain terrain : worldGrid.getTerrainList()) {
            masterRenderer.processTerrain(terrain);
        }

        if(zoneState) {
            for (ZoneTile zone : worldGrid.getZoneList()) {
                masterRenderer.processZoneTiles(zone);
            }
        } else {
            for (Entity zoneBuildable: worldGrid.getZoneBuildableList()) {
                masterRenderer.processEntities(zoneBuildable);
            }
        }

        for (Entity buildable : worldGrid.getBuildableList()) {
            masterRenderer.processEntities(buildable);
        }
    }

    /**
     * Clean up function which deletes the Objects from GPU memory.
     */
    public void cleanUp() {
        masterRenderer.cleanUp();
        TextMaster.cleanUp();
        loader.cleanUp();
        guiRenderer.cleanUp();
    }

    /**
     * Clears the grid of assets and gets the list of new updated assets that need to be used.
     * Calls 2 helper functions
     */
    private void setWorldGrid() {
        worldGrid.clearGrid();
        addBuildablesWorldGrid(gameModel.getFacilityBuildable());
        addZonesWorldGrid(gameModel.getZoneBuildable());
    }

    /**
     * Helper function which loads the buildables to the world grid.
     * @param gameModelBuildables
     */
    private void addBuildablesWorldGrid(List<Buildable> gameModelBuildables) {
        for (Buildable b : gameModelBuildables) {
            worldGrid.addBuildable(b.getCoordinate().getRow(), b.getCoordinate().getCol(), getGridEntity(b));
        }
    }

    /**
     * Helper function which loads the Zones to the world grid.
     * @param gameModelZones
     */
    private void addZonesWorldGrid(List<Buildable> gameModelZones) {
        for (Buildable b : gameModelZones) {
            worldGrid.addZone(b.getCoordinate().getRow(), b.getCoordinate().getCol(), getZoneTile(b));
        }
    }

    /**
     * Helper function for choosing the correct entity.
     * @param buildable
     * @return
     */
    private Entity getGridEntity(Buildable buildable) {
        Entity entity = null;
        switch (buildable.getBuildableType()) {
            case ROAD -> {entity = new Entity(assets.getRoad(), new Vector3f(buildable.getCoordinate().getRow() * Terrain.getSize(), 0, (buildable.getCoordinate().getCol() + 1) * Terrain.getSize()), 0, 0, 0, Assets.ROAD.getScale());}
            case FOREST -> {
                Forest forest = (Forest) buildable;
                entity = new Entity(assets.getForest(forest.getAge()), new Vector3f(buildable.getCoordinate().getRow() * Terrain.getSize(), 0, (buildable.getCoordinate().getCol() + 1) * Terrain.getSize()), 0, 0, 0, Assets.FOREST1.getScale());
            }
            case POLICE -> {entity = new Entity(assets.getPolice(), new Vector3f(buildable.getCoordinate().getRow() * Terrain.getSize(), 0, (buildable.getCoordinate().getCol() + 1) * Terrain.getSize()), 0, 0, 0, Assets.POLICE.getScale());}
            case STADIUM -> {entity = new Entity(assets.getStadium(), new Vector3f(buildable.getCoordinate().getRow() * Terrain.getSize(), 0, (buildable.getCoordinate().getCol() + 2) * Terrain.getSize()), 0, 0, 0, Assets.STADIUM.getScale()*2);}
            case SCHOOL -> {entity = new Entity(assets.getSchool(), new Vector3f(buildable.getCoordinate().getRow() * Terrain.getSize(), 0, (buildable.getCoordinate().getCol() + 1) * Terrain.getSize()), 0, 0, 0, Assets.SCHOOL.getScale());}
            case UNIVERSITY -> {entity = new Entity(assets.getUniversity(), new Vector3f(buildable.getCoordinate().getRow() * Terrain.getSize(), 0, (buildable.getCoordinate().getCol() + 1) * Terrain.getSize()), 0, 0, 0, Assets.UNIVERSITY.getScale());}
        }
        return entity;
    }

    /**
     * Helper function for choosing the correct zone type.
     * @param buildable
     * @return
     */
    private ZoneTile getZoneTile(Buildable buildable) {
        ZoneTile zoneTile = null;
        switch (buildable.getBuildableType()) {
            case RESIDENTIAL -> {zoneTile = getZoneTileHelper(buildable, assets.getResidentialBuilding(), "zones/residentialzonetile", Assets.RESIDENTIAL.getScale());}
            case COMMERCIAL -> {zoneTile = getZoneTileHelper(buildable, assets.getCommercialBuilding(), "zones/commercialzonetile", Assets.COMMERCIAL.getScale());}
            case INDUSTRIAL -> {zoneTile = getZoneTileHelper(buildable, assets.getIndustrialBuilding(), "zones/industrialzonetile", Assets.INDUSTRIAL.getScale());}
        }
        return  zoneTile;
    }

    /**
     * Helper function to the GetZoneTile which is used to set the asset if it is under construction or not
     * @param buildable
     * @param asset
     * @param filename
     * @param scale
     * @return
     */
    private ZoneTile getZoneTileHelper(Buildable buildable, TexturedModel asset, String filename, float scale) {
        Entity entity = null;
        ZoneTile zoneTile = new ZoneTile(buildable.getCoordinate().getRow(), buildable.getCoordinate().getCol(), loader, new TextureAttribute(loader.loadTexture(filename)));
        if (buildable.isUnderConstruction()) {
            entity = new Entity(assets.getConstruction(), new Vector3f(buildable.getCoordinate().getRow() * Terrain.getSize(), 0, (buildable.getCoordinate().getCol() + 1) * Terrain.getSize()), 0, 0, 0, Assets.CONSTRUCTION.getScale());
        } else {
            entity = new Entity(asset, new Vector3f(buildable.getCoordinate().getRow() * Terrain.getSize(), 0, (buildable.getCoordinate().getCol() + 1) * Terrain.getSize()), 0, 0, 0, scale);
        }
        worldGrid.addZoneBuildable(buildable.getCoordinate().getRow(), buildable.getCoordinate().getCol(), entity);
        return zoneTile;
    }

    @Override
    public void updateGridSystem(Coordinate coordinate, Buildable buildable) {
        setWorldGrid();
    }

    @Override
    public void updateBudgetPanel(Budget budget) {
        /*System.out.println("________Callback Inform Budget_________");
        System.out.println("Tax rate: " + budget.getTaxRate());
        System.out.println("Balance: " + budget.getBalance());
        System.out.println("Maintenance fee: " + budget.getTotalMaintenanceFee());
        System.out.println("Tax revenue: " + budget.getRevenue(gameModel));
        System.out.println("Total spend: " + budget.getSpend(gameModel));
        System.out.println("---------------------------------------");*/
    }

    @Override
    public void updateStatisticPanel(Zone zone) {
        ZoneStatistics zoneStatistics = zone.getStatistics();
        System.out.println("________Callback Inform Zone Statistic_________");
        System.out.println("Selected Zone connection: " + zone.isConnected());
        System.out.println("Selected Zone under construction: " + zone.isUnderConstruction());
        System.out.println("Selected Zone type: " + zone.getBuildableType());
        System.out.println("Selected Zone population: " + zoneStatistics.getPopulation());
        System.out.println("Selected Zone capacity: " + zone.getCapacity());
        System.out.println("Selected Zone satisfaction: " + zone.getZoneSatisfaction(gameModel));
        System.out.println("Selected Zone citizens: ");
        List<Citizen> citizens = zone.getCitizens();
        for (Citizen c :
                citizens) {
            System.out.println(c);
            System.out.println("Citizen Satisfaction: " + c.getSatisfaction(gameModel));
        }
        System.out.println("-----------------------------------------------");
        viewModel.createZoneSelector(gameModel, zone);
    }

    @Override
    public void updateDatePanel(Date date) {
        /*System.out.println("________Callback Inform City Date_________");
        System.out.println("City Date: " + date);
        System.out.println("------------------------------------------");*/
    }

    @Override
    public void updateCityStatisticPanel(CityStatistics cityStatistics) {
        viewModel.update();
        /*System.out.println("________Callback Inform City Statistic_________");
        System.out.println("City population: " + cityStatistics.getPopulation(gameModel.getCityRegistry()));
        System.out.println("City satisfaction: " + cityStatistics.getCitySatisfaction());
        System.out.println("-----------------------------------------------");*/
    }

    @Override
    public void shoutLose(boolean isLost) {
        isGameOver = isLost;
        //System.out.println("Game is lost: " + isLost);
    }
}
