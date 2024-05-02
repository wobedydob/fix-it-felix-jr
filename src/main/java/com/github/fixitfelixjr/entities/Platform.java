package com.github.fixitfelixjr.entities;

import com.github.fixitfelixjr.scenes.LevelScene;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.impl.RectangleEntity;

import java.util.List;

/**
 * Represents a platform in the game scene. This class extends {@link RectangleEntity} and implements {@link Collided}
 * to handle interactions between the platform and other colliding entities, such as the player.
 * The platform influences player movement and positioning based on the player's interaction with the platform edges.
 */
public class Platform extends RectangleEntity implements Collided
{
    private Coordinate2D position;

    /**
     * Constructs a platform entity at a specified position.
     *
     * @param position The {@link Coordinate2D} position where the platform will be located.
     */
    public Platform(final Coordinate2D position)
    {
        super(position);
        this.position = position;
        setHeight(5);
        setWidth(WindowFrame.WIDTH * LevelScene.SPRITE_SIZE_APPLIER);  // Set the width based on the game level settings
    }

    /**
     * Handles collision with other {@link Collider} entities, specifically the player.
     * The method adjusts the player's position and motion based on where they collide with the platform.
     *
     * @param collidingObjects A list of {@link Collider} entities that are currently colliding with this platform.
     */
    @Override
    public void onCollision(final List<Collider> collidingObjects)
    {
        for (Collider collider : collidingObjects) {
            if (collider instanceof Player player) {
                if (player.getAnchorLocation().getY() < this.position.getY() && player.getGravityConstant() != 0) {
                    // if the player is above the platform and affected by gravity, stop the fall
                    player.setMotion(0, 0);
                    player.setGravityConstant(0);
                    player.setAnchorLocation(new Coordinate2D(this.position.getX(), this.position.getY() - player.getHeight()));
                } else if (player.getAnchorLocation().getX() > (this.position.getX() + this.getWidth() - 15)) {
                    // handle the player moving beyond the right edge of the platform
                    player.setAnchorLocation(new Coordinate2D(player.getAnchorLocation().getX() + 15, player.getAnchorLocation().getY()));
                    player.setGravityConstant(Player.GRAVITY_CONSTANT);
                } else if (player.getAnchorLocation().getX() < (this.position.getX() - this.getWidth())) {
                    // handle the player moving beyond the left edge of the platform
                    player.setAnchorLocation(new Coordinate2D(player.getAnchorLocation().getX() - 15, player.getAnchorLocation().getY()));
                    player.setGravityConstant(Player.GRAVITY_CONSTANT);
                }
            }
        }
    }

    /**
     * Gets the current position of the platform.
     *
     * @return The current position as a {@link Coordinate2D}.
     */
    public Coordinate2D getPosition()
    {
        return this.position;
    }

    /**
     * Sets the position of the platform to a new location.
     *
     * @param position The new position as a {@link Coordinate2D}.
     */
    public void setPosition(Coordinate2D position)
    {
        this.position = position;
    }
}
