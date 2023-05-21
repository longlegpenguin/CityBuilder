package engine.tools;

import engine.display.DisplayManager;
import engine.entities.Camera;
import engine.terrain.Terrain;
import engine.world.Tile;
import engine.world.WorldGrid;
import org.joml.*;

/**
 * Mousepicker class is used to know with which Tile in the world the mouse is intersecting.
 * This is done by reversing the process in which the mouse position is calculated by going backwards from 3D ray to position in 2D space.
 */
public class MousePicker {

    private static final int RECURSION_COUNT = 200;
    private static final float RAY_RANGE = 800;
    private Vector3f currentRay;

    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;
    private Camera camera;

    private Terrain terrain;
    Tile[][] worldMatrix;
    WorldGrid worldGrid;
    private Vector3f currentTerrainPoint;


    /**
     * Instantiated and takes in the cameram, WorldGrid and projection matrix.
     * @param camera
     * @param projectionMatrix
     * @param worldGrid
     */
    public MousePicker(Camera camera, Matrix4f projectionMatrix, WorldGrid worldGrid) {
        this.camera = camera;
        this.projectionMatrix = projectionMatrix;
        this.viewMatrix = Maths.createViewMatrix(camera);
        this.worldGrid = worldGrid;
        this.worldMatrix = this.worldGrid.getWorldmatrix();
    }

    public Vector3f getCurrentRay() {
        return currentRay;
    }

    public Vector3f getCurrentTerrainPoint() {
        return currentTerrainPoint;
    }

    /**
     * Gets the current tile on which the mouse is hovering.
     * @return
     */
    public Vector2i getCurrentTileCoords() {
        if (currentTerrainPoint == null) {
            return null;
        }
        int x = (int) (currentTerrainPoint.x / Terrain.getSize());
        int z = (int) (currentTerrainPoint.z / Terrain.getSize());
        return new Vector2i(x, z);
    }

    /**
     * Updates variables in the mousepicker class with the new ones when called each frame.
     */
    public void update() {
        viewMatrix = Maths.createViewMatrix(camera);
        currentRay = calculateMouseRay();
        if (intersectionInRange(0, RAY_RANGE, currentRay)) {
            currentTerrainPoint = binarySearch(0, 0, RAY_RANGE, currentRay);
        } else {
            currentTerrainPoint = null;
        }
    }

    /**
     * Method which calls all the methods needed to calculate the ray.
     * @return
     */
    private Vector3f calculateMouseRay() {
        float mouseX = Mouse.getX();
        float mouseY = Mouse.getY();
        Vector2f normalizedDeviceCoords = getNormalizedDeviceCoords(mouseX, mouseY);
        Vector4f clipCoords = new Vector4f(normalizedDeviceCoords.x, normalizedDeviceCoords.y, -1f, 1f);
        Vector4f eyeCoords = toEyeCoords(clipCoords);
        Vector3f worldRay = toWorldCoords(eyeCoords);
        return worldRay;
    }

    /**
     * Normalizes the position coordinates to the system used by OpenGL to render and the DisplayManager.
     * @param mouseX
     * @param mouseY
     * @return
     */
    public Vector2f getNormalizedDeviceCoords(float mouseX, float mouseY) {
        float x = (2f * mouseX) / DisplayManager.getWindowWidth() - 1f;
        float y = (2f * mouseY) / DisplayManager.getWindowHeight() - 1f;
        return  new Vector2f(x, y);
    }

    /**
     * Calculates the coords before the projection matrix was applied.
     * @param clipCoords
     * @return
     */
    private Vector4f toEyeCoords(Vector4f clipCoords) {
        Matrix4f invertedProjection = new Matrix4f();
        projectionMatrix.invert(invertedProjection);
        Vector4f eyeCoords = new Vector4f();
        invertedProjection.transform(clipCoords, eyeCoords);
        return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
    }

    /**
     * Calculates the position coords before the viewmatrix was applied.
     * @param eyeCoords
     * @return
     */
    private Vector3f toWorldCoords(Vector4f eyeCoords) {
        Matrix4f invertedView = viewMatrix;
        invertedView.invert();
        Vector4f rayWorld = new Vector4f();
        invertedView.transform(eyeCoords, rayWorld);
        Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
        mouseRay.normalize();
        return mouseRay;
    }

    /**
     * Finds where the Camera sits on the ray.
     * @param ray
     * @param distance
     * @return
     */
    private Vector3f getPointOnRay(Vector3f ray, float distance) {
        Vector3f cameraPos = camera.getPosition();
        Vector3f start = new Vector3f(cameraPos.x, cameraPos.y, cameraPos.z);
        Vector3f scaledRay = new Vector3f(ray.x * distance, ray.y * distance, ray.z * distance);
        Vector3f pointOnRay = new Vector3f();
        start.add(scaledRay, pointOnRay);
        return pointOnRay;
    }

    /**
     * Searches where the ray intersects the tile or terrain.
     * @param count
     * @param start
     * @param finish
     * @param ray
     * @return
     */
    private Vector3f binarySearch(int count, float start, float finish, Vector3f ray) {
        float half = start + ((finish - start) / 2f);
        if (count >= RECURSION_COUNT) {
            Vector3f endPoint = getPointOnRay(ray, half);
            Terrain terrain = getTerrain(ray.x, endPoint.z);
            if (terrain != null) {
                return endPoint;
            } else {
                return null;
            }
        }
        if (intersectionInRange(start, half, ray)) {
            return binarySearch(count + 1, start, half, ray);
        } else {
            return binarySearch(count+1, half, finish, ray);
        }
    }

    /**
     * Checks if the intersection is within the intersection range specified.
     * @param start
     * @param finish
     * @param ray
     * @return
     */
    private boolean intersectionInRange(float start, float finish, Vector3f ray) {
        Vector3f startPoint = getPointOnRay(ray, start);
        Vector3f endPoint = getPointOnRay(ray, finish);
        if (!isUnderGround(startPoint) && isUnderGround(endPoint)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the point is underground
     * @param point
     * @return
     */
    private boolean isUnderGround(Vector3f point) {
        Terrain t = getTerrain(point.x, point.z);
        float height = 0;
        if (terrain != null) {
            height = 0;
        }
        if (point.y < height) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the terrain which is selected.
     * @param worldX
     * @param worldZ
     * @return
     */
    private Terrain getTerrain(float worldX, float worldZ) {
        int x = (int) (worldX / Terrain.getSize());
        int z = (int) (worldZ / Terrain.getSize());

        if (x >= worldGrid.getWorldSize() || x < 0 || z >= worldGrid.getWorldSize() || z < 0) {
            return null;
        } else {
            return worldMatrix[x][z].getTerrain();
        }
    }
}
