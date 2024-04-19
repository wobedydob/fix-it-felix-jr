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

public class Enemy extends DynamicSpriteEntity implements KeyListener, SceneBorderTouchingWatcher, Newtonian, Collided, Collider
{

    public final static String SPRITE_IMAGE = "sprites/Ralph_front_sprite.png";
    public final static Size SIZE = new Size(180, 195);
    public final static int[] SPRITE_ROWS_COLS = {1, 1};
    public final static Position INITIAL_POSITION = Position.ENEMY_INITIAL_POSITION;

    private static final int MAX_HEALTH = 10;
    private static final double MOVE_SPEED = 7.5;
    private static final double JUMP_STRENGTH = 20;
    private static final double GRAVITY_CONSTANT = 0.5;

    private int health;
    private boolean isJumping = false;

    public Enemy()
    {
        super(SPRITE_IMAGE, INITIAL_POSITION.getCoordinate(), SIZE, SPRITE_ROWS_COLS[0], SPRITE_ROWS_COLS[1]);
        this.health = MAX_HEALTH;
        setGravityConstant(GRAVITY_CONSTANT);
    }

    @Override
    public void onPressedKeysChange(Set<KeyCode> pressedKeys)
    {

    }



    @Override
    public void onCollision(List<Collider> collidingObjects)
    {
        System.out.println("Player collided with: " + collidingObjects);
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