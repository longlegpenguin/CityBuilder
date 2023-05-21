package engine.display;

import engine.tools.Keyboard;
import engine.tools.Mouse;
import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * This class contains all methods needed to set up, maintain and close a LWJGL display
 */
public class DisplayManager {
    private static int WIDTH = 1280;
    private static int HEIGHT = 720;
    private static boolean RESIZED = false;
    private static final String TITLE = "Utopia";

    private static long lastFrameTime;
    private static float delta;

    //Window ID
    public static long window;

    public static Keyboard keyboard = new Keyboard();
    public Mouse mouse = new Mouse();
    private static GLFWWindowSizeCallback windowSizeCallback;

    /**
     * Creates a display windows on which the game can be rendered.
     * Sets up the GLFW Properties needed to maintain the display as well as the size and other attributes such as v-sync
     * GLFW: https://javadoc.lwjgl.org/org/lwjgl/glfw/GLFW.html
     */
    public static void createDisplay() {
        System.out.println("LWJGL " + Version.getVersion());

        GLFWErrorCallback.createPrint(System.err).set();

        //Initialization of GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        //Configuration settings of GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2); //GLFW Version 3.2
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE); //Set Forward Compatability to true
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_COMPAT_PROFILE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); //Window is hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); //Window can be resized

        //Creation of the window
        window = glfwCreateWindow(WIDTH, HEIGHT, TITLE, NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        Mouse.createCallbacks();
        glfwSetKeyCallback(window, keyboard);


        //Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            //Getting the window size which was used to create the window
            glfwGetWindowSize(window, pWidth, pHeight);

            //Get the resolution of the primary monitor
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            //Center the window
            glfwSetWindowPos(window, (vidMode.width() - pWidth.get(0)) / 2, (vidMode.height() - pHeight.get(0)) / 2);
        } //Stack frame is popped automatically

        //Makes the window the current OpenGL Context
        glfwMakeContextCurrent(window);

        //Enable v-sync
        glfwSwapInterval(1);

        //Makes the window visible
        glfwShowWindow(window);

        //Creates GLCapabilities instance and makes OpenGL bindings available for use
        GL.createCapabilities();

        //initialize the lastFrameTime
        lastFrameTime = getCurrentTime();

        setLocalCallbacks();
    }

    public static boolean isRESIZED() {
        return RESIZED;
    }

    public static void setRESIZED(boolean RESIZED) {
        DisplayManager.RESIZED = RESIZED;
    }

    public static void resize() {
        GL11.glViewport(0,0,WIDTH, HEIGHT);
    }

    public static void setWIDTH(int WIDTH) {
        DisplayManager.WIDTH = WIDTH;
    }

    public static void setHEIGHT(int HEIGHT) {
        DisplayManager.HEIGHT = HEIGHT;
    }

    /**
     * Updates the display every frame showing any changes made by the RenderEngine
     */
    public static void updateDisplay() {
        glfwSwapBuffers(window); //Swaps the color buffers
        glfwPollEvents(); //Polls for window events

        //Calculation to get the time between frames which is used for time reliant systems in the game
        long currentFrameTime = getCurrentTime();
        delta = (currentFrameTime - lastFrameTime)/1000f;
        lastFrameTime = currentFrameTime;
    }

    /**
     * @return window width
     */
    public static int getWindowWidth() {
        IntBuffer w = BufferUtils.createIntBuffer(1);
        glfwGetWindowSize(window, w, null);
        return w.get(0);
    }

    /**
     * @return window height
     */
    public static int getWindowHeight() {
        IntBuffer h = BufferUtils.createIntBuffer(1);
        glfwGetWindowSize(window, null, h);
        return h.get(0);
    }

    /**
     * Free the window callbacks
     * Destroy the window
     * Terminate GLFW and error callback
     */
    public static void closeDisplay() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    /**
     * @return long value of the current time in seconds.
     */
    private static long getCurrentTime() {
        return (long) (glfwGetTime() * 1000);
    }

    /**
     * @return delta - time in between frames in seconds.
     */
    public static float getFrameTimeSeconds() {
        return delta;
    }

    private static void setLocalCallbacks() {
        windowSizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long l, int i, int i1) {
                DisplayManager.setWIDTH(i);
                DisplayManager.setHEIGHT(i1);
                DisplayManager.setRESIZED(true);
            }
        };

        glfwSetWindowSizeCallback(window, windowSizeCallback);
    }
}

