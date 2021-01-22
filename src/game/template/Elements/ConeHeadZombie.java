package game.template.Elements;

import javax.swing.*;
import java.awt.*;

public class ConeHeadZombie implements Zombie, Images{
    private Image fullZombie;
    public ConeHeadZombie()
    {
        setImages();
    }
    /**
     * Sets all images of that flower
     */
    @Override
    public void setImages(){
        fullZombie = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\coneheadzombie.gif").getImage();
    }
    /**
     * Returns the image of full flower
     */
    @Override
    public Image getFullImage(){
        return fullZombie;
    }
}
