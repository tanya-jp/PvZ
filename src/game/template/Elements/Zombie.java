package game.template.Elements;

import game.template.bufferstrategy.GameState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Zombie {
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
    ArrayList<Integer> squashes;
    public Zombie()
    {
        normal = new ArrayList<>();
        bucketHead = new ArrayList<>();
        coneHead = new ArrayList<>();
        information = new HashMap<>();
        normalInfo = new HashMap<>();
        bucketInfo = new HashMap<>();
        coneInfo = new HashMap<>();
        squashes = new ArrayList<>();
        normalNum = 0;
        bucketNum = 0;
        coneNum = 0;
        zombieNum = 0;
        cleared = 0;
    }
    public void setNormal(int loc)
    {
            normalInfo.put(normalNum, new NormalZombie());
        normalInfo.get(normalNum).setRow(loc);
        normalInfo.get(normalNum).setX(1005);
//        normalInfo.get(normalNum).add(200);
        zombieNum++;
    }
    public HashMap<Integer, NormalZombie> getNormalInfo(){return normalInfo;}
    public void setBucket(int loc)
    {
        bucketInfo.put(bucketNum, new BucketHeadZombie());
        bucketInfo.get(bucketNum).setRow(loc);
        bucketInfo.get(bucketNum).setX(1005);
//        bucketInfo.get(bucketNum).add(1300);
        zombieNum++;
    }
    public HashMap<Integer, BucketHeadZombie> getBucketInfo(){return bucketInfo;}
    public void setCone(int loc)
    {
            coneInfo.put(coneNum, new ConeHeadZombie());
        coneInfo.get(coneNum).setRow(loc);
        coneInfo.get(coneNum).setX(1005);
//        coneInfo.get(coneNum).add(560);
        zombieNum++;
    }
    public HashMap<Integer, ConeHeadZombie> getConeInfo(){return coneInfo;}
    public void move(String level)
    {
        for (Map.Entry<Integer, NormalZombie> info: normalInfo.entrySet())
        {
//            System.out.println(System.currentTimeMillis()-info.getValue().getBurntTime());
            if(!info.getValue().isStopped())
            {
                if(info.getValue().isBurnt() && (System.currentTimeMillis()-info.getValue().getBurntTime())>6000)
                {
                    info.getValue().setX((float) (info.getValue().getX() - 1010));
//                    System.out.println(info.getValue().getX());
                }
                else
                    info.getValue().setX((float) (info.getValue().getX() - 0.9));
            }
        }
        for (Map.Entry<Integer, BucketHeadZombie> info: bucketInfo.entrySet())
        {
            if(!info.getValue().isStopped())
            {
                if(level.equals("normal"))
                    info.getValue().setX((float) (info.getValue().getX() - 1));
                else if(level.equals("hard"))
                    info.getValue().setX((float) (info.getValue().getX() - 1.1));
            }
        }
        for (Map.Entry<Integer, ConeHeadZombie> info: coneInfo.entrySet())
        {
            if(!info.getValue().isStopped())
            {
                if(level.equals("normal"))
                    info.getValue().setX((float) (info.getValue().getX() - 1));
                else if(level.equals("hard"))
                    info.getValue().setX((float) (info.getValue().getX() - 1.1));
            }
        }
    }
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
//    public void clearList()
//    {
//        for (int j = 0; j< squashes.size(); j++)
//        {
//            squashes.remove(j);
//        }
//    }
    public void getAttackBySquash(HashMap<Integer, String> info)
    {
        if(!squashes.isEmpty())
            squashes.clear();
        int y = 0;
        int x = 0;
        int c;
        int loc;
        int num;
        Iterator<Map.Entry<Integer, NormalZombie>> itr = normalInfo.entrySet().iterator();
        while(itr.hasNext())
        {
            Map.Entry<Integer, NormalZombie> entry = itr.next();
            NormalZombie zombie = entry.getValue();
            num = entry.getKey();
            y = zombie.getRow();
            x = (int) zombie.getX();
            c = GameState.findColumn(x);
            loc = (int) (y*10 + c-1);
            if(info.get(loc) != null)
            {
                if(info.get(loc).equals("squash"))
                {
                    squashes.add(loc);
                    normalInfo.remove(num);
                }
            }
        }
        Iterator<Map.Entry<Integer, BucketHeadZombie>> itr2 = bucketInfo.entrySet().iterator();
        while(itr2.hasNext())
        {
            Map.Entry<Integer, BucketHeadZombie> entry = itr2.next();
            BucketHeadZombie zombie = entry.getValue();
            num = entry.getKey();
            y = zombie.getRow();
            x = (int) zombie.getX();
            c = GameState.findColumn(x);
            loc = (int) (y*10 + c-1);
            if(info.get(loc) != null)
            {
                if(info.get(loc).equals("squash"))
                {
                    squashes.add(loc);
                    bucketInfo.remove(num);
                }
            }
        }
        Iterator<Map.Entry<Integer, ConeHeadZombie>> itr3 = coneInfo.entrySet().iterator();
        while(itr3.hasNext())
        {
            Map.Entry<Integer, ConeHeadZombie> entry = itr3.next();
            ConeHeadZombie zombie = entry.getValue();
            num = entry.getKey();
            y = zombie.getRow();
            x = (int) zombie.getX();
            c = GameState.findColumn(x);
            loc = (int) (y*10 + c-1);
            if(info.get(loc) != null)
            {
                if(info.get(loc).equals("squash"))
                {
                    squashes.add(loc);
                    coneInfo.remove(num);
                }
            }
        }
    }
//    public void removeSquash(int loc)
//    {
//        Iterator<Integer> itr = squashes.iterator();
//        while(itr.hasNext())
//        {
//           Integer entry = itr.next();
//           if(entry == loc)
//               squashes.remove(entry);
//        }
//    }
    public ArrayList<Integer> getSquashes(){return squashes;}

}
