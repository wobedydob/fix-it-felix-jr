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


// TODO: remove old movement logic
public class Player extends DynamicSpriteEntity implements KeyListener, SceneBorderTouchingWatcher, Newtonian, Collided, Collider
{

    public static final String SPRITE_IMAGE = "sprites/felix.png";
    public static final Size SIZE = new Size(75, 102);
    public static final int[] SPRITE_ROWS_COLS = {1, 2};
    public static final Position INITIAL_POSITION = Position.PLAYER_INITIAL_POSITION;

    private static final int MAX_HEALTH = 10;
    private static final double MOVE_SPEED = 7.5; // todo remove if unused
    private static final double JUMP_STRENGTH = 20; // todo remove if unused
    public static final double GRAVITY_CONSTANT = 0.5;

    private int health;
    private boolean isJumping = false;
    private KeyCode lastPressedKey;

    public Player()
    {
        super(SPRITE_IMAGE, INITIAL_POSITION.getCoordinate(), SIZE, SPRITE_ROWS_COLS[0], SPRITE_ROWS_COLS[1]);
        this.health = MAX_HEALTH;
        setGravityConstant(GRAVITY_CONSTANT);
    }

    public void fixIt()
    {
        WindowFrame windowToRepair = findNearestWindow();
        if (windowToRepair != null) {
            windowToRepair.getWindow().repair();
        }
    }

    public void destroy()
    {
        WindowFrame windowToRepair = findNearestWindow();
        if (windowToRepair != null) {
            windowToRepair.getWindow().damage();
        }
    }

    public void moveUp()
    {
        WindowFrame window = this.findNearestWindow(Direction.UP);
        if (window != null) {
            setAnchorLocation(new Coordinate2D(window.getAnchorLocation().getX(), window.getAnchorLocation().getY() + 50));
        }
        System.out.println("moved up");
    }

    public void moveDown()
    {
        WindowFrame window = this.findNearestWindow(Direction.DOWN);
        if (window != null) {
            setAnchorLocation(new Coordinate2D(window.getAnchorLocation().getX(), window.getAnchorLocation().getY() + 50));
        }
        System.out.println("moved down");
    }

    public void moveLeft()
    {
        WindowFrame window = this.findNearestWindow(Direction.LEFT);
        if (window != null) {
            setAnchorLocation(new Coordinate2D(window.getAnchorLocation().getX(), getAnchorLocation().getY()));
        }
        System.out.println("moved left");
    }

    public void moveRight()
    {
        WindowFrame window = this.findNearestWindow(Direction.RIGHT);
        if (window != null) {
            setAnchorLocation(new Coordinate2D(window.getAnchorLocation().getX(), getAnchorLocation().getY()));
        }
    }

    @Override
    public void onPressedKeysChange(Set<KeyCode> pressedKeys)
    {
        boolean left = pressedKeys.contains(KeyCode.LEFT);
        boolean right = pressedKeys.contains(KeyCode.RIGHT);
        boolean up = pressedKeys.contains(KeyCode.UP);
        boolean down = pressedKeys.contains(KeyCode.DOWN);

        // System.out.println("Player pressed keys: " + pressedKeys.stream().findFirst().orElse(null));
        if (this.lastPressedKey != pressedKeys.stream().findFirst().orElse(null)) {
            this.lastPressedKey = null;
        }

        if (up && this.lastPressedKey != KeyCode.UP) {
            this.lastPressedKey = KeyCode.UP;
            this.moveUp();
        } else if (down && lastPressedKey != KeyCode.DOWN) {
            lastPressedKey = KeyCode.DOWN;
            this.moveDown();
        } else if (left && lastPressedKey != KeyCode.LEFT) {
            lastPressedKey = KeyCode.LEFT;
            this.moveLeft();
        } else if (right && lastPressedKey != KeyCode.RIGHT) {
            lastPressedKey = KeyCode.RIGHT;
            this.moveRight();
        }
    }

