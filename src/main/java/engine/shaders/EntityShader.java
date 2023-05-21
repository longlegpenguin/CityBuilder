package engine.shaders;

import engine.entities.Camera;
import engine.entities.Light;
import engine.tools.Maths;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * Extends the ShaderProgram and is responsible for handling the shaders for all 3D entities.
 */
public class EntityShader extends ShaderProgram{

    private static final String VERTEX_FILE = "src/main/java/engine/shaders/EntityVertexShader.glsl";
    private static final String FRAGMENT_FILE = "src/main/java/engine/shaders/EntityFragmentShader.glsl";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColor;
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_skyColor;

    public EntityShader() {
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
        location_lightPosition = super.getUniformLocation("lightPosition");
        location_lightColor = super.getUniformLocation("lightColor");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_skyColor = super.getUniformLocation("skyColor");
    }

    /**
     * Loads the transformation matrix to the shader.
     * @param matrix
     */
    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    /**
     * Loads the projection matrix to the shader.
     * @param projection
     */
    public void loadProjectionMatrix(Matrix4f projection) {
        super.loadMatrix(location_projectionMatrix, projection);
    }

    /**
     * Loads the view matrix to the shader after creating it based on the camera.
     * @param camera
     */
    public void loadViewMatrix(Camera camera){
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }

    /**
     * Loads the light to the shader - mainly its position and color.
     * @param light
     */
    public void loadLight(Light light) {
        super.loadVector(location_lightPosition, light.getPosition());
        super.loadVector(location_lightColor, light.getColor());
    }

    /**
     * Loads the shine and reflection variables to the shader.
     * @param shineDamper
     * @param reflectivity
     */
    public void loadShineVariables(float shineDamper, float reflectivity) {
        super.loadFloat(location_shineDamper, shineDamper);
        super.loadFloat(location_reflectivity, reflectivity);
    }

    /**
     * Loads the sky color to the shader - based on colors set in master renderer.
     * @param r
     * @param g
     * @param b
     */
    public void loadSkyColor(float r, float g, float b) {
        super.loadVector(location_skyColor, new Vector3f(r, g, b));
    }

}
