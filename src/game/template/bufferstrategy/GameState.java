/*** In The Name of Allah ***/
package game.template.bufferstrategy;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
        * This class holds the state of game and all of its elements.
        * This class also handles user inputs, which affect the game state.
        */
public class GameState {

    public static final int GAME_HEIGHT = 772;
    public static final int GAME_WIDTH = 1010;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    private boolean sunState;
    public int sunX, sunY,sunNumber, cardW,cardH;
    private final Image sun;
    private boolean peaShooter, sunFlower, cherryBomb, wallNut, freezePeaShooter;
    Random rand = new Random();
    private long peaShooterTime, sunFlowerTime, cherryBombTime, wallNutTime, freezePeaShooterTime;
    private String type;


    public GameState(String type) {
        sunX = rand.nextInt(GAME_WIDTH);
        this.type = type;
        sunY = 60;
        sunNumber = 0;
        sunState = false;
        peaShooter = false;
        sunFlower = false;
        cherryBomb = false;
        wallNut = false;
        peaShooterTime = 0;
        sunFlowerTime = 0;
        cherryBombTime = 0;
        wallNutTime = 0;
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
        changeSunState();
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

        }
        if(freezePeaShooter)
        {
            if(type.equals("normal") && (System.currentTimeMillis() - freezePeaShooterTime) >= 7500)
                freezePeaShooter = false;
            else if(type.equals("hard") && (System.currentTimeMillis() - freezePeaShooterTime) >= 30000)
                freezePeaShooter = false;
        }
    }

    /**
     * This class controls sun when it is dropping down.
     */
    private void changeSunState()
    {
        if(sunY > (GAME_HEIGHT - 100))
        {
            sunState = false;
            try {
                Thread.sleep(2500);
                sunY = 60;
                sunX = rand.nextInt(GAME_WIDTH) - 100;
                if(sunX < 100)
                    sunX = sunX + 100;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
                Thread.sleep(1000);
                sunY = sunY + 30;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public KeyListener getKeyListener() {
        return keyHandler;
    }
    public MouseListener getMouseListener() {
        return mouseHandler;
    }
    public MouseMotionListener getMouseMotionListener() {
        return mouseHandler;
    }



    public boolean getSun()
    {
        return sunState;
    }

    public int getSunNumber(){
        return sunNumber;
    }

    public boolean getPea()
    {
        return peaShooter;
    }
    public boolean getSunFlower()
    {
        return sunFlower;
    }
    public boolean getCherry()
    {
        return cherryBomb;
    }
    public boolean getWallNut()
    {
        return wallNut;
    }
    public boolean getFreezePea()
    {
        return freezePeaShooter;
    }

    /**
     * The keyboard handler.
     */
    class KeyHandler implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

    }

    /**
     * The mouse handler.
     */
    class MouseHandler implements MouseListener, MouseMotionListener {

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
                }
                System.out.println("p");
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
                    System.out.println("s");
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
                }
                System.out.println("c");
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
                }
                System.out.println("w");
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
                }
                System.out.println("f");
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if((e.getX() >= sunX - sun.getWidth(null) &&
                    e.getX() <= sunX + sun.getWidth(null)) &&
                    (e.getY() >= sunY - sun.getHeight(null) &&
                            e.getY() <= sunY + sun.getHeight(null)))
            {
                sunState = true;
                sunNumber += 25;
                sunY = GAME_HEIGHT;
            }
            else
                sunState = false;

        }

        @Override
        public void mouseReleased(MouseEvent e) {
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

