package game.template.Elements;

import javax.swing.*;
import java.util.HashMap;
import java.awt.*;
/**
 * This class controls the time of showing card of sunFlower and showing it.
 * Also manages producing sun by sunFlower.
 * Sets sunFlower images.
 * @version 1.0
 * @author Tanya Djavaherpour
 */
public class SunFlower implements Card, Images {

    private final int neededSuns;
    private String type;
    private String timeType;
    private boolean card;
    private long flowerTime;
    private boolean lock;
    private HashMap<Integer, Boolean> sunFlowerState;
    private HashMap<Integer, Long> sunFlowerSunTime;
    private Image sunFlowerCard;
    private Image sunFlowerFull;

    /**
     * Constructs a new sunflower
     * @param type normal / hard
     * @param timeType night / day
     */
    public SunFlower(String type, String timeType)
    {
        sunFlowerState = new HashMap<>();
        sunFlowerSunTime = new HashMap<>();
        this.type = type;
        this.timeType = timeType;
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
    private void init()
    {
        sunFlowerState = new HashMap<>();
        for (int i = 1; i <= 9; i++)
        {
            for(int j = 1; j <= 5; j++)
            {
                int loc = j*10 + i;
                sunFlowerState.put(loc, null);
                sunFlowerSunTime.put(loc, null);
            }
        }
    }
    /**
     * Checks if sun flower can make based on game type.
     * If it is possible, sets it location.
     */
    public void setSunFlowerState()
    {
        long time = System.currentTimeMillis();
        for (HashMap.Entry<Integer, Boolean> set : sunFlowerState.entrySet()) {
            if (set.getValue() != null)
                if(!set.getValue())
                {
                    int loc = set.getKey();
                    if(type.equals("normal") && time - sunFlowerSunTime.get(loc) >= 20000)
                        sunFlowerState.replace(loc, true);
                    else if(type.equals("hard") && time - sunFlowerSunTime.get(loc) >= 25000)
                        sunFlowerState.replace(loc, true);
                }
        }
    }
    /**
     * If sunflower's sun can be appeared, returns true.
     */
    public HashMap<Integer, Boolean> getSunFlowerState()
    {
        return sunFlowerState;
    }

    /**
     * After clicking on the produced sun by sunFlower, increases sunNumbers and removes if from hashmaps.
     * @param loc clicked location(location of sinFlower)
     * @param sunNumber number of chosen suns
     * @return new sun number, after adding produced sun of sunFlower
     */
    public int saveSun(int loc, int sunNumber)
    {
        if(sunFlowerState.get(loc) != null)
            if (sunFlowerState.get(loc))
            {
                sunFlowerState.replace(loc, false);
                sunFlowerSunTime.replace(loc, System.currentTimeMillis());
                sunNumber += 25;
            }
        return sunNumber;
    }

    /**
     * makes a new sunFlower and sets time of adding and sets its state true.
     * @param loc as location of sunFlower
     */
    public void addSunFlower(int loc)
    {
        if(sunFlowerState.get(loc) == null)
            sunFlowerState.replace(loc, true);
        long time = System.currentTimeMillis();
        if(sunFlowerSunTime.get(loc) == null)
            sunFlowerSunTime.replace(loc, time);
    }

    /**
     * Removes chosen sun flower by shovel
     * @param loc as location of selected sunFlower
     */
    public void removeSunFlower(int loc)
    {
        sunFlowerState.replace(loc, null);
        sunFlowerSunTime.replace(loc, null);
    }
    /**
     * Sets all images of that sunFlower
     */
    @Override
    public void setImages()
    {
        sunFlowerCard = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_sunflower.png").getImage();
        sunFlowerFull = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\sun_flower.gif").getImage();
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
     */
    public Image getFullImage()
    {
        return sunFlowerFull;
    }
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
}
