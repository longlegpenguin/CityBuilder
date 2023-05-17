package engine.engineMain;

import engine.display.DisplayManager;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class MainApp {

    public static void main(String[] args) {
        DisplayManager.createDisplay();
        Handler handler = new Handler("Savefile");

        while(!glfwWindowShouldClose(DisplayManager.window)) {

            handler.render();
            DisplayManager.updateDisplay();

        }


        handler.cleanUp();
        DisplayManager.closeDisplay();
    }


}
