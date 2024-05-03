package com.github.fixitfelixjr.entities;

import com.github.fixitfelixjr.Game;
import com.github.fixitfelixjr.TimeEvent;
import com.github.fixitfelixjr.entities.powerups.PowerUp;
import com.github.fixitfelixjr.enums.Position;
import com.github.fixitfelixjr.scenes.LevelScene;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.TimerContainer;
import com.github.hanyaeger.api.entities.*;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;

import java.util.List;
import java.util.Random;

/**
 * Represents an enemy character in the game that can move across predefined positions and launch projectiles at intervals.
 * This class extends {@link DynamicSpriteEntity} and implements {@link TimerContainer} for scheduling movements and attacks,
 * and {@link Collider} to interact with other entities.
 */
public class Enemy extends DynamicSpriteEntity implements TimerContainer, Collider, Collided
{
    public static final String SPRITE_IMAGE = "sprites/ralph.png";
    public static final double WIDTH = 183;
    public static final double HEIGHT = 56;
    public static final Size SIZE = new Size(WIDTH * LevelScene.SPRITE_SIZE_APPLIER, HEIGHT * LevelScene.SPRITE_SIZE_APPLIER);
    public static final int[] SPRITE_ROWS_COLS = {1, 3};
    public static final Position INITIAL_POSITION = Position.ENEMY_INITIAL_POSITION;
    public static final int MOVE_RATE = 5; // in seconds
    public static final int MOVE_OPTIONS_COUNT = 5; // 0 = left, 1 = center left, 2 = center, 3 = center right , 4 = right (5 options)
    public static final int DESTROY_RATE = 3; // in seconds
    public static final int BRICK_COUNT = 2; // counts +1 when randomized

    private boolean isMoving = false;
    private boolean isWrecking = false;

    /**
     * Constructs an enemy at a predefined initial position.
     */
    public Enemy()
    {
        super(SPRITE_IMAGE, INITIAL_POSITION.getCoordinate(), SIZE, SPRITE_ROWS_COLS[0], SPRITE_ROWS_COLS[1]);
    }

    /**
     * Simulates the enemy attacking by throwing projectiles. The number and trajectory of projectiles is randomized.
     */
    public void destroy()
    {
        this.isWrecking = true;
        this.destroyAnimation();

        // add difficulty for stages
        int stage = Game.getInstance().getLevelScene().getLevelStage();

        // determine how many bricks are thrown
        int random = new Random().nextInt(BRICK_COUNT) + stage; // between 1 and 3

        for (int i = 0; i <= random; i++) {
            double[] allowedGravityConstant = {0.13, 0.24, 0.35, 0.46};
            double gravityConstant = allowedGravityConstant[new Random().nextInt(allowedGravityConstant.length)];

            Coordinate2D position = new Coordinate2D(getAnchorLocation().getX() + (WIDTH / 2), getAnchorLocation().getY() + HEIGHT * 3);
            Projectile projectile = new Projectile(position, gravityConstant);
            Game.getInstance().getLevelScene().addEntity(projectile);
        }

        this.isWrecking = false;
    }


    /**
     * Manages the animation during the attack sequence by cycling through sprite frames.
     */
    private void destroyAnimation()
    {
        int initialFrameIndex = getCurrentFrameIndex();

        TimeEvent goToFrame1 = new TimeEvent(50, () -> {
            setCurrentFrameIndex(1);
        });
        addTimer(goToFrame1);

        TimeEvent goToFrame2 = new TimeEvent(200, () -> {
            setCurrentFrameIndex(2);
        });
        addTimer(goToFrame2);

        TimeEvent resetFrame = new TimeEvent(350, () -> {
            setCurrentFrameIndex(initialFrameIndex);
        });
        addTimer(resetFrame);
    }

    /**
     * Moves the enemy to a specified position.
     *
     * @param position The position to which the enemy should move.
     */
    public void move(Position position)
    {
        this.isMoving = true;
        setAnchorLocation(position.getCoordinate());
        this.isMoving = false;
    }

    /**
     * Randomizes the enemy's movement based on predefined positions.
     */
    public void randomizeMove()
    {
        if (this.isMoving || this.isWrecking) {
            return;
        }

        int random = new Random().nextInt(MOVE_OPTIONS_COUNT);
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

    }

    /**
     * Sets up timers for the object to trigger specific events at regular intervals.
     * Timers are added for moving and destroying the object.
     * <p>
     * The move timer triggers the {@link #randomizeMove()} method periodically,
     * based on the MOVE_RATE constant defined for the object.
     * <p>
     * The destroy timer triggers the {@link #destroy()} method periodically,
     * based on the DESTROY_RATE constant defined for the object.
     */
    @Override
    public void setupTimers()
    {
        // every level decrease move rate by 250 milliseconds unless the move rate is less than 500 milliseconds
        int level = Game.getInstance().getLevelScene().getLevel();
        int moveRate = (MOVE_RATE * 1000) - (level * 250);
        if (moveRate < 500) {
            moveRate = 500;
        }
        TimeEvent moveEvent = new TimeEvent(moveRate, this::randomizeMove, true);
        addTimer(moveEvent);

        // every level decrease destroy rate by 250 milliseconds unless the destroy rate is less than 500 milliseconds
        int destroyRate = (DESTROY_RATE * 1000) - (level * 250);
        if (destroyRate < 500) {
            destroyRate = 500;
        }
        TimeEvent destroyEvent = new TimeEvent(destroyRate, this::destroy, true);
        addTimer(destroyEvent);
    }

    @Override
    public void onCollision(List<Collider> collidingObjects)
    {
        if (collidingObjects.size() > 1) {
            return;
        }

        Collider collidingObject = collidingObjects.stream().findFirst().orElse(null);
        if (collidingObject instanceof NPC npc) {
            npc.remove();
            Game.getInstance().getLevelScene().getBuilding().clearNPCs();
        } else if (collidingObject instanceof PowerUp powerUp) {
            powerUp.remove();
            Game.getInstance().getLevelScene().getBuilding().clearPowerUps();
        }
    }
}