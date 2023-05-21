package engine.guis;

import org.joml.Vector2f;

/**
 * Parent class for UI components on screen such as tabs, buttons and text fields.
 */
public abstract class UiComponent {
    protected Vector2f position;
    protected Vector2f scale;

    protected int texture;
    protected  boolean isClickable;

    /**
     * Constructor of UI Component.
     * @param texture Texture ID of the texture
     * @param position Vector2f of the position
     * @param scale Vector2f Scale of the component
     */
    public UiComponent(int texture ,Vector2f position ,Vector2f scale) {
        this.texture = texture;
        this.position = position;
        this.scale = scale;

    }

    /**
     * @return Vector2f of components centre position
     */
    public Vector2f getPosition() {
        return position;
    }

    /**
     * @return Vector2f of components scale in X and Y direction
     */
    public Vector2f getScale() {
        return scale;
    }

    /**
     * @return int value of components Texture value.
     */
    public int getTexture() {
        return texture;
    }
}
