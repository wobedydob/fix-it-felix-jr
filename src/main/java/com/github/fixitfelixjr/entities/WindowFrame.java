package com.github.fixitfelixjr.entities;

import com.github.fixitfelixjr.Game;
import com.github.fixitfelixjr.TimeEvent;
import com.github.fixitfelixjr.WindowRepairListener;
import com.github.fixitfelixjr.entities.powerups.PiePowerUp;
import com.github.fixitfelixjr.entities.powerups.PowerUp;
import com.github.fixitfelixjr.scenes.LevelScene;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.TimerContainer;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;

import java.util.concurrent.atomic.AtomicInteger;

public class WindowFrame extends DynamicSpriteEntity
{
    public static final String SPRITE_IMAGE = "sprites/window_frame.png";
    public static final double WIDTH = 24;
    public static final double HEIGHT = 43;
    public static final Size SIZE = new Size(WIDTH * LevelScene.SPRITE_SIZE_APPLIER, HEIGHT * LevelScene.SPRITE_SIZE_APPLIER);
    public final static int[] SPRITE_ROWS_COLS = {1, 3};
    public static final int NEARBY_WINDOW_THRESHOLD = 70;

    private Window window;
    private Platform platform;
    private PowerUp powerUp = null;
    private NPC npc = null;

    public WindowFrame(Coordinate2D position)
    {
        super(SPRITE_IMAGE, position, SIZE, SPRITE_ROWS_COLS[0], SPRITE_ROWS_COLS[1]);

        Coordinate2D platformPosition = new Coordinate2D(position.getX(), position.getY() + (HEIGHT * LevelScene.SPRITE_SIZE_APPLIER) - 21);
        this.platform = new Platform(platformPosition);
        Game.getInstance().getLevelScene().addEntity(this.platform);

        Coordinate2D windowPosition = new Coordinate2D(position.getX(), position.getY());
        this.window = new Window(windowPosition);
        Game.getInstance().getLevelScene().addEntity(this.window);
    }

    public boolean hasPowerUp()
    {
        return this.powerUp != null;
    }

    public boolean hasNPC()
    {
        return this.npc != null;
    }

    public Window getWindow()
    {
        return this.window;
    }

    public Platform getPlatform()
    {
        return this.platform;
    }

    public PowerUp getPowerUp()
    {
        return this.powerUp;
    }

    public NPC getNPC()
    {
        return this.npc;
    }

    public void setWindow(Window window)
    {
        this.window = window;
    }

    public void setPlatform(Platform platform)
    {
        this.platform = platform;
    }

    public void setPowerUp(PowerUp powerUp)
    {
        this.powerUp = powerUp;
    }

    public void setNPC(NPC npc)
    {
        this.npc = npc;
    }
}
