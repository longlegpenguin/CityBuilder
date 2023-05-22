package engine.fontMeshCreator;

/**
 * Stores the vertex data for all the quads on which a text will be rendered.
 */
public class TextMeshData {
	
	private float[] vertexPositions;
	private float[] textureCoords;
	
	protected TextMeshData(float[] vertexPositions, float[] textureCoords){
		this.vertexPositions = vertexPositions;
		this.textureCoords = textureCoords;
	}

	/**
	 *
	 * @return the vertex positions of the text
	 */
	public float[] getVertexPositions() {
		return vertexPositions;
	}

	/**
	 *
	 * @return the texture Coordinates of the text
	 */
	public float[] getTextureCoords() {
		return textureCoords;
	}

	/**
	 *
	 * @return the vertex count of the text
	 */
	public int getVertexCount() {
		return vertexPositions.length/2;
	}

}
