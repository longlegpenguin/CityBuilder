package engine.shaders;

import engine.entities.Camera;
import engine.tools.Maths;
import org.joml.Matrix4f;

/**
 * Extends the ShaderProgram and is responsible for handling the shaders for the selector square used for selecting a zone.
 */
public class SelectorShader extends ShaderProgram {

    private static final String VERTEX_FILE = "/shader/SelectorVertexShader.glsl";
    private static final String FRAGMENT_FILE = "/shader/SelectorFragmentShader.glsl";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    public SelectorShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    /**
     * Binds all Attributes found in the GLSL file.
     */
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    /**
     * Finds all uniform loactions in the GLSL file.
     */
    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
    }

    /**
     * Loads the transformation matrix to the shader.
     * @param matrix
     */
    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    /**
     * Loads the view matrix to the shader after creating it based on the camera.
     * @param camera
     */
    public void loadViewMatrix(Camera camera) {
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }

    /**
     * Loads the projection matrix to the shader.
     * @param projection
     */
    public void loadProjectionMatrix(Matrix4f projection) {
        super.loadMatrix(location_projectionMatrix, projection);
    }
}
