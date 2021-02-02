package game.template.Elements;

import java.util.HashMap;

/**
 * This class is used for producing sun.
 * Flowers which produce sun such as mushroom and sunFlower extends from this class and
 * it handles time of sun producing and saving.
 * @version 1.0 2021
 * @authors Tanya Djavaherpour, Elaheh Akbari
 */
public class SunProducer {
    private HashMap<Integer, Boolean> sunFlowerState;
    private HashMap<Integer, Long> sunFlowerSunTime;
    private String type;
    private String timeType;

    public SunProducer(String type, String timeType)
    {
        this.type = type;
        this.timeType = timeType;
        sunFlowerState = new HashMap<>();
        sunFlowerSunTime = new HashMap<>();
    }
    /**
     * Puts all cells to the  hashMaps and makes their values null for the first time
     * that means there is not any sun that location.
     */
    public void init()
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
     * Returns time of appearing suns.
     */
    public HashMap<Integer, Long> getSunFlowerSunTime() {
        return sunFlowerSunTime;
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
}
