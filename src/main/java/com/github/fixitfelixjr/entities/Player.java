package com.github.fixitfelixjr.entities;

import com.github.fixitfelixjr.Game;
import com.github.fixitfelixjr.TimeEvent;
import com.github.fixitfelixjr.entities.powerups.HardhatPowerUp;
import com.github.fixitfelixjr.entities.powerups.LifePowerUp;
import com.github.fixitfelixjr.entities.powerups.PiePowerUp;
import com.github.fixitfelixjr.entities.powerups.PowerUp;
import com.github.fixitfelixjr.enums.KeyBindings;
import com.github.fixitfelixjr.enums.Position;
import com.github.fixitfelixjr.scenes.GameOverScene;
import com.github.fixitfelixjr.scenes.LevelScene;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.TimerContainer;
import com.github.hanyaeger.api.entities.*;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;
import com.github.hanyaeger.api.userinput.KeyListener;
import javafx.scene.input.KeyCode;

import java.util.List;
import java.util.Set;

/**
 * Represents the player character in the game, capable of moving in various directions, repairing windows,
 * and interacting with power-ups and other game elements. This class extends {@link DynamicSpriteEntity}
 * and implements various interfaces to handle game dynamics such as collision detection, keyboard input, and
 * applying gravity effects.
 */
public class Player extends DynamicSpriteEntity implements KeyListener, Newtonian, Collided, Collider, TimerContainer
{
    public static final String SPRITE_IMAGE = "sprites/felix.png";
    public static final double WIDTH = 128;
    public static final double HEIGHT = 102;
    public static final int[] SPRITE_ROWS_COLS = {3, 4};
    public static final Size SIZE = new Size(WIDTH * LevelScene.SPRITE_SIZE_APPLIER, HEIGHT * LevelScene.SPRITE_SIZE_APPLIER / SPRITE_ROWS_COLS[0]);
    public static final Position INITIAL_POSITION = Position.PLAYER_INITIAL_POSITION;
    public static final Direction INITIAL_FACING_DIR = Direction.LEFT;
    public static final double GRAVITY_CONSTANT = 0.5;
    public static final int MAX_HEALTH = 3;

    private int health;
    private KeyCode lastPressedKey;
    private Direction facing;
    private PowerUp powerUp = null;

    /**
     * Initializes a player with default settings, positioning, and health.
     */
    public Player()
    {
        super(SPRITE_IMAGE, INITIAL_POSITION.getCoordinate(), SIZE, SPRITE_ROWS_COLS[0], SPRITE_ROWS_COLS[1]);
        this.health = MAX_HEALTH;
        this.facing = INITIAL_FACING_DIR;
        setGravityConstant(GRAVITY_CONSTANT);
    }

    /**
     * Handles the player's repair action on the nearest window, applying special effects if a power-up is active.
     */
    public void repair()
    {
        WindowFrame windowToRepair = Game.getInstance().getLevelScene().getBuilding().findNearestWindow(getAnchorLocation());
        if (windowToRepair != null) {
            Window window = windowToRepair.getWindow();

            if (this.powerUp != null && this.powerUp instanceof PiePowerUp) {
                this.repairAnimation();
                window.repair(Window.MAX_REPAIR);
            } else {
                this.repairAnimation();
                window.repair();
            }

        }
    }

    /**
     * Animates the player during the repair action.
     */
    private void repairAnimation()
    {
        int initialFrameIndex = getCurrentFrameIndex();
        int newFrameIndex = initialFrameIndex;

        // calculate new frame index
        if (this.facing == Direction.LEFT) {
            newFrameIndex = 2;
        } else if (this.facing == Direction.RIGHT) {
            newFrameIndex = 3;
        }

        // check if player has power-up
        if (this.powerUp instanceof PiePowerUp) {
            newFrameIndex += SPRITE_ROWS_COLS[1];
        } else if (this.powerUp instanceof HardhatPowerUp) {
            newFrameIndex += SPRITE_ROWS_COLS[1] * 2;
        }

        setCurrentFrameIndex(newFrameIndex);

        TimeEvent event = new TimeEvent(50, () -> {
            setCurrentFrameIndex(initialFrameIndex);
        });
        addTimer(event);
    }

