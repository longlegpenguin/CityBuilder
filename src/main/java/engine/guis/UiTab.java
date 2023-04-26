package engine.guis;

import engine.guis.UiComponent;
import org.joml.Vector2f;

public class UiTab extends UiComponent {

    public UiTab(int texture, Vector2f position, Vector2f scale) {
        super(texture, position, scale);
        this.isClickable = false;
    }
}
