package engine.engineMain;

/**
 * Enum used to store the necesarry data to load the files into the Texture processing and VAO's.
 * Stores the scale of each object, as well as the .obj filename and .png texture filename.
 */
public enum Assets {
    ROAD ("road", "road", 5),
    RESIDENTIAL("cube", "residential", 5),
    COMMERCIAL("cube", "commercial", 5),
    INDUSTRIAL("cube", "Industrial", 5),
    POLICE("cube", "Police", 5),
    STADIUM("cube", "Stadium", 5),
    FOREST("cube", "Forest", 5),
    UNIVERSITY("cube", "University", 5),
    SCHOOL("cube", "School", 5);

    private String OBJFilename;
    private String TextureFileName;
    private float scale;

    /**
     * Constructor of enum.
     * @param OBJFileName
     * @param TextureFileName
     * @param scale
     */
    Assets(String OBJFileName, String TextureFileName, float scale) {
        this.OBJFilename = OBJFileName;
        this.TextureFileName = TextureFileName;
        this.scale = scale;
    }

    /**
     * @return .obj filename as a string.
     */
    public String getOBJFilename() {
        return OBJFilename;
    }

    /**
     * @return .png filename as a string.
     */
    public String getTextureFileName() {
        return TextureFileName;
    }

    /**
     * @return scale of object as int
     */
    public float getScale() {
        return scale;
    }
}
