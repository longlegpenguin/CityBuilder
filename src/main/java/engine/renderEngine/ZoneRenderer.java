package engine.renderEngine;

import engine.models.RawModel;
import engine.shaders.SelectorShader;
import engine.terrain.ZoneTile;
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

public class ZoneRenderer {
    private SelectorShader shader;

    public ZoneRenderer(SelectorShader shader, Matrix4f projectionMatrix) {
        this.shader = shader;
        this.shader.start();
        this.shader.loadProjectionMatrix(projectionMatrix);
        this.shader.stop();
    }

    public void render(Map<TextureAttribute, List<ZoneTile>> zonetiles) {
        for(TextureAttribute texture: zonetiles.keySet()) {
            List<ZoneTile> batch = zonetiles.get(texture);
            prepareTile(batch.get(0));
            for(ZoneTile tile: batch) {
                prepareInstance(tile);
                GL11.glDrawElements(GL11.GL_TRIANGLES, tile.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
            unbindTexturedModel();
        }
    }

    private void prepareTile(ZoneTile zoneTile) {
        RawModel rawModel = zoneTile.getModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        TextureAttribute texture = zoneTile.getTexture();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
    }

    private void unbindTexturedModel() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void prepareInstance(ZoneTile zoneTile) {
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(zoneTile.getX(), 0, zoneTile.getZ()), 0, 0, 0, 1);
        shader.loadTransformationMatrix(transformationMatrix);
    }
}
