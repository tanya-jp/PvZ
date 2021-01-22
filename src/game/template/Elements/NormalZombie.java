package game.template.Elements;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;


public class NormalZombie extends Zombie implements Images {
    private Image fullZombie;
    public NormalZombie()
    {
        super();
        setImages();
    }
    public void setInfo()
    {

    }
    /**
     * Sets all images of that flower
     */
    @Override
    public void setImages(){
        fullZombie = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\zombie_normal.gif").getImage();
    }
    /**
     * Returns the image of full flower
     */
    @Override
    public Image getFullImage(){
        return fullZombie;
    }
}
