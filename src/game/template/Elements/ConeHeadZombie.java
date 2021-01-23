package game.template.Elements;

import javax.swing.*;
import java.awt.*;

public class ConeHeadZombie implements Images, Zombies{
    private Image fullZombie;
    private Image burntZombie;
    private int life;
    private long stopTime;
    private boolean stopped;
    //1-9
    private int row;
    private float x;
    public ConeHeadZombie()
    {
        super();
        setImages();
        life = 560;
        stopped= false;
    }
    /**
     * Sets the row of this zombie
     */
    @Override
    public void setRow(int row)
    {
        this.row = row;
    }
    /**
     * Gets the row number of this zombie
     */
    @Override
    public int getRow()
    {
        return row;
    }

    /**
     *Sets the x coordinate of this zombie
     */
    @Override
    public void setX(float x)
    {
        this.x = x;
    }
    /**
     *Gets the x coordinate of this zombie
     */
    @Override
    public float getX()
    {
        return x;
    }

    /**
     * Changes life of the zombie after attacking.
     * @param number the number of decrement
     */
    public void setLife(int number)
    {
        life = life-number;
    }

    /**
     * Returns the life of this zombie that shows how long can this zombie be alive
     */
    public int getLife()
    {
        return life;
    }
    /**
     * Sets all images of that flower
     */
    @Override
    public void setImages(){
        fullZombie = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\coneheadzombie.gif").getImage();
        burntZombie = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\burntZombie.gif").getImage();
    }
    /**
     * Returns the image of full flower
     */
    @Override
    public Image getFullImage(){
        return fullZombie;
    }
    /**
     * Returns image of burnt zombie
     */
    @Override
    public Image getBurntImage(){
        return burntZombie;
    }
    /**
     * After receiving to flower, shows zombie is stopped or not.
     * @return true if it is stopped.
     */
    @Override
    public boolean isStopped() {
        return stopped;
    }
    /**
     *  After receiving to flower, sets zombie stop time and after flower life decrement, stop time updates.
     */
    @Override
    public void setStopTime(long stopTime) {
        this.stopTime = stopTime;
    }
    /**
     * Stops zombie or makes it move
     * @param stopped -> true if it is stopped.
     */
    @Override
    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }
    /**
     * Returns StopTime
     */
    @Override
    public long getStopTime() {
        return stopTime;
    }
}
