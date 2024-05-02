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
    BOTTOM_CENTER(getGameWidth() / 2, getGameHeight()),
    BOTTOM_RIGHT(getGameWidth(), getGameHeight()),

    BUTTON_BOTTOM_CENTER(getGameWidth() / 2, getGameHeight() / 2 + 200),

    PLAYER_INITIAL_POSITION(880, 730),
    PLAYER_LIFE_POSITION(1130, 10),

    ENEMY_INITIAL_POSITION(getGameWidth() / 2 - 75, 0),
    ENEMY_CENTER_LEFT_POSITION(getGameWidth() / 2 - 225, 0),
    ENEMY_LEFT_POSITION(getGameWidth() / 2 - 350, 0),
    ENEMY_CENTER_RIGHT_POSITION(getGameWidth() / 2 + 75, 0),
    ENEMY_RIGHT_POSITION(getGameWidth() / 2 + 200, 0),

    SCOREBOARD_POSITION_TITLE(0, 0),
    SCOREBOARD_POSITION_LEVEL(10, 10),
    SCOREBOARD_POSITION_END(getGameWidth() / 2 - 50, getGameHeight() / 2 + 100),
    ;

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

