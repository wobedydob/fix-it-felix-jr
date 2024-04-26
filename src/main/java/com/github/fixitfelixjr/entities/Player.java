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
    private static final double MOVE_SPEED = 7.5;
    private static final double JUMP_STRENGTH = 20;
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

    @Override
    public void onPressedKeysChange(Set<KeyCode> pressedKeys)
    {
        boolean moveLeft = pressedKeys.contains(KeyCode.LEFT) || pressedKeys.contains(KeyCode.A);
        boolean moveRight = pressedKeys.contains(KeyCode.RIGHT) || pressedKeys.contains(KeyCode.D);
        boolean moveDown = pressedKeys.contains(KeyCode.DOWN) || pressedKeys.contains(KeyCode.S);
        boolean jump = pressedKeys.contains(KeyCode.UP) || pressedKeys.contains(KeyCode.W) || pressedKeys.contains(KeyCode.SPACE);
        boolean isOnWindow = findNearestWindow() != null;

        System.out.println("Player pressed keys: " + pressedKeys.stream().findFirst().orElse(null));
        if (lastPressedKey != pressedKeys.stream().findFirst().orElse(null)) {
            lastPressedKey = null;
        }

        if(jump && lastPressedKey != KeyCode.UP) {
            lastPressedKey = KeyCode.UP;
            WindowFrame window = findNearestWindow(Direction.UP);

            if(window != null) {
                setAnchorLocation(new Coordinate2D(window.getAnchorLocation().getX(), window.getAnchorLocation().getY() + 50));
            }
        }
        else if(moveDown && lastPressedKey != KeyCode.DOWN){
            lastPressedKey = KeyCode.DOWN;
            WindowFrame window = findNearestWindow(Direction.DOWN);
            if(window != null) {
                setAnchorLocation(new Coordinate2D(window.getAnchorLocation().getX(), window.getAnchorLocation().getY() + 50));
            }
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

        System.out.println("size" + windowFrames.size());


        if (direction == Direction.UP && windowFrames.indexOf(nearestWindow) + Building.WINDOWS_PER_FLOOR < windowFrames.size()) {
            int index = windowFrames.indexOf(nearestWindow) + Building.WINDOWS_PER_FLOOR;
            nearestWindow = windowFrames.get(index);
            return nearestWindow;

        }
        else if(direction == Direction.DOWN && windowFrames.indexOf(nearestWindow) - Building.WINDOWS_PER_FLOOR > 0) {
            int index = windowFrames.indexOf(nearestWindow) - Building.WINDOWS_PER_FLOOR;
            nearestWindow = windowFrames.get(index);
            return nearestWindow;
        }else {
            System.out.println("NULL?!");
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