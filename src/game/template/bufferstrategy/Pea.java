package game.template.bufferstrategy;

import java.util.ArrayList;
import java.util.HashMap;

public class Pea{
    private long bulletTime;
    private HashMap<Integer, ArrayList<Integer>> bullets;
    public Pea(String type, String timeType)
    {
        bulletTime = System.currentTimeMillis();
        bullets = new HashMap<>();
    }
    public void addPea(int peaLoc) {
        if(bullets.isEmpty())
            bulletTime = System.currentTimeMillis();
        if (bullets.get(peaLoc) == null)
            bullets.put(peaLoc, new ArrayList<Integer>()); //no ArrayList assigned, create new ArrayList
        bullets.get(peaLoc).add(peaLoc % 10);
    }
    public HashMap<Integer, ArrayList<Integer>> getBullets()
    {
        return bullets;
    }
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
