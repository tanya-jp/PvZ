package game.template.Elements;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This  class fixes the location of peas that peashooter and freeze pea shooter shoots,
 * and controls the time of shooting peas.
 * Sets and gets images of pea.
 * @version 1.0 2021
 * @author Tanya Djavaherpour, Elaheh akbari
 */
public abstract class Pea{
    private long bulletTime;
    //key --> location , values --> location of shooting peas
    private HashMap<Integer, ArrayList<Integer>> bullets;
    /**
     * Constructs a new arraylist of peas for new peashooter that has benn added recently.
     * @param type normal / hard
     * @param timeType day / night
     */
    public Pea(String type, String timeType)
    {
        bulletTime = System.currentTimeMillis();
        bullets = new HashMap<>();
    }
    /**
     * Sets related pea image.
     */
    public abstract void setPeaImage();
    /**
     *Returns related pea image.
     */
    public abstract Image getPea();
    /**
     * when a nea peashooter is added to the playground, this method adds new key to the Hashmap.
     * @param peaLoc location of new peashooter in the form of yx
     */
    public void addPea(int peaLoc) {
        if(bullets.isEmpty())
            bulletTime = System.currentTimeMillis();
        if (bullets.get(peaLoc) == null)
            bullets.put(peaLoc, new ArrayList<Integer>()); //no ArrayList assigned, create new ArrayList
        bullets.get(peaLoc).add(peaLoc % 10);
    }
    /**
     * Returns the hashmap of peas
     */
    public HashMap<Integer, ArrayList<Integer>> getBullets()
    {
        return bullets;
    }
    /**
     * Changes the location of peas and adds a new pea when its time arrives.
     */
    public void setBullets()
    {
        int flag = 0;
        for(HashMap.Entry<Integer, ArrayList<Integer>> set : bullets.entrySet())
        {
            int i = 0;
            for (Integer value : set.getValue())
            {
                value = value+10;
                set.getValue().set(i, value);
                i++;
            }
            if(System.currentTimeMillis() - bulletTime > 500) {
                addPea(set.getKey());
                flag ++;
            }
        }
        if (flag>0)
            bulletTime = System.currentTimeMillis();
    }

}
