package game.template.bufferstrategy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import game.template.Elements.*;

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 * @version 1.0 2021
 * @authors Tanya Djavaherpour and Elaheh Akbari
 */
public class GameState {

    public static final int GAME_HEIGHT = 772;
    public static final int GAME_WIDTH = 1010;
    private final MouseHandler mouseHandler;
    private boolean sunState;
    public int sunX, sunY, sunNumber, cardW, cardH;
    private final Image sun;
    private boolean lock, shovel;
    private boolean gameOver;
    private boolean menu;
    private HashMap<Integer, String> info;
    private HashMap<Integer, Integer> lifeInfo;
    Random rand = new Random();
    private long sunTime, cherryBombState, sunDropping, startTime;
    private String type;
    private String timeType;
    private PeaShooter peashooter;
    private SunFlower sunFlower;
    private CherryBomb cherryBomb;
    private WallNut wallNut;
    private FreezePeaShooter freezePeaShooter;
    private Squash squash;
    private Mushroom mushroom;
    private Zombies zombie;
    private NormalZombie normalZombie;
    private ConeHeadZombie coneHead;
    private BucketHeadZombie bucketHead;
    private HashMap<Integer, Long> deletedSquash;
    private HashMap<Integer, Integer> stoppedPeas;
    private ArrayList<LawnMower> lawnMowers;

