package engine.guis;

import org.joml.Vector2f;

/**
 * Extension of the UI Component
 * Non-clickable tab which is a rectangular object with Texture.
 * Used as back plane for for menu's
 */
public class UiTab extends UiComponent {

    public UiTab(int texture, Vector2f position, Vector2f scale) {
        super(texture, position, scale);
        this.isClickable = false;
    }
}
