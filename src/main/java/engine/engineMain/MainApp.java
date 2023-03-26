package engine.engineMain;

import engine.display.DisplayManager;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
public class MainApp {

    public static void main(String[] args) {
        DisplayManager.createDisplay();

        while(!glfwWindowShouldClose(DisplayManager.window)) {
            DisplayManager.updateDisplay();
        }
        System.out.println("Hello, merge");
        DisplayManager.closeDisplay();;

    }
}
