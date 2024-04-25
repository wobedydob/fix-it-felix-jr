package com.github.fixitfelixjr.entities;

import com.github.fixitfelixjr.scenes.LevelScene;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;

public class WindowFrame extends DynamicSpriteEntity
{

    public static final String SPRITE_IMAGE = "sprites/window_frame.png";
    public static final double WIDTH = 24 * 4;
    public static final double HEIGHT = 43 * 4;
    public final static Size SIZE = new Size(WIDTH, HEIGHT);
    public final static int[] SPRITE_ROWS_COLS = {1, 3};
    public static final int NEARBY_WINDOW_THRESHOLD = 70;

    private Coordinate2D position;
    private Window window;
    private Platform platform;

    public WindowFrame(Coordinate2D position, LevelScene scene)
    {
        super(SPRITE_IMAGE, position, SIZE, SPRITE_ROWS_COLS[0], SPRITE_ROWS_COLS[1]);

        Coordinate2D platformPosition = new Coordinate2D(position.getX(), position.getY() + HEIGHT - 10);
        this.platform = new Platform(platformPosition);
        scene.addEntity(platform);

        Coordinate2D windowPosition = new Coordinate2D(position.getX(), position.getY());
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
