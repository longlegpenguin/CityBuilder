package engine.engineMain;

import engine.models.RawModel;
import engine.models.TexturedModel;
import engine.objConverter.ModelData;
import engine.objConverter.OBJFileLoader;
import engine.renderEngine.Loader;
import engine.textures.TextureAttribute;

/**
 * This class is called when loading into the game.
 * It uses the Asset Enum to load all of the assets into the GPU memory as Textured models.
 */
public class AssetLoader {

    private Loader LOADER = new Loader();
    private final TexturedModel road;
    private final TexturedModel residentialBuilding;
    private final TexturedModel commercialBuilding;
    private final TexturedModel industrialBuilding;
    private final TexturedModel police;
    private final TexturedModel stadium;
    private final TexturedModel forest;
    private final TexturedModel university;
    private final TexturedModel school;

    /**
     * Constructor which initializes each Textured Model attribute by calling the loadAsset() method.
     */
    public AssetLoader() {
        this.road = loadAsset(Assets.ROAD.getOBJFilename(), Assets.ROAD.getTextureFileName());
        this.residentialBuilding = loadAsset(Assets.RESIDENTIAL.getOBJFilename(), Assets.RESIDENTIAL.getTextureFileName());
        this.commercialBuilding = loadAsset(Assets.COMMERCIAL.getOBJFilename(), Assets.COMMERCIAL.getTextureFileName());
        this.industrialBuilding = loadAsset(Assets.INDUSTRIAL.getOBJFilename(), Assets.INDUSTRIAL.getTextureFileName());
        this.police = loadAsset(Assets.POLICE.getOBJFilename(), Assets.POLICE.getTextureFileName());
        this.stadium = loadAsset(Assets.STADIUM.getOBJFilename(), Assets.STADIUM.getTextureFileName());
        this.forest = loadAsset(Assets.FOREST.getOBJFilename(), Assets.FOREST.getTextureFileName());
        this.university = loadAsset(Assets.UNIVERSITY.getOBJFilename(), Assets.UNIVERSITY.getTextureFileName());
        this.school = loadAsset(Assets.SCHOOL.getOBJFilename(), Assets.SCHOOL.getTextureFileName());
    }

    /**
     * Uses the file names given as parameters to first create a ModelData object which stores the data needed by the VAO
     * Next a rawModel is created from the modelData data.
     * Finally a TexturedModel is created from the Texture and RawModel after which it is returned.
     * @param objFileName
     * @param textureFilename
     * @return TexturedModel of the asset.
     */
    private TexturedModel loadAsset(String objFileName, String textureFilename) {
        ModelData modelData = OBJFileLoader.loadOBJ(objFileName);
        RawModel rawModel = LOADER.loadToVAO(modelData.getVertices(), modelData.getTextureCoords(), modelData.getNormals(), modelData.getIndices());
        TexturedModel texturedModel = new TexturedModel(rawModel, new TextureAttribute(LOADER.loadTexture(textureFilename)));
        return texturedModel;
    }

    public TexturedModel getRoad() {
        return road;
    }

    public TexturedModel getResidentialBuilding() {
        return residentialBuilding;
    }

    public TexturedModel getCommercialBuilding() {
        return commercialBuilding;
    }

    public TexturedModel getIndustrialBuilding() {
        return industrialBuilding;
    }

    public TexturedModel getPolice() {
        return police;
    }

    public TexturedModel getStadium() {
        return stadium;
    }

    public TexturedModel getForest() {
        return forest;
    }

    public TexturedModel getUniversity() {
        return university;
    }

    public TexturedModel getSchool() {
        return school;
    }
}
