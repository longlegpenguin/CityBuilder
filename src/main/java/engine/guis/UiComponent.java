package engine.guis;

import engine.display.DisplayManager;
import engine.textures.TextureAttribute;
import engine.tools.Mouse;
import org.joml.Vector2f;

public abstract class UiComponent {
    protected final long window = DisplayManager.window;
    protected Vector2f position;
    protected Vector2f scale;

    protected int texture;
    protected  boolean isClickable;


    public UiComponent(int texture ,Vector2f position ,Vector2f scale) {
        this.texture = texture;
        this.position = position;
        this.scale = scale;

    }



    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getScale() {
        return scale;
    }

    public int getTexture() {
        return texture;
    }


}
