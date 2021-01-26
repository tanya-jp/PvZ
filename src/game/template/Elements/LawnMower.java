package game.template.Elements;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This class makes lawn maker and fixes its state and image, changes its x coordinate when lawn maker should move.
 * @version 1.0 2021
 * @authors Tanya Djavaherpour, Elaheh Akbari
 */
public class LawnMower {
    private int row;
    private int x;
    private boolean available;
    private Image image;
    /**
     * Constructs a new lawn mower
     * @param num as row of lawn mower
     */
    public LawnMower(int num)
    {
        this.row = num;
        available = true;
        this.x = -35;
        image = new ImageIcon(".\\PVS Design Kit\\images\\Lawn_Mower.png").getImage();
    }

    /**
     * Returns the image of lawn maker
     */
    public Image getImage() {
        return image;
    }

    /**
     * If lawn mover is not available and have moved yet, sets false as its value.
     * @param available as lawn mover state
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * If lawn mover is available and have not moved yet, returns true
     * @return available as lawn mover state
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Gets the rows(number) of this lawn maker.
     */
    public int getRow() {
        return row;
    }

    /**
     * Moves lawn maker by setting its x coordinate.
     */
    public void move() {
        if(!available)
            x = x + 10;
    }
    /**
     * Returns x coordinate of lawn maker.
     */
    public int getX() {
        return x;
    }
}
