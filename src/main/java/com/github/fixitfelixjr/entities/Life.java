package com.github.fixitfelixjr.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.impl.SpriteEntity;

/**
 * Represents a life icon in a game, displaying visual representation of player lives.
 * This class extends {@link SpriteEntity}, which allows it to manage and display a sprite image for each life.
 * The life icons are used to visually indicate the number of lives a player has left.
 */
public class Life extends SpriteEntity
{
    public static final String SPRITE_IMAGE = "sprites/felix_life_sprite.png";
    public static final double SPRITE_SIZE_APPLIER = 2.5;
    public static final double WIDTH = 14;
    public static final double HEIGHT = 14;
    public static final Size SIZE = new Size(WIDTH * SPRITE_SIZE_APPLIER, HEIGHT * SPRITE_SIZE_APPLIER);
    public static final int LIFE_SPRITE_SPACING = 35;  // Spacing used when displaying multiple lives

    private Coordinate2D position;

    /**
     * Constructs a Life entity with a specific position.
     *
     * @param position The initial position of the life icon within the game scene.
     */
    public Life(Coordinate2D position)
    {
        super(SPRITE_IMAGE, position, SIZE);
        this.position = position;
    }

    /**
     * Gets the current position of the life icon.
     *
     * @return The current position as a {@link Coordinate2D}.
     */
    public Coordinate2D getPosition()
    {
        return position;
    }

    /**
     * Sets the position of the life icon to a new location and updates its anchor location.
     *
     * @param position The new position as a {@link Coordinate2D}.
     */
    public void setPosition(Coordinate2D position)
    {
        this.position = position;
        setAnchorLocation(position);
    }
}
