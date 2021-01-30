package game.template.Elements;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * This class controls the time of showing card of mushroom and showing it.(only at night)
 * Sets all images of mushroom.
 * @version 1.0 2021
 * @author Tanya Djavaherpour, Elaheh akbari
 */
public class Mushroom extends SunProducer implements Card, Images{
    private final int neededSuns;
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
        super(type, timeType);
        this.card = false;
        this.lock = true;
        neededSuns = 25;
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
     * Sets card state
     */
    @Override
    public void setCardState(boolean card){this.card = card;}
    /**
     * makes state of cards based on proper time
     */
    @Override
    public void setCard() {
        if(card && (System.currentTimeMillis() - flowerTime) >= 7000)
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
