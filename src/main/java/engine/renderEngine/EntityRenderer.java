package engine.renderEngine;

import engine.models.RawModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class EntityRenderer {

    public EntityRenderer() {

    }

    public void render(RawModel model) {
        prepareModel(model);
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        unbindModel();
    }

    public void prepareModel(RawModel model) {
        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);

    }

    public void unbindModel() {
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

}
