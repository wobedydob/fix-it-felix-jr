package com.github.fixitfelixjr.entities.powerups;

import com.github.fixitfelixjr.entities.Life;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;


/**
 * Represents a life power-up entity in the game.
 * The life power-up is a specific type of power-up that grants an extra life to the player
 * when collected.
 */
public class LifePowerUp extends PowerUp
{
    public static final String SPRITE_IMAGE = Life.SPRITE_IMAGE;
    public static final int SPRITE_SIZE_APPLIER = 2;
    public static final Size SIZE = new Size(Life.WIDTH * SPRITE_SIZE_APPLIER, Life.HEIGHT * SPRITE_SIZE_APPLIER);
    public static final int DURATION = 5;

    /**
     * Constructs a new LifePowerUp object.
     *
     * @param position The initial position of the life power-up.
     */
    public LifePowerUp(Coordinate2D position)
    {
        super(SPRITE_IMAGE, DURATION, position, SIZE, 1, 1);
    }
}
