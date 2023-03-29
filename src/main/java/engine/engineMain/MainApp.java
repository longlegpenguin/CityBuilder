package engine.engineMain;

import engine.display.DisplayManager;
import engine.models.RawModel;
import engine.models.TexturedModel;
import engine.renderEngine.Loader;
import engine.renderEngine.MasterRenderer;
import engine.textures.TextureAttribute;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class MainApp {

    public static void main(String[] args) {
        DisplayManager.createDisplay();
        Loader loader = new Loader();

        float[] vertices = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f,0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f
        };

        int[] indices = {
                0,1,3,
                3,1,2
        };

        float[] textureCoords = {
                0,0,
                0,1,
                1,1,
                1,0
        };

        RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
        TextureAttribute texture = new TextureAttribute(loader.loadTexture("spiral"));
        TexturedModel texturedModel = new TexturedModel(model, texture);

        MasterRenderer renderer = new MasterRenderer();

        while(!glfwWindowShouldClose(DisplayManager.window)) {
            renderer.render(texturedModel);
            DisplayManager.updateDisplay();
        }

        DisplayManager.closeDisplay();
    }


}
