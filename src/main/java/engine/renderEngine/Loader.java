package engine.renderEngine;

import engine.textures.Texture;
import org.lwjgl.BufferUtils;
import engine.models.RawModel;
import org.lwjgl.opengl.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Loader class is used to load the VBO's for each VAO of all Assets, UIComponents, Terrains and Texts in the game.
 */
public class Loader {
    private List<Integer> vaos = new ArrayList<Integer>();
    private List<Integer> textVaos = new ArrayList<Integer>();
    private List<Integer> vbos = new ArrayList<Integer>();
    private List<Integer> textures = new ArrayList<Integer>();
    /**
     * Method used to return RawModel of 3D Object
     * Creates a VAO and then:
     *                  Indices are stored int an index buffer and bound to the VAO
     *                  Stores the position data of the vertices into attribute 0 of the VAO
     *                  Stores the texture coordinates into attribute 1 of the VAO
     *                  Stores the normal vectors into attribute 2 of the VAO
     * @param positions
     *                  - The 3D positions of each vertex in the geometry

     * @param indices
     *
             * @return the loaded model
     */
    public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals , int[] indices) {
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0,3, positions);
        storeDataInAttributeList(1, 2, textureCoords);
        storeDataInAttributeList(2, 3, normals);
        unbindVAO();
        return new RawModel(vaoID, indices.length);
    }

    /**
     * Method responsible for loading VAO for GUI Objects.
     * Same process as LoadToVAO for entities however index array is not required.
     * @param positions
     * @return
     */
    public RawModel loadToVAO(float[] positions) {
        int vaoID = createVAO();
        this.storeDataInAttributeList(0, 2, positions);
        unbindVAO();
        return new RawModel(vaoID, positions.length/2);
    }

    /**
     * Method responsible for loading VAO for Text Objects.
     * Same process as LoadToVAO for entities however index array is not required.
     * @param positions
     * @param textureCoords
     * @return
     */
    public int loadToVAO(float[] positions, float[] textureCoords) {
        int vaoID = createTextVAO();

        storeDataInAttributeList(0,2, positions);
        storeDataInAttributeList(1, 2, textureCoords);
        unbindVAO();
        return vaoID;
    }

    /**
     * Creates a Texture Object using the input filename of the png texture
     *
     * @param fileName  - Name of the file which contains the texture of an object
     * @return ID of the loaded texture
     */
    public int loadTexture(String fileName) {
        Texture texture = Texture.loadTexture("/textures/" + fileName + ".png");

        int textureID = texture.getTextureID();
        textures.add(textureID);
        return textureID;
    }

    /**
     *
     * @param fileName
     * @return
     */
    public int loadFontTexture(String fileName) {
        Texture texture = Texture.loadTexture("/textures/" + fileName + ".png");
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS,0);
        int textureID = texture.getTextureID();
        textures.add(textureID);
        return textureID;
    }


    /**
     * Create a new VAO and return the ID of the VAO.

     * VAO (Vertex Array Object) holds geometry data which can be rendered and is stored on VRAM of GPU for quick access during rendering
     * The created VAO returns its ID
     * Before using a VAO it needs to be made active - only 1 VAO can be active at a time
     * To activate the VAO to enable data to be stored it needs to be bound.
     * @return the ID of the created VAO.
     */
    private int createVAO() {
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    /**
     * Creation of the VAO for text - stored in a different VAO id.
     * @return
     */
    private int createTextVAO() {
        int vaoID = GL30.glGenVertexArrays();
        textVaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }
    /**
     * Deletes all the VAOs, VBOs and Textures when the game is closed or terminated
     * VAOs, VBOs and Textures are stored int the VRAM (Video Memory)
     */
    public void cleanUp() {
        for (int vao: vaos) {
            GL30.glDeleteVertexArrays(vao);
        }
        for (int textVao: textVaos) {
            GL30.glDeleteVertexArrays(textVao);
        }
        for (int vbo: vbos) {
            GL15.glDeleteBuffers(vbo);
        }
        for (int texture: textures) {
            GL11.glDeleteTextures(texture);
        }
    }

    /**
     * Unbinds the VAO after the program is finished with it
     * If VAO needs to be edited or used again, it would have to be bound again
     */
    private void unbindVAO() {
        GL30.glBindVertexArray(0);
    }

    /**
     * Converts the indices from an int array to an IntBuffer so that the data can be stored in the VBO
     * @param data  - The indices in an int[]
     * @return the indices buffer
     */
    private IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;

    }


    /**
     *  Stores position data of vertices into attribute number of the VAO
     *  Positions need to first be stored in VBO (Vertex Buffer Object) - array of data stored in VRAM of GPU for quick access during rendering

     *  Need to create new VBO like VAO - make it active by binding it using the ID that is returned

     *  Data is then stored using glBufferData method() - Indicate GL_STATIC_DRAW as data won't have to change - can use GL_DYNAMIC_DRAW for animation

     *  Connect VBO to VAO using glVertexAttribPointer() method and requires following info:
     *                          - attribute number of the VAO where data needs to be stored
     *                          - number of floats in each vertex e.g (x, y, z) = 3
     *                          - Type of data that should be stored - GL11.GL_FlOAT in this case
     *                          - data does not need to be normalized, stride is 0 as byte offset is not needed, pointer is 0 as no offset from first component is needed.

     *  Finally, the VBO is made inactive by unbinding it.
     *
     * @param attributeNumber   - Number of the attribute of the VAO where data is to be stored
     * @param coordinateSize    - Number of floats in each vertex, e.g: 2 for 2D or 3 for 3D
     * @param data              - Geometry data to be stored in the VAO, in this case the positions of the vertices
     */
    private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    /**
     * Before data can be stored in VBO needs to be in the format of a Buffer - using Float Buffer in this case as the data needed to be stored is floats

     * Creates empty FloatBuffer (similar to Array with a pointer)
     * Once data is added to the buffer, pointer points to first empty space - if more data is added, will not overwrite data already contained
     * Once data is stored, buffer needs to be made ready for reading
     * Done using flip() function which now makes the pointer point to the start of the buffer

     * @param data  - Float data that will be stored in buffer
     * @return      The FloatBuffer containing the data - this buffer is ready to be loaded into a VBO.
     */
    private FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    /**
     * Creates an index buffer which is then bound to the current active VAO and filled with indices data.

     * Index buffer tells OpenGL how each vertex should be connected and in what order.
     * Each VAO can only have one index buffer - has specific "slot" for it
     * Therefore by binding the index buffer, it is automatically bound to the currently active VAO due to the use of GL_ELEMENT_ARRAY_BUFFER
     * For this reason, the VBO does not need to be unbound.
     * @param indices
     */
    private void bindIndicesBuffer(int[] indices) {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    /**
     * Clears the Text VAO as well as the VBO arrays.
     */
    public void clearTextVaos() {
        vbos.clear();
        textVaos.clear();
    }
}
