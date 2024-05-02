package com.github.fixitfelixjr.entities.powerups;

import com.github.fixitfelixjr.scenes.LevelScene;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;

/**
 * Represents a pie power-up entity in the game.
 * <p>
 * The pie power-up is a specific type of power-up that provides a temporary effect
 * when collected by the player.
 */
public class PiePowerUp extends PowerUp
{
    public static final String SPRITE_IMAGE = "sprites/pie_power_up.png";
    public static final double WIDTH = 22;
    public static final double HEIGHT = 13;
    public static final Size SIZE = new Size(WIDTH * LevelScene.SPRITE_SIZE_APPLIER, HEIGHT * LevelScene.SPRITE_SIZE_APPLIER);
    public static final int[] SPRITE_ROWS_COLS = {1, 2};
    public static final int DURATION = 5;


    /**
     * Constructs a new PiePowerUp object.
     *
     * @param position The initial position of the pie power-up.
     */
    public PiePowerUp(Coordinate2D position)
    {
        super(SPRITE_IMAGE, DURATION, position, SIZE, SPRITE_ROWS_COLS[0], SPRITE_ROWS_COLS[1]);
    }
}
