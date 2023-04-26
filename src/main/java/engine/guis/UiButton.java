package engine.guis;

import engine.display.DisplayManager;
import engine.tools.Mouse;
import org.joml.Vector2f;


public class UiButton extends UiComponent {

    private ButtonEnum buttonEnum;

    public UiButton(int texture, Vector2f position, Vector2f scale, ButtonEnum buttonEnum) {
        super(texture, position, scale);
        this.buttonEnum = buttonEnum;
        this.isClickable = true;

    }

    public int getTexture() {
        return texture;
    }

    public ButtonEnum getButtonEnum() {
        return buttonEnum;
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
