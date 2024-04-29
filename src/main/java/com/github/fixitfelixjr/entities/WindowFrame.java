package com.github.fixitfelixjr.entities;

import com.github.fixitfelixjr.entities.powerups.PiePowerUp;
import com.github.fixitfelixjr.entities.powerups.PowerUp;
import com.github.fixitfelixjr.scenes.LevelScene;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;

public class WindowFrame extends DynamicSpriteEntity
{

    public static final String SPRITE_IMAGE = "sprites/window_frame.png";
    public static final double WIDTH = 24;
    public static final double HEIGHT = 43;
    public static final Size SIZE = new Size(WIDTH * LevelScene.SPRITE_SIZE_APPLIER,  HEIGHT * LevelScene.SPRITE_SIZE_APPLIER);
    public final static int[] SPRITE_ROWS_COLS = {1, 3};
    public static final int NEARBY_WINDOW_THRESHOLD = 70;

    private Window window;
    private Platform platform;
    private PowerUp powerUp = null;

    public WindowFrame(Coordinate2D position, LevelScene scene)
    {
        super(SPRITE_IMAGE, position, SIZE, SPRITE_ROWS_COLS[0], SPRITE_ROWS_COLS[1]);

        Coordinate2D platformPosition = new Coordinate2D(position.getX(), position.getY() + (HEIGHT * LevelScene.SPRITE_SIZE_APPLIER) - 10);
        this.platform = new Platform(platformPosition);
        scene.addEntity(platform);

        Coordinate2D windowPosition = new Coordinate2D(position.getX(), position.getY());
        this.window = new Window(windowPosition);
        scene.addEntity(window);

        Coordinate2D powerUpPosition = new Coordinate2D(position.getX() + (26), position.getY() + (HEIGHT * 2));
        PowerUp pie = new PiePowerUp(powerUpPosition);
        scene.addEntity(pie);
    }

    public Window getWindow()
    {
        return window;
    }

    public Platform getPlatform()
    {
        return platform;
    }

    public void setWindow(Window window)
    {
        this.window = window;
    }

    public void setPlatform(Platform platform)
    {
        this.platform = platform;
    }

}
