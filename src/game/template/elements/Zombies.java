package game.template.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * This class controls zombies entrance and their movement,
 * makes list of zombies which are in the game.
 * @version 1.0 2021
 * @authors Tanya Djavaherpour, Elaheh akbari
 */
public class Zombies {
    protected ArrayList<Integer> normal;
    protected ArrayList<Integer> bucketHead;
    protected ArrayList<Integer> coneHead;
    private int normalNum;
    private int bucketNum;
    private int coneNum;
    private int zombieNum;
    private long time;
    private HashMap<Integer, String> information;
    private HashMap<Integer, NormalZombie> normalInfo;
    private HashMap<Integer, BucketHeadZombie> bucketInfo;
    private HashMap<Integer, ConeHeadZombie> coneInfo;
    private int cleared;
    /**
     * Creates new zombies lists
     */
    public Zombies()
    {
        normal = new ArrayList<>();
        bucketHead = new ArrayList<>();
        coneHead = new ArrayList<>();
        information = new HashMap<>();
        normalInfo = new HashMap<>();
        bucketInfo = new HashMap<>();
        coneInfo = new HashMap<>();
        normalNum = 0;
        bucketNum = 0;
        coneNum = 0;
        zombieNum = 0;
        cleared = 0;
    }
    /**
     * Adds a new normal zombie to its list and sets its properties such as row and x coordinate
     * @param loc as row number
     */
    public void setNormal(int loc)
    {
        normalInfo.put(normalNum, new NormalZombie());
        normalInfo.get(normalNum).setRow(loc);
        normalInfo.get(normalNum).setX(1005);
//        normalInfo.get(normalNum).add(200);
        zombieNum++;
    }
    /**
     * Returns normal zombies information
     * @return normalInfo --> key: zombie number, value: information of this zombie
     */
    public HashMap<Integer, NormalZombie> getNormalInfo(){return normalInfo;}
    /**
     * Adds a new bucket head zombie to its list and sets its properties such as row and x coordinate
     * @param loc as row number
     */
    public void setBucket(int loc)
    {
        bucketInfo.put(bucketNum, new BucketHeadZombie());
        bucketInfo.get(bucketNum).setRow(loc);
        bucketInfo.get(bucketNum).setX(1005);
//        bucketInfo.get(bucketNum).add(1300);
        zombieNum++;
    }
    /**
     * Returns bucket head zombies information
     * @return bucketInfo --> key: zombie number, value: information of this zombie
     */
    public HashMap<Integer, BucketHeadZombie> getBucketInfo(){return bucketInfo;}
    /**
     * Adds a new cone head zombie to its list and sets its properties such as row and x coordinate
     * @param loc as row number
     */
    public void setCone(int loc)
    {
        coneInfo.put(coneNum, new ConeHeadZombie());
        coneInfo.get(coneNum).setRow(loc);
        coneInfo.get(coneNum).setX(1005);
//        coneInfo.get(coneNum).add(560);
        zombieNum++;
    }

    /**
     * Returns cone head zombies information
     * @return coneInfo --> key: zombie number, value: information of this zombie
     */
    public HashMap<Integer, ConeHeadZombie> getConeInfo(){return coneInfo;}
    /**
     * Changes zombies location if they can move(they are not stopped by a flower)
     * and controls their speed based on game level
     * @param level normal / hard
     */
    public void move(String level)
    {
        float dec = 0;
        //moves normal zombies
        for (Map.Entry<Integer, NormalZombie> info: normalInfo.entrySet())
        {
            if(!info.getValue().isStopped())
            {
                if(info.getValue().isBurnt() && (System.currentTimeMillis()-info.getValue().getBurntTime())>6000)
                    dec = 1010;
                else
                    dec = (float) 0.9;
                if(info.getValue().isFrozen())
                    dec = dec / 2;
                info.getValue().setX((float) (info.getValue().getX() - dec));
            }
        }
        //moves bucket head zombie
        for (Map.Entry<Integer, BucketHeadZombie> info: bucketInfo.entrySet())
        {
            if(!info.getValue().isStopped())
            {
                dec = setDec(level);
                if(info.getValue().isFrozen())
                    dec = dec/2;
                info.getValue().setX((float) (info.getValue().getX() - dec));
            }
        }
        //moves cone head zombies
        for (Map.Entry<Integer, ConeHeadZombie> info: coneInfo.entrySet())
        {
            if(!info.getValue().isStopped())
            {
                dec = setDec(level);
                if(info.getValue().isFrozen())
                    dec = dec/2;
                info.getValue().setX((float) (info.getValue().getX() - dec));
            }
        }
    }

