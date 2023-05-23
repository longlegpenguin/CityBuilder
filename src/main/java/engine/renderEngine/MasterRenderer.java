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
import engine.terrain.ZoneTile;
import engine.textures.TextureAttribute;
import engine.world.Tile;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The main renderer class which starts all individual renderers and prepares the array of objects for each.
 */
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

    private ZoneRenderer zoneRenderer;
    private Map<TextureAttribute, List<ZoneTile>> zoneTiles = new HashMap<TextureAttribute, List<ZoneTile>>();

    /**
     * Sets OPENGL Properties and starts all the renderers.
     */
    public MasterRenderer() {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        createProjectionMatrix();
        entityRenderer = new EntityRenderer(entityShader, projectionMatrix);
        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
        selectorRenderer = new SelectorRenderer(selectorShader, projectionMatrix);
        zoneRenderer = new ZoneRenderer(selectorShader, projectionMatrix);
    }

    /**
     * Calls the render function of each renderer as well as loading the camera position and light to each shader.
     * @param selector
     * @param camera
     * @param light
     */
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

        selectorShader.start();
        selectorShader.loadViewMatrix(camera);
        zoneRenderer.render(zoneTiles);
        selectorShader.stop();
        zoneTiles.clear();
    }

    /**
     * Processes each entity into the entity HashMap
     * @param entity
     */
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

    /**
     * Processes each terrain into the terrain hashmap
     * @param terrain
     */
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

    public void processZoneTiles(ZoneTile zoneTile) {
        TextureAttribute texture = zoneTile.getTexture();
        List<ZoneTile> batch = zoneTiles.get(texture);
        if (batch != null) {
            batch.add(zoneTile);
        } else {
            List<ZoneTile> newBatch = new ArrayList<ZoneTile>();
            newBatch.add(zoneTile);
            zoneTiles.put(texture, newBatch);
        }
    }

    /**
     * Sets the OPENGL properties before rendering.
     */
    public void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(RED,GREEN,BLUE, 1);
    }

    /**
     * Calls the cleanup methods for each shader.
     */
    public void cleanUp() {
        entityShader.cleanUp();
        terrainShader.cleanUp();
        selectorShader.cleanUp();
    }

    /**
     * @return the current projection matrix based on the screen
     */
    public Matrix4f getProjectionMatrix() {
        return  projectionMatrix;
    }

    /**
     * Creates the projection matrix based on the screen size.
     */
    private void createProjectionMatrix() {
        projectionMatrix = new Matrix4f().perspective(
                (float) Math.toRadians(FOV),
                (float) DisplayManager.getWindowWidth() / (float) DisplayManager.getWindowHeight(),
                NEAR_PLANE, FAR_PLANE
        );
    }
}
