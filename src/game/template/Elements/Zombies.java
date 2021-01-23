package game.template.Elements;

import java.awt.*;

public interface Zombies {
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
     *  After receiving to flower, sets zombie stop time and after flower life decrement, stop time updates.
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
     * Returns image of burnt zombie
     */
    Image getBurntImage();
}
