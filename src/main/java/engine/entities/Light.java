package engine.entities;

import org.joml.Vector3f;

/**
 * Light class is used by the shaders to illuminate all objects in the world.
 */
public class Light {
    private Vector3f position;
    private Vector3f color;

    /**
     * Constructor sets the position and color of the Light
     * @param position
     * @param color
     */
    public Light(Vector3f position, Vector3f color) {
        this.position = position;
        this.color = color;
    }

    /**
     * @return the position in 3D space of the light
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * @param position - the position to set the light
     */
    public void setPosition(Vector3f position) {
        this.position = position;
    }

    /**
     * @return the color of the light in RGB format
     */
    public Vector3f getColor() {
        return color;
    }

    /**
     * @param color set the color of the light in RGB format
     */
    public void setColor(Vector3f color) {
        this.color = color;
    }
}