    /**
     * Moves the player in a specified direction.
     *
     * @param direction The direction in which the player should move.
     */
    public void move(Direction direction)
    {
        WindowFrame window = Game.getInstance().getLevelScene().getBuilding().findNearestWindow(getAnchorLocation(), direction);
        if (window != null) {

            double y = window.getAnchorLocation().getY();
            if (direction == Direction.UP || direction == Direction.DOWN) {
                y += 20;
            } else {
                y = getAnchorLocation().getY();
            }

            setAnchorLocation(new Coordinate2D(window.getAnchorLocation().getX(), y));
        }
    }

    /**
     * Moves the player to the left, updating the animation frame based on current power-ups.
     */
    public void moveLeft()
    {
        int frameIndex = 0;
        Direction direction = Direction.LEFT;
        this.move(direction);
        this.facing = direction;

        if (this.powerUp instanceof PiePowerUp) {
            setCurrentFrameIndex(frameIndex + SPRITE_ROWS_COLS[1]);
        } else if (this.powerUp instanceof HardhatPowerUp) {
            setCurrentFrameIndex(frameIndex + SPRITE_ROWS_COLS[1] * 2);
        } else {
            setCurrentFrameIndex(frameIndex);
        }
    }


    /**
     * Moves the player to the right, similarly to {@link #moveLeft()}, but in the opposite direction.
     */
    public void moveRight()
    {
        int frameIndex = 1;
        Direction direction = Direction.RIGHT;
        this.facing = direction;
        this.move(direction);

        if (this.powerUp instanceof PiePowerUp) {
            setCurrentFrameIndex(frameIndex + SPRITE_ROWS_COLS[1]);
        } else if (this.powerUp instanceof HardhatPowerUp) {
            setCurrentFrameIndex(frameIndex + SPRITE_ROWS_COLS[1] * 2);
        } else {
            setCurrentFrameIndex(frameIndex);
        }
    }

    /**
     * Activates the current power-up, if any, applying its effects.
     */
    public void activatePowerUp()
    {
        if (this.powerUp != null) {

            if(this.powerUp instanceof LifePowerUp) {
                this.activateLifePowerUp();
                return;
            }

            // calculate sprite frame index (based on power-ups)
            int addition = 0;
            int frameIndex = 0;
            if (this.facing == Direction.RIGHT) {
                frameIndex = 1;
            }

            if (this.powerUp instanceof PiePowerUp) {
                addition = SPRITE_ROWS_COLS[1];
            } else if (this.powerUp instanceof HardhatPowerUp) {
                addition = SPRITE_ROWS_COLS[1] * 2; // 2 rows
            }

            setCurrentFrameIndex(frameIndex + addition);

            int duration = this.powerUp.getDuration() * 1000; // convert to seconds
            TimeEvent event = new TimeEvent(duration, this::deactivatePowerUp);
            addTimer(event);
        }
    }

    /**
     * Specifically handles the activation of a life power-up, incrementing the player's health.
     */
    public void activateLifePowerUp()
    {
        if (this.health <= MAX_HEALTH) {
            this.health++;
            Game.getInstance().getLevelScene().updateLives(this.health);
        }
    }

    /**
     * Deactivates any active power-up, removing its effects.
     */
    public void deactivatePowerUp()
    {
        if (this.powerUp != null) {
            this.powerUp = null;
        }
    }

    /**
     * Handles the player's death by checking health, updating the game state, and potentially setting the game over scene.
     */
    public void die()
    {
        // check if player is dead
        if (this.health <= 0) {
            remove();
            Game.getInstance().setActiveScene(GameOverScene.SCENE_ID);
            return;
        }

        this.health--;
        Game.getInstance().getLevelScene().updateLives(this.health);
        setAnchorLocation(Position.PLAYER_INITIAL_POSITION.getCoordinate());
    }


