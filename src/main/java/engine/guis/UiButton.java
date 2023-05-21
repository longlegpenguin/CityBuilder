package engine.guis;

import engine.display.DisplayManager;
import engine.tools.Mouse;
import org.joml.Vector2f;

/**
 * Button component which extends UIComponent.
 * Button is a 2d clickable object on screen for the user to interact with the game.
 */
public class UiButton extends UiComponent {

    private ButtonEnum buttonEnum;
    private boolean enabled;

    /**
     * Constructor for the button.
     * @param texture Texture ID of the button.
     * @param position Position on screen as a Vector2f - center point of the object.
     * @param scale Scale in X and Y from Centre point as Vector2f
     * @param buttonEnum Type of the button - used for distinction when the button is pressed.
     */
    public UiButton(int texture, Vector2f position, Vector2f scale, ButtonEnum buttonEnum) {
        super(texture, position, scale);
        this.buttonEnum = buttonEnum;
        this.isClickable = true;
        this.enabled = false;
    }

    /**
     * @return Enum of the button type.
     */
    public ButtonEnum getButtonEnum() {
        return buttonEnum;
    }

    /**
     * @param enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return if button is enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Checks if the mouse is within the position of the button.
     * If mouse is and method is called - returns true.
     * @return boolean value
     */
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
