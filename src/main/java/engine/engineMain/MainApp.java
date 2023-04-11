package engine.engineMain;

import engine.display.DisplayManager;
import engine.entities.Camera;
import engine.entities.Entity;
import engine.entities.Light;
import engine.models.RawModel;
import engine.models.TexturedModel;
import engine.objConverter.ModelData;
import engine.objConverter.OBJFileLoader;
import engine.renderEngine.Loader;
import engine.renderEngine.MasterRenderer;
import engine.terrain.Selector;
import engine.terrain.Terrain;
import engine.textures.TextureAttribute;
import engine.tools.Mouse;
import engine.tools.MousePicker;
import engine.world.WorldGrid;
import org.joml.Vector3f;
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

        MousePicker mousePicker = new MousePicker(camera, renderer.getProjectionMatrix(),worldGrid);

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
            DisplayManager.updateDisplay();
        }


        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }


}
