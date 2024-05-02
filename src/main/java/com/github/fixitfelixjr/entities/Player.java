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

    public Player()
    {
        super(SPRITE_IMAGE, INITIAL_POSITION.getCoordinate(), SIZE, SPRITE_ROWS_COLS[0], SPRITE_ROWS_COLS[1]);
        this.health = MAX_HEALTH;
        this.facing = INITIAL_FACING_DIR;
        setGravityConstant(GRAVITY_CONSTANT);
    }

    public void repair()
    {
        System.out.println(" ");
        System.out.println("-----------------------------");
        System.out.println("trying to repair");

        WindowFrame windowToRepair = Game.getInstance().getLevelScene().getBuilding().findNearestWindow(getAnchorLocation());
        if (windowToRepair != null) {
            Window window = windowToRepair.getWindow();

            if (powerUp != null && powerUp instanceof PiePowerUp) {
                System.out.println("player has powerup");
                this.repairAnimation();
                window.repair(Window.MAX_REPAIR);
            } else {
                System.out.println("default repair");
                this.repairAnimation();
                window.repair();
            }

        }

        System.out.println("-----------------------------");
        System.out.println(" ");

    }

    private void repairAnimation()
    {
        System.out.println("player is facing: " + this.facing);

        int initialFrameIndex = getCurrentFrameIndex();
        int newFrameIndex = initialFrameIndex;
        System.out.println("initial frame index: " + initialFrameIndex);

        if (this.facing == Direction.LEFT) {
            newFrameIndex = 2;
        } else if (this.facing == Direction.RIGHT) {
            newFrameIndex = 3;
        }

        if (this.powerUp instanceof PiePowerUp) {
            System.out.println("repairing with powerup");
            newFrameIndex += SPRITE_ROWS_COLS[1];
        } else if (this.powerUp instanceof HardhatPowerUp) {
            System.out.println("repairing with hardhat");
            newFrameIndex += SPRITE_ROWS_COLS[1] * 2;
        }

        System.out.println("new frame index: " + newFrameIndex);

        setCurrentFrameIndex(newFrameIndex);

        TimeEvent event = new TimeEvent(50, () -> {
            setCurrentFrameIndex(initialFrameIndex);
        });
        addTimer(event);

    }

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

    public void activatePowerUp()
    {
        if (this.powerUp != null) {
            System.out.println("activated powerup");
            System.out.println("while facing: " + this.facing);

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
            TimeEvent event = new TimeEvent(duration, () -> {
                this.deactivatePowerUp();
            });
            addTimer(event);
        }
    }

    public void deactivatePowerUp()
    {
        if (this.powerUp != null) {
            System.out.println("deactivated powerup");
            this.powerUp = null;
        }
    }

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

    public void onPowerUpCollision(PowerUp powerUp)
    {
        if(powerUp instanceof LifePowerUp) {

            this.health++;
            Game.getInstance().getLevelScene().updateLives(this.health);
            return;
        }

        this.powerUp = powerUp;
        this.activatePowerUp();
        this.powerUp.remove();
        Game.getInstance().getLevelScene().getBuilding().clearPowerUps();
        Game.getInstance().getScoreBoard().addScore(PowerUp.SCORE_POINTS);
    }

    public void onProjectileCollision(Projectile projectile)
    {
        if (this.powerUp instanceof HardhatPowerUp) {
            projectile.remove();
            return;
        }

        projectile.remove();
        this.die();
    }

    public void onEnemyCollision()
    {
        this.die();
    }

    @Override
    public void setupTimers()
    {
        // ... fuck whoever thought this was a good idea ...
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

    public PowerUp getPowerUp()
    {
        return powerUp;
    }

    public void setPowerUp(PowerUp powerUp)
    {
        this.powerUp = powerUp;
    }

}