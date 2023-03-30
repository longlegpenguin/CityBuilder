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
import engine.terrain.Terrain;
import engine.textures.TextureAttribute;
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
        Terrain terrain = new Terrain(0, 0, loader, new TextureAttribute(loader.loadTexture("grass")));

        Camera camera = new Camera(new Vector3f(0,150,0));
        Light light = new Light(new Vector3f(2000, 2000, 2000), new Vector3f(1,1,1));

        MasterRenderer renderer = new MasterRenderer();



        while(!glfwWindowShouldClose(DisplayManager.window)) {
            camera.move();
            renderer.render(entity, terrain, camera, light);
            DisplayManager.updateDisplay();
        }


        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }


}
