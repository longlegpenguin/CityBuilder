package engine.engineMain;

import engine.display.DisplayManager;

import static org.lwjgl.glfw.GLFW.*;

public class MainApp {

    public static void main(String[] args) {
        DisplayManager.createDisplay();
        Handler handler = new Handler("Savefile");
        Boolean ended;
        while(!glfwWindowShouldClose(DisplayManager.window)) {

            ended = handler.render();
            DisplayManager.updateDisplay();

            if (ended == true) break;
        }


        handler.cleanUp();
        DisplayManager.closeDisplay();
    }


}
