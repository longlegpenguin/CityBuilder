package engine.engineMain;

import engine.display.DisplayManager;
import engine.models.RawModel;
import engine.renderEngine.Loader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class MainApp {

    public static void main(String[] args) {
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        float[] vertices = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f,0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f

        };

        int[] indices = {
                0,1,3,3,1,2
        };
        RawModel model = loader.loadToVAO(vertices,indices);

        while(!glfwWindowShouldClose(DisplayManager.window)) {
            GL11.glClearColor(1,0,0,1);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            GL30.glBindVertexArray(model.getVaoID());
            GL20.glEnableVertexAttribArray(0);

            GL11.glDrawElements(GL11.GL_TRIANGLES,model.getVertexCount(),GL11.GL_UNSIGNED_INT,0);
            GL20.glDisableVertexAttribArray(0);
            DisplayManager.updateDisplay();

        }
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }


}
