package engine.renderEngine;

import engine.display.DisplayManager;
import engine.entities.Camera;
import engine.entities.Entity;
import engine.entities.Light;
import engine.shaders.EntityShader;
import engine.shaders.TerrainShader;
import engine.terrain.Terrain;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public class MasterRenderer {

    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 2000f;

    private static final float RED = 1.0f;
    private static final float GREEN = 1.0f;
    private static final float BLUE = 1.0f;

    private Matrix4f projectionMatrix;

    private EntityShader entityShader = new EntityShader();
    private EntityRenderer entityRenderer;

    TerrainShader terrainShader = new TerrainShader();
    TerrainRenderer terrainRenderer;


    public MasterRenderer() {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        createProjectionMatrix();
        entityRenderer = new EntityRenderer(entityShader, projectionMatrix);
        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
    }

    public void render(Entity entity, Terrain terrain, Camera camera, Light light) {
        prepare();
        entityShader.start();
        terrainShader.loadLight(light);
        terrainShader.loadSkyColor(RED, GREEN, BLUE);
        entityShader.loadViewMatrix(camera);
        entityRenderer.render(entity);
        entityShader.stop();

        terrainShader.start();
        terrainShader.loadLight(light);
        terrainShader.loadSkyColor(RED, GREEN, BLUE);
        terrainShader.loadViewMatrix(camera);
        terrainRenderer.render(terrain);
        terrainShader.stop();

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
        System.out.println(projectionMatrix.toString());

    }
}
