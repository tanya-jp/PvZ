package game.template.Elements;

import java.util.ArrayList;
import java.util.HashMap;
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
    private HashMap<Integer, ArrayList<Integer>> normalInfo;
    private HashMap<Integer, ArrayList<Integer>> bucketInfo;
    private HashMap<Integer, ArrayList<Integer>> coneInfo;
    public Zombie()
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
    }
//    public HashMap<Integer, String> getInformation(){return information;}
    public void setNormal(int loc)
    {
//        normalInfo.put(normalNum, null);
//        if (normalInfo.get(normalNum) == null)
            normalInfo.put(normalNum, new ArrayList<Integer>());
        normalInfo.get(normalNum).add(loc);
        normalInfo.get(normalNum).add(1005);
        normalInfo.get(normalNum).add(200);
        zombieNum++;
    }
    public HashMap<Integer, ArrayList<Integer>> getNormalInfo(){return normalInfo;}
    public void setBucket(int loc)
    {
//        bucketInfo.put(bucketNum, null);
//        if (bucketInfo.get(bucketNum) == null)
            bucketInfo.put(bucketNum, new ArrayList<Integer>());
        bucketInfo.get(bucketNum).add(loc);
        bucketInfo.get(bucketNum).add(1005);
        bucketInfo.get(bucketNum).add(1300);
        zombieNum++;
    }
    public HashMap<Integer, ArrayList<Integer>> getBucketInfo(){return bucketInfo;}
    public void setCone(int loc)
    {
//        coneInfo.put(coneNum, null);
//        if (coneInfo.get(coneNum) == null)
            coneInfo.put(coneNum, new ArrayList<Integer>());
        coneInfo.get(coneNum).add(loc);
        coneInfo.get(coneNum).add(1005);
        coneInfo.get(coneNum).add(560);
        zombieNum++;
    }
    public HashMap<Integer, ArrayList<Integer>> getConeInfo(){return coneInfo;}
    public void changeLocation(HashMap<Integer, ArrayList<Integer>> hashmap)
    {
        int i;
        for (Map.Entry<Integer, ArrayList<Integer>> info: hashmap.entrySet())
        {
            i = 0;
            for(Integer num: info.getValue())
            {
                if(i == 1)
                {
                    num = num-1;
                    info.getValue().set(i, num);
                }
                i++;
            }

        }
    }
    public void move()
    {
        changeLocation(normalInfo);
        changeLocation(bucketInfo);
        changeLocation(coneInfo);
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
//                    System.out.println(loc);
//                    System.out.println(info.getValue());
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
//        System.out.println(zombieNum);
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
//            for(Map.Entry<Integer, String> inf: information.entrySet())
//            {
//                information.remove(inf.getKey(), inf.getValue());
//            }
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
//                    System.out.println(j+"   normal");
                }
                else if(peaShooters>others)
                {
                    bucketHead.add(j);
                    information.put(j , "bucket");
//                    System.out.println(j+"   bucket");
                    flag++;
                }
                else if(others>0)
                {
                    coneHead.add(j);
                    information.put(j , "cone");
//                    System.out.println(j+"   cone");
                    flag++;
                }
            }
            if(flag == 0)
            {
                normal.add(j);
                information.put(j , "normal");
            }
//            System.out.println("row "+j);
//            System.out.println("empty "+empty);
//            System.out.println("shooter "+peaShooters);
//            System.out.println("squash "+squash);
//            System.out.println("others : " +others);
        }
    }
}
