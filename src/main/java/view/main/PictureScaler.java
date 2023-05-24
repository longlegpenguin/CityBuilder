package view.main;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PictureScaler {

    private BufferedImage image;
    private Image scaledImage;

    public PictureScaler(String fileUrl, int width, int height) {
        try {
            image = ImageIO.read(new File(fileUrl));
        } catch (IOException ex) {
            System.err.println(ex);
        }
        scaledImage = scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    public Image getScaledImage() {
        return scaledImage;
    }
}
