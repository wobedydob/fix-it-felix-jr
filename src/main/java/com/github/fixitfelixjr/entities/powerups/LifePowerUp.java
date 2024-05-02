package com.github.fixitfelixjr.entities.powerups;

import com.github.fixitfelixjr.entities.Life;
import com.github.fixitfelixjr.scenes.LevelScene;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;

public class LifePowerUp extends PowerUp
{
    public static final String SPRITE_IMAGE = Life.SPRITE_IMAGE;
    public static final int SPRITE_SIZE_APPLIER = 2;
    public static final Size SIZE = new Size(Life.WIDTH * SPRITE_SIZE_APPLIER, Life.HEIGHT * SPRITE_SIZE_APPLIER);
    public static final int DURATION = 5;

    public LifePowerUp(Coordinate2D position)
    {
        super(SPRITE_IMAGE, DURATION, position, SIZE, 1, 1);
    }
}
