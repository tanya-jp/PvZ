package game.template.Elements;

import javax.swing.*;
import java.awt.*;
/**
 * This class sets cone head zombie images and stores its state and properties.
 * @version 1.0 2021
 * @authors Tanya Djavaherpour and  Elaheh Akbari
 */
public class ConeHeadZombie implements Images, Zombies{
    private Image fullZombie;
    private Image burntZombie;
    private Image dyingZombie;
    private int life;
    private long stopTime;
    private long burntTime;
    private long squashAttackTime;
    private boolean stopped;
    private boolean burnt;
    private boolean squashAttacked;
    private boolean frozen;
    //1-9
    private int row;
    private float x;

    /**
     * Constructs a new cone head zombie
     */
    public ConeHeadZombie()
    {
        super();
        setImages();
        life = 560;
        stopped= false;
        squashAttacked = false;
        burnt = false;
        frozen = false;
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
        dyingZombie = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\zombie_normal_dying.gif").getImage();
    }
    /**
     * Returns the image of full flower
     */
    @Override
    public Image getFullImage(){
        return fullZombie;
    }
    /**
     * Returns the image of dying zombie
     */
    @Override
    public Image getDyingImage(){return dyingZombie;}
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
    /**
     * When cherryBomb burns zombie, sets that this zombie has been burnt.
     * @param burnt -> true if zombie has been burnt.
     */
    @Override
    public void setBurnt(boolean burnt) {
        this.burnt = burnt;
    }
    /**
     * Returns true if this zombie has been burnt.
     */
    @Override
    public boolean isBurnt() {
        return burnt;
    }
    /**
     * Sets the time that cherryBomb burt zombie
     */
    @Override
    public void setBurntTime(long burntTime) {
        this.burntTime = burntTime;
    }
    /**
     * Gets the time that cherryBomb burt zombie
     * @return burtTime
     */
    @Override
    public long getBurntTime() {
        return burntTime;
    }
    /**
     * when squash jumps on this zombie squashAttack should be true
     */
    @Override
    public void setSquashAttacked(boolean squashAttacked){this.squashAttacked = squashAttacked;}
    /**
     * Returns if this flower has been attacked by squash
     * @return true when has been attacked by squash
     */
    @Override
    public boolean isSquashAttacked(){return squashAttacked;}
    /**
     * Sets the time of squash jumping
     * @param squashAttackTime as time of starting attack.
     */
    @Override
    public void setSquashAttackTime(long squashAttackTime){this.squashAttackTime = squashAttackTime;}
    /**
     * Gets the time of squash jumping
     * @return squashAttackTime as time of starting attack.
     */
    @Override
    public long getSquashAttackTime(){return squashAttackTime;}
    /**
     * After first frozen pea, frozen sets true than means zombie speed should decrease.
     */
    @Override
    public void setFrozen(boolean frozen){this.frozen = frozen;}
    /**
     * If zombie has been attacked by a frozen pea returns true, otherwise returns false.
     */
    @Override
    public boolean isFrozen(){return frozen;}
}
