package view.main;

import javax.swing.*;
import java.awt.*;

public class MainMenu {
    private JFrame frame;
    private BackgroundPanel backPanel;
    private int defaultWidth = 1280;
    private int defaultHeight = 720;

    public MainMenu() {
        frame = new JFrame("Utopia");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout());
        frame.setResizable(false);

        backPanel = new BackgroundPanel(defaultWidth, defaultHeight);
        backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.PAGE_AXIS));

        frame.getContentPane().add(backPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
