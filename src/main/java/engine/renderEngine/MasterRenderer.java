package engine.renderEngine;

import engine.display.DisplayManager;
import engine.entities.Entity;
import engine.shaders.EntityShader;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public class MasterRenderer {

    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 2000;

    private static final float RED = 0f;
    private static final float GREEN = 0f;
    private static final float BLUE = 0f;

    private Matrix4f projectionMatrix;

    private EntityRenderer entityRenderer;
    private EntityShader entityShader = new EntityShader();

    public MasterRenderer() {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        createProjectionMatrix();
        entityRenderer = new EntityRenderer(entityShader, projectionMatrix);
    }

    public void render(Entity entity) {
        prepare();
        entityShader.start();

        entityRenderer.render(entity);
        entityShader.stop();
    }

    public void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(RED,GREEN,BLUE, 1);
    }

    public void cleanUp() {
        entityShader.cleanUp();
    }

    public Matrix4f getProjectionMatrix() {
        return  projectionMatrix;
    }

    private void createProjectionMatrix() {
        projectionMatrix = new Matrix4f().perspective(
                (float) Math.toRadians(FOV),
                (float) DisplayManager.getWindowWidth() / (float) DisplayManager.getWindowHeight(),
                NEAR_PLANE, FAR_PLANE
        );

        /*float aspectRatio = (float) DisplayManager.getWindowWidth() / (float) DisplayManager.getWindowHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00(x_scale);
        projectionMatrix.m11(y_scale);
        projectionMatrix.m22(-((FAR_PLANE + NEAR_PLANE) / frustum_length));
        projectionMatrix.m23(-1);
        projectionMatrix.m32(-((2 * NEAR_PLANE * FAR_PLANE) / frustum_length));
        projectionMatrix.m33(0);*/

    }
}
