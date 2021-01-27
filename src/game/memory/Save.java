package game.memory;

import game.template.Elements.BucketHeadZombie;
import game.template.Elements.ConeHeadZombie;
import game.template.Elements.LawnMower;
import game.template.Elements.NormalZombie;
import game.template.bufferstrategy.GameState;
import utils.FileUtils;

import java.util.Map;

/**
 * This class merges all information of game that should be saved and
 * save them in the folder of users as a text file with username of player.
 * @authors Tanya Djavaherpour, Elaheh Akbari
 * @version 1.0 2021
 */
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
    private String lawnMowers;
    private static final String PATH = ".\\users\\";

    public Save(GameState state)
    {
        this.state = state;
        userName = "Qoli";
        text = "";
        setInformation();
    }

    /**
     * Sets all necessary information to save them in a text file.
     */
    public void setInformation()
    {
        this.type = state.getType();
        this.timeType = state.getTimeType();
        this.startTime = state.getStartTime();
        this.currentTime = System.currentTimeMillis();
        setPlaygroundInfo();
        setZombiesState();
        setLawMower();
        text = toString();
        saveInformation();
        System.out.println(text);
    }

    public void saveInformation()
    {
        FileUtils.makeFolder(PATH);
        FileUtils.fileWriter(text, PATH);
    }
    /**
     * Sets flowers state and their life state, by getting their hashmap from game state.
     */
    public void setPlaygroundInfo()
    {
        info = "";
        lifeInfo = "";
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

    /**
     * Sets x  coordinate and row of all zombies which are in the playground.
     */
    public void setZombiesState()
    {
        normalZombies = "";
        coneHead = "";
        bucketHead = "";
        for (Map.Entry<Integer, NormalZombie> info: state.getZombie().getNormalInfo().entrySet())
            if (info.getValue().getX() > 0)
                normalZombies = normalZombies + info.getValue().getX() + " " +
                        info.getValue().getRow() + " " + info.getValue().getLife() + "\n";
        for (Map.Entry<Integer, ConeHeadZombie> info: state.getZombie().getConeInfo().entrySet())
            if (info.getValue().getX() > 0)
                coneHead = coneHead + info.getValue().getX() + " " +
                        info.getValue().getRow() + " " + info.getValue().getLife() + "\n";
        for (Map.Entry<Integer, BucketHeadZombie> info: state.getZombie().getBucketInfo().entrySet())
            if (info.getValue().getX() > 0)
                bucketHead = bucketHead + info.getValue().getX() + " " +
                        info.getValue().getRow() + " " + info.getValue().getLife() + "\n";
    }

    /**
     * Sets lawn makers state based on their row and x coordinate.
     */
    public void setLawMower()
    {
        lawnMowers = "";
        for(LawnMower lawnMower: state.getLawnMowers())
            lawnMowers = lawnMowers + lawnMower.getX() + " " + lawnMower.getRow() + "\n";
    }

    /**
     * Appends all information and make a string that should be saved in file.
     * @return text that should be saved.
     */
    @Override
    public String toString()
    {
        return userName + "\ntype " + type + "\ntimeType " + timeType
                + "\nstartTime " + startTime + "\ncurrentTime " + currentTime
                + "\ninfo\n" + info + "lifeInfo\n" + lifeInfo
                + "normalZombies\n" + normalZombies + "coneHeadZombies\n" + coneHead + "bucketHeadZombies\n" + bucketHead
                + "lawnMowers\n" + lawnMowers;
    }

}

