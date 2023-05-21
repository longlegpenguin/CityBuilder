package engine.renderEngine;

import engine.models.RawModel;
import engine.shaders.TerrainShader;
import engine.terrain.Terrain;

import engine.textures.TextureAttribute;
import engine.tools.Maths;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;


import java.util.List;
import java.util.Map;

/**
 * This class is responsible for rendering all terrain elements on the grid.
 */
public class TerrainRenderer {

    private TerrainShader shader;

    /**
     * Constructor which starts the shader and loads the projection matrix in the shader.
     * @param shader
     * @param projectionMatrix
     */
    public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix) {
        this.shader = shader;
        this.shader.start();
        this.shader.loadProjectionMatrix(projectionMatrix);
        this.shader.stop();
    }

    /**
     * Terrains are stored in the hashmap
     * Render function prepares each type of TexturedModel and the prepares each individual instance of that model before drawing it to the screen.
     * @param terrains
     */
    public  void render(Map<TextureAttribute, List<Terrain>> terrains) {
        for(TextureAttribute texture: terrains.keySet()) {
            List<Terrain> batch = terrains.get(texture);
            prepareTerrain(batch.get(0));
            for (Terrain terrain: batch) {
                prepareInstance(terrain);
                GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
            unbindTexturedModel();
        }
    }

    /**
     * The vertex attribute arrays for each model are enabled,
     * the shaders are laoded with the shinedampers and reflectivity,
     * the texture banks are activated and the texture is loaded.
     * @param terrain
     */
    private void prepareTerrain(Terrain terrain) {
        RawModel rawModel = terrain.getModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        TextureAttribute texture = terrain.getTexture();
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
    }

    /**
     * Disables and unbinds all the Vertex Arrays.
     */
    private void unbindTexturedModel() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    /**
     * Prepares each individual entity by loading its transformation matrix based on its position in the world to the shader.
     * @param terrain
     */
    private void prepareInstance(Terrain terrain) {
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(terrain.getX(), 0, terrain.getZ()), 0, 0, 0, 1);
        shader.loadTransformationMatrix(transformationMatrix);
    }

}

