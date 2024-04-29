package com.github.fixitfelixjr.entities.powerups;

import com.github.fixitfelixjr.scenes.LevelScene;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;

public class PiePowerUp extends PowerUp
{
    public static final String SPRITE_IMAGE = "sprites/pie_powerup.png";
    public static final double WIDTH = 22;
    public static final double HEIGHT = 13;
    public static final Size SIZE = new Size(WIDTH * LevelScene.SPRITE_SIZE_APPLIER,  HEIGHT * LevelScene.SPRITE_SIZE_APPLIER);
    public static final int[] SPRITE_ROWS_COLS = {1, 2};
    public static final int DURATION = 10;

    public PiePowerUp(Coordinate2D position)
    {
        // TODO: fix random position for powerup within a window frame...
        super(SPRITE_IMAGE, DURATION, position, SIZE, SPRITE_ROWS_COLS[0], SPRITE_ROWS_COLS[1]);
    }
}
