package com.github.fixitfelixjr.entities;

import com.github.fixitfelixjr.TimeEvent;
import com.github.fixitfelixjr.scenes.LevelScene;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.TimerContainer;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;

import java.util.List;

/**
 * Represents a non-player character (NPC) in the game, depicted as a resident in a building.
 * This class extends {@link DynamicSpriteEntity} and includes functionality for timed events and collision handling.
 * NPCs are associated with specific windows (window frames) and will despawn after a set amount of time or upon certain collisions.
 */
public class NPC extends DynamicSpriteEntity implements TimerContainer, Collided
{
    public static final String SPRITE_IMAGE = "sprites/nicelanders.png";
    public static final double WIDTH = 78;
    public static final double HEIGHT = 19;
    public static final Size SIZE = new Size(WIDTH * LevelScene.SPRITE_SIZE_APPLIER, HEIGHT * LevelScene.SPRITE_SIZE_APPLIER);
    public static final int[] SPRITE_ROWS_COLS = {1, 6};
    public static final int NPC_COUNT = 6;
    public static final int DESPAWN_RATE = 5; // Despawn rate in seconds

    private final WindowFrame windowFrame;

    /**
     * Creates an NPC associated with a specific window frame.
     *
     * @param position The initial position of the NPC within the game scene.
     * @param spriteIndex The index of the sprite to display from the NPC sprite sheet.
     * @param windowFrame The {@link WindowFrame} with which this NPC is associated.
     */
    public NPC(Coordinate2D position, int spriteIndex, WindowFrame windowFrame)
    {
        super(SPRITE_IMAGE, position, SIZE, SPRITE_ROWS_COLS[0], SPRITE_ROWS_COLS[1]);
        setCurrentFrameIndex(spriteIndex);
        this.windowFrame = windowFrame;
    }

    /**
     * Removes the NPC from the game scene and disassociates it from the window frame.
     */
    public void despawn()
    {
        remove();
        this.windowFrame.setNPC(null);
    }

    /**
     * Sets up a timer for the NPC to automatically despawn after a certain duration.
     */
    @Override
    public void setupTimers()
    {
        TimeEvent despawnEvent = new TimeEvent(DESPAWN_RATE * 1000, this::despawn, false);
        addTimer(despawnEvent);
    }

    /**
     * Handles collisions with other {@link Collider} objects, specifically reacting to projectiles by despawning.
     *
     * @param collidingObjects A list of objects currently colliding with this NPC.
     */
    @Override
    public void onCollision(List<Collider> collidingObjects)
    {

        if (collidingObjects.size() > 1) {
            return;
        }

        Collider collidingObject = collidingObjects.stream().findFirst().orElse(null);
        if (collidingObject instanceof Projectile) {
            this.despawn();
        }
    }
}
