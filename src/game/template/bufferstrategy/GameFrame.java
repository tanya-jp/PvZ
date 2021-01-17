/*** In The Name of Allah ***/
package game.template.bufferstrategy;

import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.swing.*;
import game.template.doublebuffering.GameCanvas;

/**
 * The window on which the rendering is performed.
 * This structure uses the modern BufferStrategy approach for
 * double-buffering; actually, it performs triple-buffering!
 * For more information on BufferStrategy check out:
 *    http://docs.oracle.com/javase/tutorial/extra/fullscreen/bufferstrategy.html
 *    http://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferStrategy.html
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class GameFrame extends JFrame {

    public static final int GAME_HEIGHT = 772;
    public static final int GAME_WIDTH = 1010;
    private final Image sun;
    private final Image peaShooterCard;
    private final Image sunFlowerCard;
    private final Image cherryBombCard;
    private final Image wallNutCard;
    private final Image freezePeaShooterCard;
    private final Image lawnMower;
    private String type;

    private BufferStrategy bufferStrategy;

    public GameFrame(String title, String type) {
        super(title);
        this.type = type;
        setResizable(false);
        setSize(GAME_WIDTH, GAME_HEIGHT);
//        background = new ImageIcon(".\\PVS Design Kit\\images\\mainBG.png").getImage();
        //
        // Initialize the JFrame ...
        //
        sun = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\sun.gif").getImage();
        peaShooterCard = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_peashooter.png").getImage();
        sunFlowerCard = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_sunflower.png").getImage();
        cherryBombCard = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_cherrybomb.png").getImage();
        wallNutCard = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_wallnut.png").getImage();
        freezePeaShooterCard = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_freezepeashooter.png").getImage();
        lawnMower = new ImageIcon(".\\PVS Design Kit\\images\\Lawn_Mower.png").getImage();
    }

    /**
     * This must be called once after the JFrame is shown:
     *    frame.setVisible(true);
     * and before any rendering is started.
     */
    public void initBufferStrategy() {
        // Triple-buffering
        createBufferStrategy(3);
        bufferStrategy = getBufferStrategy();
    }


    /**
     * Game rendering with triple-buffering using BufferStrategy.
     */
    public void render(GameState state) {
        // Get a new graphics context to render the current frame
        Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
        try {
            // Do the rendering
            doRendering(graphics, state);
        } finally {
            // Dispose the graphics, because it is no more needed
            graphics.dispose();
        }
        // Display the buffer
        bufferStrategy.show();
        // Tell the system to do the drawing NOW;
        // otherwise it can take a few extra ms and will feel jerky!
        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * Rendering all game elements based on the game state.
     */
    private void doRendering(Graphics2D g2d, GameState state) {
        GameCanvas canvas = new GameCanvas();
        canvas.paintComponent(g2d);
        //set cards
        if(!state.getPea() && state.getSunNumber() >= 100)
            g2d.drawImage(peaShooterCard, 110, 38, null);
        if(!state.getSunFlower() && state.getSunNumber() >= 50)
            g2d.drawImage(sunFlowerCard, 110+peaShooterCard.getWidth(null), 38, null);
        if(!state.getCherry() && state.getSunNumber() >= 150)
            g2d.drawImage(cherryBombCard, 110+2*peaShooterCard.getWidth(null), 38, null);
        if(!state.getWallNut() && state.getSunNumber() >= 50)
            g2d.drawImage(wallNutCard, 110+3*peaShooterCard.getWidth(null), 38, null);
        if(!state.getFreezePea() && state.getSunNumber() >= 175)
            g2d.drawImage(freezePeaShooterCard, 110+4*peaShooterCard.getWidth(null), 38, null);
        //set lawn mowers
        g2d.drawImage(lawnMower,-35,150,null);
        g2d.drawImage(lawnMower,-35,150 + 120,null);
        g2d.drawImage(lawnMower,-35,150 + 120*2,null);
        g2d.drawImage(lawnMower,-35,150 + 120*3,null);
        g2d.drawImage(lawnMower,-35,150 + 120*4,null);
        g2d.drawImage(lawnMower,-35,150 + 120*5,null);
        long start = System.currentTimeMillis();
        long delay = (1000 / 30) - (System.currentTimeMillis() - start);
        if(delay > 0 && !state.getSun())
            g2d.drawImage(sun, state.sunX, state.sunY, null);
        g2d.setFont(g2d.getFont().deriveFont(18.0f));
        g2d.drawString(String.valueOf(state.sunNumber), 55, 125);
//        canvas.render(state);
        //
        // Draw all game elements according
        //  to the game 'state' using 'g2d' ...
        //
    }

}
