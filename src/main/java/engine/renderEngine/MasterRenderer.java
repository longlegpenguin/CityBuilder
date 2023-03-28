package engine.renderEngine;

import engine.models.RawModel;
import org.lwjgl.opengl.GL11;

public class MasterRenderer {

    private static final float RED = 1.0f;
    private static final float GREEN = 0f;
    private static final float BLUE = 0f;

    private EntityRenderer entityRenderer;

    public MasterRenderer() {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        entityRenderer = new EntityRenderer();
    }

    public void render(RawModel model) {
        prepare();
        entityRenderer.render(model);
    }

    public void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(RED,GREEN,BLUE, 1);
    }
}