    /**
     * Processes changes in pressed keys, triggering appropriate actions such as movement or repairs.
     *
     * @param pressedKeys A set of currently pressed keys.
     */
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
        } else if (down) {
            this.move(Direction.DOWN);
        } else if (left) {
            this.moveLeft();
        } else if (right) {
            this.moveRight();
        } else if (repair) {
            this.repair();
        }

        this.lastPressedKey = pressedKey;
    }

    /**
     * Handles collision events with various game entities like power-ups and projectiles.
     *
     * @param collidingObjects A list of objects that the player has collided with.
     */
    @Override
    public void onCollision(List<Collider> collidingObjects)
    {
        // only one collision at a time
        if (collidingObjects.size() > 1) {
            return;
        }

        Collider collidingObject = collidingObjects.stream().findFirst().orElse(null);
        if (collidingObject instanceof PowerUp powerUp) {
            this.onPowerUpCollision(powerUp);
        } else if (collidingObject instanceof Projectile projectile) {
            this.onProjectileCollision(projectile);
        } else if (collidingObject instanceof Enemy) {
            this.onEnemyCollision();
        }
    }

    /**
     * Handles collision with a power-up object. Activates the power-up, logs it,
     * clears all power-ups from the building, updates the score, and removes the power-up object.
     *
     * @param powerUp The power-up the player has collided with.
     */
    public void onPowerUpCollision(PowerUp powerUp)
    {
        this.powerUp = powerUp;
        this.activatePowerUp();
        Game.getInstance().getLevelScene().getBuilding().clearPowerUps();
        Game.getInstance().getScoreBoard().addScore(PowerUp.SCORE_POINTS);
        this.powerUp.remove();
    }

    /**
     * Handles collision with a projectile. If the player has a HardhatPowerUp,
     * the projectile is removed without further effects. Otherwise, the player dies.
     *
     * @param projectile The projectile the player has collided with.
     */
    public void onProjectileCollision(Projectile projectile)
    {
        if (this.powerUp instanceof HardhatPowerUp) {
            projectile.remove();
            return;
        }

        projectile.remove();
        this.die();
    }

    /**
     * Handles collision with an enemy. Results in the player's death.
     */
    public void onEnemyCollision()
    {
        this.die();
    }

    /**
     * Initializes any necessary timers for the player.
     * The method is currently not implemented and contains a placeholder comment.
     */
    @Override
    public void setupTimers()
    {

    }

    /**
     * Returns the player's current health.
     *
     * @return The current health of the player.
     */
    public int getHealth()
    {
        return this.health;
    }

    /**
     * Returns the last key pressed by the player. This can be used to determine the last action taken.
     *
     * @return The last {@link KeyCode} pressed.
     */
    public KeyCode getLastPressedKey()
    {
        return this.lastPressedKey;
    }

    /**
     * Returns the current active power-up affecting the player, if any.
     *
     * @return The current active {@link PowerUp}, or null if no power-up is active.
     */
    public PowerUp getPowerUp()
    {
        return this.powerUp;
    }

    /**
     * Sets the player's health to a specified value. This method can be used to update the player's health
     * during gameplay, such as when healing or receiving damage.
     *
     * @param health The new health value for the player.
     */
    public void setHealth(int health)
    {
        this.health = health;
    }

    /**
     * Sets the last key pressed by the player. This can be used in conjunction with game mechanics
     * that require tracking the sequence of keys pressed.
     *
     * @param lastPressedKey The last {@link KeyCode} that was pressed.
     */
    public void setLastPressedKey(KeyCode lastPressedKey)
    {
        this.lastPressedKey = lastPressedKey;
    }

    /**
     * Sets the current power-up affecting the player. This method allows for the dynamic application
     * and removal of power-ups during gameplay.
     *
     * @param powerUp The {@link PowerUp} to set as currently active.
     */
    public void setPowerUp(PowerUp powerUp)
    {
        this.powerUp = powerUp;
    }
}
