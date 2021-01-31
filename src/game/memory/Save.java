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
    private int sunNumber;
    private String cards;
    private String text;
    private String info;
    private String lifeInfo;
    private String normalZombies;
    private String coneHead;
    private String bucketHead;
    private String lawnMowers;
    private String sunFlowerTime;
    private String sunFlowerState;
    private String mushroomTime;
    private String mushroomState;
    private String finishState;
    private String sun;
    private static final String PATH = ".\\users\\";

    public Save(GameState state, String userName, String finishState)
    {
        this.state = state;
        this.userName = userName;
        this.finishState = finishState;
        text = "";
        setInformation();
        saveInformation();
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
        this.sunNumber = state.getSunNumber();
        setCards();
        setPlaygroundInfo();
        setZombiesState();
        setLawMower();
        setSun();
        text = toString();
//        System.out.println(text);
    }

    /**
     * Saves information as a text file in defined path.
     */
    public void saveInformation()
    {
        FileUtils.makeFolder(PATH);
        FileUtils.gamesWriter(text, PATH+userName+"\\");
    }
    /**
     * Sets information which is necessary to show a card.
     */
    public void setCards()
    {
        cards = "";
        //CherryBomb
        cards = "cherryBomb " + state.getCherry().getCard() + " "
                + state.getCherry().getFlowerTime() + " " +state.getCherryBombState();
        //peaShooter
        cards = cards + "\npeaShooter " + state.getPea().getCard() + " " + state.getPea().getFlowerTime();
        //freezePea
        cards = cards + "\nfreezePeaShooter " + state.getFreezePea().getCard() + " " + state.getFreezePea().getFlowerTime();
        //squash
        cards = cards + "\nsquash " + state.getSquash().getCard() + " " + state.getSquash().getFlowerTime();
        //sunFlower
        cards = cards + "\nsunFlower " + state.getSunFlower().getCard() + " " + state.getSunFlower().getFlowerTime();
        //wallNut
        cards = cards + "\nwallNut " + state.getWallNut().getCard() + " " + state.getWallNut().getFlowerTime();
        //mushroom
        if(timeType.equals("night"))
            cards = cards + "\nmushroom " + state.getMushroom().getCard() + " " + state.getMushroom().getFlowerTime();
    }
    /**
     * Sets flowers state and their life state, by getting their hashmap from game state.
     * Also sets information of sunFlowers
     */
    public void setPlaygroundInfo()
    {
        info = "";
        lifeInfo = "";
        sunFlowerTime = "";
        sunFlowerState = "";
        mushroomState = "";
        mushroomTime = "";
        for (int j = 1; j <= 5; j++){
            for (int i = 1; i <= 9; i++){
                int loc = j * 10 + i;
                info = info + state.getInfo().get(loc) + " ";
                lifeInfo = lifeInfo + state.getLifeInfo().get(loc) + " ";
                sunFlowerTime = sunFlowerTime + state.getSunFlower().getSunFlowerSunTime().get(loc) + " ";
                sunFlowerState = sunFlowerState + state.getSunFlower().getSunFlowerState().get(loc) + " ";
                if(timeType.equals("night"))
                {
                    mushroomTime = mushroomTime + state.getMushroom().getSunFlowerSunTime().get(loc) + " ";
                    mushroomState = mushroomState + state.getMushroom().getSunFlowerState().get(loc) + " ";
                }
            }
            info = info + ("\n");
            lifeInfo = lifeInfo + "\n";
            sunFlowerTime = sunFlowerTime + ("\n");
            sunFlowerState = sunFlowerState + "\n";
            mushroomTime = mushroomTime + "\n";
            mushroomState = mushroomState + "\n";
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

    public void setSun()
    {
        sun = state.getSunDropping() + " " + state.sunX + " " + state.sunY;
    }

    /**
     * Appends all information and make a string that should be saved in file.
     * @return text that should be saved.
     */
    @Override
    public String toString()
    {
        String res = finishState + "\ntype " + type + "\ntimeType " + timeType
                + "\nstartTime " + startTime + "\ncurrentTime " + currentTime +"\nsunNumber " + sunNumber
                + "\nsun " + sun
                + "\ninfo\n" + info + "lifeInfo\n" + lifeInfo
                + "sunFlowerTime\n" + sunFlowerTime + "sunFlowerState\n" + sunFlowerState
                + "normalZombies\n" + normalZombies + "coneHeadZombies\n" + coneHead + "bucketHeadZombies\n" + bucketHead
                + "lawnMowers\n" + lawnMowers + "cards\n" + cards;
        if(timeType.equals("night"))
            res = res + "\nmushroomTime\n" + mushroomTime + "mushroomState\n" + mushroomState;
        return res;
    }

}

