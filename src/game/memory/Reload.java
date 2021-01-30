package game.memory;

import game.template.Elements.BucketHeadZombie;
import game.template.Elements.ConeHeadZombie;
import game.template.Elements.LawnMower;
import game.template.Elements.NormalZombie;
import game.template.bufferstrategy.GameState;
import utils.FileUtils;

import java.io.File;

/**
 * This class reloads the saved game, after choosing it from the main menu,
 * Sets all elements and everything which is related to game state
 * @version 1.0 2021
 * @authors Tanya Djavaherpour, Elaheh Akbari
 */
public class Reload {
    private GameState state;
    private String thisUser;
    private String timeType;
    private long startTime;
    private long stopTime;
    private long newStartTime;
    private static final String PATH = ".\\users\\";
    private int lawnMowers;
    private int mushroom;
    /**
     * Creates reload class structure by game state and the path of saved  game
     */
    public Reload(GameState state, String path)
    {
        this.state = state;
        this.thisUser = path;
        reloadGame();
    }

    /**
     * Calls all necessary methods to loade a saved game
     */
    public void reloadGame()
    {
        setType();
        setTimeType();
        setStartTime();
        setSunNumber();
        setSunDropping();
        setSunX();
        setSunY();
        setHashmaps();
        setZombies();
        setLawnMowers();
        setCards();
        System.out.println(stopTime - startTime);
        if(timeType.equals("night"))
            setMushroom();
    }

    /**
     * Gets wanted saved information by reading the file and scanning to find wanted line.
     * @param lineNumber as the number of wanted line
     * @param SplitNumber as the number of word in the wanted line
     * @return requested word
     */
    public String getInfo(int lineNumber, int SplitNumber)
    {
        String typeString = FileUtils.scanByLineNumber(new File(PATH+thisUser+".txt"), lineNumber);
        String[] res = typeString.split(" ");
        return res[SplitNumber];
    }

    /**
     * Sets type of game -> normal / hard
     */
    private void setType() { state.setType(getInfo(1, 1)); }

    /**
     * Sets time type -> day / night
     */
    private void setTimeType() {
        timeType = getInfo(2, 1);
        state.setTimeType(timeType);
    }
    /**
     * Finds how many millie seconds ago this game has benn started.
     */
    private void setStartTime(){
        startTime = Long.parseLong(getInfo(3, 1));
        stopTime = Long.parseLong(getInfo(4, 1));
        newStartTime = System.currentTimeMillis() - (stopTime - startTime);
        state.setStartTime(newStartTime);
    }
    /**
     * Sets sun numbers
     */
    private void setSunNumber(){  state.setSunNumber(Integer.parseInt(getInfo(5, 1)));}

