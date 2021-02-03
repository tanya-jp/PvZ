package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * This class is used whenever we want to have a background image on GUI frames.
 * It overrides the paintComponent method.
 * An object of this class is created wherever needed.
 * @version 1.0 2021
 * @authors Elaheh Akbari and Tanya Djavaherpour
 */
public class Background extends JPanel {
    private final Image backgroundImg;

    /**
     * Constructor
     * @param fileName name of background image file.
     * @throws IOException possible exception.
     */
    public Background(String fileName) throws IOException {
        backgroundImg = ImageIO.read(new File(fileName));
    }

    /**
     * Override paintComponent method.
     * @param g an object of Graphics.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImg, 0, 0, this);
    }
}
