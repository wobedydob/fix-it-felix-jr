package com.github.fixitfelixjr.entities.powerups;

import com.github.fixitfelixjr.TimeEvent;
import com.github.fixitfelixjr.entities.WindowFrame;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.TimerContainer;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.impl.SpriteEntity;

public class PowerUp extends SpriteEntity implements Collider, TimerContainer
{
    public static final int DESPAWN_DELAY = 10; // TODO: add despawning?

    private int duration;
    private WindowFrame windowFrame;

    public PowerUp(String sprite, int duration, Coordinate2D position, Size size, int spriteRows, int spriteColumns, WindowFrame windowFrame)
    {
        super(sprite, position, size, spriteRows, spriteColumns);
        this.duration = duration;
        this.windowFrame = windowFrame;
    }

    public void despawn()
    {
        remove();
        this.windowFrame.setPowerUp(null);
    }

    public int getDuration()
    {
        return duration;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    @Override
    public void setupTimers()
    {
        TimeEvent despawnEvent = new TimeEvent(DESPAWN_DELAY * 1000, () -> this.despawn(), false);
        addTimer(despawnEvent);
    }
}
