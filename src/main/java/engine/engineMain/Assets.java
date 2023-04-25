package engine.engineMain;

public enum Assets {
    ROAD ("road", "road", 5),
    RESEDENTIAL("cube", "spiral", 5),
    COMMERCIAL("cube", "spiral", 5),
    INDUSTRIAL("cube", "spiral", 5),
    POLICE("cube", "spiral", 5),
    STADIUM("cube", "spiral", 5),
    FOREST("cube", "spiral", 5),
    UNIVERSITY("cube", "spiral", 5),
    SCHOOL("cube", "spiral", 5);

    private String OBJFilename;
    private String TextureFileName;
    private float scale;

    Assets(String OBJFileName, String TextureFileName, float scale) {
        this.OBJFilename = OBJFileName;
        this.TextureFileName = TextureFileName;
        this.scale = scale;
    }

    public String getOBJFilename() {
        return OBJFilename;
    }

    public String getTextureFileName() {
        return TextureFileName;
    }

    public float getScale() {
        return scale;
    }
}
