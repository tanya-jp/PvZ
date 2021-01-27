package game.template.Elements;

import javax.swing.*;
import java.awt.*;

/**
 * This class controls the time of showing card of mushroom and showing it.(only at night)
 * Sets all images of mushroom.
 * @version 1.0 2021
 * @author Tanya Djavaherpour, Elaheh akbari
 */
public class Mushroom implements Card, Images{
    private final int neededSuns;
    private String type;
    private String timeType;
    private boolean card;
    private long flowerTime;
    private boolean lock;
    private Image mushroomCard;
    private Image mushroomFull;

    /**
     * Constructs a new mushroom when it is night.
     * @param type normal / hard
     * @param timeType night / day
     */
    public Mushroom(String type, String timeType)
    {
        this.type = type;
        this.timeType = timeType;
        this.card = false;
        this.lock = true;
        neededSuns = 25;
        setImages();
    }
    /**
     * Sets all images of that mushroom
     */
    @Override
    public void setImages()
    {
        mushroomCard = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_sun-shroom.png").getImage();
        mushroomFull = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\Sun_Shroom.gif").getImage();
    }
    /**
     *Returns the image of card
     */
    @Override
    public Image getCardImage()
    {
        return mushroomCard;
    }
    /**
     * Returns the image of full flower
     */
    public Image getFullImage()
    {
        return mushroomFull;
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
    /**
     * Returns the time that this flower has been chosen.
     */
    @Override
    public long getFlowerTime(){return flowerTime;}
}
