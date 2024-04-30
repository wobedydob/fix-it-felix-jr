package com.github.fixitfelixjr.entities;

import com.github.fixitfelixjr.TimeEvent;
import com.github.fixitfelixjr.entities.powerups.HardhatPowerUp;
import com.github.fixitfelixjr.entities.powerups.PiePowerUp;
import com.github.fixitfelixjr.enums.Position;
import com.github.fixitfelixjr.scenes.LevelScene;
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
    public static final double WIDTH = 180;
    public static final double HEIGHT = 195;
    public static final Size SIZE = new Size(WIDTH, HEIGHT);
    public static final int[] SPRITE_ROWS_COLS = {1, 1};
    public static final Position INITIAL_POSITION = Position.ENEMY_INITIAL_POSITION;
    public static final int MOVE_RATE = 2; // in seconds
    public static final int DESTROY_RATE = 2; // in seconds

    private LevelScene scene;

    private boolean isMoving = false;
    private boolean isWrecking = false;

    public Enemy(LevelScene levelScene)
    {
        super(SPRITE_IMAGE, INITIAL_POSITION.getCoordinate(), SIZE, SPRITE_ROWS_COLS[0], SPRITE_ROWS_COLS[1]);
        this.scene = levelScene;
    }

    public void destroy()
    {
        this.isWrecking = true;

        // determine how many bricks are thrown
        int random = new Random().nextInt(5) + 3; // between 3 and 7

        for (int i = 0; i <= random; i++) {

            // randomize offset from enemy position x
            int offsetX = new Random().nextInt(100) - 50; // between -50 and 50
            int offsetY = new Random().nextInt(100) - 50; // between -50 and 50

            double[] allowedGravityConstant = {0.13, 0.24, 0.35, 0.46};
            double gravityConstant = allowedGravityConstant[new Random().nextInt(allowedGravityConstant.length)];

            Coordinate2D position = new Coordinate2D(getAnchorLocation().getX() + offsetX, getAnchorLocation().getY() + HEIGHT + offsetY);
            Projectile projectile = new Projectile(position, gravityConstant);
            this.scene.addEntity(projectile);
        }
        this.isWrecking = false;
    }

    public void move(Position position)
    {
        this.isMoving = true;
        System.out.println("moving to: " + position);
        setAnchorLocation(position.getCoordinate());
        this.isMoving = false;
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

        TimeEvent destroyEvent = new TimeEvent(DESTROY_RATE * 1000, () -> this.destroy(), true);
        addTimer(destroyEvent);
    }

    // TODO: improve movement with animation
    public void randomizeMove()
    {
        System.out.println(" ");
        System.out.println("-----------------------------");

        if (this.isMoving || this.isWrecking) {
            System.out.println("wrecking, can't move");
            return;
        }

        System.out.println("trying to randomize move");
        int random = new Random().nextInt(6); // 0 = left, 1 = center left, 2 = center, 3 = center right , 4 = right
        switch (random) {
            case 0:
                move(Position.ENEMY_LEFT_POSITION);
                break;
            case 1:
                move(Position.ENEMY_CENTER_LEFT_POSITION);
                break;
            case 2:
                move(Position.ENEMY_INITIAL_POSITION);
                break;
            case 3:
                move(Position.ENEMY_CENTER_RIGHT_POSITION);
                break;
            case 4:
                move(Position.ENEMY_RIGHT_POSITION);
                break;
        }

        System.out.println("-----------------------------");
        System.out.println(" ");
    }

}