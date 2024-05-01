package com.github.fixitfelixjr.entities.powerups;

import com.github.fixitfelixjr.TimeEvent;
import com.github.fixitfelixjr.entities.WindowFrame;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.TimerContainer;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.impl.SpriteEntity;

public class PowerUp extends SpriteEntity implements Collider
{
    public static final int POWER_UP_COUNT = 2; // amount of powerups
    private int duration;

    public PowerUp(String sprite, int duration, Coordinate2D position, Size size, int spriteRows, int spriteColumns)
    {
        super(sprite, position, size, spriteRows, spriteColumns);
        this.duration = duration;
    }

    public int getDuration()
    {
        return duration;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }

}
