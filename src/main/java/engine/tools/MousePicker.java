package engine.tools;

import engine.display.DisplayManager;
import engine.entities.Camera;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class MousePicker {
    private Vector3f currentRay;

    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;
    private Camera camera;

    public MousePicker(Camera camera, Matrix4f projectionMatrix) {
        this.camera = camera;
        this.projectionMatrix = projectionMatrix;
        this.viewMatrix = Maths.createViewMatrix(camera);
    }

    public Vector3f getCurrentRay() {
        return currentRay;
    }

    public void update() {
        viewMatrix = Maths.createViewMatrix(camera);
        currentRay = calculateMouseRay();
    }

    private Vector3f calculateMouseRay() {
        float mouseX = Mouse.getX();
        float mouseY = Mouse.getY();
        Vector2f normalizedDeviceCoords = getNormalizedDeviceCoords(mouseX, mouseY);
        Vector4f clipCoords = new Vector4f(normalizedDeviceCoords.x, normalizedDeviceCoords.y, -1f, 1f);
        Vector4f eyeCoords = toEyeCoords(clipCoords);
        Vector3f worldRay = toWorldCoords(eyeCoords);
        return worldRay;
    }

    private Vector2f getNormalizedDeviceCoords(float mouseX, float mouseY) {
        float x = (2f * mouseX) / DisplayManager.getWindowWidth() - 1f;
        float y = (2f * mouseY) / DisplayManager.getWindowHeight() - 1f;
        return  new Vector2f(x, y);
    }

    private Vector4f toEyeCoords(Vector4f clipCoords) {
        Matrix4f invertedProjection = projectionMatrix; // Matrix4f.invert(projectionMatrix, null);
        invertedProjection.invert();
        Vector4f eyeCoords = new Vector4f(); //Matrix4f.transform(invertedProjection, clipCoords, null);
        invertedProjection.transform(clipCoords, eyeCoords);
        return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
    }

    private Vector3f toWorldCoords(Vector4f eyeCoords) {
        Matrix4f invertedView = viewMatrix;
        invertedView.invert();
        //= Matrix4f.invert(viewMatrix, null);
        Vector4f rayWorld = new Vector4f();
        invertedView.transform(eyeCoords, rayWorld);
        // = Matrix4f.transform(invertedView, eyeCoords, null);
        Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
        mouseRay.normalize();
        return mouseRay;
    }
}