    /**
     * Constructs game state and sets first state of game lements
     *
     * @param type     normal/hard
     * @param timeType day/night
     */
    public GameState(String type, String timeType) {
        //
        // Initialize the game state and all elements ...
        //
        sunX = rand.nextInt(GAME_WIDTH);
        info = new HashMap<>();
        lifeInfo = new HashMap<>();
        this.type = type;
        this.timeType = timeType;
        sunY = 60;
        sunNumber = 800;
        peashooter = new PeaShooter(type, timeType);
        sunFlower = new SunFlower(type, timeType);
        cherryBomb = new CherryBomb(type, timeType);
        wallNut = new WallNut(type, timeType);
        freezePeaShooter = new FreezePeaShooter(type, timeType);
        squash = new Squash(type, timeType);
        if (timeType.equals("night"))
            mushroom = new Mushroom(type, timeType);
        zombie = new Zombies();
        normalZombie = new NormalZombie();
        bucketHead = new BucketHeadZombie();
        coneHead = new ConeHeadZombie();
        deletedSquash = new HashMap<>();
        stoppedPeas = new HashMap<>();
        sunState = false;
        lock = false;
        shovel = false;
        gameOver = false;
        menu = false;
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 5; j++) {
                int loc = j * 10 + i;
                info.put(loc, null);
                lifeInfo.put(loc, null);
                stoppedPeas.put(loc, null);
            }
        }
        sunTime = System.currentTimeMillis() + 50000;
        startTime = System.currentTimeMillis();
        cardW = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_peashooter.png").getImage().getWidth(null);
        cardH = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_peashooter.png").getImage().getHeight(null);
        mouseHandler = new MouseHandler();
        sun = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\sun.gif").getImage();
        setLawnMowers();
    }

    /**
     * The method which updates the game state.
     */
    public void update() {

        //
        // Update the state of all game elements
        //  based on user input and elapsed time ...
        //
        //set cards
        setCardsState();
        //checks time of dropping
        if (timeType.equals("day")) {
            if (sunState && ((type.equals("normal") && (System.currentTimeMillis() - sunTime) >= 25000) ||
                    (type.equals("hard") && (System.currentTimeMillis() - sunTime) >= 30000))) {
                sunState = false;
                changeSunState();
            } else if (!sunState)
                changeSunState();
        }
        //checks sunflower's sun time
        sunFlower.setSunFlowerState();
        //shoots peas
        peashooter.setBullets();
        freezePeaShooter.setBullets();
        //set zombies
        updateZombies();
        //update lawn makers state
        checkLawnMakers();
        //checks if lawn maker clears a flower
        removeFlowers();
    }
    /**
     * When lawn maker moves, this method clears flowers and every thing that is related to that flower.
     */
    public void removeFlowers()
    {
        for (int i = 1; i <= 9; i++)
            for (int j = 1; j <= 5; j++){
                int loc = j*10 + i;
                int lawnMakerColumn = findColumn(lawnMowers.get(j-1).getX());
                if(j == lawnMowers.get(j-1).getRow() && i == lawnMakerColumn){
                    if(info.get(loc) != null){
                        if(info.get(loc).equals("deadPeaShooter") || info.get(loc). equals("peaShooter")){
                            info.replace(loc, null);
                            peashooter.removeBullet(loc);
                            stoppedPeas.replace(loc, null);}
                        else if(info.get(loc).equals("deadSunFlower") || info.get(loc).equals("sunFlower"))
                            info.replace(loc, null);
                        else if(info.get(loc).equals("deadWallNut") || info.get(loc).equals("wallNut")
                                || info.get(loc).equals("halfWallNut"))
                            info.replace(loc, null);
                        else if(info.get(loc).equals("freezePeaShooter")){
                            info.replace(loc, null);
                            freezePeaShooter.removeBullet(loc);
                            stoppedPeas.replace(loc, null);}
                        else
                            info.replace(loc, null);
                    }
                    //replace flowers with their dying version
                    if(info.get(loc+1) != null){
                        if(info.get(loc+1).equals("peaShooter"))
                            info.replace(loc+1, "deadPeaShooter");
                        else if(info.get(loc+1).equals("sunFlower"))
                            info.replace(loc+1, "deadSunFlower");
                        else if(info.get(loc+1).equals("wallNut")
                                || info.get(loc+1).equals("halfWallNut"))
                            info.replace(loc+1, "deadWallNut"); }
                }
            }
    }

    /**
     * When lawn maker arrives, kills zombies.
     */
    public void removeZombies()
    {
        int zombieRow;
        int lawnMakerCol;
        int dec;
        for (Map.Entry<Integer, NormalZombie> normal : zombie.getNormalInfo().entrySet()){
            zombieRow = normal.getValue().getRow();
            lawnMakerCol = findColumn(lawnMowers.get(zombieRow-1).getX());
            dec = setZombiesLife(zombieRow, (int) normal.getValue().getX(), lawnMakerCol);
            if(dec>=0)
                normal.getValue().setLife(dec);
        }
        for (Map.Entry<Integer, ConeHeadZombie> cone : zombie.getConeInfo().entrySet()){
            zombieRow = cone.getValue().getRow();
            lawnMakerCol = findColumn(lawnMowers.get(zombieRow-1).getX());
            dec = setZombiesLife(zombieRow, (int) cone.getValue().getX(), lawnMakerCol);
            if(dec>=0)
                cone.getValue().setLife(dec+360);
        }
        for (Map.Entry<Integer, BucketHeadZombie> bucket : zombie.getBucketInfo().entrySet()){
            zombieRow = bucket.getValue().getRow();
            lawnMakerCol = findColumn(lawnMowers.get(zombieRow-1).getX());
            dec = setZombiesLife(zombieRow, (int) bucket.getValue().getX(), lawnMakerCol);
            if(dec>=0)
                bucket.getValue().setLife(dec+1100);
        }
    }

    /**
     * When lawn maker arrives, changes zombies life state to show them the way they are.
     * @param zombieRow as row of zombie
     * @param zombieX as x coordinate of zombie
     * @param lawnMakerCol as column of the cell that lawn maker is in that
     * @return decrement of zombie's life state
     */
    public int setZombiesLife(int zombieRow, int zombieX, int lawnMakerCol)
    {
        int zombieCol;
        zombieCol = findColumn(zombieX);
        if(lawnMowers.get(zombieRow-1).getX()>-30 && lawnMowers.get(zombieRow-1).getX()<=GAME_WIDTH) {
            if(lawnMakerCol == zombieCol || zombieCol == 0)
                return 40;
            else if(lawnMakerCol == zombieCol+1 || zombieCol == 1)
                return 0;
        }
        return -1;
    }
    /**
     * Sets lawn maker state at first
     */
    public void setLawnMowers()
    {
        lawnMowers = new ArrayList<>();
        for (int i = 1; i <= 5; i++)
            lawnMowers.add(new LawnMower(i));
    }

    /**
     * Moves lawn makers when a zombie arrives and if lawn maker is not available the player will lose.
     * @param zombieX as x coordinate of zombie
     * @param row as row of zombie
     */
    public boolean moveLawnMovers(int zombieX, int row)
    {
        if(zombieX <= 40 && zombieX > 0)
            for (LawnMower lawnMower: lawnMowers)
                if(row == lawnMower.getRow()){
                    if(lawnMower.isAvailable()){
                        lawnMower.setAvailable(false);
                        return true;
                    }
                    else if(lawnMower.getX() > GAME_WIDTH)
                        gameOver = true;
                }
        return false;
    }


    /**
     * Checks if a zombie is approaching to a lawn maker, moves it.
     */
    public void checkLawnMakers()
    {
        for (Map.Entry<Integer, NormalZombie> normal : zombie.getNormalInfo().entrySet())
            if(!normal.getValue().isKilled())
                normal.getValue().setKilled(moveLawnMovers((int) normal.getValue().getX(), normal.getValue().getRow()));
        for (Map.Entry<Integer, ConeHeadZombie> cone : zombie.getConeInfo().entrySet())
            if(!cone.getValue().isKilled())
                cone.getValue().setKilled(moveLawnMovers((int) cone.getValue().getX(), cone.getValue().getRow()));
        for (Map.Entry<Integer, BucketHeadZombie> bucket : zombie.getBucketInfo().entrySet())
            if(!bucket.getValue().isKilled())
                bucket.getValue().setKilled(moveLawnMovers((int) bucket.getValue().getX(), bucket.getValue().getRow()));
        for (LawnMower lawnMower: lawnMowers)
            lawnMower.move();

    }
    /**
     * This method calls all methods which are related to zombies and sets zombies state.
     */
    public void updateZombies() {
        //find zombies location
        zombie.findCells(info);
        if(System.currentTimeMillis() - startTime > 50000 &&
                System.currentTimeMillis() - startTime < 150000)
            zombie.setZombies(1, 25000);
        else if(System.currentTimeMillis() - startTime >= 150000 &&
                System.currentTimeMillis() - startTime < 150000+180000)
            zombie.setZombies(2, 30000);
        else if(System.currentTimeMillis() - startTime >= 150000+180000 &&
                System.currentTimeMillis() - startTime < 150000+180000+150000)
            zombie.setZombies(2, 25000);
        zombie.move(type);
        checkZombies();
        killZombies();
        removeZombies();
    }
    /**
     * Decrease life state of zombie after peashooters attacking.
     * @param set as list of peas
     * @param normal as list of normalZombies
     * @param dec as decrement of life state
     * @param row as row number
     * @param shooterX as x coordinate of peashooter
     * @param zombieX as x coordinate of zombie
     * @param loc as location
     */
    public void normalKiller(HashMap.Entry<Integer, ArrayList<Integer>> set , Map.Entry<Integer, NormalZombie> normal,
                             int dec,int row,int shooterX,int zombieX,int loc) {
        row = set.getKey() / 10;
        shooterX = set.getKey() % 10;
        loc = set.getKey();
        shooterX = 66 + (shooterX - 1) * 102;
        if (normal.getValue().getRow() == row) {
            int i = 0;
            for (Integer x : set.getValue()) {
                if (normal.getValue().getX() < (x + shooterX + 3.5) &&
                        normal.getValue().getX() > (x + shooterX - 3.5) &&
                        (stoppedPeas.get(loc) == null ||
                                stoppedPeas.get(loc) > shooterX + x)) {
                    normal.getValue().setLife(dec);
                    if(dec == 35)
                        normal.getValue().setFrozen(true);
                    stoppedPeas.replace(loc, (int) normal.getValue().getX());
                    int zombieLoc = row * 10 + findColumn((int) normal.getValue().getX());
                    if(normal.getValue().getLife() < 40)
                        stoppedPeas.replace(loc, null);
                    if (normal.getValue().getLife() < 70 && info.get(zombieLoc) != null &&
                            info.get(zombieLoc).contains("dying"))
                        info.replace(zombieLoc, null);
                }
                i++;
            }
        }
    }
    /**
     * Decrease life state of zombie after peashooters attacking.
     * @param set as list of peas
     * @param cone as list of coneHeadZombies
     * @param dec as decrement of life state
     * @param row as row number
     * @param shooterX as x coordinate of peashooter
     * @param zombieX as x coordinate of zombie
     * @param loc as location
     */
    public void coneKiller(HashMap.Entry<Integer, ArrayList<Integer>> set , Map.Entry<Integer, ConeHeadZombie> cone,
                           int dec,int row,int shooterX,int zombieX,int loc)
    {
        row = set.getKey() / 10;
        shooterX = set.getKey() % 10;
        loc = set.getKey();
        shooterX = 66 + (shooterX - 1) * 102;
        if (cone.getValue().getRow() == row) {
            int i = 0;
            for (Integer x : set.getValue()) {
                if (cone.getValue().getX() < (x + shooterX + 3.5) &&
                        cone.getValue().getX() > (x + shooterX - 3.5) &&
                        (stoppedPeas.get(loc) == null ||
                                stoppedPeas.get(loc) > shooterX + x)) {
                    cone.getValue().setLife(dec);
                    if(dec == 35)
                        cone.getValue().setFrozen(true);
                    stoppedPeas.replace(loc, (int) cone.getValue().getX());
                    int zombieLoc = row * 10 + findColumn((int) cone.getValue().getX());
                    if(cone.getValue().getLife() < 40)
                        stoppedPeas.replace(loc, null);
                    if (cone.getValue().getLife() < 70 && info.get(zombieLoc) != null &&
                            info.get(zombieLoc).contains("dying"))
                        info.replace(zombieLoc, null);
                }
                i++;
            }
        }
    }
    /**
     * Decrease life state of zombie after peashooters attacking.
     * @param set as list of peas
     * @param bucket as list of bucketHeadZombies
     * @param dec as decrement of life state
     * @param row as row number
     * @param shooterX as x coordinate of peashooter
     * @param zombieX as x coordinate of zombie
     * @param loc as location
     */
    public void bucketKiller (HashMap.Entry<Integer, ArrayList<Integer>> set , Map.Entry<Integer, BucketHeadZombie> bucket,
                              int dec,int row,int shooterX,int zombieX,int loc)
    {
        row = set.getKey() / 10;
        shooterX = set.getKey() % 10;
        loc = set.getKey();
        shooterX = 66 + (shooterX - 1) * 102;
        if (bucket.getValue().getRow() == row) {
            int i = 0;
            for (Integer x : set.getValue()) {
                if (bucket.getValue().getX() < (x + shooterX + 3.5) &&
                        bucket.getValue().getX() > (x + shooterX - 3.5) &&
                        (stoppedPeas.get(loc) == null ||
                                stoppedPeas.get(loc) > shooterX + x)) {
                    bucket.getValue().setLife(dec);
                    if(dec == 35)
                        bucket.getValue().setFrozen(true);
                    stoppedPeas.replace(loc, (int) bucket.getValue().getX());
                    int zombieLoc = row * 10 + findColumn((int) bucket.getValue().getX());
                    if(bucket.getValue().getLife() < 40)
                        stoppedPeas.replace(loc, null);
                    if (bucket.getValue().getLife() < 70 && info.get(zombieLoc) != null &&
                            info.get(zombieLoc).contains("dying"))
                        info.replace(zombieLoc, null);
                }
                i++;
            }
        }
    }

    /**
     * Iterates zombies and peashooter lists and calls killer mothodas
     */
    public void killZombies() {
        int row = 0;
        int shooterX = 0;
        int zombieX = 0;
        int i = 0;
        int loc = 0;
        for (Map.Entry<Integer, NormalZombie> normal : zombie.getNormalInfo().entrySet()) {
            for (HashMap.Entry<Integer, ArrayList<Integer>> set : peashooter.getBullets().entrySet())
                normalKiller(set, normal, 30, row, shooterX, zombieX, loc);
            for (HashMap.Entry<Integer, ArrayList<Integer>> set : freezePeaShooter.getBullets().entrySet())
                normalKiller(set, normal, 35, row, shooterX, zombieX, loc);
        }
        for (Map.Entry<Integer, ConeHeadZombie> cone : zombie.getConeInfo().entrySet()) {
            for (HashMap.Entry<Integer, ArrayList<Integer>> set : peashooter.getBullets().entrySet())
                coneKiller(set, cone, 30, row, shooterX, zombieX, loc);
            for (HashMap.Entry<Integer, ArrayList<Integer>> set : freezePeaShooter.getBullets().entrySet())
                coneKiller(set, cone, 35, row, shooterX, zombieX, loc);
        }
        for (Map.Entry<Integer, BucketHeadZombie> bucket : zombie.getBucketInfo().entrySet()) {
            for (HashMap.Entry<Integer, ArrayList<Integer>> set : peashooter.getBullets().entrySet())
                bucketKiller(set, bucket, 30, row, shooterX, zombieX, loc);
            for (HashMap.Entry<Integer, ArrayList<Integer>> set : freezePeaShooter.getBullets().entrySet())
                bucketKiller(set, bucket, 35, row, shooterX, zombieX, loc);
        }
    }

    /**
     * Iterates all zombie lists and checks their action based on the cells they are in and the flowers.
     * Zombies stop till they or flowers are alive,
     * Zombies will die if they receive to squash or cherryBomb
     */
    public void checkZombies()
    {
        int zombieLoc;
        int loc;
        //Checks normal zombies
        for (Map.Entry<Integer, NormalZombie> normal: zombie.getNormalInfo().entrySet())
        {
            zombieLoc = normal.getValue().getRow()*10 + findColumn((int)normal.getValue().getX());
            //Checks if zombie has received to squash
            loc = (int) (zombieLoc-0.25);
            if(info.get(normal.getValue().getRow()*10+9)!=null &&
                    info.get(normal.getValue().getRow()*10+9).contains("quash"))
            {
                info.replace(normal.getValue().getRow()*10+9, "attackSquash");
                normal.getValue().setSquashAttacked(true);
                normal.getValue().setSquashAttackTime(System.currentTimeMillis());
            }
            else if(info.get(loc) != null && info.get(loc).contains("quash") && !normal.getValue().isSquashAttacked())
            {
                info.replace(loc, "attackSquash");
                normal.getValue().setSquashAttacked(true);
                normal.getValue().setSquashAttackTime(System.currentTimeMillis());
            }
            //Checks if cherryBomb exploded
            if(normal.getValue().isBurnt() && (System.currentTimeMillis()-normal.getValue().getBurntTime())>5000)
            {
                normal.getValue().setX((float) (-200));
            }
            //Checks if zombie has received to flower and controls it
            for(Map.Entry<Integer, String> information: info.entrySet())
            {
                if(information.getKey() == zombieLoc && information.getValue()!=null)
                {
                    if(!normal.getValue().isStopped())
                    {
                        normal.getValue().setStopTime(System.currentTimeMillis());
                        normal.getValue().setStopped(true);
                    }
                    else if(info.get(zombieLoc).equals("cherryBomb") && (System.currentTimeMillis() - cherryBombState) >= 1800)
                    {
                        normal.getValue().setBurnt(true);
                        normal.getValue().setBurntTime(System.currentTimeMillis());
                    }
                    else if(normal.getValue().isStopped() &&
                            (System.currentTimeMillis() - normal.getValue().getStopTime()) >= 1000 ){
                        int life = lifeInfo.get(zombieLoc);
                        if(changeFlower(zombieLoc, life, 5))
                            normal.getValue().setStopTime(System.currentTimeMillis());
                        else
                            normal.getValue().setStopped(false);

                    }
                }
            }
            if(info.get(zombieLoc) == null && !normal.getValue().isBurnt())
                normal.getValue().setStopped(false);
        }
        //Checks coneHead zombies
        for (Map.Entry<Integer, ConeHeadZombie> cone: zombie.getConeInfo().entrySet())
        {
            zombieLoc = cone.getValue().getRow()*10 + findColumn((int)cone.getValue().getX());
            loc = (int) (zombieLoc-0.25);
            //Checks if zombie has received to squash
            if(info.get(cone.getValue().getRow()*10+9)!=null &&
                    info.get(cone.getValue().getRow()*10+9).contains("quash"))
            {
                info.replace(cone.getValue().getRow()*10+9, "attackSquash");
                cone.getValue().setSquashAttacked(true);
                cone.getValue().setSquashAttackTime(System.currentTimeMillis());
            }
            else if(info.get(loc) != null && info.get(loc).contains("quash") && !cone.getValue().isSquashAttacked())
            {
                info.replace(loc, "attackSquash");
                cone.getValue().setSquashAttacked(true);
                cone.getValue().setSquashAttackTime(System.currentTimeMillis());
            }
            //Checks if cherryBomb exploded
            if(cone.getValue().isBurnt() && (System.currentTimeMillis()-cone.getValue().getBurntTime())>5000)
            {
                cone.getValue().setX((float) (-200));
            }
            //Checks if zombie has received to flower and controls it
            for(Map.Entry<Integer, String> information: info.entrySet())
            {
                zombieLoc = cone.getValue().getRow()*10 + findColumn((int)cone.getValue().getX());
                if(information.getKey() == zombieLoc && information.getValue()!=null)
                {
                    if(!cone.getValue().isStopped())
                    {
                        cone.getValue().setStopTime(System.currentTimeMillis());
                        cone.getValue().setStopped(true);
                    }
                    else if(info.get(zombieLoc).equals("cherryBomb") && (System.currentTimeMillis() - cherryBombState) >= 1800)
                    {
                        cone.getValue().setBurnt(true);
                        cone.getValue().setBurntTime(System.currentTimeMillis());
                    }
                    else if(cone.getValue().isStopped() &&
                            (System.currentTimeMillis() - cone.getValue().getStopTime()) >= 1000 ){
                        int life = lifeInfo.get(zombieLoc);
                        int dec;
                        if(type.equals("normal"))
                            dec = 10;
                        else
                            dec = 15;
                        if(changeFlower(zombieLoc, life, dec))
                            cone.getValue().setStopTime(System.currentTimeMillis());
                        else
                            cone.getValue().setStopped(false);

                    }
                }
            }
            if(info.get(zombieLoc) == null && !cone.getValue().isBurnt())
                cone.getValue().setStopped(false);
        }
        //Checks bucketHead zombies
        for (Map.Entry<Integer, BucketHeadZombie> bucket: zombie.getBucketInfo().entrySet())
        {
            zombieLoc = bucket.getValue().getRow()*10 + findColumn((int)bucket.getValue().getX());
            loc = (int) (zombieLoc-0.25);
            //Checks if zombie has received to squash
            if(info.get(bucket.getValue().getRow()*10+9)!=null &&
                    info.get(bucket.getValue().getRow()*10+9).contains("quash"))
            {
                info.replace(bucket.getValue().getRow()*10+9, "attackSquash");
                bucket.getValue().setSquashAttacked(true);
                bucket.getValue().setSquashAttackTime(System.currentTimeMillis());
            }
            else if(info.get(loc) != null && info.get(loc).contains("quash") && !bucket.getValue().isSquashAttacked())
            {
                info.replace(loc, "attackSquash");
                bucket.getValue().setSquashAttacked(true);
                bucket.getValue().setSquashAttackTime(System.currentTimeMillis());
            }
            //Checks if cherryBomb exploded
            if(bucket.getValue().isBurnt() && (System.currentTimeMillis()-bucket.getValue().getBurntTime())>5000)
            {
                bucket.getValue().setX((float) (-200));
            }
            //Checks if zombie has received to flower and controls it
            for(Map.Entry<Integer, String> information: info.entrySet())
            {
                zombieLoc = bucket.getValue().getRow()*10 + findColumn((int)bucket.getValue().getX());
                if(information.getKey() == zombieLoc && information.getValue()!=null)
                {
                    if(!bucket.getValue().isStopped())
                    {
                        bucket.getValue().setStopTime(System.currentTimeMillis());
                        bucket.getValue().setStopped(true);
                    }
                    else if(info.get(zombieLoc).equals("cherryBomb") && (System.currentTimeMillis() - cherryBombState) >= 1800)
                    {
                        bucket.getValue().setBurnt(true);
                        bucket.getValue().setBurntTime(System.currentTimeMillis());
                    }
                    else if(bucket.getValue().isStopped() &&
                            (System.currentTimeMillis() - bucket.getValue().getStopTime()) >= 1000 ){
                        int life = lifeInfo.get(zombieLoc);
                        int dec;
                        if(type.equals("normal"))
                            dec = 20;
                        else
                            dec = 25;
                        if(changeFlower(zombieLoc, life, dec))
                            bucket.getValue().setStopTime(System.currentTimeMillis());
                        else
                            bucket.getValue().setStopped(false);

                    }
                }
            }
            if(info.get(zombieLoc) == null && !bucket.getValue().isBurnt())
                bucket.getValue().setStopped(false);
        }
    }
    /**
     * Changes flower based on its life state when zombie received to it and stops
     * @param zombieLoc location of zombie and flower
     * @param life as flower life state
     * @param decrement as decrement of flower life state based on zombie effect
     * @return true if flower is still alive and false if it is not alive anymore and has been removed.
     */
    public boolean changeFlower(int zombieLoc, int life, int decrement)
    {
        int num = 20;
        if(decrement == 5)
            num = 8;
        else if(decrement == 10)
            num = 18;
        if(life>0)
        {
            lifeInfo.replace(zombieLoc,life - decrement);
            if(life > num && info.get(zombieLoc).equals("wallNut"))
                info.replace(zombieLoc,"halfWallNut");
            else if(life<num && info.get(zombieLoc).equals("halfWallNut"))
                info.replace(zombieLoc,"deadWallNut");
            else if(life<num && info.get(zombieLoc).equals("sunFlower"))
                info.replace(zombieLoc,"deadSunFlower");
            else if(life<num && info.get(zombieLoc).equals("peaShooter"))
                info.replace(zombieLoc,"deadPeaShooter");
            return true;
        }
        else
        {
            if(info.get(zombieLoc).equals("deadPeaShooter") || info.get(zombieLoc).equals("peaShooter"))
                peashooter.removeBullet(zombieLoc);
            else if(info.get(zombieLoc).equals("freezePeaShooter"))
                freezePeaShooter.removeBullet(zombieLoc);
            lifeInfo.replace(zombieLoc, null);
            info.replace(zombieLoc, null);
            return false;
        }
    }
    /**
     * makes state of cards based on proper time
     */
    private void setCardsState()
    {
        sunFlower.setCard();
        peashooter.setCard();
        wallNut.setCard();
        cherryBomb.setCard();
        if(cherryBomb.getCard())
        {
            if((System.currentTimeMillis() - cherryBombState) >= 2000)
            {
                for (HashMap.Entry<Integer, String> set : info.entrySet()) {
                    if (set.getValue() != null)
                        if(set.getValue().equals("cherryBomb"))
                        {
                            int loc = set.getKey();
                            info.replace(loc, null);
                            lifeInfo.replace(loc, null);
                        }
                }
            }

        }
        freezePeaShooter.setCard();
        squash.setCard();
        if(timeType.equals("night"))
            mushroom.setCard();
        //Unlock --> new flower can be added to the playground
        if(!sunFlower.getCard() && !peashooter.getCard() && !wallNut.getCard() &&
                !cherryBomb.getCard() && !freezePeaShooter.getCard() && !squash.getCard())
            if((timeType.equals(" night") && !mushroom.getCard()) || timeType.equals(" day"))
                lock = false;

    }
    /**
     * This class controls sun when it is dropping down.
     */
    private void changeSunState() {
        if(sunY > (GAME_HEIGHT - 100)) {
            if(System.currentTimeMillis() - sunDropping > 3000 ) {
                sunY = 60;
                sunX = rand.nextInt(GAME_WIDTH) - 100;
                if(sunX < 150)
                    sunX = sunX + 150;
                sunDropping = System.currentTimeMillis();
            }
        }
        else {
            if(System.currentTimeMillis() - sunDropping > 1000 ) {
                sunY = sunY + 30;
                if(sunY > (GAME_HEIGHT - 100)) {
                    sunTime = System.currentTimeMillis();
                    sunState = true;
                }
                sunDropping = System.currentTimeMillis();
            }
        }
    }
    /**
     * Returns the information of peas which are stopped by zombies
     * @return HashMap<Integer, Integer> --> key: location of pea shooter,
     * value: x coordinate tha this peashooter's peas are stopped
     */
    public HashMap<Integer, Integer> getStoppedPeas() {
        return stoppedPeas;
    }

    /**
     * After dying a zombie, removes the location of peashooter that its peas were stopped by zombie
     * @param loc as location of pea shooter/ freeze pea shooter.
     */
    public void removeStoppedPea(int loc) { stoppedPeas.replace(loc, null); }
    /**
     * find columns number of a location
     * @param x as x coordinate
     * @return column number
     */
    public static int findColumn(int x)
    {
        int c=0;
        if( x > 38 && x <= 144)
            c = 1;
        else if(x > 144 && x <= 244)
            c = 2;
        else if(x > 244 && x <= 348)
            c = 3;
        else if(x > 348 && x <= 446)
            c = 4;
        else if(x > 446 && x <= 552)
            c = 5;
        else if(x > 552 && x <= 650)
            c = 6;
        else if(x > 650 && x <= 748)
            c = 7;
        else if(x > 748 && x <= 844)
            c = 8;
        else if(x > 748 && x <= 972)
            c = 9;
        return c;
    }
    /**
     * Finds the row and the column of the chosen cell.
     * @param x of clicked location
     * @param y of clicked location
     * @return chosen location in the form of yx -> row*10 + column
     */
    public static int findLoc(int x, int y)
    {
        int c=0, r=0;
        //find row of chosen cell
        if( y > 138 && y <= 246)
            r = 1;
        else if( y > 246 && y <= 370)
            r = 2;
        else if( y > 370 && y <= 500)
            r = 3;
        else if( y > 500 && y <= 618)
            r = 4;
        else if( y > 618 && y <= 742)
            r = 5;
        //find column on chosen cell
        c = findColumn(x);
        return r*10 + c;
    }

    /**
     * Finds which item has been chosen. Flower or shovel.
     * If a flower had been chosen will find which flower
     * @param e clicked location
     */
    private void chooseItem(MouseEvent e)
    {
        //peaShooter has been chosen
        if(e.getX() >= 110 - cardW &&
                e.getX() <= 110 + cardW &&
                e.getY() >= 38 - cardH &&
                e.getY() <= 38 + cardH)
        {
            sunNumber = peashooter.chooseFlower(sunNumber);
            lock = false;
            //unlock
            peashooter.setLock(false);
        }
        //sunFlower has been chosen
        else if(e.getX() >= 110 - cardW + cardW &&
                e.getX() <= 110 + cardW + cardW &&
                e.getY() >= 38 - cardH &&
                e.getY() <= 38 + cardH)
        {
            sunNumber = sunFlower.chooseFlower(sunNumber);
            lock = false;
            //unlock
            sunFlower.setLock(false);
        }
        //cherryBomb has been chosen
        else if(e.getX() >= 110 - cardW + cardW*2 &&
                e.getX() <= 110 + cardW + cardW*2 &&
                e.getY() >= 38 - cardH &&
                e.getY() <= 38 + cardH)
        {
            sunNumber = cherryBomb.chooseFlower(sunNumber);
            lock = false;
            //unlock
            cherryBomb.setLock(false);
        }
        //walletNut has been chosen
        else if(e.getX() >= 110 - cardW + cardW*3 &&
                e.getX() <= 110 + cardW + cardW*3 &&
                e.getY() >= 38 - cardH &&
                e.getY() <= 38 + cardH)
        {
            sunNumber = wallNut.chooseFlower(sunNumber);
            lock = false;
            //unlock
            wallNut.setLock(false);
        }
        //freezePeaShooter has been chosen
        else if(e.getX() >= 110 - cardW + cardW*4 &&
                e.getX() <= 110 + cardW + cardW*4 &&
                e.getY() >= 38 - cardH &&
                e.getY() <= 38 + cardH)
        {
            sunNumber = freezePeaShooter.chooseFlower(sunNumber);
            lock = false;
            //unlock
            freezePeaShooter.setLock(false);
        }
        //squash has been chosen
        else if(e.getX() >= 110 - cardW + cardW*5 &&
                e.getX() <= 110 + cardW + cardW*5 &&
                e.getY() >= 38 - cardH &&
                e.getY() <= 38 + cardH)
        {
            sunNumber = squash.chooseFlower(sunNumber);
            lock = false;
            //unlock
            squash.setLock(false);
        }
        //mushroom has been chosen
        else if(e.getX() >= 110 - cardW + cardW*6 &&
                e.getX() <= 110 + cardW + cardW*6 &&
                e.getY() >= 38 - cardH &&
                e.getY() <= 38 + cardH && timeType.equals("night"))
        {
            sunNumber = mushroom.chooseFlower(sunNumber);
            lock = false;
            //unlock
            mushroom.setLock(false);
        }
        //shovel has been chosen
        else if(e.getX() >= 600 &&
                e.getX() <= 700 &&
                e.getY() >= 38 - cardH &&
                e.getY() <= 38 + cardH)
        {
            shovel = true;
        }
    }

    /**
     * Saves dropping sun after clicking
     * @param e clicked location
     */
    private void saveSun(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();
        //saves sun
        if(timeType.equals("day"))
            if((e.getX() >= sunX - sun.getWidth(null) &&
                    e.getX() <= sunX + sun.getWidth(null)) &&
                    (e.getY() >= sunY - sun.getHeight(null) &&
                            e.getY() <= sunY + sun.getHeight(null)))
            {
                sunState = true;
                sunNumber += 25;
                sunY = GAME_HEIGHT;
                sunTime = System.currentTimeMillis();
            }

        int loc = findLoc(x,y);
        sunNumber = sunFlower.saveSun(loc, sunNumber);
    }

    /**
     * Puts flower in selected cell after choosing its card
     * @param e clicked location
     */
    private void putFlower(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();
        //find the selected location for putting flowers
        if((peashooter.getCard() || sunFlower.getCard() || cherryBomb.getCard()
                || wallNut.getCard() || freezePeaShooter.getCard() || squash.getCard() ||
                (timeType.equals("night") && mushroom.getCard())) && !lock)
        {
            int loc = findLoc(x, y);
            if(info.get(loc) == null)
            {
                if(peashooter.getCard() && !peashooter.getLock())
                {
                    info.replace(loc, "peaShooter");
                    lifeInfo.replace(loc,70);
                    peashooter.setLock(true);
                    peashooter.addPea(loc);
                }
                else if(sunFlower.getCard() && !sunFlower.getLock())
                {
                    info.replace(loc, "sunFlower");
                    lifeInfo.replace(loc,50);
                    sunFlower.addSunFlower(loc);
                    sunFlower.setLock(true);
                }
                else if(cherryBomb.getCard() && !cherryBomb.getLock())
                {
                    info.replace(loc, "cherryBomb");
                    lifeInfo.replace(loc,70);
                    cherryBombState = System.currentTimeMillis();
                    cherryBomb.setLock(true);
                }
                else if(wallNut.getCard() && !wallNut.getLock())
                {
                    info.replace(loc, "wallNut");
                    lifeInfo.replace(loc,150);
                    wallNut.setLock(true);
                }
                else if(freezePeaShooter.getCard() && !freezePeaShooter.getLock())
                {
                    info.replace(loc, "freezePeaShooter");
                    lifeInfo.replace(loc,100);
                    freezePeaShooter.setLock(true);
                    freezePeaShooter.addPea(loc);
                }
                else if(squash.getCard() && !squash.getLock())
                {
                    info.replace(loc, "squash");
                    lifeInfo.replace(loc,70);
                    squash.setLock(true);
                }
                else if(timeType.equals("night"))
                    if(mushroom.getCard() && !mushroom.getLock())
                    {
                        info.replace(loc, "mushroom");
                        lifeInfo.replace(loc,50);
                        mushroom.setLock(true);
                    }
                lock = true;
            }
        }
    }

    /**
     * After choosing shovel, removes selected flower.
     * @param e clicked location
     */
    private void removeFlower(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();
        //removes a flower by shovel
        if(shovel)
        {
            int loc = findLoc(x, y);
            if(info.get(loc) != null)
            {
                if(info.get(loc).equals("sunFlower"))
                    sunFlower.removeSunFlower(loc);
                else if(info.get(loc).equals("deadPeaShooter") || info.get(loc). equals("peaShooter"))
                {
                    info.replace(loc, null);
                    peashooter.removeBullet(loc);
                    removeStoppedPea(loc);
                }
                else if(info.get(loc).equals("freezePeaShooter"))
                {
                    info.replace(loc, null);
                    freezePeaShooter.removeBullet(loc);
                    removeStoppedPea(loc);
                }
                info.replace(loc, null);
                shovel = false;
            }
        }
    }
    /**
     * The mouse handler.
     */
    class MouseHandler implements MouseListener, MouseMotionListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            chooseItem(e);
            int x = e.getX();
            int y = e.getY();
            if(x > 864 && x < 990 && y > 32 && y < 60)
                menu = true;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            saveSun(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            putFlower(e);
            removeFlower(e);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mouseDragged(MouseEvent e) {
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }
    }
    //Getter setters
    /**
     * Returns mouse handler
     */
    public MouseListener getMouseListener() { return mouseHandler; }
    /**
     * Returns mouse handler
     */
    public MouseMotionListener getMouseMotionListener() { return mouseHandler;}
    /**
     *If sun has been chosen returns true.
     * When returns false, means sun should continue dropping.
     */
    public boolean getSun() { return sunState; }
    /**
     * Returns the number of chosen suns*25.
     */
    public int getSunNumber(){ return sunNumber; }
    /**
     * Returns peashooter
     */
    public PeaShooter getPea() { return peashooter; }
    /**
     * Returns sunFlower
     */
    public SunFlower getSunFlower() { return sunFlower; }
    /**
     * Returns getCherry
     */
    public CherryBomb getCherry() { return cherryBomb; }
    /**
     * Returns wall nut
     */
    public WallNut getWallNut() { return wallNut; }
    /**
     * Returns squash
     */
    public Squash getSquash() { return squash; }
    /**
     * Returns freeze pea shooter
     */
    public FreezePeaShooter getFreezePea() { return freezePeaShooter; }
    /**
     *Returns mushroom
     */
    public Mushroom getMushroom() { return mushroom; }
    /**
     * If shovel has been chosen, returns true.
     */
    public boolean getShovel() { return shovel; }
    /**
     * Returns info
     */
    public HashMap<Integer, String> getInfo() { return info; }
    /**
     * Returns all zombies that are playing
     */
    public Zombies getZombie(){return zombie;}
    /**
     * Returns game type: normal / hard
     */
    public String getType() { return type; }
    /**
     * Returns game time type: day / night
     */
    public String getTimeType() {return timeType; }
    /**
     * Returns hashmap of life state infromation
     */
    public HashMap<Integer, Integer> getLifeInfo() { return lifeInfo; }
    /**
     * Returns cherryBomb state -> the time of starting to explode
     */
    public long getCherryBombState() { return cherryBombState; }
    /**
     * Returns the time of starting to drop
     */
    public long getSunDropping() {return sunDropping; }
    /**
     * Returns the arrayList of 5 lawn makers in the game
     */
    public ArrayList<LawnMower> getLawnMowers() { return lawnMowers; }
    /**
     * Returns true if player lose and false if they should continue playing
     */
    public boolean isGameOver() { return gameOver; }
    /**
     * Returns the time that game started
     */
    public long getStartTime(){return startTime;}
    /**
     * If menu has been selected returns true
     */
    public boolean getMenu(){return menu;}
    /**
     * Sets if menu has been chosen -> true
     */
    public void setMenu(boolean set){menu = set;}
    /**
     * Sets time of game -> normal / hard
     */
    public void setType(String type) {this.type = type; }
    /**
     * Sets time type of game -> night / day
     */
    public void setTimeType(String timeType) { this.timeType = timeType; }
    /**
     * Sets time of starting the game
     */
    public void setStartTime(long startTime) { this.startTime = startTime; }
    /**
     * Sets the number of suns
     */
    public void setSunNumber(int sunNumber) {this.sunNumber = sunNumber; }
}

