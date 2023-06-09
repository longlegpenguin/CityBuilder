package engine.terrain;

import engine.models.RawModel;
import engine.renderEngine.Loader;
import engine.textures.TextureAttribute;

/**
 * TODO Parent class of this and terrain so that code is not copied
 * Selector Object which is the square which appears when hovering over a cell.
 */
public class ZoneTile {
    private static final float SIZE = 10;
    private static final int VERTEX_COUNT = 4;

    private float x;
    private float y = 0.05f;
    private float z;
    private RawModel model;
    private TextureAttribute texture;

    /**
     * Sets the position and texture and then generates the model for the selector.
     * @param gridX
     * @param gridZ
     * @param loader
     * @param texture
     */
    public ZoneTile(int gridX, int gridZ, Loader loader, TextureAttribute texture) {
        this.texture = texture;
        this.x = gridX * SIZE;
        this.z = gridZ * SIZE;
        this.model = generateSelector(loader);
    }

    public static float getSize() {
        return SIZE;
    }

    public void setX(float x) {
        this.x = x * SIZE;
    }

    public void setZ(float z) {
        this.z = z * SIZE;
    }

    public float getX() {
        return x;
    }

    public float getZ() {
        return z;
    }

    public RawModel getModel() {
        return model;
    }

    public TextureAttribute getTexture() {
        return texture;
    }

    /**
     * Smaller version of the OBJFile loader which creates the vertices, normals and texture coordinates for the selector as it is just a 2D plane
     * @param loader
     * @return
     */
    private RawModel generateSelector(Loader loader){
        int count = VERTEX_COUNT * VERTEX_COUNT;
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count*2];
        int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
        int vertexPointer = 0;
        for(int i=0;i<VERTEX_COUNT;i++){
            for(int j=0;j<VERTEX_COUNT;j++){
                vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
                vertices[vertexPointer*3+1] = y;
                vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
                normals[vertexPointer*3] = 0;
                normals[vertexPointer*3+1] = 1;
                normals[vertexPointer*3+2] = 0;
                textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
                textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
                vertexPointer++;
            }
        }
        int pointer = 0;
        for(int gz=0;gz<VERTEX_COUNT-1;gz++){
            for(int gx=0;gx<VERTEX_COUNT-1;gx++){
                int topLeft = (gz*VERTEX_COUNT)+gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }
        return loader.loadToVAO(vertices, textureCoords, normals, indices);
    }
}
