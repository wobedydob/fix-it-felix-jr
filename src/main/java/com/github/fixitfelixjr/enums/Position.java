package com.github.fixitfelixjr.enums;

import com.github.fixitfelixjr.Game;
import com.github.hanyaeger.api.Coordinate2D;

public enum Position
{
    TOP_LEFT(0, 0),
    TOP_CENTER(getGameWidth() / 2, 0),
    TOP_RIGHT(getGameWidth(), 0),
    LEFT(0, getGameHeight() / 2),
    CENTER(getGameWidth() / 2, getGameHeight() / 2),
    RIGHT(getGameWidth(), getGameHeight() / 2),
    BOTTOM_LEFT(0, getGameHeight()),
    BOTTOM_CENTER(getGameWidth() / 2, 960),
    BOTTOM_RIGHT(getGameWidth(), getGameHeight()),

    BUTTON_BOTTOM_CENTER(getGameWidth() / 2, getGameHeight() / 2 + 210),

    PLAYER_INITIAL_POSITION(1000, 960);

    private final double x;
    private final double y;

    Position(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public Coordinate2D getCoordinate()
    {
        return new Coordinate2D(x, y);
    }

    public static double getGameWidth()
    {
        return Game.GAME_WIDTH;
    }

    public static double getGameHeight()
    {
        return Game.GAME_HEIGHT;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

}

