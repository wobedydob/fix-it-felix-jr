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
import java.util.Set;

public class Player extends DynamicSpriteEntity implements KeyListener, SceneBorderTouchingWatcher, Newtonian, Collided, Collider
{

    public static final String SPRITE_IMAGE = "sprites/felix.png";
    public static final Size SIZE = new Size(75, 102);
    public static final int[] SPRITE_ROWS_COLS = {1, 2};
    public static final Position INITIAL_POSITION = Position.PLAYER_INITIAL_POSITION;

    private static final int MAX_HEALTH = 10;
    private static final double MOVE_SPEED = 7.5;
    private static final double JUMP_STRENGTH = 20;
    public static final double GRAVITY_CONSTANT = 0.5;

    private int health;
    private boolean isJumping = false;

    public Player()
    {
        super(SPRITE_IMAGE, INITIAL_POSITION.getCoordinate(), SIZE, SPRITE_ROWS_COLS[0], SPRITE_ROWS_COLS[1]);
        this.health = MAX_HEALTH;
        setGravityConstant(GRAVITY_CONSTANT);
    }

    public void fixIt()
    {
        System.out.println("fix it felix!");

        Window windowToRepair = findNearestWindow();
        if (windowToRepair != null) {
            windowToRepair.repair();
        }
    }

    private boolean isNearWindow(Window window)
    {
        return Math.abs(this.getAnchorLocation().getX() - window.getAnchorLocation().getX()) < 50 &&
                Math.abs(this.getAnchorLocation().getY() - window.getAnchorLocation().getY()) < 50;
    }

    @Override
    public void onPressedKeysChange(Set<KeyCode> pressedKeys)
    {
        boolean moveLeft = pressedKeys.contains(KeyCode.LEFT) || pressedKeys.contains(KeyCode.A);
        boolean moveRight = pressedKeys.contains(KeyCode.RIGHT) || pressedKeys.contains(KeyCode.D);
        boolean moveDown = pressedKeys.contains(KeyCode.DOWN) || pressedKeys.contains(KeyCode.S);
        boolean jump = pressedKeys.contains(KeyCode.UP) || pressedKeys.contains(KeyCode.W) || pressedKeys.contains(KeyCode.SPACE);

        if (!moveLeft && !moveRight && !moveDown && !jump) {
            setMotion(0, 0);
        }

        if (jump && !isJumping) {
            double direction = 0.0;
            if (moveLeft) {
                direction = Direction.UP_LEFT.getValue();
            } else if (moveRight) {
                direction = Direction.UP_RIGHT.getValue();
            } else {
                direction = Direction.UP.getValue();
            }
            setMotion(JUMP_STRENGTH, direction);
            setGravityConstant(GRAVITY_CONSTANT);
            isJumping = true;
        }

        if (moveLeft && moveRight) {
            setSpeed(0);
        } else if (!isJumping && moveLeft) {
            setMotion(MOVE_SPEED, Direction.LEFT.getValue());
            setCurrentFrameIndex(0);
        } else if (!isJumping && moveRight) {
            setMotion(MOVE_SPEED, Direction.RIGHT.getValue());
            setCurrentFrameIndex(1);
        }

        if (moveDown) {
            setMotion(MOVE_SPEED, Direction.DOWN.getValue());
        }

        if (!moveLeft && !moveRight && !jump && !moveDown) {
            setSpeed(getSpeed() * 0.5);
        }

        if (pressedKeys.contains(KeyCode.ENTER)) {
            fixIt();
        }
    }

    private Window findNearestWindow()
    {
        List<Window> windows = Building.getInstance().getWindows();
        Window nearestWindow = null;
        double nearestDistance = Double.MAX_VALUE;

        for (Window window : windows) {
            double distance = getAnchorLocation().distance(window.getAnchorLocation());

            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestWindow = window;
            }
        }

        // Overweeg een drempelwaarde voor afstand waarbinnen een raam als 'dichtbij' wordt beschouwd.
        final double NEARBY_WINDOW_THRESHOLD = 50; // Deze waarde kun je aanpassen.
        if (nearestDistance <= NEARBY_WINDOW_THRESHOLD) {
            return nearestWindow;
        } else {
            return null;
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
                isJumping = false;
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

    public void setIsJumping(boolean isJumping)
    {
        this.isJumping = isJumping;
    }
}