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

public class NPC extends DynamicSpriteEntity implements TimerContainer, Collided
{

    public static final String SPRITE_IMAGE = "sprites/nicelanders.png";
    public static final double WIDTH = 78;
    public static final double HEIGHT = 19;
    public static final Size SIZE = new Size(WIDTH * LevelScene.SPRITE_SIZE_APPLIER, HEIGHT * LevelScene.SPRITE_SIZE_APPLIER);
    public static final int[] SPRITE_ROWS_COLS = {1, 6};
    public static final int NPC_COUNT = 6;

    private WindowFrame windowFrame;

    public NPC(Coordinate2D position, int spriteIndex, WindowFrame windowFrame)
    {
        super(SPRITE_IMAGE, position, SIZE, SPRITE_ROWS_COLS[0], SPRITE_ROWS_COLS[1]);
        setCurrentFrameIndex(spriteIndex);
        this.windowFrame = windowFrame;
    }

    public void despawn()
    {
        remove();
        this.windowFrame.setNPC(null);
    }

    @Override
    public void setupTimers()
    {
        TimeEvent despawnEvent = new TimeEvent(5000, () -> this.despawn(), false);
        addTimer(despawnEvent);
    }

    @Override
    public void onCollision(List<Collider> collidingObjects)
    {
        // only one collision at a time
        if (collidingObjects.size() > 1) {
            return;
        }

        Collider collidingObject = collidingObjects.stream().findFirst().orElse(null);
        if (collidingObject instanceof Projectile) {
            this.despawn();
        }

    }

}