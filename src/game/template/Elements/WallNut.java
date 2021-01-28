package game.template.Elements;

import javax.swing.*;
import java.awt.*;

/**
 * This class controls the time of showing card of wall nut and showing it.
 * Sets all images of wall nut.
 * @version 1.0 2021
 * @author Tanya Djavaherpour, Elaheh akbari
 */
public class WallNut implements Card, Images{

    private final int neededSuns;
    private String type;
    private String timeType;
    private boolean card;
    private long flowerTime;
    private boolean lock;
    private Image wallNutCard;
    private Image walnutFull;
    private Image wallNutHalf;
    private Image wallNutDead;

    /**
     * Constructs a new wall nut
     * @param type normal / hard
     * @param timeType night / day
     */
    public WallNut(String type, String timeType)
    {
        this.type = type;
        this.timeType = timeType;
        this.card = false;
        this.lock = true;
        neededSuns = 50;
        setImages();
    }
    /**
     * Sets all images of that wallNut
     */
    @Override
    public void setImages()
    {
        wallNutCard = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_wallnut.png").getImage();
        walnutFull = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\walnut_full_life.gif").getImage();
        wallNutHalf = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\walnut_half_life.gif").getImage();
        wallNutDead = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\walnut_dead.gif").getImage();
    }
    /**
     *Returns the image of card
     */
    @Override
    public Image getCardImage()
    {
        return wallNutCard;
    }
    /**
     * Returns the image of full flower
     */
    @Override
    public Image getFullImage()
    {
        return walnutFull;
    }
    /**
     * Returns the image of half flower
     */
    public Image getHalfImage()
    {
        return wallNutHalf;
    }
    /**
     * Returns the image of dead flower
     */
    public Image getDeadImage()
    {
        return wallNutDead;
    }
    /**
     * Sets card state
     */
    @Override
    public void setCardState(boolean card){this.card = card;}
    /**
     * makes state of cards based on proper time
     */
    @Override
    public void setCard() {
        if(card && (System.currentTimeMillis() - flowerTime) >= 30000)
            card = false;
    }
    /**
     * If card can be appeared, returns false.
     */
    @Override
    public boolean getCard()
    {
        return card;
    }
    /**
     * Checks if flower can be chosen, changes it state and sets the time that flower has been chosen
     * @param sunsNumber the number of available suns
     * @return the number of available suns
     */
    @Override
    public int chooseFlower(int sunsNumber)
    {
        if(sunsNumber >= neededSuns && !card)
        {
            sunsNumber -= neededSuns;
            flowerTime = System.currentTimeMillis();
            card = true;
        }
        return sunsNumber;
    }
    /**
     * Defines if the flower can be placed.
     * @return true if the flower is lock and can not be placed and false when it is unlocked and can be placed
     */
    @Override
    public boolean getLock()
    {
        return lock;
    }
    /**
     * Locks or unlocks the flower.
     */
    @Override
    public void setLock(boolean lock)
    {
        this.lock = lock;
    }
    /**
     * Returns the time that this flower has been chosen.
     */
    @Override
    public long getFlowerTime(){return flowerTime;}
}
