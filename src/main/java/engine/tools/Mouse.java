package engine.tools;

import engine.display.DisplayManager;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

/**
 * the Mouse class is designed to get the mouse position in the screen
 */
public class Mouse {
    private static float mouseX, mouseY, prevMouseX, prevMouseY;
    private static boolean leftButtonPressed, rightButtonPressed;
    private static float dWheel;

    /**
     *
     * @return the mouse position
     */
    public static GLFWMouseButtonCallback createCallbacks() {
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
        return mouseButtonCallback;
    }

    public static void update() {
        dWheel = 0;
    }

    public static float getX() {
        return mouseX;
    }

    public static float getY() {
        return mouseY;
    }

    public static float getDX() {
        return mouseX - prevMouseX;
    }

    public static float getDY() {
        return mouseY - prevMouseY;
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
