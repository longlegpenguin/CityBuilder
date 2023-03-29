package engine.shaders;

public class EntityShader extends ShaderProgram{

    private static final String VERTEX_FILE = "src/main/java/engine/shaders/EntityVertexShader.glsl";
    private static final String FRAGMENT_FILE = "src/main/java/engine/shaders/EntityFragmentShader.glsl";



    public EntityShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }



    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
    }

    @Override
    protected void getAllUniformLocations() {

    }

}
