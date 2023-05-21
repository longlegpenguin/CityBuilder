package engine.renderEngine;

import engine.entities.Entity;
import engine.models.RawModel;
import engine.models.TexturedModel;
import engine.shaders.EntityShader;
import engine.textures.TextureAttribute;
import engine.tools.Maths;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.List;
import java.util.Map;

/**
 * This class is responsible for rendering anything considered an entity.
 * An entity constitutes as anything that has a 3D asset.
 * Starts the entity shaders as well as drawing every asset to the screen with the help of the shaders.
 */
public class EntityRenderer {

    private EntityShader shader;

    /**
     * Constructor which starts the shader and loads the projection matrix in the shader.
     * @param shader
     * @param projectionMatrix
     */
    public EntityRenderer(EntityShader shader, Matrix4f projectionMatrix) {
        this.shader = shader;
        this.shader.start();
        this.shader.loadProjectionMatrix(projectionMatrix);
        this.shader.stop();
    }

    /**
     * Entities are stored in the hashmap
     * Render function prepares each type of TexturedModel and the prepares each individual instance of that model before drawing it to the screen.
     * @param entities
     */
    public void render(Map<TexturedModel, List<Entity>> entities) {
        for(TexturedModel textureModel: entities.keySet()) {
            List<Entity> batch = entities.get(textureModel);
            prepareModel(textureModel);
            for(Entity entity: batch) {
                prepareInstance(entity);
                GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
            unbindModel();
        }
    }

    /**
     * The vertex attribute arrays for each model are enabled,
     * the shaders are laoded with the shinedampers and reflectivity,
     * the texture banks are activated and the texture is loaded.
     * @param texturedModel
     */
    public void prepareModel(TexturedModel texturedModel) {
        RawModel rawModel = texturedModel.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        TextureAttribute texture = texturedModel.getTexture();
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());

    }

    /**
     * Disables and unbinds all the Vertex Arrays.
     */
    public void unbindModel() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    /**
     * Prepares each individual entity by loading its transformation matrix based on its position in the world to the shader.
     * @param entity
     */
    private void prepareInstance(Entity entity) {
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
    }
}
