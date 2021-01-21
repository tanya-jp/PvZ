package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Background extends JPanel {
    private final Image backgroundImg;

    public Background(String fileName) throws IOException {
        backgroundImg = ImageIO.read(new File(fileName));
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImg, 0, 0, this);
    }
}
