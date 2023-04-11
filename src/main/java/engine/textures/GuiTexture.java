package engine.textures;

import engine.display.DisplayManager;
import engine.tools.Mouse;
import org.joml.Vector2f;


public class GuiTexture {
    private int texture;
    private Vector2f position;
    private Vector2f scale;

    private boolean isClicked;


    public GuiTexture(int texture, Vector2f position, Vector2f scale) {
        this.texture = texture;
        this.position = position;
        this.scale = scale;
        this.isClicked = false;

    }

    public int getTexture() {
        return texture;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getScale() {
        return scale;
    }

    public void setTexture(int texture) {
        this.texture = texture;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public void setScale(Vector2f scale) {
        this.scale = scale;
    }





    public boolean isClicked() {
        double mouseX =  (2f * Mouse.getX()) / DisplayManager.getWindowWidth() - 1f;
        double mouseY = (2f * Mouse.getY() ) / DisplayManager.getWindowHeight() - 1f;
        if (mouseX >= position.x - scale.x && mouseX <= position.x  + scale.x && mouseY >=  position.y - scale.y && mouseY <= position.y + scale.y) {
            System.out.println("Clicked");
            isClicked = true;
            return true;
        }

        else return false;
    }
}
