package game.template.bufferstrategy;

//import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.swing.*;
//import javax.swing.plaf.basic.BasicTreeUI;
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
//    private KeyHandler keyHandler;
    private final MouseHandler mouseHandler;
    private boolean sunState;
    private HashMap<Integer, Boolean> sunFlowerState;
    private final HashMap<Integer, Long> sunFlowerSunTime;
    public int sunX, sunY,sunNumber, cardW, cardH;
    private final Image sun;
    private boolean peaShooter, sunFlower, cherryBomb, wallNut, freezePeaShooter, lock, shovel;
    private HashMap<Integer, String> info ;
    Random rand = new Random();
    private long peaShooterTime, sunFlowerTime, cherryBombTime, wallNutTime, freezePeaShooterTime, sunTime, cherryBombState;
    private String type;


    public GameState(String type) {
        sunX = rand.nextInt(GAME_WIDTH);
        info = new HashMap<>();
        sunFlowerState = new HashMap<>();
        sunFlowerSunTime = new HashMap<>();
        this.type = type;
        sunY = 60;
        sunNumber = 0;
        sunState = false;
        peaShooter = false;
        sunFlower = false;
        cherryBomb = false;
        wallNut = false;
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
        peaShooterTime = 0;
        sunFlowerTime = 0;
        cherryBombTime = 0;
        wallNutTime = 0;
        sunTime = System.currentTimeMillis() + 50000;
        freezePeaShooterTime = 0;
        cardW = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_peashooter.png").getImage().getWidth(null);
        cardH = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_peashooter.png").getImage().getHeight(null);
        //
        // Initialize the game state and all elements ...
        //
//        keyHandler = new KeyHandler();
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
        if(sunState && ((type.equals("normal") && (System.currentTimeMillis() - sunTime) >= 25000) ||
                (type.equals("hard") && (System.currentTimeMillis() - sunTime) >= 30000)))
        {
            sunState = false;
            changeSunState();
        }
        else if(!sunState)
            changeSunState();
        //checks sunflower's sun time
        setSunFlowerState();
    }

    /**
     * makes state of cards based on proper time
     */
    private void setCardsState()
    {
        if(sunFlower && (System.currentTimeMillis() - sunFlowerTime) >= 7500)
            sunFlower = false;
        if(peaShooter && (System.currentTimeMillis() - peaShooterTime) >= 7500)
            peaShooter = false;
        if(wallNut && (System.currentTimeMillis() - wallNutTime) >= 30000)
            wallNut = false;
        if(cherryBomb)
        {
            if(type.equals("normal") && (System.currentTimeMillis() - cherryBombTime) >= 30000)
                cherryBomb = false;
            else if(type.equals("hard") && (System.currentTimeMillis() - cherryBombTime) >= 45000)
                cherryBomb = false;
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
        if(freezePeaShooter)
        {
            if(type.equals("normal") && (System.currentTimeMillis() - freezePeaShooterTime) >= 7500)
                freezePeaShooter = false;
            else if(type.equals("hard") && (System.currentTimeMillis() - freezePeaShooterTime) >= 30000)
                freezePeaShooter = false;
        }
        //Unlock --> new flower can be added to the playground
        if(!sunFlower && !peaShooter && !wallNut && !cherryBomb && !freezePeaShooter)
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

//    public KeyListener getKeyListener() {
//        return keyHandler;
//    }
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
     * If peaShooter's card can be appeared, returns false.
     */
    public boolean getPea()
    {
        return peaShooter;
    }
    /**
     * If sunFlower's card can be appeared, returns false.
     */
    public boolean getSunFlower()
    {
        return sunFlower;
    }
    /**
     * If cherryBomb's card can be appeared, returns false.
     */
    public boolean getCherry()
    {
        return cherryBomb;
    }
    /**
     * If wallNut's card can be appeared, returns false.
     */
    public boolean getWallNut()
    {
        return wallNut;
    }
    /**
     * If freezePeaShooter's card can be appeared, returns false.
     */
    public boolean getFreezePea()
    {
        return freezePeaShooter;
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
//    /**
//     * The keyboard handler.
//     */
//    class KeyHandler implements KeyListener {
//
//        @Override
//        public void keyTyped(KeyEvent e) {
//        }
//
//        @Override
//        public void keyPressed(KeyEvent e) {
//        }
//
//        @Override
//        public void keyReleased(KeyEvent e) {
//        }
//
//    }

    /**
     * The mouse handler.
     */
    class MouseHandler implements MouseListener, MouseMotionListener {
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

        @Override
        public void mouseClicked(MouseEvent e) {
            //peaShooter has been chosen
            if(e.getX() >= 110 - cardW &&
                    e.getX() <= 110 + cardW &&
                    e.getY() >= 38 - cardH &&
                    e.getY() <= 38 + cardH)
            {
                if(sunNumber >= 100 && !peaShooter)
                {
                    peaShooter = true;
                    sunNumber -= 100;
                    peaShooterTime = System.currentTimeMillis();
                    lock = false;
                }
            }
            //sunFlower has been chosen
            else if(e.getX() >= 110 - cardW + cardW &&
                    e.getX() <= 110 + cardW + cardW &&
                    e.getY() >= 38 - cardH &&
                    e.getY() <= 38 + cardH)
            {
                if(sunNumber >= 50 && !sunFlower)
                {
                    sunFlower = true;
                    sunNumber -= 50;
                    sunFlowerTime = System.currentTimeMillis();
                    lock = false;
                }
            }
            //cherryBomb has been chosen
            else if(e.getX() >= 110 - cardW + cardW*2 &&
                    e.getX() <= 110 + cardW + cardW*2 &&
                    e.getY() >= 38 - cardH &&
                    e.getY() <= 38 + cardH)
            {
                if(sunNumber >= 150 && !cherryBomb)
                {
                    cherryBomb = true;
                    sunNumber -= 150;
                    cherryBombTime = System.currentTimeMillis();
                    lock = false;
                }
            }
            //walletNut has been chosen
            else if(e.getX() >= 110 - cardW + cardW*3 &&
                    e.getX() <= 110 + cardW + cardW*3 &&
                    e.getY() >= 38 - cardH &&
                    e.getY() <= 38 + cardH)
            {
                if(sunNumber >= 50 && !wallNut)
                {
                    wallNut = true;
                    sunNumber -= 50;
                    wallNutTime = System.currentTimeMillis();
                    lock = false;
                }
            }
            //freezePeaShooter has been chosen
            else if(e.getX() >= 110 - cardW + cardW*4 &&
                    e.getX() <= 110 + cardW + cardW*4 &&
                    e.getY() >= 38 - cardH &&
                    e.getY() <= 38 + cardH)
            {
                if(sunNumber >= 175 && !freezePeaShooter)
                {
                    freezePeaShooter = true;
                    sunNumber -= 175;
                    freezePeaShooterTime = System.currentTimeMillis();
                    lock = false;
                }
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

        @Override
        public void mousePressed(MouseEvent e) {
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

        @Override
        public void mouseReleased(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            //find the selected location for putting flowers
            if((peaShooter || sunFlower || cherryBomb || wallNut || freezePeaShooter) && !lock)
            {
                int loc = findLoc(x, y);
                if(info.get(loc) == null)
                {
                    if(peaShooter)
                        info.replace(loc, "peaShooter");
                    else if(sunFlower)
                    {
                        info.replace(loc, "sunFlower");
                        if(sunFlowerState.get(loc) == null)
                            sunFlowerState.replace(loc, true);
                        long time = System.currentTimeMillis();
                        if(sunFlowerSunTime.get(loc) == null)
                            sunFlowerSunTime.replace(loc, time);
                    }
                    else if(cherryBomb)
                    {
                        info.replace(loc, "cherryBomb");
                        cherryBombState = System.currentTimeMillis();
                    }
                    else if(wallNut)
                        info.replace(loc, "wallNut");
                    else if(freezePeaShooter)
                        info.replace(loc, "freezePeaShooter");
                    lock = true;
                }
            }
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

