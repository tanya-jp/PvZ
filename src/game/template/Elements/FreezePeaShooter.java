package game.template.Elements;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * This class controls the time of showing card of freeze pea shooter, showing freeze pea shooter
 * and shooting peas.
 * Sets all freeze pea shooter images
 * @version 1.0 2021
 * @author Tanya Djavaherpour, Elaheh akbari
 */
public class FreezePeaShooter extends Pea implements Card, Images{
    private final int neededSuns;
    private String type;
    private String timeType;
    private boolean card;
    private long flowerTime;
    private boolean lock;
    private Image freezePeaShooterCard;
    private Image freezeShooterFull;
    private Image pea;

    /**
     * Constructs a new freeze pea shooter
     * @param type normal / hard
     * @param timeType night / day
     */
    public FreezePeaShooter(String type, String timeType)
    {
        super(type, timeType);
        this.type = type;
        this.timeType = timeType;
        this.card = false;
        this.lock = true;
        neededSuns = 175;
        setImages();
    }
    /**
     * Sets related pea image.
     */
    @Override
    public void setPeaImage() {
        pea = new ImageIcon(".\\PVS Design Kit\\images\\freezepea.png").getImage();
    }
    /**
     *Returns related pea image.
     */
    @Override
    public Image getPea(){return pea;}
    /**
     * Sets all images of that sunFlower
     */
    @Override
    public void setImages()
    {
        freezePeaShooterCard = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_freezepeashooter.png").getImage();
        freezeShooterFull = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\freezepeashooter.gif").getImage();
        setPeaImage();
    }
    /**
     *Returns the image of card
     */
    @Override
    public Image getCardImage()
    {
        return freezePeaShooterCard;
    }
    /**
     * Returns the image of full flower
     */
    public Image getFullImage()
    {
        return freezeShooterFull;
    }
    /**
     * when a nea peashooter is added to the playground, this method adds new key to the Hashmap.
     * @param peaLoc location of new peashooter in the form of yx
     */
    @Override
    public void addPea(int peaLoc) {
        super.addPea(peaLoc);
    }
    /**
     * Returns the hashmap of peas
     */
    @Override
    public HashMap<Integer, ArrayList<Integer>> getBullets()
    {
        return super.getBullets();
    }
    /**
     * Changes the location of peas and adds a new pea when its time arrives.
     */
    @Override
    public void setBullets()
    {
        super.setBullets();
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
    /**
     * Sets the time that this flower has been chosen.
     */
    @Override
    public void setFlowerTime(long flowerTime) {
        this.flowerTime = flowerTime;
    }
}
