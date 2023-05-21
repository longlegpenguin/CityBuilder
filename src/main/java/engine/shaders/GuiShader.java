package engine.shaders;

import org.joml.Matrix4f;

/**
 * Extends the ShaderProgram and is responsible for handling the shaders for all 2D UI components.
 */
public class GuiShader extends ShaderProgram {
    private static final String VERTEX_FILE ="src/main/java/engine/shaders/guiVertexShader.glsl";
    private static final String FRAGMENT_FILE="src/main/java/engine/shaders/guiFragmentShader.glsl";

    private int location_transformationMatrix;

    public GuiShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    /**
     * Loads the transformation matrix to the shader.
     * @param matrix
     */
    public void loadTransformation(Matrix4f matrix)
    {
        super.loadMatrix(location_transformationMatrix,matrix);
    }

    /**
     * Binds all Attributes found in the GLSL file.
     */
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0,"position");
    }

    /**
     * Finds all uniform loactions in the GLSL file.
     */
    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");

    }
}
