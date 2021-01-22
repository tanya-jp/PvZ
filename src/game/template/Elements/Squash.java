package game.template.Elements;

import javax.swing.*;
import java.awt.*;

/**
 * This class controls the time of showing card of squash and showing it.
 * Sets all images of squash.
 * @version 1.0
 * @author Tanya Djavaherpour
 */
public class Squash implements Card, Images{
    private final int neededSuns;
    private String type;
    private String timeType;
    private boolean card;
    private long flowerTime;
    private boolean lock;
    private Image squashCard;
    private Image squashFull;

    /**
     * Constructs a new mushroom when it is night.
     * @param type normal / hard
     * @param timeType night / day
     */
    public Squash(String type, String timeType)
    {
        this.type = type;
        this.timeType = timeType;
        this.card = false;
        this.lock = true;
        neededSuns = 50;
        setImages();
    }
    /**
     * Sets all images of that squash
     */
    @Override
    public void setImages()
    {
        squashCard = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_squash.jpg").getImage();
        squashFull = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\squash.gif").getImage();
    }
    /**
     *Returns the image of card
     */
    @Override
    public Image getCardImage()
    {
        return squashCard;
    }
    /**
     * Returns the image of full flower
     */
    public Image getFullImage()
    {
        return squashFull;
    }
    /**
     * makes state of cards based on proper time
     */
    @Override
    public void setCard() {
        if(card)
        {
            if(type.equals("normal") && (System.currentTimeMillis() - flowerTime) >= 7500)
                card = false;
            else if(type.equals("hard") && (System.currentTimeMillis() - flowerTime) >= 30000)
                card = false;
        }
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
}