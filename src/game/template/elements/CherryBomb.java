package game.template.elements;

import javax.swing.*;
import java.awt.*;

/**
 * This class controls the time of showing card of cherry bomb and showing it.
 * Sets cherry bomb images.
 * @version 1.0 2021
 * @author Tanya Djavaherpour, Elaheh akbari
 */
public class CherryBomb implements Card, Images{

    private final int neededSuns;
    private String type;
    private String timeType;
    private boolean card;
    private long flowerTime;
    private boolean lock;
    private Image cherryBombCard;
    private Image cherryFull;

    /**
     * Constructs a new cherry bomb
     * @param type normal / hard
     * @param timeType night / day
     */
    public CherryBomb(String type, String timeType)
    {
        this.type = type;
        this.timeType = timeType;
        this.card = false;
        this.lock = true;
        neededSuns = 150;
        setImages();
    }
    /**
     * Sets all images of that cherryBomb
     */
    @Override
    public void setImages()
    {
        cherryBombCard = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_cherrybomb.png").getImage();
        cherryFull = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\newCherryBomb.gif").getImage();
    }
    /**
     *Returns the image of card
     */
    @Override
    public Image getCardImage()
    {
        return cherryBombCard;
    }
    /**
     * Returns the image of full flower
     */
    public Image getFullImage()
    {
        return cherryFull;
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
        if(card)
        {
            if(type.equals("normal") && (System.currentTimeMillis() - flowerTime) >= 30000)
                card = false;
            else if(type.equals("hard") && (System.currentTimeMillis() - flowerTime) >= 45000)
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
    /**
     * Sets the time that this flower has been chosen.
     */
    @Override
    public void setFlowerTime(long flowerTime) {
        this.flowerTime = flowerTime;
    }
}
