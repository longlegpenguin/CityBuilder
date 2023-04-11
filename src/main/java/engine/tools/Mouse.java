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

/*import engine.display.DisplayManager;
import org.joml.Vector2d;
import org.joml.Vector2f;
import static org.lwjgl.glfw.GLFW.*;

public class Mouse {

    private static final Vector2d previousPos = new Vector2d(-1, 1);
    private static final Vector2d currentPos = new Vector2d(0,0);
    private static final Vector2f displVec = new Vector2f();
    private static float dWheel;
    private static boolean inWindow = false;
    private static boolean lefButtonPressed = false;
    private static boolean rightButtonPressed = false;


    public static void init() {
        glfwSetCursorPosCallback(DisplayManager.window, (DisplayManager, xpos, ypos)-> {
            currentPos.x = xpos;
            currentPos.y = ypos;
        });

        glfwSetCursorEnterCallback(DisplayManager.window, (DisplayManager, entered) -> {
           inWindow = entered;
        });

        glfwSetMouseButtonCallback(DisplayManager.window, (DisplayManager, button, action, mode) -> {
           lefButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
           rightButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
        });

        glfwSetScrollCallback(DisplayManager.window, (DisplayManager, xoffset, yoffset) -> {
            dWheel = (float) yoffset;
        });
    }

    public static float getX() {
        return (float) currentPos.x;
    }

    public static float getY() {
        return (float) currentPos.y;
    }

    public static Vector2f getDisplVec() {
        return  displVec;
    }

    public static void update() {
        displVec.x = 0;
        displVec.y = 0;

        if (previousPos.x > 0 && previousPos.y > 0 && inWindow) {
            double deltax = currentPos.x - previousPos.x;
            double deltay = currentPos.y - previousPos.y;
            boolean rotateX = deltax != 0;
            boolean rotateY = deltay != 0;

            if (rotateX) {
                displVec.y = (float) deltax;
            }
            if (rotateY) {
                displVec.x = (float) deltay;
            }
        }
        previousPos.x = currentPos.x;
        previousPos.y = currentPos.y;
    }

    public static boolean isLefButtonPressed() {
        return lefButtonPressed;
    }

    public static boolean isRightButtonPressed() {
        return rightButtonPressed;
    }

    public static float getDWheel() {
        return dWheel;
    }

}*/
