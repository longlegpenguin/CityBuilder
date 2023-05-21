package engine.tools;

import engine.entities.Camera;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * Class used to create the Matrices needed by the shaders for calculations.
 */
public class Maths {

    /**
     * Transformation matrix is calculated by the change in position of the 2D components within the 3D world.
     * @param translation
     * @param scale
     * @return
     */
    public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.identity();
        matrix.translate(new Vector3f(translation.x, translation.y, 0));
        matrix.scale(new Vector3f(scale.x, scale.y, 1f));
        return matrix;
    }

    /**
     * Transformation matrix is calculated by the change in position of the 3D objects within the 3D world.
     * @param translation
     * @param rx
     * @param ry
     * @param rz
     * @param scale
     * @return
     */
    public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.identity();
        matrix.translate(translation);
        matrix.rotate((float) Math.toRadians(rx), new Vector3f(1,0,0));
        matrix.rotate((float) Math.toRadians(ry), new Vector3f(0,1,0));
        matrix.rotate((float) Math.toRadians(rz), new Vector3f(0,0,1));
        matrix.scale(new Vector3f(scale, scale, scale));
        return matrix;
    }

    /**
     * ViewMatrix is calculated with the position of the camera withing the 3D world
     * @param camera
     * @return
     */
    public static Matrix4f createViewMatrix(Camera camera) {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.identity();
        viewMatrix.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0));
        viewMatrix.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0));
        Vector3f cameraPos = camera.getPosition();
        Vector3f negativeCameraPos = new Vector3f(-cameraPos.x,-cameraPos.y,-cameraPos.z);
        viewMatrix.translate(negativeCameraPos);
        return viewMatrix;
    }
}
