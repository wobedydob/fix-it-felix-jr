package com.github.fixitfelixjr.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.impl.SpriteEntity;

public class Lifes extends SpriteEntity {

    private Coordinate2D position;

    public Lifes(String imagePath, Coordinate2D position, Size size) {
        super(imagePath, position, size);
        this.position = position;
    }

    public Coordinate2D getPosition() {
        return position;
    }

    public void setPosition(Coordinate2D position) {
        this.position = position;
        setAnchorLocation(position);
    }
}
