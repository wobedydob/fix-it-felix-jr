package com.github.fixitfelixjr.entities;

import com.github.fixitfelixjr.scenes.LevelScene;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.impl.RectangleEntity;

import java.util.List;

public class Platform extends RectangleEntity implements Collided
{
    private Coordinate2D position;

    public Platform(final Coordinate2D position)
    {
        super(position);
        this.position = position;
        setHeight(5);
        setWidth((WindowFrame.WIDTH) * LevelScene.SPRITE_SIZE_APPLIER);
    }

    // TODO: wtf does this do exactly?
    @Override
    public void onCollision(final List<Collider> collidingObjects)
    {
        for (Collider collider : collidingObjects) {

            if (collider instanceof Player player) {

                if (player.getAnchorLocation().getY() < this.position.getY() && player.getGravityConstant() != 0) {

                    player.setMotion(0, 0);
                    player.setGravityConstant(0);
                    player.setAnchorLocation(new Coordinate2D(this.position.getX(), this.position.getY() - player.getHeight()));

                } else if (player.getAnchorLocation().getX() > (this.position.getX() + this.getWidth() - 15)) {

                    player.setAnchorLocation(new Coordinate2D(player.getAnchorLocation().getX() + 15, player.getAnchorLocation().getY()));
                    player.setGravityConstant(Player.GRAVITY_CONSTANT);

                } else if (player.getAnchorLocation().getX() < (this.position.getX() - this.getWidth())) {

                    player.setAnchorLocation(new Coordinate2D(player.getAnchorLocation().getX() - 15, player.getAnchorLocation().getY()));
                    player.setGravityConstant(Player.GRAVITY_CONSTANT);

                }

            }

        }

    }

    public Coordinate2D getPosition()
    {
        return this.position;
    }

    public void setPosition(Coordinate2D position)
    {
        this.position = position;
    }
}
