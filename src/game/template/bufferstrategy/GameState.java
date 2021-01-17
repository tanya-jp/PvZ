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
    public int sunX, sunY,sunNumber;
    private final Image sun;
    Random rand = new Random();


    public GameState() {
        sunX = rand.nextInt(GAME_WIDTH);
        sunY = 60;
        sunNumber = 0;
        sunState = false;
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
        System.out.println(sunNumber);
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
                if(sunX < 60)
                    sunX = sunX + 60;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
                Thread.sleep(500);
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
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if((e.getX() >= sunX - sun.getWidth(null)/2 &&
                    e.getX() <= sunX + sun.getWidth(null)/2) &&
                    (e.getY() >= sunY - sun.getHeight(null)/2 &&
                            e.getY() <= sunY + sun.getHeight(null)/2))
            {
                sunState = true;
                sunNumber += 25;
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

