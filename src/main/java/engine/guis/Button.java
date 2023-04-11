package engine.guis;
import engine.textures.Texture;
import engine.textures.TextureAttribute;
import engine.tools.Mouse;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;



public class Button {
    private long window;
    private float x;
    private float y;
    private float width;
    private float height;
    private TextureAttribute texture;
    private boolean isClicked;
    private GLFWMouseButtonCallback buttonCallback;

    public Button(long window, float x, float y, float width, float height, TextureAttribute texture) {
        this.window = window;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texture = texture;
        this.isClicked = false;

        // Set up the mouse button callback
        buttonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                double mouseX = Mouse.getX();
                double mouseY = Mouse.getDY();

                if (Mouse.isLeftButtonPressed() && mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
                    isClicked = true;
                }
            }
        };
        GLFW.glfwSetMouseButtonCallback(window, buttonCallback);
    }

    public boolean isClicked() {
        if (isClicked) {
            isClicked = false;
            return true;
        }
        else return false;
    }
}