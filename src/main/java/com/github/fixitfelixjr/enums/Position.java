package com.github.fixitfelixjr.enums;

import com.github.fixitfelixjr.Game;
import com.github.hanyaeger.api.Coordinate2D;

/**
 * The {@code Position} enum defines a set of predefined positions within the game's screen.
 * These positions are commonly used to place game elements like players, enemies, buttons, and scoreboards
 * at specific locations on the screen. Each position is related to the dimensions of the game screen,
 * which are retrieved dynamically from the {@code Game} class.
 *
 * <p>This enum simplifies the placement of entities by providing consistent reference points across different
 * screen sizes and resolutions, facilitating responsive design and alignment in the game's user interface.
 */
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

    ENEMY_INITIAL_POSITION(getGameWidth() / 2 - 110, 0),
    ENEMY_CENTER_LEFT_POSITION(getGameWidth() / 2 - 275, 0),
    ENEMY_LEFT_POSITION(getGameWidth() / 2 - 415, 0),
    ENEMY_CENTER_RIGHT_POSITION(getGameWidth() / 2 + 25, 0),
    ENEMY_RIGHT_POSITION(getGameWidth() / 2 + 175, 0),

    SCOREBOARD_POSITION_TITLE(0, 0),
    SCOREBOARD_POSITION_LEVEL(10, 10),
    SCOREBOARD_POSITION_END(getGameWidth() / 2 - 50, getGameHeight() / 2 + 100);

    private final double x;
    private final double y;

    /**
     * Constructs a new position with specified x and y coordinates.
     *
     * @param x the x-coordinate of the position.
     * @param y the y-coordinate of the position.
     */
    Position(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the position as a {@link Coordinate2D} object.
     *
     * @return the position represented as a {@link Coordinate2D}.
     */
    public Coordinate2D getCoordinate()
    {
        return new Coordinate2D(x, y);
    }

    /**
     * Retrieves the standard game width from the {@code Game} class.
     * This is used to dynamically calculate position based on the game screen width.
     *
     * @return the game width.
     */
    public static double getGameWidth()
    {
        return Game.GAME_WIDTH;
    }

    /**
     * Retrieves the standard game height from the {@code Game} class.
     * This is used to dynamically calculate position based on the game screen height.
     *
     * @return the game height.
     */
    public static double getGameHeight()
    {
        return Game.GAME_HEIGHT;
    }

    /**
     * Gets the x-coordinate of this position.
     *
     * @return the x-coordinate.
     */
    public double getX()
    {
        return x;
    }

    /**
     * Gets the y-coordinate of this position.
     *
     * @return the y-coordinate.
     */
    public double getY()
    {
        return y;
    }
}
