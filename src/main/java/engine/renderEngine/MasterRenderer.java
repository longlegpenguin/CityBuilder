package engine.renderEngine;

import engine.display.DisplayManager;
import engine.entities.Camera;
import engine.entities.Entity;
import engine.entities.Light;
import engine.models.TexturedModel;
import engine.shaders.EntityShader;
import engine.shaders.SelectorShader;
import engine.shaders.TerrainShader;
import engine.terrain.Selector;
import engine.terrain.Terrain;
import engine.textures.TextureAttribute;
import engine.world.Tile;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();

    private TerrainShader terrainShader = new TerrainShader();
    private TerrainRenderer terrainRenderer;
    private Map<TextureAttribute, List<Terrain>> terrains = new HashMap<TextureAttribute, List<Terrain>>();

    private SelectorShader selectorShader = new SelectorShader();
    private SelectorRenderer selectorRenderer;


    public MasterRenderer() {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        createProjectionMatrix();
        entityRenderer = new EntityRenderer(entityShader, projectionMatrix);
        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
        selectorRenderer = new SelectorRenderer(selectorShader, projectionMatrix);
    }

    public void render(Selector selector, Camera camera, Light light) {
        prepare();
        entityShader.start();
        entityShader.loadLight(light);
        entityShader.loadSkyColor(RED, GREEN, BLUE);
        entityShader.loadViewMatrix(camera);
        entityRenderer.render(entities);
        entityShader.stop();
        entities.clear();

        terrainShader.start();
        terrainShader.loadLight(light);
        terrainShader.loadSkyColor(RED, GREEN, BLUE);
        terrainShader.loadViewMatrix(camera);
        terrainRenderer.render(terrains);
        terrainShader.stop();
        terrains.clear();

        selectorShader.start();
        selectorShader.loadViewMatrix(camera);
        selectorRenderer.render(selector);
        selectorShader.stop();
    }

    public void processEntities(Entity entity) {
        TexturedModel texturedModel = entity.getModel();
        List<Entity> batch = entities.get(texturedModel);
        if (batch != null) {
            batch.add(entity);
        } else {
            List<Entity> newBatch = new ArrayList<Entity>();
            newBatch.add(entity);
            entities.put(texturedModel, newBatch);
        }
    }

    public void processTerrain(Terrain terrain) {
        TextureAttribute texture = terrain.getTexture();
        List<Terrain> batch = terrains.get(texture);
        if (batch != null) {
            batch.add(terrain);
        } else {
            List<Terrain> newBatch = new ArrayList<Terrain>();
            newBatch.add(terrain);
            terrains.put(texture, newBatch);
        }
    }

    public void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(RED,GREEN,BLUE, 1);
    }

    public void cleanUp() {
        entityShader.cleanUp();
        terrainShader.cleanUp();
        selectorShader.cleanUp();
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
    }
}
