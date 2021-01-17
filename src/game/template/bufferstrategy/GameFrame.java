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

    private BufferStrategy bufferStrategy;

    public GameFrame(String title) {
        super(title);
        setResizable(false);
        setSize(GAME_WIDTH, GAME_HEIGHT);
//        background = new ImageIcon(".\\PVS Design Kit\\images\\mainBG.png").getImage();
        //
        // Initialize the JFrame ...
        //
        sun = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\sun.gif").getImage();
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
