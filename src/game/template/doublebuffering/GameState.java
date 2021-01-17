///*** In The Name of Allah ***/
//package game.template.doublebuffering;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.event.MouseMotionListener;
//import java.util.Random;
//
///**
// * This class holds the state of game and all of its elements.
// * This class also handles user inputs, which affect the game state.
// */
//public class GameState {
//
//    public static final int GAME_HEIGHT = 772;
//    public static final int GAME_WIDTH = 1010;
//    private final Image sun ;
//    private KeyHandler keyHandler;
//    private MouseHandler mouseHandler;
//    public int locX, locY;
//    Random rand = new Random();
//
//
//    public GameState() {
//        sun = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\sun.gif").getImage();
//        locX = rand.nextInt(GAME_WIDTH);
//        locY = 60;
//        //
//        // Initialize the game state and all elements ...
//        //
//        keyHandler = new KeyHandler();
//        mouseHandler = new MouseHandler();
//    }
//
//    /**
//     * The method which updates the game state.
//     */
//    public void update() {
//
//        //
//        // Update the state of all game elements
//        //  based on user input and elapsed time ...
//        //
//        if(locY > GAME_HEIGHT)
//        {
//            locY = 60;
//            locX = rand.nextInt(GAME_WIDTH);
//        }
//        else
//            locY = locY + 30;
//    }
//
//
//    public KeyListener getKeyListener() {
//        return keyHandler;
//    }
//    public MouseListener getMouseListener() {
//        return mouseHandler;
//    }
//    public MouseMotionListener getMouseMotionListener() {
//        return mouseHandler;
//    }
//
//
//
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
//
//    /**
//     * The mouse handler.
//     */
//    class MouseHandler implements MouseListener, MouseMotionListener {
//
//        @Override
//        public void mouseClicked(MouseEvent e) {
//        }
//
//        @Override
//        public void mousePressed(MouseEvent e) {
//        }
//
//        @Override
//        public void mouseReleased(MouseEvent e) {
//        }
//
//        @Override
//        public void mouseEntered(MouseEvent e) {
//        }
//
//        @Override
//        public void mouseExited(MouseEvent e) {
//        }
//
//        @Override
//        public void mouseDragged(MouseEvent e) {
//        }
//
//        @Override
//        public void mouseMoved(MouseEvent e) {
//        }
//    }
//}
//
