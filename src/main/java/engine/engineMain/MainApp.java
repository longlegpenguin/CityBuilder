package engine.engineMain;

import controller.Controller;
import engine.display.DisplayManager;
import engine.entities.Camera;
import engine.entities.Entity;
import engine.entities.Light;
import engine.guis.UiComponent;
import engine.models.RawModel;
import engine.models.TexturedModel;
import engine.objConverter.ModelData;
import engine.objConverter.OBJFileLoader;
import engine.renderEngine.GuiRenderer;
import engine.renderEngine.Loader;
import engine.renderEngine.MasterRenderer;
import engine.terrain.Selector;
import engine.terrain.Terrain;
import engine.textures.UiButton;
import engine.textures.TextureAttribute;
import engine.textures.UiTab;
import engine.tools.Mouse;
import engine.tools.MousePicker;
import engine.world.WorldGrid;
import model.GameModel;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.awt.desktop.SystemEventListener;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class MainApp {

    public static void main(String[] args) {
        DisplayManager.createDisplay();
        Loader loader = new Loader();

        ModelData data = OBJFileLoader.loadOBJ("cube");

        RawModel model = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
        TextureAttribute texture = new TextureAttribute(loader.loadTexture("spiral"));
        TexturedModel texturedModel = new TexturedModel(model, texture);

        Entity entity = new Entity(texturedModel, new Vector3f(0,0, -5), 0, 0, 0, 1);

        WorldGrid worldGrid = new WorldGrid(loader, new TextureAttribute(loader.loadTexture("grass")));

        Selector selector = new Selector(0,0, loader, new TextureAttribute(loader.loadTexture("selector")));

        Camera camera = new Camera(new Vector3f(Terrain.getSize() * worldGrid.getWorldSize() / 4,150,Terrain.getSize() * worldGrid.getWorldSize() / 4));
        Light light = new Light(new Vector3f(Terrain.getSize() * worldGrid.getWorldSize() / 2, 1000, Terrain.getSize() * worldGrid.getWorldSize() / 2), new Vector3f(1,1,1));

        MasterRenderer renderer = new MasterRenderer();

        GuiRenderer guiRenderer = new GuiRenderer((loader));
        MousePicker mousePicker = new MousePicker(camera, renderer.getProjectionMatrix(),worldGrid);
        //ArrayList<UiTab> guiTabs = new ArrayList<UiComponent>();
        ArrayList<UiButton>  guiButtons = new ArrayList<UiButton>();
        //UiTab tab = new UiTab(loader.loadTexture("Tab Background"),new Vector2f(0f,-0.85f), new Vector2f(1f, 0.15f));
        //guiTabs.add(tab);
        UiButton button = new UiButton(loader.loadTexture("Button"),new Vector2f(-0.7f,-0.5f), new Vector2f(0.1f, 0.1f));
        guiButtons.add(button);
        /** --------------------------------------------------------------------
         * Try these methods !
         *         controller.switchModeRequest(GameMode.COMMERCIAL_MODE); // fot the type of new things
         *         controller.mouseClickRequest(new Coordinate(3, 3), null); // for new things onto map
         *         gm.getAllBuildable(); // for a list of buildables
         *         gm.getCurrentDate();
         *         gm.queryCityStatistics();
         *         gm.queryCityBudget();
         *         gm.queryZoneStatistics(new Coordinate(3,3));
         *
         * Try to implement ICallBack interface and pass to mouseClickRequest !
         * It is an option to update a single cell on grid system, when things happen.
         */
        GameModel gm = new GameModel(1000, 1000);
        gm.initialize();
        Controller controller = new Controller(gm);
        /**
         * ----------------------------------------------------------------------
         */

        while(!glfwWindowShouldClose(DisplayManager.window)) {

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
                renderer.processTerrain(terrain);
            }

            renderer.render(entity, selector, camera, light);

            guiRenderer.render(guiButtons);
            button.isClicked();
            DisplayManager.updateDisplay();

        }


        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }


}
