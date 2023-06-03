package engine.textures;

import java.awt.image.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.spi.FileSystemProvider;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import javax.imageio.ImageIO;

import static org.lwjgl.opengl.GL11.*;



public class Texture {
    private ByteBuffer buffer;
    private int textureID, width, height;

    public Texture(int width, int heigh, ByteBuffer image) {
        this.buffer = image;
        this.height = heigh;
        this.width = width;
    }

    /**
     *
     * @return the ID of the Texture as stored in OpenGL
     */
    public int getTextureID() {
        textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        glBindTexture(GL_TEXTURE_2D, 0);
        return textureID;
    }

    /**
     *
     * @return the buffer created from the image
     */
    public ByteBuffer getBuffer() {
        return buffer;
    }

    /**
     *
     * @return the width of the texture
     */
    public int getWidth() {
        return width;
    }

    /**
     *
     * @return the height of the texture
     */
    public int getHeight() {
        return height;
    }

    /**
     *  Converts the texture image to a byteBuffer
     * @param path is the name of the texture image
     *
     * @return a Texture object
     */
    public static Texture loadTexture(String path) {
        ByteBuffer image;
        int width, heigh;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer comp = stack.mallocInt(1);
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);

            URL resource = Texture.class.getResource(path);
            URI uri  = resource.toURI();
            if("jar".equals(uri.getScheme())){
                for (FileSystemProvider provider: FileSystemProvider.installedProviders()) {
                    if (provider.getScheme().equalsIgnoreCase("jar")) {
                        try {
                            provider.getFileSystem(uri);
                        } catch (FileSystemNotFoundException e) {
                            // in this case we need to initialize it first:
                            provider.newFileSystem(uri, Collections.emptyMap());
                        }
                    }
                }
            }
            Path source = Paths.get(uri);
            path = source.toString();
            if (path.startsWith("/")) {
                path = path.substring(1);
                path = "./classes/" + path;
            }
            image = STBImage.stbi_load(path, w, h, comp, STBImage.STBI_rgb_alpha );
            if (image == null) {
                System.err.println("Couldn't load " + path);
            }
            width = w.get();
            heigh = h.get();
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }

        return new Texture(width, heigh, image);
    }
}
