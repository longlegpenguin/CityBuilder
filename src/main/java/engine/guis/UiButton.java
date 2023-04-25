package engine.guis;

import engine.display.DisplayManager;
import engine.tools.Mouse;
import org.joml.Vector2f;


public class UiButton extends UiComponent {

    public UiButton(int texture, Vector2f position, Vector2f scale) {
        super(texture, position, scale);
        this.isClickable = true;

    }

    public int getTexture() {
        return texture;
    }

    public boolean isClicked() {
        double mouseX =  (2f * Mouse.getX()) / DisplayManager.getWindowWidth() - 1f;
        double mouseY = (2f * Mouse.getY() ) / DisplayManager.getWindowHeight() - 1f;
        if (mouseX >= position.x - scale.x && mouseX <= position.x  + scale.x && mouseY >=  position.y - scale.y && mouseY <= position.y + scale.y) {
            return true;
        }
        else
            return false;
    }
}
