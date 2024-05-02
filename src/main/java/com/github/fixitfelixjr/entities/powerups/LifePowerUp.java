package com.github.fixitfelixjr.entities.powerups;

import com.github.fixitfelixjr.scenes.LevelScene;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;

public class LifePowerUp extends PowerUp
{
    public static final String SPRITE_IMAGE = "sprites/felix_life_sprite.png";
    public static final double WIDTH = 10;
    public static final double HEIGHT = 7;
    public static final Size SIZE = new Size(WIDTH * LevelScene.SPRITE_SIZE_APPLIER, HEIGHT * LevelScene.SPRITE_SIZE_APPLIER);
    public static final int[] SPRITE_ROWS_COLS = {1, 1};
    public static final int DURATION = 5;

    public LifePowerUp(Coordinate2D position)
    {
        super(SPRITE_IMAGE, DURATION, position, SIZE, SPRITE_ROWS_COLS[0], SPRITE_ROWS_COLS[1]);
    }
}
