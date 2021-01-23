package game.template.Elements;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;


public class NormalZombie  implements Images, Zombies{
    private Image fullZombie;
    private Image burntZombie;
    private int life;
    private long stopTime;
    private long burntTime;
    private boolean stopped;
    private boolean burnt;
    //1-9
    private int row;
    private float x;
    public NormalZombie()
    {
        super();
        setImages();
        life = 200;
        stopped= false;
        burnt = false;
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
        fullZombie = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\zombie_normal.gif").getImage();
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
    public void setBurnt(boolean burnt) {
        this.burnt = burnt;
    }
    public boolean isBurnt() {
        return burnt;
    }

    public void setBurntTime(long burntTime) {
        this.burntTime = burntTime;
    }

    public long getBurntTime() {
        return burntTime;
    }
}
