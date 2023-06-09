package engine.renderEngine;

import engine.guis.UiTab;
import engine.models.RawModel;
import engine.shaders.GuiShader;
import engine.guis.UiButton;
import engine.tools.Maths;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.List;

/**
 * GUI Renderer is responsible for rendering all UIComponents to the screen.
 * This includes all UIButtons and UITabs.
 */
public class GuiRenderer {

    private final RawModel quad;
    private GuiShader shader;

    /**
     * Constructor which takes in the loader to create a 2D plane.
     * All UIComponents are just flat rectangles.
     * @param loader
     */
    public GuiRenderer(Loader loader) {
        float[] positions = {-1,1,-1,-1,1,1,1,-1};
        quad = loader.loadToVAO(positions);
        shader = new GuiShader();
    }

    /**
     * Main Render function.
     * Receives a list of buttons and tabs.
     * Enables the Texture banks, necessary OPENGL settings and VertexAttribute arrauys for each.
     * Starts and stops the shaders and loads the transformation matrices for each.
     * @param buttons
     * @param tabs
     */
    public void render(List<UiButton>buttons, List<UiTab>tabs) {
        shader.start();
        GL30.glBindVertexArray(quad.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        for (UiTab tab : tabs){
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, tab.getTexture());
            Matrix4f matrix = Maths.createTransformationMatrix(tab.getPosition(), tab.getScale());
            shader.loadTransformation(matrix);
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
        }

        for (UiButton button : buttons){
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, button.getTexture());
            Matrix4f matrix = Maths.createTransformationMatrix(button.getPosition(), button.getScale());
            shader.loadTransformation(matrix);
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
        }

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shader.stop();
    }

    /**
     * Cleans up the shader for the UIComponents.
     */
    public void cleanUp() {
        shader.cleanUp();
    }

}

