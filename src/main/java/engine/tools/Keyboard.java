package engine.tools;

import org.lwjgl.glfw.GLFWKeyCallback;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * Keyboard class to detect when a key is pressed.
 * Uses the GLFWKeyCallBack to detect.
 */
public class Keyboard extends GLFWKeyCallback {
    private static boolean[] keys = new boolean[65536];
    private static boolean[] keys2 = new boolean[65536];
    private static List<Integer> pressed = new ArrayList<Integer>();

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if (key < 0) {
            return;
        }
        keys[key] = action != GLFW_RELEASE;
        keys2[key] = action == GLFW_RELEASE;
    }

    /**
     * Returns whether a key has been pressed.
     * @param keycode
     * @return
     */
    public static boolean isKeyDown(int keycode) {
        return keys[keycode];
    }

    public static boolean isClicked(int keycode) {
        if (keys2[keycode]) {
            keys2[keycode] =false;
            return true;
        }
        return false;
    }
}

