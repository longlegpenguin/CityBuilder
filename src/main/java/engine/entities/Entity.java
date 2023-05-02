package engine.entities;

import engine.models.TexturedModel;
import org.joml.Vector3f;

/**
 * Represents any 3D entity's textured model, position and rotation within the 3D world.
 *
 * Camera extends this class by having position and rotation, but no textured model.
 */
public class Entity {
    private TexturedModel model;
    protected Vector3f position;

    protected float rotX, rotY, rotZ;
    protected float scale;

    /**
     * Constructor for regular entities which have a TexturedModel.
     * @param model - The textured model of the entity
     * @param position - Position in 3D space
     * @param rotX - X rotation
     * @param rotY - Y rotation
     * @param rotZ Z - rotation
     * @param scale - Size of the Entity relative to it's TexturedModel.
     */
    public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }

    /**
     * Secondary Constructor used by the light and camera to position them in 3D space.
     * Initializes rotation to 0.
     * These 2 Entities have a visible effect on the game and shader's however they are not visible in game themselves.
     * @param position - Position in 3D space
     */
    public Entity(Vector3f position) {
        this.position = position;
        this.rotX = 0;
        this.rotY = 0;
        this.rotZ = 0;
    }

    /**
     * @return TexturedModel of the Entity
     */
    public TexturedModel getModel() {
        return model;
    }

    /**
     * @param model - TexturedModel to which the Entity will be set
     */
    public void setModel(TexturedModel model) {
        this.model = model;
    }

    /**
     * @return the position of the Entity in 3D space as a Vector3f
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * @param position - Position in 3D space to which the Entity will be set to
     */
    public void setPosition(Vector3f position) {
        this.position = position;
    }

    /**
     * @return X rotation as a float
     */
    public float getRotX() {
        return rotX;
    }

    /**
     * @param rotX set X rotation
     */
    public void setRotX(float rotX) {
        this.rotX = rotX;
    }

    /**
     * @return Y rotation as a float
     */
    public float getRotY() {
        return rotY;
    }

    /**
     * @param rotY set Y rotation
     */
    public void setRotY(float rotY) {
        this.rotY = rotY;
    }

    /**
     * @return get Z rotation as a float
     */
    public float getRotZ() {
        return rotZ;
    }

    /**
     * @param rotZ Set the Z rotation
     */
    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }

    /**
     * @return The scale as a float
     */
    public float getScale() {
        return scale;
    }

    /**
     * @param scale set the scale
     */
    public void setScale(float scale) {
        this.scale = scale;
    }

    /**
     * Increase the position of the Entity by the addition of the Vector3f of x, y and z
     * @param dx
     * @param dy
     * @param dz
     */
    public void increasePosition(float dx, float dy, float dz) {
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }

    /**
     * Increase the rotation of the Entity by the addition of the Vector3f of dx, dy and dz
     * @param dx
     * @param dy
     * @param dz
     */
    public void increaseRotation(float dx, float dy, float dz) {
        this.rotX += dx;
        this.rotY += dy;
        this.rotZ += dz;
    }
}
