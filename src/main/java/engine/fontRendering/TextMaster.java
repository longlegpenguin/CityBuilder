package engine.fontRendering;

import engine.fontMeshCreator.FontType;
import engine.fontMeshCreator.GUIText;
import engine.fontMeshCreator.TextMeshData;
import engine.renderEngine.Loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * the Class  of the Text rendering
 */

public class TextMaster {
    private static Loader loader;
    private static Map<FontType, List<GUIText>> texts = new HashMap<FontType,List<GUIText>>();
    private static FontRenderer renderer;

    /**
     * initilizing the class with the loader
     * @param theLoader
     */
    public static void  init(Loader theLoader)
    {
        renderer = new FontRenderer();
        loader = theLoader;
    }

    /**
     * method to load a text into the text batch and the array list of the texts ready to render
     * @param text
     */
    public static void loadText (GUIText text)
    {
        FontType font = text.getFont();
        TextMeshData data = font.loadText(text);
        int vao = loader.loadToVAO(data.getVertexPositions(),data.getTextureCoords());
        text.setMeshInfo(vao,data.getVertexCount());
        List<GUIText> textBatch  = texts.get(font);
        if (textBatch == null )
        {
            textBatch = new ArrayList<GUIText>();
            texts.put(font,textBatch);

        }
        textBatch.add(text);
    }

    /**
     * method to render the texts in the array list of texts
     */
    public static void render()
    {
        renderer.render(texts);
    }

    /**
     * method to remove a text from the textbatch and the array list of texts
     * @param text
     */
    public static void removeText(GUIText text ){
        List<GUIText> textBatch  = texts.get(text.getFont());
        textBatch.remove(text);
        if(textBatch.isEmpty())
        {
            texts.remove(text.getFont());
        }
    }

    /**
     * method to clean up the renderer
     */
    public static void cleanUp()
    {
        renderer.cleanUp();
    }

}
