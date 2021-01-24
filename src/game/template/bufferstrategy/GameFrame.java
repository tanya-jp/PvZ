/*** In The Name of Allah ***/
package game.template.bufferstrategy;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

import game.template.Elements.*;
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
    public static final int GAME_WIDTH = 1005;
    private Image sun;
    private Image shovel;
    private String type;
    private String timeType;
    private NormalZombie normalZombie;
    private BucketHeadZombie bucketHeadZombie;
    private ConeHeadZombie coneHeadZombie;

    private BufferStrategy bufferStrategy;

    public GameFrame(String title, String type, String timeType) {
        super(title);
        this.timeType = timeType;
        this.type = type;
        setResizable(false);
        setSize(GAME_WIDTH, GAME_HEIGHT);
        normalZombie = new NormalZombie();
        bucketHeadZombie = new BucketHeadZombie();
        coneHeadZombie = new ConeHeadZombie();
//        background = new ImageIcon(".\\PVS Design Kit\\images\\mainBG.png").getImage();
        //
        // Initialize the JFrame ...
        //
        setImages();
    }

    /**
     * Sets all images and gifs
     */
    private void setImages() {
//        sun = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\sun.gif").getImage();
        sun = new ImageIcon(".\\PVS Design Kit\\images\\sun.png").getImage();
        shovel = new ImageIcon(".\\PVS Design Kit\\images\\shovel.png").getImage();
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
        GameCanvas canvas = new GameCanvas(timeType);
        canvas.paintComponent(g2d);
        //set shovel
        setShovel(g2d, state);
        //set cards
        putCards(g2d, state);
        //set zombies
        setZombies(g2d, state);
        //set flowers
        setFlowers(g2d, state);
        //set lawn mowers
        putLawnMower(g2d, state);
        //set sun
        setSun(g2d, state);

//        canvas.render(state);
        //
        // Draw all game elements according
        //  to the game 'state' using 'g2d' ...
        //
    }
    private void setDyingZombie()
    {

    }
    private void setZombies(Graphics2D g2d, GameState state)
    {
        int y = 0;
        int x = 0;
        int i = 0;
        int sizeX = normalZombie.getFullImage().getWidth(null) - 25;
        int sizeY = normalZombie.getFullImage().getHeight(null) - 35;
        for (Map.Entry<Integer, NormalZombie> info: state.getZombie().getNormalInfo().entrySet())
        {
            float x2;
            y = info.getValue().getRow();
            x2 = info.getValue().getX();
            int locY = 154 + (y-1)*118;
            if(info.getValue().isBurnt())
                g2d.drawImage(normalZombie.getBurntImage(), (int) x2, locY, sizeX, sizeY, null);
            else if (info.getValue().isSquashAttacked()&& System.currentTimeMillis() - info.getValue().getSquashAttackTime() > 1650)
            {
                g2d.drawImage(normalZombie.getDyingImage(), (int) x2 - 2, locY, sizeX, sizeY, null);
                if(System.currentTimeMillis() - info.getValue().getSquashAttackTime() > 2200)
                {
                    state.removeSquash((int)x2, locY);
                    info.getValue().setX(-200);
                    info.getValue().setSquashAttacked(false);
                }
            }
            else if(info.getValue().getLife()<50)
            {
                g2d.drawImage(normalZombie.getDyingImage(), (int) x2 - 2, locY, sizeX, sizeY, null);
                if(info.getValue().getLife()<0)
                {
                    info.getValue().setX(-200);
                    int loc = state.findLoc((int) x2, locY);
                    state.removeStoppedPea(loc);
                }
            }
            else
                g2d.drawImage(normalZombie.getFullImage(), (int) x2, locY, sizeX, sizeY, null);
        }
        for (Map.Entry<Integer, ConeHeadZombie> info: state.getZombie().getConeInfo().entrySet())
        {
            float x2;
            y = info.getValue().getRow();
            x2 = info.getValue().getX();
            int locY = 154 + (y-1)*118;
            if(info.getValue().isBurnt())
                g2d.drawImage(coneHeadZombie.getBurntImage(), (int) x2, locY, sizeX, sizeY, null);
            else if (info.getValue().isSquashAttacked()&& System.currentTimeMillis() - info.getValue().getSquashAttackTime() > 1650)
            {
                g2d.drawImage(coneHeadZombie.getDyingImage(), (int) x2 - 2, locY, sizeX, sizeY, null);
                if(System.currentTimeMillis() - info.getValue().getSquashAttackTime() > 2200)
                {
                    state.removeSquash((int)x2, locY);
                    info.getValue().setX(-200);
                    info.getValue().setSquashAttacked(false);
                }
            }
            else if(info.getValue().getLife()<200 && info.getValue().getLife()>=50)
                g2d.drawImage(normalZombie.getFullImage(), (int) x2 - 2, locY, sizeX, sizeY, null);
            else if(info.getValue().getLife()<50)
            {
                g2d.drawImage(normalZombie.getDyingImage(), (int) x2 - 2, locY, sizeX, sizeY, null);
                if(info.getValue().getLife()<0)
                {
                    info.getValue().setX(-200);
                    int loc = state.findLoc((int) x2, locY);
                    state.removeStoppedPea(loc);
                }
            }
            else
                g2d.drawImage(coneHeadZombie.getFullImage(), (int) x2, locY, null);
        }
        for (Map.Entry<Integer, BucketHeadZombie> info: state.getZombie().getBucketInfo().entrySet())
        {
            float x2;
            y = info.getValue().getRow();
            x2 = info.getValue().getX();
            int locY = 154 + (y-1)*118;
            if(info.getValue().isBurnt())
                g2d.drawImage(bucketHeadZombie.getBurntImage(), (int) x2, locY, sizeX, sizeY, null);
            else if (info.getValue().isSquashAttacked()&& System.currentTimeMillis() - info.getValue().getSquashAttackTime() > 1650)
            {
                g2d.drawImage(coneHeadZombie.getDyingImage(), (int) x2 - 2, locY, sizeX, sizeY, null);
                if(System.currentTimeMillis() - info.getValue().getSquashAttackTime() > 2200)
                {
                    state.removeSquash((int)x2, locY);
                    info.getValue().setX(-200);
                    info.getValue().setSquashAttacked(false);
                }
            }
            else if(info.getValue().getLife()<200 && info.getValue().getLife()>=50)
                g2d.drawImage(normalZombie.getFullImage(), (int) x2 - 2, locY, sizeX, sizeY, null);
            else if(info.getValue().getLife()<50)
            {
                g2d.drawImage(normalZombie.getDyingImage(), (int) x2 - 2, locY, sizeX, sizeY, null);
                if(info.getValue().getLife()<0)
                {
                    info.getValue().setX(-200);
                    int loc = state.findLoc((int) x2, locY);
                    state.removeStoppedPea(loc);
                }
            }
            else
                g2d.drawImage(bucketHeadZombie.getFullImage(), (int) x2, locY, sizeX+10, sizeY+10, null);
        }
    }

    /**
     * Shows Shovel and makes it bigger when it has been selected.
     */
    private void setShovel(Graphics2D g2d, GameState state)
    {
        int x = shovel.getWidth(null);
        int y = shovel.getHeight(null);
        if(!state.getShovel())
            g2d.drawImage(shovel, 600, 38, null);
        else
            g2d.drawImage(shovel, 600, 38, x+10, y+10, null);
    }

    /**
     * Shows cards based on the game state
     */
    private void putCards(Graphics2D g2d, GameState state)
    {
//        System.out.println(state.getSunFlower().getCard());
        int x = state.getPea().getCardImage().getWidth(null);
        int y = state.getPea().getCardImage().getHeight(null);
        if(!state.getPea().getCard() && state.getSunNumber() >= 100)
            g2d.drawImage(state.getPea().getCardImage(), 110, 38, null);
        if(!state.getSunFlower().getCard() && state.getSunNumber() >= 50)
            g2d.drawImage(state.getSunFlower().getCardImage(), 110+x, 38, null);
        if(!state.getCherry().getCard() && state.getSunNumber() >= 150)
            g2d.drawImage(state.getCherry().getCardImage(), 110+2*x, 38, null);
        if(!state.getWallNut().getCard() && state.getSunNumber() >= 50)
            g2d.drawImage(state.getWallNut().getCardImage(), 110+3*x, 38, null);
        if(!state.getFreezePea().getCard() && state.getSunNumber() >= 175)
            g2d.drawImage(state.getFreezePea().getCardImage(), 110+4*x, 38, null);
        if(!state.getSquash().getCard() && state.getSunNumber() >= 50)
            g2d.drawImage(state.getSquash().getCardImage(), 110+5*x, 38, x, y, null);
        if(timeType.equals("night"))
            if(!state.getMushroom().getCard() && state.getSunNumber() >= 25)
                g2d.drawImage(state.getMushroom().getCardImage(), 110+6*x, 38, x-2, y, null);
    }

    /**
     * Shows lawnMowers based on the game state
     */
    private void putLawnMower(Graphics2D g2d, GameState state)
    {
        for (LawnMower lawnMowers: state.getLawnMowers())
        {
            int row = lawnMowers.getRow();
            int x = lawnMowers.getX();
            g2d.drawImage(lawnMowers.getImage(), x, 150 + 120 * (row-1), null);
        }
    }

    /**
     * Shows sun and the number of caught suns based on the game state
     */
    private void setSun(Graphics2D g2d, GameState state)
    {
        long start = System.currentTimeMillis();
        long delay = (1000 / 30) - (System.currentTimeMillis() - start);
        if(timeType.equals("day"))
            if(delay > 0 && !state.getSun())
                g2d.drawImage(sun, state.sunX, state.sunY, null);
        g2d.setFont(g2d.getFont().deriveFont(18.0f));
        g2d.drawString(String.valueOf(state.sunNumber), 55, 125);
    }

    /**
     *Show flowers on the playground
     */
    private void setFlowers(Graphics2D g2d, GameState state) {
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 5; j++) {
                int loc = j * 10 + i;
                int locX = 0, locY = 0;
                int squash = 0;
                if (state.getInfo().get(loc) != null) {
                    int x = 66;
                    locX = x + (i - 1) * 102;
                    int y = 154;
                    locY = y + (j - 1) * 118;

                    if (state.getInfo().get(loc).equals("peaShooter")) {
                        g2d.drawImage(state.getPea().getFullImage(), locX, locY, null);
                            for (HashMap.Entry<Integer, ArrayList<Integer>> set : state.getPea().getBullets().entrySet())
                                if (set.getKey() == loc)
                                    for (Integer value : set.getValue())
                                        if(state.getStoppedPeas().get(loc) == null ||
                                            state.getStoppedPeas().get(loc) > locX+value)
                                        g2d.drawImage(state.getPea().getPea(), locX + value + 51, locY + 10, null);
                    } else if (state.getInfo().get(loc).equals("deadPeaShooter"))
                        g2d.drawImage(state.getPea().getDeadImage(), locX, locY, null);
                    else if (state.getInfo().get(loc).equals("sunFlower")) {
                        g2d.drawImage(state.getSunFlower().getFullImage(), locX, locY, null);
                        if (state.getSunFlower().getSunFlowerState().containsKey(loc) &&
                                state.getSunFlower().getSunFlowerState().get(loc))
                            g2d.drawImage(sun, locX - 25, locY + 15, null);
                    }else if (state.getInfo().get(loc).equals("deadSunFlower"))
                        g2d.drawImage(state.getSunFlower().getDeadImage(), locX, locY, null);
                    else if (state.getInfo().get(loc).equals("cherryBomb"))
                        g2d.drawImage(state.getCherry().getFullImage(), locX, locY + 30, null);
                    else if (state.getInfo().get(loc).equals("wallNut"))
                        g2d.drawImage(state.getWallNut().getFullImage(), locX, locY, null);
                    else if (state.getInfo().get(loc).equals("halfWallNut"))
                        g2d.drawImage(state.getWallNut().getHalfImage(), locX, locY, null);
                    else if (state.getInfo().get(loc).equals("deadWallNut"))
                        g2d.drawImage(state.getWallNut().getDeadImage(), locX, locY, null);
                    else if (state.getInfo().get(loc).equals("freezePeaShooter")) {
                        g2d.drawImage(state.getFreezePea().getFullImage(), locX, locY, null);
                        for (HashMap.Entry<Integer, ArrayList<Integer>> set : state.getFreezePea().getBullets().entrySet())
                            if (set.getKey() == loc)
                                for (Integer value : set.getValue())
                                    if(state.getStoppedPeas().get(loc) == null ||
                                            state.getStoppedPeas().get(loc) > locX+value)
                                        g2d.drawImage(state.getFreezePea().getPea(), locX + value + 51, locY + 10, null);
                    } else if (state.getInfo().get(loc).equals("mushroom"))
                        g2d.drawImage(state.getMushroom().getFullImage(), locX - 20, locY, 100, 100, null);
                    else if (state.getInfo().get(loc).equals("squash"))
                        g2d.drawImage(state.getSquash().getFullImage(), locX-15, locY, 100, 100, null);
                    else if (state.getInfo().get(loc).equals("attackSquash"))
                        g2d.drawImage(state.getSquash().getAttackSquash(), locX-10, locY-90, 185, 185, null);
                }
            }
        }
    }
}
