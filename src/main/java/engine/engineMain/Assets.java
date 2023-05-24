package engine.engineMain;

/**
 * Enum used to store the necesarry data to load the files into the Texture processing and VAO's.
 * Stores the scale of each object, as well as the .obj filename and .png texture filename.
 */
public enum Assets {
    ROAD ("road", "road", 1),
    RESIDENTIAL("house", "assets/residential", 0.2f),
    COMMERCIAL("commercial", "assets/commercial", 1f),
    INDUSTRIAL("industrial", "assets/industrial", 0.7f),
    POLICE("police", "assets/police", 0.8f),
    STADIUM("stadium", "assets/stadium", 0.5f),
    FOREST1("forest1", "assets/forest", 1f),
    FOREST2("forest2", "assets/forest", 1f),
    FOREST3("forest3", "assets/forest", 1f),
    FOREST4("forest4", "assets/forest", 1f),
    FOREST5("forest5", "assets/forest", 1f),
    FOREST6("forest6", "assets/forest", 1f),
    FOREST7("forest7", "assets/forest", 1f),
    FOREST8("forest8", "assets/forest", 1f),
    FOREST9("forest9", "assets/forest", 1f),
    FOREST10("forest10", "assets/forest", 1f),
    UNIVERSITY("university", "assets/university", 2f),
    SCHOOL("school", "assets/school", 1f),
    CONSTRUCTION("construction", "assets/construction", 1f);

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
