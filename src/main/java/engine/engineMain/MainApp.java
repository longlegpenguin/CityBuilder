package engine.engineMain;

import engine.display.DisplayManager;
import engine.models.RawModel;
import engine.renderEngine.Loader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class MainApp {

    public static void main(String[] args) {
        DisplayManager.createDisplay();



        while(!glfwWindowShouldClose(DisplayManager.window)) {

            DisplayManager.updateDisplay();

        }

        DisplayManager.closeDisplay();
    }


}
