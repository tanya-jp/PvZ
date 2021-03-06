package game.template.elements;

import javax.swing.*;
import java.util.HashMap;
import java.awt.*;
/**
 * This class controls the time of showing card of sunFlower and showing it.
 * Also manages producing sun by sunFlower.
 * Sets sunFlower images.
 * @version 1.0 2021
 * @author Tanya Djavaherpour, Elaheh akbari
 */
public class SunFlower extends SunProducer implements Card, Images {

    private final int neededSuns;
    private boolean card;
    private long flowerTime;
    private boolean lock;
    private Image sunFlowerCard;
    private Image sunFlowerFull;
    private Image sunFlowerDead;

    /**
     * Constructs a new sunflower
     * @param type normal / hard
     * @param timeType night / day
     */
    public SunFlower(String type, String timeType)
    {
        super(type, timeType);
        this.card = false;
        this.lock = true;
        neededSuns = 50;
        setImages();
        init();
    }

    /**
     * Puts all cells to the  hashMaps and makes their values null for the first time
     * that means there is not any sun that location.
     */
    @Override
    public void init()
    {
        super.init();
    }
    /**
     * Checks if sun flower can make based on game type.
     * If it is possible, sets it location.
     */
    @Override
    public void setSunFlowerState()
    {
        super.setSunFlowerState();
    }
    /**
     * If sunflower's sun can be appeared, returns true.
     */
    @Override
    public HashMap<Integer, Boolean> getSunFlowerState()
    {
        return super.getSunFlowerState();
    }

    /**
     * Returns time of appearing suns.
     */
    @Override
    public HashMap<Integer, Long> getSunFlowerSunTime() {
        return super.getSunFlowerSunTime();
    }

    /**
     * After clicking on the produced sun by sunFlower, increases sunNumbers and removes if from hashmaps.
     * @param loc clicked location(location of sinFlower)
     * @param sunNumber number of chosen suns
     * @return new sun number, after adding produced sun of sunFlower
     */
    @Override
    public int saveSun(int loc, int sunNumber)
    {
        return super.saveSun(loc, sunNumber);
    }

    /**
     * makes a new sunFlower and sets time of adding and sets its state true.
     * @param loc as location of sunFlower
     */
    @Override
    public void addSunFlower(int loc)
    {
        super.addSunFlower(loc);
    }

    /**
     * Removes chosen sun flower by shovel
     * @param loc as location of selected sunFlower
     */
    @Override
    public void removeSunFlower(int loc)
    {
        super.removeSunFlower(loc);
    }
    /**
     * Sets all images of that sunFlower
     */
    @Override
    public void setImages()
    {
        sunFlowerCard = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_sunflower.png").getImage();
        sunFlowerFull = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\sun_flower.gif").getImage();
        sunFlowerDead = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\sun_flower_dying.gif").getImage();
    }
    /**
     *Returns the image of card
     */
    @Override
    public Image getCardImage()
    {
        return sunFlowerCard;
    }
    /**
     * Returns the image of full flower
     */@Override
    public Image getFullImage()
    {
        return sunFlowerFull;
    }
    /**
     * Returns the image of dead flower
     */
    public Image getDeadImage()
    {
        return sunFlowerDead;
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
        if(card && (System.currentTimeMillis() - flowerTime) >= 7500)
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
    /**
     * Sets the time that this flower has been chosen.
     */
    @Override
    public void setFlowerTime(long flowerTime) {
        this.flowerTime = flowerTime;
    }
}
