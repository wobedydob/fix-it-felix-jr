package com.github.fixitfelixjr.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.impl.RectangleEntity;

import java.util.List;

public class Platform extends RectangleEntity implements Collided {

    public static final String SPRITE_IMAGE = "sprites/window.png";
    public final static Size SIZE = new Size(80, 1);

    private int repairState;
    private Coordinate2D position;

    public Platform(final Coordinate2D location) {
        super(location); // Toegevoegd om sprite image en size toe te passen

        this.position = location;
    }

    @Override
    public void onCollision(final List<Collider> collidingObjects) {
        for (Collider collider : collidingObjects) {
            if (collider instanceof Player) {
                Player player = (Player) collider;
                System.out.println("Player collided with platform");
            }
        }
    }


    public Coordinate2D getPosition() {
        return position;
    }
}
