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
    private Image sun;
    private Image shovel;
    private Image peaShooterCard;
    private Image sunFlowerCard;
    private Image cherryBombCard;
    private Image wallNutCard;
    private Image freezePeaShooterCard;
    private Image mushroomCard;
    private Image lawnMower;
    private Image walnutFull;
    private Image sunFlowerFull;
    private Image peaShooterFull;
    private Image freezeShooterFull;
    private Image cherryFull;
    private Image mushroomFull;
    private String type;
    private String timeType;

    private BufferStrategy bufferStrategy;

    public GameFrame(String title, String type, String timeType) {
        super(title);
        this.timeType = timeType;
        this.type = type;
        setResizable(false);
        setSize(GAME_WIDTH, GAME_HEIGHT);
//        background = new ImageIcon(".\\PVS Design Kit\\images\\mainBG.png").getImage();
        //
        // Initialize the JFrame ...
        //
        setImages();
    }

    /**
     * Sets all images and gifs
     */
    private void setImages()
    {
//        sun = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\sun.gif").getImage();
        sun = new ImageIcon(".\\PVS Design Kit\\images\\sun.png").getImage();
        shovel = new ImageIcon(".\\PVS Design Kit\\images\\shovel.png").getImage();
        peaShooterCard = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_peashooter.png").getImage();
        sunFlowerCard = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_sunflower.png").getImage();
        cherryBombCard = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_cherrybomb.png").getImage();
        wallNutCard = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_wallnut.png").getImage();
        freezePeaShooterCard = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_freezepeashooter.png").getImage();
        mushroomCard = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_sun-shroom.png").getImage();
        lawnMower = new ImageIcon(".\\PVS Design Kit\\images\\Lawn_Mower.png").getImage();
        walnutFull = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\walnut_full_life.gif").getImage();
        sunFlowerFull = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\sun_flower.gif").getImage();
        peaShooterFull = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\pea_shooter.gif").getImage();
        freezeShooterFull = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\freezepeashooter.gif").getImage();
        cherryFull = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\newCherryBomb.gif").getImage();
        mushroomFull = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\Sun_Shroom.gif").getImage();
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
//        if(timeType.equals("night"))
//            state = new Night(type);
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
//        if(timeType.equals("night"))
//            state = new Night(type);
        GameCanvas canvas = new GameCanvas(timeType);
        canvas.paintComponent(g2d);
        //set shovel
        setShovel(g2d, state);
        //set cards
        putCards(g2d, state);
        //set lawn mowers
        putLawnMower(g2d, state);
        //set flowers
        setFlowers(g2d, state);
        //set sun
        setSun(g2d, state);

//        canvas.render(state);
        //
        // Draw all game elements according
        //  to the game 'state' using 'g2d' ...
        //
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
        int x = peaShooterCard.getWidth(null);
        int y = peaShooterCard.getHeight(null);
        if(!state.getPea().getCard() && state.getSunNumber() >= 100)
            g2d.drawImage(peaShooterCard, 110, 38, null);
        if(!state.getSunFlower().getCard() && state.getSunNumber() >= 50)
            g2d.drawImage(sunFlowerCard, 110+x, 38, null);
        if(!state.getCherry().getCard() && state.getSunNumber() >= 150)
            g2d.drawImage(cherryBombCard, 110+2*x, 38, null);
        if(!state.getWallNut().getCard() && state.getSunNumber() >= 50)
            g2d.drawImage(wallNutCard, 110+3*x, 38, null);
        if(!state.getFreezePea().getCard() && state.getSunNumber() >= 175)
            g2d.drawImage(freezePeaShooterCard, 110+4*x, 38, null);
        if(timeType.equals("night"))
            if(!state.getMushroom().getCard() && state.getSunNumber() >= 25)
                g2d.drawImage(mushroomCard, 110+5*peaShooterCard.getWidth(null), 38, x-2, y, null);
    }

    /**
     * Shows lawnMowers based on the game state
     */
    private void putLawnMower(Graphics2D g2d, GameState state)
    {
        g2d.drawImage(lawnMower,-35,150,null);
        g2d.drawImage(lawnMower,-35,150 + 120,null);
        g2d.drawImage(lawnMower,-35,150 + 120*2,null);
        g2d.drawImage(lawnMower,-35,150 + 120*3,null);
        g2d.drawImage(lawnMower,-35,150 + 120*4,null);
        g2d.drawImage(lawnMower,-35,150 + 120*5,null);
    }

    /**
     * Shows sun and the number of caught suns based on the game state
     */
    private void setSun(Graphics2D g2d, GameState state)
    {
        long start = System.currentTimeMillis();
        long delay = (1000 / 30) - (System.currentTimeMillis() - start);
        if(delay > 0 && !state.getSun())
            g2d.drawImage(sun, state.sunX, state.sunY, null);
        g2d.setFont(g2d.getFont().deriveFont(18.0f));
        g2d.drawString(String.valueOf(state.sunNumber), 55, 125);
    }

    /**
     *Show flowers on the playground
     */
    private void setFlowers(Graphics2D g2d, GameState state)
    {
        for (int i = 1; i <= 9; i++)
        {
            for(int j = 1; j <= 5; j++)
            {
                int loc = j*10 + i;
                int locX = 0, locY = 0;
                if(state.getInfo().get(loc) != null)
                {
                    int x = 66;
                    locX = x + (i-1)*102;
                    int y = 154;
                    locY = y + (j-1)*118;

                    if(state.getInfo().get(loc).equals("peaShooter"))
                        g2d.drawImage(peaShooterFull, locX, locY, null);
                    else if(state.getInfo().get(loc).equals("sunFlower"))
                    {
                        g2d.drawImage(sunFlowerFull, locX, locY, null);
                        if(state.getSunFlowerState().containsKey(loc) &&
                        state.getSunFlowerState().get(loc))
                            g2d.drawImage(sun, locX-25, locY+15, null);
                    }
                    else if(state.getInfo().get(loc).equals("cherryBomb"))
                        g2d.drawImage(cherryFull, locX, locY+30, null);
                    else if(state.getInfo().get(loc).equals("wallNut"))
                        g2d.drawImage(walnutFull, locX, locY, null);
                    else if(state.getInfo().get(loc).equals("freezePeaShooter"))
                        g2d.drawImage(freezeShooterFull, locX, locY, null);
                    else if(state.getInfo().get(loc).equals("mushroom"))
                        g2d.drawImage(mushroomFull, locX-15, locY, 100, 100, null);

                }

            }
        }
    }

}
