package engine.objConverter;

/**
 * The modelData class is storing the data of the object to load it later.
 */
public class ModelData {
    private float[] vertices;
    private float[] textureCoords;
    private float[] normals;
    private int[] indices;
    private float furthestPoint;

    public ModelData(float[] vertices, float[] textureCoords, float[] normals, int[] indices,
                     float furthestPoint) {
        this.vertices = vertices;
        this.textureCoords = textureCoords;
        this.normals = normals;
        this.indices = indices;
        this.furthestPoint = furthestPoint;
    }

    /**
     * @return float array of all Vertices in the asset.
     */
    public float[] getVertices() {
        return vertices;
    }

    /**
     * @return float array of the texture coordinates of the asset - also known as UV coords.
     */
    public float[] getTextureCoords() {
        return textureCoords;
    }

    /**
     * @return float array of the normals of the asset used in lighting calculations.
     */
    public float[] getNormals() {
        return normals;
    }

    /**
     * @return int array of the indices which specify to shader how vertices of assets should be connected.
     */
    public int[] getIndices() {
        return indices;
    }
}