    /**
     * Sets dropping time of last sun based on current time
     */
    private void setSunDropping()
    {
        long sunDropping = Long.parseLong(getInfo(6, 1));
        sunDropping = System.currentTimeMillis() - (sunDropping - stopTime);
        state.setSunDropping(sunDropping);
    }
    /**
     * Sets x coordinate of dropped sun
     */
    private void setSunX() { state.setSunX(Integer.parseInt(getInfo(6, 2))); }
    /**
     * Sets y coordinate of dropped sun
     */
    private void setSunY() { state.setSunY(Integer.parseInt(getInfo(6, 3))); }
    /**
     * Sets all hashmaps that are used in the game state.
     * info, lifeInfo, sunFlowersSunTime, sunFlowersSunState
     * If there is a pea or freeze pea shooter sets it bullets too.
     */
    private void setHashmaps()
    {
        for (int j = 1; j <= 5; j++){
            for (int i = 1; i <= 9; i++) {
                int loc = j * 10 + i;
                //info
                if(!getInfo(8+j-1, (i-1)).equals("null")) {
                    state.getInfo().replace(loc, getInfo(8 + j - 1, (i - 1)));
                    if (getInfo(8 + j - 1, (i - 1)).equals("peaShooter"))
                        state.getPea().addPea(loc);
                    else if (getInfo(8 + j - 1, (i - 1)).equals("freezePeaShooter"))
                        state.getFreezePea().addPea(loc);
                }
                //lifeInfo
                if(!getInfo(14+j-1, (i-1)).equals("null"))
                    state.getLifeInfo().replace(loc, Integer.parseInt(getInfo(14+j-1, (i-1))));
                //sunFlowerSunTime
                if(!getInfo(20+j-1, (i-1)).equals("null")){
                    long time = Long.parseLong(getInfo(20+j-1, (i-1)));
                    state.getSunFlower().getSunFlowerSunTime().replace(loc, System.currentTimeMillis() - (stopTime - time));
                }
                //sunFlowerState
                if(!getInfo(26+j-1, (i-1)).equals("null"))
                    state.getSunFlower().getSunFlowerState().replace(loc, Boolean.parseBoolean(getInfo(26+j-1, (i-1))));
            }
        }
    }
    /**
     * Sets all zombies that were in the playground. Sets their x coordinate, roe number and life state.
     */
    private void setZombies()
    {
        int i = 32;
        int num = 0;
        int row, life;
        float x;
        //set normal zombies
        while (!getInfo(i, 0).equals("coneHeadZombies")){
            state.getZombie().getNormalInfo().put(num, new NormalZombie());
            state.getZombie().getNormalInfo().get(num).setX(Float.parseFloat(getInfo(i, 0)));
            state.getZombie().getNormalInfo().get(num).setRow(Integer.parseInt(getInfo(i, 1)));
            state.getZombie().getNormalInfo().get(num).setLife(200 - Integer.parseInt(getInfo(i, 2)));
            i++;
            num++;
        }
        state.getZombie().setNormalNum(num);
        num = 0;
        i++;
        //set cone head zombies
        while (!getInfo(i, 0).equals("bucketHeadZombies")){
            state.getZombie().getConeInfo().put(num, new ConeHeadZombie());
            state.getZombie().getConeInfo().get(num).setX(Float.parseFloat(getInfo(i, 0)));
            state.getZombie().getConeInfo().get(num).setRow(Integer.parseInt(getInfo(i, 1)));
            state.getZombie().getConeInfo().get(num).setLife(560 - Integer.parseInt(getInfo(i, 2)));
            i++;
            num++;
        }
        state.getZombie().setConeNum(num);
        num = 0;
        i++;
        //set bucket head zombies
        while (!getInfo(i, 0).equals("lawnMowers")){
            state.getZombie().getBucketInfo().put(num, new BucketHeadZombie());
            state.getZombie().getBucketInfo().get(num).setX(Float.parseFloat(getInfo(i, 0)));
            state.getZombie().getBucketInfo().get(num).setRow(Integer.parseInt(getInfo(i, 1)));
            state.getZombie().getBucketInfo().get(num).setLife(1300 - Integer.parseInt(getInfo(i, 2)));
            i++;
            num++;
        }
        state.getZombie().setBucketNum(num);
        //line number of lawnMowers
        lawnMowers = ++i;
    }
    /**
     * Sets lawnMowers state -> their row and x coordinate and availability
     */
    private void setLawnMowers()
    {
        for(LawnMower lawnMower: state.getLawnMowers())
        {
            lawnMower.setX(Integer.parseInt(getInfo(lawnMowers, 0)));
            if(lawnMower.getX() > -35)
                lawnMower.setAvailable(false);
            lawnMowers ++;
        }
        state.checkLawnMakers();
    }
    /**
     * Sets availability of cards and the last time of their usages
     */
    private void setCards()
    {
        int cards = ++lawnMowers;
        long time;
        //cherryBomb
        state.getCherry().setCardState(Boolean.parseBoolean(getInfo(cards, 1)));
        System.out.println(state.getCherry().getCard());
        if(!getInfo(cards, 2).equals("0"))
        {
            time = Long.parseLong(getInfo(cards, 2));
            state.getCherry().setFlowerTime(System.currentTimeMillis() - (stopTime - time));
        }
        if(!getInfo(cards, 3).equals("0"))
        {
            time = Long.parseLong(getInfo(cards, 3));
            state.getCherry().setFlowerTime(System.currentTimeMillis() - (stopTime - time));
        }
        //peashooter
        ++cards;
        state.getPea().setCardState(Boolean.parseBoolean(getInfo(cards, 1)));
        if(!getInfo(cards, 2).equals("0"))
        {
            time = Long.parseLong(getInfo(cards, 2));
            state.getPea().setFlowerTime(System.currentTimeMillis() - (stopTime - time));
        }
        //freezePeaShooter
        ++cards;
        state.getFreezePea().setCardState(Boolean.parseBoolean(getInfo(cards, 1)));
        if(!getInfo(cards, 2).equals("0"))
        {
            time = Long.parseLong(getInfo(cards, 2));
            state.getFreezePea().setFlowerTime(System.currentTimeMillis() - (stopTime - time));
        }
        //squash
        ++cards;
        state.getSquash().setCardState(Boolean.parseBoolean(getInfo(cards, 1)));
        if(!getInfo(cards, 2).equals("0"))
        {
            time = Long.parseLong(getInfo(cards, 2));
            state.getSquash().setFlowerTime(System.currentTimeMillis() - (stopTime - time));
        }
        //sunFlower
        ++cards;
        state.getSunFlower().setCardState(Boolean.parseBoolean(getInfo(cards, 1)));
        if(!getInfo(cards, 2).equals("0"))
        {
            time = Long.parseLong(getInfo(cards, 2));
            state.getSunFlower().setFlowerTime(System.currentTimeMillis() - (stopTime - time));
        }
        //wallNut
        ++cards;
        state.getWallNut().setCardState(Boolean.parseBoolean(getInfo(cards, 1)));
        if(!getInfo(cards, 2).equals("0"))
        {
            time = Long.parseLong(getInfo(cards, 2));
            state.getWallNut().setFlowerTime(System.currentTimeMillis() - (stopTime - time));
        }
        //mushroom
        if(timeType.equals("night"))
        {
            ++cards;
            state.getMushroom().setCardState(true);
            if(!getInfo(cards, 2).equals("0"))
            {
                time = Long.parseLong(getInfo(cards, 2));
                state.getMushroom().setFlowerTime(System.currentTimeMillis() - (stopTime - time));
            }
        }
        mushroom = cards;
    }
    private void setMushroom()
    {
        System.out.println(getInfo(mushroom+1+5+1, (1-1)));
        for (int j = 1; j <= 5; j++){
            for (int i = 1; i <= 9; i++) {
                int loc = j * 10 + i;
                //mushroomSunTime
                if(!getInfo(mushroom+j+1, (i-1)).equals("null")){
                    long time = Long.parseLong(getInfo(mushroom+j+1, (i-1)));
                    state.getMushroom().getSunFlowerSunTime().replace(loc, System.currentTimeMillis() - (stopTime - time));
                }
                //mushroomState
                if(!getInfo(mushroom+5+j+2, (i-1)).equals("null"))
                    state.getMushroom().getSunFlowerState().replace(loc, Boolean.parseBoolean(getInfo(mushroom +5+j+2, (i-1))));
            }
        }
    }

}
