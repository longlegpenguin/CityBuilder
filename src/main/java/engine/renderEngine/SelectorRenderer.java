package engine.renderEngine;

import engine.models.RawModel;
import engine.shaders.SelectorShader;
import engine.terrain.Selector;
import engine.textures.TextureAttribute;
import engine.tools.Maths;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * This class is responsible for rendering the selector square used to select a zone.
 */
public class SelectorRenderer {
    private SelectorShader shader;

    /**
     * Constructor which starts the shader and loads the projection matrix in the shader.
     * @param shader
     * @param projectionMatrix
     */
    public SelectorRenderer(SelectorShader shader, Matrix4f projectionMatrix) {
        this.shader = shader;
        this.shader.start();
        this.shader.loadProjectionMatrix(projectionMatrix);
        this.shader.stop();
    }

    /**
     * Render function prepares the selector and the instance of it before drawing it to the screen and then unbinding it.
     * @param selector
     */
    public void render(Selector selector) {
        prepareSelector(selector);
        prepareInstance(selector);
        GL11.glDrawElements(GL11.GL_TRIANGLES, selector.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        unbindTexturedModel();
    }

    /**
     * The vertex attribute arrays for each model are enabled,
     * the shaders are laoded with the shinedampers and reflectivity,
     * the texture banks are activated and the texture is loaded.
     * @param selector
     */
    private void prepareSelector(Selector selector) {
        RawModel rawModel = selector.getModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        TextureAttribute texture = selector.getTexture();
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
     * * Prepares selector object by loading its transformation matrix based on its position in the world to the shader.
     * @param selector
     */
    private void prepareInstance(Selector selector) {
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(selector.getX(), 0, selector.getZ()), 0, 0, 0, 1);
        shader.loadTransformationMatrix(transformationMatrix);
    }
}
