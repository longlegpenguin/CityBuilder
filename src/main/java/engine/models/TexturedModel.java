package engine.models;

import engine.textures.TextureAttribute;

/**
 * Represents a textured model
 * Contains a raw model and a model texture
 */
public class TexturedModel {

    private RawModel rawModel;
    private TextureAttribute texture;

    /**
     * @param rawModel
     * @param texture
     */
    public TexturedModel(RawModel rawModel, TextureAttribute texture) {
        this.rawModel = rawModel;
        this.texture = texture;
    }

    /**
     * @return RawModel object of Asset.
     */
    public RawModel getRawModel() {
        return rawModel;
    }

    /**
     * @return The texture of the model as Texture Attribute object.
     */
    public TextureAttribute getTexture() {
        return texture;
    }
}
