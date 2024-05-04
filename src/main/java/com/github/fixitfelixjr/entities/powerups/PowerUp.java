package com.github.fixitfelixjr.entities.powerups;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.impl.SpriteEntity;

/**
 * Represents a power-up entity in the game.
 * Power-ups are special items that provide benefits to the player when collected.
 * Each power-up has a duration during which its effect remains active.
 */
public abstract class PowerUp extends SpriteEntity implements Collider
{

    public static final int SCORE_POINTS = 300;
    public static final int POWER_UP_COUNT = 3; // amount of powerups
    private int duration;

    /**
     * Constructs a new PowerUp object.
     *
     * @param sprite       The path to the sprite image representing the power-up.
     * @param duration     The duration of the power-up's effect in seconds.
     * @param position     The initial position of the power-up.
     * @param size         The size of the power-up.
     * @param spriteRows   The number of rows in the sprite sheet.
     * @param spriteColumns The number of columns in the sprite sheet.
     */
    public PowerUp(String sprite, int duration, Coordinate2D position, Size size, int spriteRows, int spriteColumns)
    {
        super(sprite, position, size, spriteRows, spriteColumns);
        this.duration = duration;
    }

    /**
     * Gets the duration of the power-up's effect.
     *
     * @return The duration of the power-up's effect in seconds.
     */
    public int getDuration()
    {
        return duration;
    }

    /**
     * Sets the duration of the power-up's effect.
     *
     * @param duration The duration of the power-up's effect in seconds.
     */
    public void setDuration(int duration)
    {
        this.duration = duration;
    }
}