    private WindowFrame findNearestWindow()
    {
        List<WindowFrame> windowFrames = Building.getInstance().getWindowFrames();
        WindowFrame nearestWindow = null;
        double nearestDistance = Double.MAX_VALUE;

        for (WindowFrame windowFrame : windowFrames) {
            double distance = getAnchorLocation().distance(windowFrame.getAnchorLocation());

            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestWindow = windowFrame;
            }
        }

        if (nearestDistance <= WindowFrame.NEARBY_WINDOW_THRESHOLD) {
            return nearestWindow;
        } else {
            return null;
        }
    }

    private WindowFrame findNearestWindow(Direction direction)
    {
        List<WindowFrame> windowFrames = Building.getInstance().getWindowFrames();
        WindowFrame nearestWindow = findNearestWindow();

        if (direction == Direction.UP && windowFrames.indexOf(nearestWindow) + Building.WINDOWS_PER_FLOOR < windowFrames.size()) {
            int index = windowFrames.indexOf(nearestWindow) + Building.WINDOWS_PER_FLOOR;
            System.out.println("UP INDEX: " + index);

            nearestWindow = windowFrames.get(index);
            return nearestWindow;

        } else if (direction == Direction.DOWN && windowFrames.indexOf(nearestWindow) - Building.WINDOWS_PER_FLOOR > 0) {
            int index = windowFrames.indexOf(nearestWindow) - Building.WINDOWS_PER_FLOOR;
            System.out.println("DOWN INDEX: " + index);

            nearestWindow = windowFrames.get(index);
            return nearestWindow;

        }

        else if (direction == Direction.LEFT) {
            int index = windowFrames.indexOf(nearestWindow);
            System.out.println("LEFT INDEX: " + index);

            // Check of we niet op de begane grond zijn OF het niet het eerste raam in de rij is
            if ((index >= Building.WINDOWS_PER_FLOOR || index % Building.WINDOWS_PER_FLOOR > 0) && nearestWindow != null) {
                nearestWindow = windowFrames.get(index - 1);
            } else {
                nearestWindow = null; // Geen raam links als het het eerste raam in de rij is of op de begane grond
            }

            return nearestWindow;
        } else if (direction == Direction.RIGHT) {
            int index = windowFrames.indexOf(nearestWindow);
            System.out.println("RIGHT INDEX: " + index);

            // Check of we niet op de begane grond zijn OF het niet het laatste raam in de rij is
            if ((index >= Building.WINDOWS_PER_FLOOR || index % Building.WINDOWS_PER_FLOOR < Building.WINDOWS_PER_FLOOR - 1) && nearestWindow != null) {
                nearestWindow = windowFrames.get(index + 1);
            } else {
                nearestWindow = null; // Geen raam rechts als het het laatste raam in de rij is of op de begane grond
            }

            return nearestWindow;
        }

//        else if (direction == Direction.LEFT) {
//            int index = windowFrames.indexOf(nearestWindow);
//            if (index % Building.WINDOWS_PER_FLOOR > 0) { // Checkt of het niet het eerste raam in de rij is
//                nearestWindow = windowFrames.get(index - 1);
//            } else {
//                nearestWindow = null; // Geen raam links als het het eerste raam in de rij is
//            }
//
//            return nearestWindow;
//        } else if (direction == Direction.RIGHT) {
//            int index = windowFrames.indexOf(nearestWindow);
//            if (index % Building.WINDOWS_PER_FLOOR < Building.WINDOWS_PER_FLOOR - 1) { // Checkt of het niet het laatste raam in de rij is
//                nearestWindow = windowFrames.get(index + 1);
//            } else {
//                nearestWindow = null; // Geen raam rechts als het het laatste raam in de rij is
//            }
//
//            return nearestWindow;
//        }

        else {
            System.out.println("NO WINDOW FOUND IN DIRECTION [" + direction + "]");
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