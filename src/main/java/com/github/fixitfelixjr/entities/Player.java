package com.github.fixitfelixjr.entities;

import com.github.fixitfelixjr.enums.Position;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.*;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;
import com.github.hanyaeger.api.scenes.SceneBorder;
import com.github.hanyaeger.api.userinput.KeyListener;
import javafx.scene.input.KeyCode;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class Player extends DynamicSpriteEntity implements KeyListener, SceneBorderTouchingWatcher, Newtonian, Collided, Collider
{

    public final static String SPRITE_IMAGE = "sprites/felix.png";
    public final static Size SIZE = new Size(75, 102);
    public final static int[] SPRITE_ROWS_COLS = {1, 2};
    public final static Position INITIAL_POSITION = Position.PLAYER_INITIAL_POSITION;

    private static final int MAX_HEALTH = 10;
    private static final double MOVE_SPEED = 7.5;
    private static final double JUMP_STRENGTH = 20;
    private static final double GRAVITY_CONSTANT = 0.5;

    private int health;
    private boolean isJumping = false;

    public Player()
    {
        super(SPRITE_IMAGE, INITIAL_POSITION.getCoordinate(), SIZE, SPRITE_ROWS_COLS[0], SPRITE_ROWS_COLS[1]);
        this.health = MAX_HEALTH;
        setGravityConstant(GRAVITY_CONSTANT);
    }

    @Override
    public void onPressedKeysChange(Set<KeyCode> pressedKeys)
    {
        if (!isJumping && (pressedKeys.contains(KeyCode.UP) || pressedKeys.contains(KeyCode.W) || pressedKeys.contains(KeyCode.SPACE))) {
            isJumping = true;
            setMotion(JUMP_STRENGTH, Direction.UP.getValue());
            setGravityConstant(GRAVITY_CONSTANT);
        }

        if (pressedKeys.contains(KeyCode.LEFT) || pressedKeys.contains(KeyCode.A)) {
            setMotion(MOVE_SPEED, Direction.LEFT.getValue());
            setCurrentFrameIndex(0);
        } else if (pressedKeys.contains(KeyCode.RIGHT) || pressedKeys.contains(KeyCode.D)) {
            setMotion(MOVE_SPEED, Direction.RIGHT.getValue());
            setCurrentFrameIndex(1);
        } else if (pressedKeys.contains(KeyCode.DOWN) || pressedKeys.contains(KeyCode.S)) {
            setMotion(MOVE_SPEED, Direction.DOWN.getValue());
        } else if (pressedKeys.isEmpty()) {
            setSpeed(0);
        }
    }



    @Override
    public void onCollision(List<Collider> collidingObjects)
    {
//        System.out.println("Player collided with: " + collidingObjects);
    }

    @Override
    public void notifyBoundaryTouching(SceneBorder border)
    {

        if (border == SceneBorder.BOTTOM) {
            isJumping = false;
            setGravityConstant(0);
            setMotion(0, 0);
        }

        setSpeed(0);
        switch (border) {
            case TOP:
                setAnchorLocationY(1);
                break;
            case BOTTOM:
                setAnchorLocationY(getSceneHeight() - getHeight() - 1);
                break;
            case LEFT:
                setAnchorLocationX(1);
                break;
            case RIGHT:
                setAnchorLocationX(getSceneWidth() - getWidth() - 1);
            default:
                break;
        }
    }

}