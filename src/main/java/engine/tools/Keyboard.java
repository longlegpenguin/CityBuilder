package engine.tools;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * Keyboard class to detect when a key is pressed.
 * Uses the GLFWKeyCallBack to detect.
 */
public class Keyboard extends GLFWKeyCallback {
    private static boolean[] keys = new boolean[65536];

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if (key < 0) {
            return;
        }
        keys[key] = action != GLFW_RELEASE;
    }

    /**
     * Returns whether a key has been pressed.
     * @param keycode
     * @return
     */
    public static boolean isKeyDown(int keycode) {
        return keys[keycode];
    }
}
