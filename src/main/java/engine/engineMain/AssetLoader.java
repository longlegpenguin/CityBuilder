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
    private final TexturedModel forest1;
    private final TexturedModel forest2;
    private final TexturedModel forest3;
    private final TexturedModel forest4;
    private final TexturedModel forest5;
    private final TexturedModel forest6;
    private final TexturedModel forest7;
    private final TexturedModel forest8;
    private final TexturedModel forest9;
    private final TexturedModel forest10;

    private final TexturedModel university;
    private final TexturedModel school;
    private final TexturedModel construction;

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
        this.forest1 = loadAsset(Assets.FOREST1.getOBJFilename(), Assets.FOREST1.getTextureFileName());
        this.forest2 = loadAsset(Assets.FOREST2.getOBJFilename(), Assets.FOREST2.getTextureFileName());
        this.forest3 = loadAsset(Assets.FOREST3.getOBJFilename(), Assets.FOREST3.getTextureFileName());
        this.forest4 = loadAsset(Assets.FOREST4.getOBJFilename(), Assets.FOREST4.getTextureFileName());
        this.forest5 = loadAsset(Assets.FOREST5.getOBJFilename(), Assets.FOREST5.getTextureFileName());
        this.forest6 = loadAsset(Assets.FOREST6.getOBJFilename(), Assets.FOREST6.getTextureFileName());
        this.forest7 = loadAsset(Assets.FOREST7.getOBJFilename(), Assets.FOREST7.getTextureFileName());
        this.forest8 = loadAsset(Assets.FOREST8.getOBJFilename(), Assets.FOREST8.getTextureFileName());
        this.forest9 = loadAsset(Assets.FOREST9.getOBJFilename(), Assets.FOREST9.getTextureFileName());
        this.forest10 = loadAsset(Assets.FOREST10.getOBJFilename(), Assets.FOREST10.getTextureFileName());
        this.university = loadAsset(Assets.UNIVERSITY.getOBJFilename(), Assets.UNIVERSITY.getTextureFileName());
        this.school = loadAsset(Assets.SCHOOL.getOBJFilename(), Assets.SCHOOL.getTextureFileName());
        this.construction = loadAsset(Assets.CONSTRUCTION.getOBJFilename(), Assets.CONSTRUCTION.getTextureFileName());
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

    public TexturedModel getForest(int age) {
        switch (age) {
            case 1 -> {return forest1;}
            case 2 -> {return forest2;}
            case 3 -> {return forest3;}
            case 4 -> {return forest4;}
            case 5 -> {return forest5;}
            case 6 -> {return forest6;}
            case 7 -> {return forest7;}
            case 8 -> {return forest8;}
            case 9 -> {return forest9;}
            case 10 -> {return forest10;}
        }
        return forest10;
    }

    public TexturedModel getUniversity() {
        return university;
    }

    public TexturedModel getSchool() {
        return school;
    }

    public TexturedModel getConstruction() {
        return construction;
    }
}
