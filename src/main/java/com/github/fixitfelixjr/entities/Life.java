package com.github.fixitfelixjr.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.impl.SpriteEntity;

public class Life extends SpriteEntity
{
    public static final String SPRITE_IMAGE = "sprites/felix_life_sprite.png";
    public static final double SPRITE_SIZE_APPLIER = 2.5;
    public static final double WIDTH = 14;
    public static final double HEIGHT = 14;
    public static final Size SIZE = new Size(WIDTH * SPRITE_SIZE_APPLIER, HEIGHT * SPRITE_SIZE_APPLIER);
    public static final int LIFE_SPRITE_SPACING = 35;

    private Coordinate2D position;

    public Life(Coordinate2D position)
    {
        super(SPRITE_IMAGE, position, SIZE);
        this.position = position;
    }

    public Coordinate2D getPosition()
    {
        return position;
    }

    public void setPosition(Coordinate2D position)
    {
        this.position = position;
        setAnchorLocation(position);
    }
}
