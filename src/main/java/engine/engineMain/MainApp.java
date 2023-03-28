package engine.engineMain;

import engine.display.DisplayManager;
import engine.models.RawModel;
import engine.renderEngine.Loader;
import engine.renderEngine.MasterRenderer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

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
                0,1,3,3,1,2
        };
        RawModel model = loader.loadToVAO(vertices,indices);

        MasterRenderer renderer = new MasterRenderer();

        while(!glfwWindowShouldClose(DisplayManager.window)) {
            renderer.render(model);
            DisplayManager.updateDisplay();
        }
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }


}
