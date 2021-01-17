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
    private boolean peaShooter, sunFlower, cherryBomb, wallNut;
    Random rand = new Random();


    public GameState() {
        sunX = rand.nextInt(GAME_WIDTH);
        sunY = 60;
        sunNumber = 0;
        sunState = false;
        peaShooter = false;
        sunFlower = false;
        cherryBomb = false;
        wallNut = false;
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
            if(e.getX() >= 110 - cardW &&
                    e.getX() <= 110 + cardW &&
                    e.getY() >= 38 - cardH &&
                    e.getY() <= 38 + cardH)
            {
                if(sunNumber >= 100)
                {
                    peaShooter = true;
                    sunNumber -= 100;
                }
                System.out.println("p");
            }

            else if(e.getX() >= 110 - cardW + cardW &&
                    e.getX() <= 110 + cardW + cardW &&
                    e.getY() >= 38 - cardH &&
                    e.getY() <= 38 + cardH)
            {
                if(sunNumber >= 50)
                {
                    sunFlower = true;
                    sunNumber -= 50;
                }
                System.out.println("s");
            }

            else if(e.getX() >= 110 - cardW + cardW*2 &&
                    e.getX() <= 110 + cardW + cardW*2 &&
                    e.getY() >= 38 - cardH &&
                    e.getY() <= 38 + cardH)
            {
                if(sunNumber >= 150)
                {
                    cherryBomb = true;
                    sunNumber -= 150;
                }
                System.out.println("c");
            }
            else if(e.getX() >= 110 - cardW + cardW*3 &&
                    e.getX() <= 110 + cardW + cardW*3 &&
                    e.getY() >= 38 - cardH &&
                    e.getY() <= 38 + cardH)
            {
                if(sunNumber >= 50)
                {
                    wallNut = true;
                    sunNumber -= 50;
                }
                System.out.println("w");
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

