package com.github.fixitfelixjr.entities;

import com.github.fixitfelixjr.scenes.LevelScene;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.*;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;
import com.github.hanyaeger.api.scenes.SceneBorder;

/**
 * Represents a projectile within the game, such as a thrown brick. This class extends {@link DynamicSpriteEntity}
 * and implements {@link SceneBorderTouchingWatcher} to detect collisions with the scene border,
 * {@link Newtonian} to apply physics such as gravity, and {@link Collider} to handle collisions with other entities.
 *
 * <p>This entity is used to model physical projectiles that can interact with other objects and are affected
 * by gravitational forces. It is set to automatically remove itself from the scene upon touching any scene border,
 * simulating a projectile falling out of the playable area or making contact with an obstacle.
 */
public class Projectile extends DynamicSpriteEntity implements SceneBorderTouchingWatcher, Newtonian, Collider
{
    public static final String SPRITE_IMAGE = "sprites/brick.png";
    public static final double WIDTH = 24;
    public static final double HEIGHT = 8;
    public static final int[] SPRITE_ROWS_COLS = {1, 2};
    public static final Size SIZE = new Size(WIDTH * LevelScene.SPRITE_SIZE_APPLIER, HEIGHT * LevelScene.SPRITE_SIZE_APPLIER);

    /**
     * Creates a new instance of {@code Projectile} with the specified initial location and gravity constant.
     *
     * @param initialLocation the initial position of the projectile in the scene.
     * @param gravityConstant the gravitational constant to apply to the projectile, influencing how it will fall.
     */
    public Projectile(Coordinate2D initialLocation, double gravityConstant)
    {
        super(SPRITE_IMAGE, initialLocation, SIZE, SPRITE_ROWS_COLS[0], SPRITE_ROWS_COLS[1]);
        setGravityConstant(gravityConstant);
    }

    /**
     * Responds to the projectile touching any of the scene borders by removing it from the scene.
     * This method is called automatically when the projectile reaches the edge of the scene.
     *
     * @param border the {@link SceneBorder} that the projectile has touched.
     */
    @Override
    public void notifyBoundaryTouching(SceneBorder border)
    {
        remove();
    }
}
