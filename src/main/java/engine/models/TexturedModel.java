package engine.models;

import engine.textures.TextureAttribute;

/**
 * Represents a textured model
 * Contains a raw model and a model texture
 */
public class TexturedModel {

    private RawModel rawModel;
    private TextureAttribute texture;

    public TexturedModel(RawModel rawModel, TextureAttribute texture) {
        this.rawModel = rawModel;
        this.texture = texture;
    }

    public RawModel getRawModel() {
        return rawModel;
    }

    public TextureAttribute getTexture() {
        return texture;
    }
}
