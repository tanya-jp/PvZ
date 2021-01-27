package game.template.meory;

import game.template.Elements.BucketHeadZombie;
import game.template.Elements.ConeHeadZombie;
import game.template.Elements.NormalZombie;
import game.template.bufferstrategy.GameState;

import java.util.HashMap;
import java.util.Map;

public class Save {
    private GameState state;
    private String type;
    private String timeType;
    //TODO set username in the beginning
    private String userName;
    private long startTime;
    private long currentTime;
    private String text;
    private String info;
    private String lifeInfo;
    private String normalZombies;
    private String coneHead;
    private String bucketHead;
    public Save(GameState state)
    {
        this.state = state;
        info = "";
        lifeInfo = "";
        userName = "tanya";
        text = "";
        normalZombies = "";
        coneHead = "";
        bucketHead = "";
        setInformation();
    }
    public void setInformation()
    {
        this.type = state.getType();
        this.timeType = state.getTimeType();
        this.startTime = state.getStartTime();
        this.currentTime = System.currentTimeMillis();
        setPlaygroundInfo();
        setZombiesState();
        text = toString();
        System.out.println(text);
    }
    public void setPlaygroundInfo()
    {
        for (int j = 1; j <= 5; j++){
             for (int i = 1; i <= 9; i++){
                int loc = j * 10 + i;
                 info = info + state.getInfo().get(loc) + " ";
                 lifeInfo = lifeInfo + state.getLifeInfo().get(loc) + " ";
            }
            info = info + ("\n");
             lifeInfo = lifeInfo + "\n";
        }
    }

    public void setZombiesState()
    {
        for (Map.Entry<Integer, NormalZombie> info: state.getZombie().getNormalInfo().entrySet())
            if (info.getValue().getX() > 0)
                normalZombies = normalZombies + info.getValue().getX() +
                        info.getValue().getRow() + info.getValue().getLife() + "\n";
        for (Map.Entry<Integer, ConeHeadZombie> info: state.getZombie().getConeInfo().entrySet())
            if (info.getValue().getX() > 0)
                coneHead = coneHead + info.getValue().getX() +
                        info.getValue().getRow() + info.getValue().getLife() + "\n";
        for (Map.Entry<Integer, BucketHeadZombie> info: state.getZombie().getBucketInfo().entrySet())
            if (info.getValue().getX() > 0)
                bucketHead = bucketHead + info.getValue().getX() +
                        info.getValue().getRow() + info.getValue().getLife() + "\n";
    }
    @Override
    public String  toString()
    {
        return userName + "\ntype " + type + "\ntimeType " + timeType
                + "\nstartTime " + startTime + "\ncurrentTime " + currentTime
                + "\ninfo\n" + info + "lifeInfo\n" + lifeInfo
                + "normalZombies\n" + normalZombies + "coneHeadZombies" + coneHead
                + "bucketHeadZombies\n" + coneHead;
    }

}
