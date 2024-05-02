package com.github.fixitfelixjr.entities;

import com.github.fixitfelixjr.scenes.LevelScene;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.*;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;
import com.github.hanyaeger.api.scenes.SceneBorder;

public class Projectile extends DynamicSpriteEntity implements SceneBorderTouchingWatcher, Newtonian, Collider
{
    public static final String SPRITE_IMAGE = "sprites/brick.png";
    public static final double WIDTH = 24;
    public static final double HEIGHT = 8;
    public static final int[] SPRITE_ROWS_COLS = {1, 2};
    public static final Size SIZE = new Size(WIDTH * LevelScene.SPRITE_SIZE_APPLIER, HEIGHT * LevelScene.SPRITE_SIZE_APPLIER);

    public Projectile(Coordinate2D initialLocation, double gravityConstant)
    {
        super(SPRITE_IMAGE, initialLocation, SIZE, SPRITE_ROWS_COLS[0], SPRITE_ROWS_COLS[1]);
        setGravityConstant(gravityConstant);
    }

    @Override
    public void notifyBoundaryTouching(SceneBorder border)
    {
        remove();
    }
}
