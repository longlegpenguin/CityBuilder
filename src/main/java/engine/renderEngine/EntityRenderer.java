package engine.renderEngine;

import engine.models.RawModel;
import engine.models.TexturedModel;
import engine.shaders.EntityShader;
import engine.textures.TextureAttribute;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class EntityRenderer {

    private EntityShader shader;

    public EntityRenderer(EntityShader shader) {
        this.shader = shader;
        shader.start();
        shader.stop();
    }

    public void render(TexturedModel texturedModel) {
        prepareModel(texturedModel);
        GL11.glDrawElements(GL11.GL_TRIANGLES, texturedModel.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        unbindModel();
    }

    public void prepareModel(TexturedModel texturedModel) {
        RawModel rawModel = texturedModel.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

        TextureAttribute texture = texturedModel.getTexture();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getTextureID());

    }

    public void unbindModel() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }

}
