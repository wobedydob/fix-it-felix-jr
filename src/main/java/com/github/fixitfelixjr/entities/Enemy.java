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
    public final static Size SIZE = new Size(50, 68);
    public final static int[] SPRITE_ROWS_COLS = {1,1};
    public final static Position INITIAL_POSITION = Position.ENEMY_INITIAL_POSITION;


    private int initialHealth = 1;
    private boolean isJumping = false;

    public Enemy()
    {
        super(SPRITE_IMAGE, INITIAL_POSITION.getCoordinate(), SIZE, SPRITE_ROWS_COLS[0], SPRITE_ROWS_COLS[1]);

        setGravityConstant(0.034);
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
    }


}
