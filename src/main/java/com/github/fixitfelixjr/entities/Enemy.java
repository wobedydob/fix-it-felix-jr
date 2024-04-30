package com.github.fixitfelixjr.entities;

import com.github.fixitfelixjr.TimeEvent;
import com.github.fixitfelixjr.entities.powerups.HardhatPowerUp;
import com.github.fixitfelixjr.entities.powerups.PiePowerUp;
import com.github.fixitfelixjr.enums.Position;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.TimerContainer;
import com.github.hanyaeger.api.entities.*;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;
import com.github.hanyaeger.api.scenes.SceneBorder;
import com.github.hanyaeger.api.userinput.KeyListener;
import javafx.scene.input.KeyCode;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class Enemy extends DynamicSpriteEntity implements TimerContainer, Collided, Collider
{

    public static final String SPRITE_IMAGE = "sprites/Ralph_front_sprite.png";
    public static final Size SIZE = new Size(180, 195);
    public static final int[] SPRITE_ROWS_COLS = {1, 1};
    public static final Position INITIAL_POSITION = Position.ENEMY_INITIAL_POSITION;
    public static final int MOVE_RATE = 2; // in seconds

    private boolean isMoving = false;
    private boolean isWrecking = false;

    private boolean isCenter = false;
    private boolean isRight = false;
    private boolean isLeft = false;

    public Enemy()
    {
        super(SPRITE_IMAGE, INITIAL_POSITION.getCoordinate(), SIZE, SPRITE_ROWS_COLS[0], SPRITE_ROWS_COLS[1]);
//        setMotion(2, 270d);
    }


    @Override
    public void onCollision(List<Collider> collidingObjects)
    {
    }

    @Override
    public void setupTimers()
    {
        TimeEvent moveEvent = new TimeEvent(MOVE_RATE * 1000, () -> this.randomizeMove(), true);
        addTimer(moveEvent);
    }

    // TODO: improve movement with animation
    public void randomizeMove()
    {
        System.out.println(" ");
        System.out.println("-----------------------------");

        if (this.isMoving || this.isWrecking) {
            System.out.println("can't move, already moving");
            return;
        }

        System.out.println("trying to randomize move");
        int random = new Random().nextInt(4); // 0 = left, 1 = right, 2 = center, 3 = destroy

        if (random == 0 && !this.isLeft) {
            System.out.println("want to move left");
            this.moveLeft();
        } else if (random == 1 && !this.isRight) {
            System.out.println("want to move right");
            this.moveRight();
        } else if (random == 2 && !this.isCenter) {
            System.out.println("want to move to center");
            this.moveCenter();
        } else {
            System.out.println("want to destroy");
        }

        System.out.println("-----------------------------");
        System.out.println(" ");
    }

    public void moveLeft()
    {
        this.clearPositions();
        this.isLeft = true;
        setAnchorLocation(Position.ENEMY_LEFT_POSITION.getCoordinate());
    }

    public void moveRight()
    {
        this.clearPositions();
        this.isRight = true;
        setAnchorLocation(Position.ENEMY_RIGHT_POSITION.getCoordinate());
    }

    public void moveCenter()
    {
        this.clearPositions();
        this.isCenter = true;
        setAnchorLocation(Position.ENEMY_INITIAL_POSITION.getCoordinate());
    }

    private void clearPositions()
    {
        this.isLeft = false;
        this.isRight = false;
        this.isCenter = false;
    }


}