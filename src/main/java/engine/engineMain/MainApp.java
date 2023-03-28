package engine.engineMain;

import engine.display.DisplayManager;
import engine.models.RawModel;
import engine.renderEngine.Loader;
import engine.renderEngine.MasterRenderer;

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



        MasterRenderer renderer = new MasterRenderer();

        while(!glfwWindowShouldClose(DisplayManager.window)) {
            renderer.render(model);
            DisplayManager.updateDisplay();
        }

        DisplayManager.closeDisplay();
    }


}
