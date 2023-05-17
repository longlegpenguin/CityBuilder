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
import model.city.CityStatistics;
import model.common.Budget;
import model.common.Buildable;
import model.common.Citizen;
import model.common.Coordinate;
import model.util.Date;
import model.zone.Zone;
import model.zone.ZoneStatistics;
import org.joml.Vector2f;
import org.joml.Vector3f;
import view.MoneyStatistic;
import view.ViewModel;
import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    private MoneyStatistic money;


    private GUIText text;

    private int counter = 0;

    private float mouseDelay = 0f;
    private float multiplier = 1;
    private float timer = 0;
    private float timer2 = 0;
    private String date = "";

    private GUIText framerate;
    private GUIText frametime;

    ArrayList<UiButton> guiButtons = new ArrayList<UiButton>();

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

        TextMaster.init(loader);
        viewModel = new ViewModel(controller,gameModel);
        this.date = gameModel.getCurrentDate().toString();

        this.money = new MoneyStatistic(controller,gameModel);

        text = new GUIText(this.date,1,new Vector2f(0.025f,0.885f),1f,false);
        text.setColour(0,0,0);
        TextMaster.loadText(text);

        frametime = new GUIText("FT (ms): ", 0.9f, new Vector2f(0.025f, 0.025f), 1, false);
        frametime.setColour(0,0,0);
        TextMaster.loadText(frametime);

        framerate = new GUIText("FPS: ", 0.9f, new Vector2f(0.025f, 0.05f), 1, false);
        framerate.setColour(0,0,0);
        TextMaster.loadText(framerate);


        setWorldGrid();
    }

    public void render() {

        if (timer2 >= 0.1f) {
            TextMaster.removeText(frametime);
            TextMaster.removeText(framerate);

            frametime.setTextString("FT (ms): " + DisplayManager.getFrameTimeSeconds() * 1000);
            TextMaster.loadText(frametime);

            framerate.setTextString("FPS: " + 1 / DisplayManager.getFrameTimeSeconds());
            TextMaster.loadText(framerate);
            timer2 -= 0.1f;
        }

        if (timer >= 3f / multiplier) {
            TextMaster.removeText(text);
            controller.regularUpdateRequest(1, this);
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

        boolean buttonPressed = false;
        if (mouseDelay == 0f && Mouse.isLeftButtonPressed()) {
            mouseDelay = 0.1f;
            for (UiButton button: viewModel.getButtons()) {
                if (button.isClicked()) {
                    buttonPressed = true;
                    switch (button.getButtonEnum()) {
                        case RESIDENTIAL_ZONE -> viewModel.getBottomMenuBar().resZoneButtonAction();
                        case COMMERICAL_ZONE -> viewModel.getBottomMenuBar().comZoneButtonAction();
                        case INDUSTRIAL_ZONE -> viewModel.getBottomMenuBar().indZoneButtonAction();
                        case DE_ZONE -> viewModel.getBottomMenuBar().deZoneButtonAction();
                        case ROAD -> viewModel.getBottomMenuBar().roadButtonAction();
                        case FOREST -> viewModel.getBottomMenuBar().forestButtonAction();
                        case STADIUM -> viewModel.getBottomMenuBar().stadiumButtonAction();
                        case POLICE -> viewModel.getBottomMenuBar().policeButtonAction();
                        case SCHOOL -> viewModel.getBottomMenuBar().schoolButtonAction();
                        case UNIVERSITY -> viewModel.getBottomMenuBar().universityButton();
//                        case DESTROY -> viewModel.getBottomMenuBar().destroyButtonAction();
                        case DESTROY -> viewModel.getBottomMenuBar().selectButtonAction();
                    }
                }
            }
            if (buttonPressed == false && coordsX < worldGrid.getWorldSize() && coordsX >= 0 && coordsY < worldGrid.getWorldSize() && coordsY >= 0) {
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


        processAllAssets();


        masterRenderer.render(selector, camera, light);
        guiRenderer.render(viewModel.getButtons(),viewModel.getTabs());

        TextMaster.render();
        loader.clearTextVaos();


        timer += DisplayManager.getFrameTimeSeconds();
        timer2 += DisplayManager.getFrameTimeSeconds();
    }

    public void processAllAssets() {
        for (Terrain terrain: worldGrid.getTerrainList()) {
            masterRenderer.processTerrain(terrain);
        }


        for (Entity zone: worldGrid.getZoneList()) {
            masterRenderer.processEntities(zone);
        }


        for (Entity buildable: worldGrid.getBuildableList()) {
            masterRenderer.processEntities(buildable);
        }
    }

    public void cleanUp() {
        masterRenderer.cleanUp();
        TextMaster.cleanUp();
        loader.cleanUp();

    }

    public void setWorldGrid() {
        worldGrid.clearGrid();
        addBuildablesWorldGrid(gameModel.getFacilityBuildable());
        addZonesWorldGrid(gameModel.getZoneBuildable());
    }

    private void addBuildablesWorldGrid(List<Buildable> gameModelBuildables) {
        for (Buildable b: gameModelBuildables) {
            worldGrid.addBuildable(b.getCoordinate().getRow(), b.getCoordinate().getCol(), getGridEntity(b));
        }
    }

    private void addZonesWorldGrid(List<Buildable> gameModelZones) {
        for (Buildable b: gameModelZones) {
            worldGrid.addBuildable(b.getCoordinate().getRow(), b.getCoordinate().getCol(), getGridEntity(b));
        }
    }

    private Entity getGridEntity(Buildable buildable) {
        Entity entity = null;
        switch (buildable.getBuildableType()) {
            case RESIDENTIAL -> {
                entity = new Entity(assets.getResidentialBuilding(), new Vector3f(buildable.getCoordinate().getRow() * Terrain.getSize(),0,(buildable.getCoordinate().getCol() + 1) *Terrain.getSize()), 0,0,0,5);
                break;
            }
            case COMMERCIAL -> {
                entity = new Entity(assets.getCommercialBuilding(), new Vector3f(buildable.getCoordinate().getRow() * Terrain.getSize(),0,(buildable.getCoordinate().getCol() + 1) *Terrain.getSize()), 0,0,0,5);
                break;
            }
            case INDUSTRIAL -> {
                entity = new Entity(assets.getIndustrialBuilding(), new Vector3f(buildable.getCoordinate().getRow() * Terrain.getSize(),0,(buildable.getCoordinate().getCol() + 1) *Terrain.getSize()), 0,0,0,5);
                break;
            }
            case ROAD -> {
                entity = new Entity(assets.getRoad(), new Vector3f(buildable.getCoordinate().getRow() * Terrain.getSize(),0,(buildable.getCoordinate().getCol() + 1) *Terrain.getSize()), 0,0,0,5);
                break;
            }
            case FOREST -> {
                entity = new Entity(assets.getForest(), new Vector3f(buildable.getCoordinate().getRow() * Terrain.getSize(),0,(buildable.getCoordinate().getCol() + 1) *Terrain.getSize()), 0,0,0,5);
                break;
            }
            case POLICE -> {
                entity = new Entity(assets.getPolice(), new Vector3f(buildable.getCoordinate().getRow() * Terrain.getSize(),0,(buildable.getCoordinate().getCol() + 1) *Terrain.getSize()), 0,0,0,5);
                break;
            }
            case STADIUM -> {
                entity = new Entity(assets.getStadium(), new Vector3f(buildable.getCoordinate().getRow() * Terrain.getSize(),0,(buildable.getCoordinate().getCol() + 2) *Terrain.getSize()), 0,0,0,5*2);
                break;
            }
            case SCHOOL -> {
                entity = new Entity(assets.getSchool(), new Vector3f(buildable.getCoordinate().getRow() * Terrain.getSize(),0,(buildable.getCoordinate().getCol() + 1) *Terrain.getSize()), 0,0,0,5);
                break;
            }
            case UNIVERSITY -> {
                entity = new Entity(assets.getUniversity(), new Vector3f(buildable.getCoordinate().getRow() * Terrain.getSize(),0,(buildable.getCoordinate().getCol() + 1) *Terrain.getSize()), 0,0,0,5);
                break;
            }
        }
        return entity;
    }

    @Override
    public void updateGridSystem(Coordinate coordinate, Buildable buildable) {
        setWorldGrid();
        /*Entity entity = null;
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
        worldGrid.addBuildable(mousePicker.getCurrentTileCoords().x, mousePicker.getCurrentTileCoords().y, entity);*/
    }

    @Override
    public void updateBudgetPanel(Budget budget) {
//        System.out.println("________Callback Inform Budget_________");
//        System.out.println("Tax rate: " + budget.getTaxRate());
//        System.out.println("Balance: " + budget.getBalance());
//        System.out.println("Maintenance fee: " + budget.getTotalMaintenanceFee());
//        System.out.println("Tax revenue: " + budget.getRevenue(gameModel));
//        System.out.println("---------------------------------------");
    }

    @Override
    public void updateStatisticPanel(Zone zone) {
        ZoneStatistics zoneStatistics = zone.getStatistics();
        System.out.println("________Callback Inform Zone Statistic_________");
        System.out.println("Selected Zone connection: " + zone.isConnected());
        System.out.println("Selected Zone population: " + zoneStatistics.getPopulation());
        System.out.println("Selected Zone capacity: " + zoneStatistics.getCapacity());
        System.out.println("Selected Zone capacity: " + zone.getLevel().getCapacity());
        System.out.println("Selected Zone satisfaction: " + zoneStatistics.getSatisfaction());
        System.out.println("Selected Zone citizens: ");
        List<Citizen> citizens = zone.getCitizens();
        for (Citizen c :
                citizens) {
            System.out.println(c);
        }
        System.out.println("-----------------------------------------------");
    }

    @Override
    public void updateDatePanel(Date date) {
        this.date = date.toString();
        text.setTextString(this.date);
        TextMaster.loadText(text);

        System.out.println("________Callback Inform City Date_________");
        System.out.println("City Date: " + date);
        System.out.println("------------------------------------------");
    }

    @Override
    public void updateCityStatisticPanel(CityStatistics cityStatistics) {
//        System.out.println("________Callback Inform City Statistic_________");
//        System.out.println("City population: " + cityStatistics.getPopulation(gameModel.getCityRegistry()));
//        System.out.println("City satisfaction: " + cityStatistics.getCitySatisfaction());
//        System.out.println("-----------------------------------------------");

        for (GUIText t: viewModel.getTexts()) {
            TextMaster.removeText(t);
        }
        viewModel.init(gameModel, cityStatistics);
    }
}
