package game.template.Elements;
import java.awt.*;
/**
 * This interface fixes everything which is related to zombies.
 * @version 1.0 2021
 * @authors Tanya Djavaherpour and Elaheh Akbari
 */
public interface Zombie {
    /**
     * Sets the row of this zombie
     */
    void setRow(int row);
    /**
     * Gets the row number of this zombie
     */
    int getRow();
    /**
     *Sets the x coordinate of this zombie
     */
    void setX(float x);
    /**
     *Gets the x coordinate of this zombie
     */
    float getX();
    /**
     * Changes life of the zombie after attacking.
     * @param number the number of decrement
     */
    void setLife(int number);
    /**
     * After receiving to flower, shows zombie is stopped or not.
     * @return true if it is stopped.
     */
    boolean isStopped();
    /**
     *  After receiving to flower, sets zombie stop time and after flower life decrement, updates stop time .
     */
    void setStopTime(long stopTime);
    /**
     * Stops zombie or makes it move
     * @param stopped -> true if it is stopped.
     */
    void setStopped(boolean stopped);
    /**
     * Returns StopTime
     */
    long getStopTime();
    /**
     * Returns the image of dying zombie
     */
    Image getDyingImage();
    /**
     * Returns image of burnt zombie
     */
    Image getBurntImage();
    /**
     * When cherryBomb burns zombie, sets that this zombie has been burnt.
     * @param burnt -> true if zombie has been burnt.
     */
    void setBurnt(boolean burnt);
    /**
     * Returns true if this zombie has been burnt.
     */
    boolean isBurnt();
    /**
     * Sets the time that cherryBomb burt zombie
     */
    void setBurntTime(long burntTime);
    /**
     * Gets the time that cherryBomb burt zombie
     * @return burtTime
     */
    long getBurntTime();
    /**
     * when squash jumps on this zombie squashAttack should be true
     */
    void setSquashAttacked(boolean squashAttacked);
    /**
     * Returns if this flower has been attacked by squash
     * @return true when has been attacked by squash
     */
    boolean isSquashAttacked();
    /**
     * Sets the time of squash jumping
     * @param squashAttackTime as time of starting attack.
     */
    void setSquashAttackTime(long squashAttackTime);
    /**
     * Gets the time of squash jumping
     * @return squashAttackTime as time of starting attack.
     */
    long getSquashAttackTime();
    /**
     * After first frozen pea, frozen sets true than means zombie speed should decrease.
     */
    void setFrozen(boolean frozen);
    /**
     * If zombie has been attacked by a frozen pea returns true, otherwise returns false.
     */
    boolean isFrozen();
    /**
     * If zombie is killed, sets true
     */
    void setKilled(boolean killed);
    /**
     * If zombie is alive returns false otherwise if it is killed returns true.
     */
    boolean isKilled();

}
