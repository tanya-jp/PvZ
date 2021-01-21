package game.template.bufferstrategy;

import java.util.ArrayList;
import java.util.HashMap;

public class FreezePeaShooter extends Pea implements Card{
    private final int neededSuns;
    private String type;
    private String timeType;
    private boolean card;
    private long flowerTime;
    private boolean lock;

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
    }
    public void addPea(int peaLoc) {
        super.addPea(peaLoc);
    }
    public HashMap<Integer, ArrayList<Integer>> getBullets()
    {
        return super.getBullets();
    }
    public void setBullets()
    {
        super.setBullets();
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
