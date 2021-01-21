package game.template.bufferstrategy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 */
public class GameState {

    public static final int GAME_HEIGHT = 772;
    public static final int GAME_WIDTH = 1010;
    private final MouseHandler mouseHandler;
    private boolean sunState;
    private HashMap<Integer, Boolean> sunFlowerState;
    private final HashMap<Integer, Long> sunFlowerSunTime;
    public int sunX, sunY,sunNumber, cardW, cardH;
    private final Image sun;
    private boolean lock, shovel;
    private HashMap<Integer, String> info ;
    Random rand = new Random();
    private long sunTime, cherryBombState;
    private String type;
    private String timeType;
    private PeaShooter peashooter;
    private SunFlower sunFlower;
    private CherryBomb cherryBomb;
    private WallNut wallNut;
    private FreezePeaShooter freezePeaShooter;
    private Squash squash;
    private Mushroom mushroom;

    /**
     * Constructs game state and sets first state of game lements
     * @param type normal/hard
     * @param timeType day/night
     */
    public GameState(String type, String timeType) {
        //
        // Initialize the game state and all elements ...
        //
        sunX = rand.nextInt(GAME_WIDTH);
        info = new HashMap<>();
        sunFlowerState = new HashMap<>();
        sunFlowerSunTime = new HashMap<>();
        this.type = type;
        this.timeType = timeType;
        sunY = 60;
        sunNumber = 0;
        peashooter = new PeaShooter(type, timeType);
        sunFlower = new SunFlower(type, timeType);
        cherryBomb = new CherryBomb(type, timeType);
        wallNut = new WallNut(type, timeType);
        freezePeaShooter = new FreezePeaShooter(type, timeType);
        squash = new Squash(type, timeType);
        if(timeType.equals("night"))
            mushroom = new Mushroom(type, timeType);
        sunState = false;
        lock = false;
        shovel = false;
        sunFlowerState = new HashMap<>();
        for (int i = 1; i <= 9; i++)
        {
            for(int j = 1; j <= 5; j++)
            {
                int loc = j*10 + i;
                info.put(loc, null);
                sunFlowerState.put(loc, null);
                sunFlowerSunTime.put(loc, null);
            }
        }
        sunTime = System.currentTimeMillis() + 50000;
        cardW = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_peashooter.png").getImage().getWidth(null);
        cardH = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_peashooter.png").getImage().getHeight(null);
        mouseHandler = new MouseHandler();
        sun = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\sun.gif").getImage();
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
        if(timeType.equals("day"))
        {
            if(sunState && ((type.equals("normal") && (System.currentTimeMillis() - sunTime) >= 25000) ||
                    (type.equals("hard") && (System.currentTimeMillis() - sunTime) >= 30000)))
            {
                sunState = false;
                changeSunState();
            }
            else if(!sunState)
                changeSunState();
        }
        //checks sunflower's sun time
        setSunFlowerState();
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
                        }
                }
            }

        }
        freezePeaShooter.setCard();
        //Unlock --> new flower can be added to the playground
        if(!sunFlower.getCard() && !peashooter.getCard() && !wallNut.getCard() &&
                !cherryBomb.getCard() && !freezePeaShooter.getCard() && !squash.getCard())
            if((timeType.equals(" night") && !mushroom.getCard()) || timeType.equals(" day"))
                lock = false;
    }

    /**
     * This class controls sun when it is dropping down.
     */
    private void changeSunState()
    {
        if(sunY > (GAME_HEIGHT - 100))
        {
            try {
                Thread.sleep(500);
                sunY = 60;
                sunX = rand.nextInt(GAME_WIDTH) - 100;
                if(sunX < 150)
                    sunX = sunX + 150;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
                Thread.sleep(1000);
                sunY = sunY + 30;
                if(sunY > (GAME_HEIGHT - 100))
                {
                    sunTime = System.currentTimeMillis();
                    sunState = true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks if sun flower can make based on game type.
     * If it is possible, sets it location.
     */
    private void setSunFlowerState()
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

    public MouseListener getMouseListener() {
        return mouseHandler;
    }
    public MouseMotionListener getMouseMotionListener() {
        return mouseHandler;
    }

    /**
     *If sun has been chosen returns true.
     * When returns false, means sun should continue dropping.
     */
    public boolean getSun()
    {
        return sunState;
    }

    /**
     * Returns the number of chosen suns*25.
     */
    public int getSunNumber(){
        return sunNumber;
    }
    /**
     * Returns peashooter
     */
    public PeaShooter getPea()
    {
        return peashooter;
    }
    /**
     * Returns sunFlower
     */
    public SunFlower getSunFlower()
    {
        return sunFlower;
    }
    /**
     * Returns getCherry
     */
    public CherryBomb getCherry()
    {
        return cherryBomb;
    }
    /**
     * Returns wall nut
     */
    public WallNut getWallNut()
    {
        return wallNut;
    }
    /**
     * Returns squash
     */
    public Squash getSquash()
    {
        return squash;
    }
    /**
     * Returns freeze pea shooter
     */
    public FreezePeaShooter getFreezePea()
    {
        return freezePeaShooter;
    }
    /**
     *Returns mushroom
     */
    public Mushroom getMushroom()
    {
        return mushroom;
    }
    /**
     * If shovel has been chosen, returns true.
     */
    public boolean getShovel()
    {
        return shovel;
    }
    /**
     * If sunflower's sun can be appeared, returns true.
     */
    public HashMap<Integer, Boolean> getSunFlowerState()
    {
        return sunFlowerState;
    }


    public HashMap<Integer, String> getInfo()
    {
        return info;
    }

    /**
     * Finds the row and the column of the chosen cell.
     * @param x of clicked location
     * @param y of clicked location
     * @return chosen location in the form of yx -> row*10 + column
     */
    private int findLoc(int x, int y)
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
        }
        //sunFlower has been chosen
        else if(e.getX() >= 110 - cardW + cardW &&
                e.getX() <= 110 + cardW + cardW &&
                e.getY() >= 38 - cardH &&
                e.getY() <= 38 + cardH)
        {
            sunNumber = sunFlower.chooseFlower(sunNumber);
            lock = false;
        }
        //cherryBomb has been chosen
        else if(e.getX() >= 110 - cardW + cardW*2 &&
                e.getX() <= 110 + cardW + cardW*2 &&
                e.getY() >= 38 - cardH &&
                e.getY() <= 38 + cardH)
        {
            sunNumber = cherryBomb.chooseFlower(sunNumber);
            lock = false;
        }
        //walletNut has been chosen
        else if(e.getX() >= 110 - cardW + cardW*3 &&
                e.getX() <= 110 + cardW + cardW*3 &&
                e.getY() >= 38 - cardH &&
                e.getY() <= 38 + cardH)
        {
            sunNumber = wallNut.chooseFlower(sunNumber);
            lock = false;
        }
        //freezePeaShooter has been chosen
        else if(e.getX() >= 110 - cardW + cardW*4 &&
                e.getX() <= 110 + cardW + cardW*4 &&
                e.getY() >= 38 - cardH &&
                e.getY() <= 38 + cardH)
        {
            sunNumber = freezePeaShooter.chooseFlower(sunNumber);
            lock = false;
        }
        //squash has been chosen
        else if(e.getX() >= 110 - cardW + cardW*5 &&
                e.getX() <= 110 + cardW + cardW*5 &&
                e.getY() >= 38 - cardH &&
                e.getY() <= 38 + cardH)
        {
            sunNumber = squash.chooseFlower(sunNumber);
            lock = false;
        }
        //mushroom has been chosen
        else if(e.getX() >= 110 - cardW + cardW*6 &&
                e.getX() <= 110 + cardW + cardW*6 &&
                e.getY() >= 38 - cardH &&
                e.getY() <= 38 + cardH && timeType.equals("night"))
        {
            sunNumber = mushroom.chooseFlower(sunNumber);
            lock = false;
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
        if(sunFlowerState.get(loc) != null)
            if (sunFlowerState.get(loc))
            {
                sunFlowerState.replace(loc, false);
                sunFlowerSunTime.replace(loc, System.currentTimeMillis());
                sunNumber += 25;
            }
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
                if(peashooter.getCard())
                    info.replace(loc, "peaShooter");
                else if(sunFlower.getCard())
                {
                    info.replace(loc, "sunFlower");
                    if(sunFlowerState.get(loc) == null)
                        sunFlowerState.replace(loc, true);
                    long time = System.currentTimeMillis();
                    if(sunFlowerSunTime.get(loc) == null)
                        sunFlowerSunTime.replace(loc, time);
                }
                else if(cherryBomb.getCard())
                {
                    info.replace(loc, "cherryBomb");
                    cherryBombState = System.currentTimeMillis();
                }
                else if(wallNut.getCard())
                    info.replace(loc, "wallNut");
                else if(freezePeaShooter.getCard())
                    info.replace(loc, "freezePeaShooter");
                else if(squash.getCard())
                {
                    info.replace(loc, "squash");
                }
                else if(timeType.equals("night"))
                    if(mushroom.getCard())
                        info.replace(loc, "mushroom");
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
                {
                    sunFlowerState.replace(loc, null);
                    sunFlowerSunTime.replace(loc, null);
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
}

