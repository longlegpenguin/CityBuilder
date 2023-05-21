package engine.tools;

import engine.display.DisplayManager;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;

/**
 * the Mouse class is designed to get the mouse position in the screen
 * Detects when the mouse buttons create input events and pass them on.
 * Uses GLFW callbacks.
 */
public class Mouse {
    private static float mouseX = 0;
    private static float mouseY = 0;
    private static float prevMouseX = 0;
    private static float prevMouseY = 0;
    private static float deltaX = 0;
    private static float deltaY = 0;
    private static boolean leftButtonPressed, rightButtonPressed;
    private static float dWheel;


    /**
     *
     * @return the mouse position
     */
    public static void createCallbacks() {
        GLFWMouseButtonCallback mouseButtonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                leftButtonPressed = button == 0 && action == 1;
                rightButtonPressed = button == 1 && action == 1;
            }
        };
        GLFWScrollCallback scrollCallback = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                dWheel = (float) yoffset;
            }
        };
        GLFWCursorPosCallback cursorPosCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                prevMouseX = mouseX;
                prevMouseY = mouseY;
                mouseX = (float) xpos;
                mouseY = (float) (DisplayManager.getWindowHeight() - ypos);
            }
        };
        mouseButtonCallback.set(DisplayManager.window);
        scrollCallback.set(DisplayManager.window);
        cursorPosCallback.set(DisplayManager.window);
    }

    /**
     * Reset the distance moved by the mouse.
     */
    public static void update() {
        dWheel = 0;
        deltaX = 0;
        deltaY = 0;
        prevMouseX = mouseX;
        prevMouseY = mouseY;
    }

    public static float getX() {
        return mouseX;
    }

    public static float getY() {
        return mouseY;
    }

    public static float getDX() {
        deltaX = mouseX - prevMouseX;
        return deltaX;
    }

    public static float getDY() {
        deltaY = mouseY - prevMouseY;
        return deltaY;
    }

    public static boolean isLeftButtonPressed() {
        return leftButtonPressed;
    }

    public static boolean isRightButtonPressed() {
        return rightButtonPressed;
    }

    public static float getDWheel() {
        return dWheel;
    }
}
