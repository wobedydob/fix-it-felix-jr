package com.github.fixitfelixjr.entities;

import com.github.fixitfelixjr.enums.KeyBindings;
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
    public static final double GRAVITY_CONSTANT = 0.5;
    public static final int MAX_HEALTH = 10;

    private int health;
    private KeyCode lastPressedKey;

    public Player()
    {
        super(SPRITE_IMAGE, INITIAL_POSITION.getCoordinate(), SIZE, SPRITE_ROWS_COLS[0], SPRITE_ROWS_COLS[1]);
        this.health = MAX_HEALTH;
        setGravityConstant(GRAVITY_CONSTANT);
    }

    public void repair()
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

    public void move(Direction direction)
    {
        WindowFrame window = this.findNearestWindow(direction);
        if (window != null) {

            double y = window.getAnchorLocation().getY();
            if(direction == Direction.UP || direction == Direction.DOWN) {
                y += 50;
            } else {
                y = getAnchorLocation().getY();
            }

            setAnchorLocation(new Coordinate2D(window.getAnchorLocation().getX(), y));
        }
    }

    // TODO: something about diagonal movement, which is currently possible (at high speed)
    @Override
    public void onPressedKeysChange(Set<KeyCode> pressedKeys)
    {
        // only one key can be pressed at a time
        if (pressedKeys.size() > 1) {
            return;
        }

        KeyCode pressedKey = pressedKeys.stream().findFirst().orElse(null);
        boolean up = pressedKey == KeyBindings.UP.getKeyCode() && this.lastPressedKey != KeyBindings.UP.getKeyCode();
        boolean down = pressedKey == KeyBindings.DOWN.getKeyCode() && this.lastPressedKey != KeyBindings.DOWN.getKeyCode();
        boolean left = pressedKey == KeyBindings.LEFT.getKeyCode() && this.lastPressedKey != KeyBindings.LEFT.getKeyCode();
        boolean right = pressedKey == KeyBindings.RIGHT.getKeyCode() && this.lastPressedKey != KeyBindings.RIGHT.getKeyCode();
        boolean repair = pressedKey == KeyBindings.REPAIR.getKeyCode() && this.lastPressedKey != KeyBindings.REPAIR.getKeyCode();

        if (this.lastPressedKey != pressedKey) {
            this.lastPressedKey = null;
        }

        if (up) {
            this.move(Direction.UP);
        }
        else if (down) {
            this.move(Direction.DOWN);
        }
        else if (left) {
            this.move(Direction.LEFT);
        }
        else if (right) {
            this.move(Direction.RIGHT);
        }
        else if (repair) {
            this.repair();
        }

        this.lastPressedKey = pressedKey;
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
        Building building = Building.getInstance();
        List<WindowFrame> windowFrames = building.getWindowFrames();
        WindowFrame nearestWindow = findNearestWindow();
        int index = windowFrames.indexOf(nearestWindow);

        if (direction == Direction.UP && index + Building.WINDOWS_PER_FLOOR < windowFrames.size()) {
            if (building.onGroundFloor(index) && index < 2) {
                index--;
            }
            index += Building.WINDOWS_PER_FLOOR;
            nearestWindow = windowFrames.get(index);
            return nearestWindow;
        }
        // TODO: fix magic numbers 3 (higher than ground floor indexes) and 6 (not the middle above door window)
        else if (direction == Direction.DOWN && index > 3 && index != 6) {
            index -= Building.WINDOWS_PER_FLOOR;
            if (building.onGroundFloor(index) && index < 2) {
                index++;
            }
            nearestWindow = windowFrames.get(index);
            return nearestWindow;
        }
        // TODO: fix magic numbers 0-14 (leftmost windows on each floor)
        else if (direction == Direction.LEFT && index != 0 && index != 4 && index != 9 && index != 14) {
            index--;
            if (!building.onBuildingEdge(index)) {
                nearestWindow = windowFrames.get(index);
            }
            return nearestWindow;
        }
        // TODO: fix magic numbers 3-18 (rightmost windows on each floor)
        else if (direction == Direction.RIGHT && index != 3 && index != 8 && index != 13 && index != 18) {
            index++;
            if (!building.onBuildingEdge(index)) {
                nearestWindow = windowFrames.get(index);
            }
            return nearestWindow;
        } else {
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

    public int getHealth()
    {
        return health;
    }

    public KeyCode getLastPressedKey()
    {
        return lastPressedKey;
    }

    public void setHealth(int health)
    {
        this.health = health;
    }

    public void setLastPressedKey(KeyCode lastPressedKey)
    {
        this.lastPressedKey = lastPressedKey;
    }

}