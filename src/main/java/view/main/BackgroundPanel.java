package view.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;


public class BackgroundPanel extends JPanel{
    private Image scaledImage;

    public BackgroundPanel(int width, int height) {
        this.setNewSize(width, height);
    }

    public void setNewSize(int width, int height) {
        this.setMinimumSize(new Dimension(width, height));
        this.setMaximumSize(new Dimension(width, height));
        this.setPreferredSize(new Dimension(width, height));
        scaledImage = new PictureScaler("src//main//resources//textures//background.png", width, height).getScaledImage();
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(scaledImage, 0, 0, this);
    }
}