    /**
     * Sets decrement of zombie's x coordinate based on game level
     * @param level normal / hard
     * @return amount of decrement
     */
    private float setDec(String level)
    {
        float dec = 0;
        if(level.equals("normal"))
            dec = 1;
        else if(level.equals("hard"))
            dec = (float) 1.1;
        return dec;
    }

    /**
     * Sets zombies line by line, based on information that is saved in information by findCells method.
     * @param num as number of zombies thar should enter
     * @param t as the time of between zombies arrival
     */
    public void setZombies(int num, int t)
    {
        int i = 0;
        int flag =0;
        if(System.currentTimeMillis()-time>=t)
        {
            while (i < num)
            {
                int j = 0;
                for(Map.Entry<Integer, String> info: information.entrySet())
                {
                    if(flag == 0)
                    {
                        int loc = info.getKey();
                        if(j == zombieNum%5)
                        {
                            if(info.getValue().equals("normal"))
                            {
                                setNormal(loc);
                                normalNum++;
                                flag++;
                            }
                            else if(info.getValue().equals("bucket"))
                            {
                                setBucket(loc);
                                bucketNum++;
                                flag++;
                            }
                            else if(info.getValue().equals("cone"))
                            {
                                setCone(loc);
                                coneNum++;
                                flag++;
                            }
                        }
                        j++;
                        if(j>zombieNum)
                        {
                            setNormal(3);
                            flag++;
                        }
                    }
                }
                i++;
                flag = 0;
            }
            time = System.currentTimeMillis();
        }
    }

    /**
     *  Sets zombies line by line.
     * If row is empty or has squash normal zombie will enter,
     * if peashooters are more than other flowers bucket head zombie will enter,
     * otherwise if other flowers are more than peashooters cone head zombie will enter.
     * @param info as information of flowers in the playground
     */
    public void findCells(HashMap<Integer, String> info)
    {
        for (int j = 1; j <= 5; j++)
        {
            int peaShooters = 0;
            int squash = 0;
            int others = 0;
            int empty = 0;
            int flag = 0;
            for(int i = 1; i <= 9; i++)
            {
                int loc = j*10 + i;
                if(info.get(loc) != null)
                {
                    if(info.get(loc).contains("Shooter"))
                        peaShooters ++;
                    else if(info.get(loc).contains("squash"))
                        squash ++;
                    else if(info.get(loc) != null)
                        others ++;
                }
                else
                    empty ++;
            }
            if(squash == 0)
            {
                if(empty==9)
                {
                    normal.add(j);
                    information.put(j , "normal");
                    flag++;
                }
                else if(peaShooters>others)
                {
                    bucketHead.add(j);
                    information.put(j , "bucket");
                    flag++;
                }
                else if(others>0)
                {
                    coneHead.add(j);
                    information.put(j , "cone");
                    flag++;
                }
            }
            if(flag == 0)
            {
                normal.add(j);
                information.put(j , "normal");
            }
        }
    }

    public void setNormalNum(int normalNum) {
        this.normalNum = normalNum;
    }

    public void setConeNum(int coneNum) {
        this.coneNum = coneNum;
    }

    public void setBucketNum(int bucketNum) {
        this.bucketNum = bucketNum;
    }

    public void setNormal(int loc, float x, int life)
    {
        normalInfo.put(normalNum, new NormalZombie());
        normalInfo.get(normalNum).setRow(loc);
        normalInfo.get(normalNum).setX(x);
        normalInfo.get(normalNum).setLife(life);
        zombieNum++;
        normalNum++;
    }
    public void setCone(int loc, float x, int life)
    {
        coneInfo.put(coneNum, new ConeHeadZombie());
        coneInfo.get(coneNum).setRow(loc);
        coneInfo.get(coneNum).setX(x);
        coneInfo.get(coneNum).setLife(life);
        zombieNum++;
        coneNum++;
    }
    public void setBucket(int loc, float x, int life)
    {
        bucketInfo.put(bucketNum, new BucketHeadZombie());
        bucketInfo.get(bucketNum).setRow(loc);
        bucketInfo.get(bucketNum).setX(x);
        bucketInfo.get(bucketNum).setLife(life);
        zombieNum++;
        bucketNum++;
    }

}
