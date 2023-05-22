package engine.fontRendering;


import engine.shaders.ShaderProgram;
import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * Class for the Font Shader extending the Shader Program
 * used mainly for texts
 */
public class FontShader extends ShaderProgram {
	/**
	 * files of the vertex and the fragment (glsl files)
	 */
	private static final String VERTEX_FILE = "src/main/java/engine/fontRendering/fontVertex.glsl";
	private static final String FRAGMENT_FILE = "src/main/java/engine/fontRendering/fontFragment.glsl";

	private int location_colour;
	private int location_translation;
	
	public FontShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);

	}

	/**
	 * method needed for the rendering to get all the uniforms (glsl file)
	 */

	@Override
	protected void getAllUniformLocations() {
		location_colour = super.getUniformLocation("colour");
		location_translation = super.getUniformLocation("translation");
		
	}

	/**
	 * binding the attributes
	 */

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0,"position");
		super.bindAttribute(1,"textureCoords");



	}

	/**
	 * method to load the colour of the texts
	 * @param colour
	 */
	protected  void loadColour(Vector3f colour)
	{
		super.loadVector(location_colour,colour);

	}

	/**
	 * loads the translation of the text when rendered
	 * @param translation
	 */
	protected  void loadTranslation(Vector2f translation)
	{
		super.load2DVector(location_translation,translation);
	}



}
