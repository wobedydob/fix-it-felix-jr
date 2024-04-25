package com.github.fixitfelixjr.entities;

import com.github.fixitfelixjr.scenes.LevelScene;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;

public class WindowFrame extends DynamicSpriteEntity
{

    public static final String SPRITE_IMAGE = "sprites/window_frame.png";

    public static final int WIDTH = 24 * 3;
    public static final int HEIGHT = 43 * 3;
    public final static Size SIZE = new Size(WIDTH, HEIGHT);
    public final static int[] SPRITE_ROWS_COLS = {1, 3};

    private Coordinate2D position;
    private Window window;
    private Platform platform;

    public WindowFrame(Coordinate2D position, LevelScene scene)
    {
        super(SPRITE_IMAGE, position, SIZE, SPRITE_ROWS_COLS[0], SPRITE_ROWS_COLS[1]);

        Coordinate2D platformPosition = new Coordinate2D(position.getX(), position.getY() + HEIGHT - 10);
        this.platform = new Platform(platformPosition);
        scene.addEntity(platform);

        Coordinate2D windowPosition = new Coordinate2D(position.getX() - 20, position.getY() + HEIGHT - 135);
        this.window = new Window(windowPosition);
        scene.addEntity(window);


//        this.window = new Window(position);
//        this.platform = new Platform(position);
    }

    public Window getWindow()
    {
        return window;
    }

    public Platform getPlatform()
    {
        return platform;
    }

}
