package com.github.fixitfelixjr.entities;

import com.github.fixitfelixjr.Game;
import com.github.fixitfelixjr.scenes.LevelScene;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;

/**
 * Represents a window in the game that can be repaired. This class extends {@link DynamicSpriteEntity}
 * to handle visual representation and interaction of a window within the game. The window has multiple
 * repair states, each represented by a different frame in the sprite sheet.
 *
 * This class manages the repair state of the window, supports incremental repairs, and handles scoring
 * when a window is repaired fully or partially. It interacts with the game's scoring system and other game
 * logic through {@link Game} and {@link LevelScene}.
 */
public class Window extends DynamicSpriteEntity
{
    public static final String SPRITE_IMAGE = "sprites/window.png";
    public static final double WIDTH = 120;
    public static final double HEIGHT = 43;
    public static final Size SIZE = new Size(WIDTH * LevelScene.SPRITE_SIZE_APPLIER, HEIGHT * LevelScene.SPRITE_SIZE_APPLIER);
    public final static int[] SPRITE_ROWS_COLS = {1, 5};
    public static final int MAX_REPAIR = 4;
    public static final int MIN_REPAIR = 0;
    public static final int SCORE_POINTS = 10;
    public static final int SCORE_POINTS_FULL = 100;

    private int repairState;
    private Coordinate2D position;

    /**
     * Constructs a new window with a specified position.
     *
     * @param position the initial position of the window within the game scene.
     */
    public Window(Coordinate2D position)
    {
        super(SPRITE_IMAGE, position, SIZE, SPRITE_ROWS_COLS[0], SPRITE_ROWS_COLS[1]);
        this.position = position;
        this.repairState = MIN_REPAIR;
    }

    /**
     * Repairs the window incrementally by one step until the window is fully repaired.
     */
    public void repair()
    {
        if (this.repairState < MAX_REPAIR) {
            boolean fullRepair = repairState == MAX_REPAIR - 1;
            this.repairState++;
            this.update(fullRepair);
        }
    }

    /**
     * Sets the window's repair state to a specific value and updates the game accordingly.
     *
     * @param repairState the target repair state to set.
     */
    public void repair(int repairState)
    {
        if (repairState <= MAX_REPAIR) {
            boolean fullRepair = repairState == MAX_REPAIR - 1;
            this.repairState = repairState;
            this.update(fullRepair);
        }
    }

    /**
     * Updates the visual state of the window and the game's score based on the repair progress.
     *
     * @param fullRepair true if the window has been fully repaired; otherwise false.
     */
    private void update(boolean fullRepair)
    {
        setCurrentFrameIndex(repairState);
        int score = fullRepair ? SCORE_POINTS_FULL : SCORE_POINTS;
        Game.getInstance().getScoreBoard().updateScore(score);
        Game.getInstance().getLevelScene().onWindowRepaired();
    }

    /**
     * Checks if the window has been fully repaired.
     *
     * @return true if fully repaired, otherwise false.
     */
    public boolean isRepaired()
    {
        return this.repairState == MAX_REPAIR;
    }

    /**
     * Gets the current repair state of the window.
     *
     * @return the current repair state.
     */
    public int getRepairState()
    {
        return this.repairState;
    }

    /**
     * Gets the position of the window.
     *
     * @return the position of the window.
     */
    public Coordinate2D getPosition()
    {
        return this.position;
    }

    /**
     * Sets the repair state of the window.
     *
     * @param repairState the new repair state to set.
     */
    public void setRepairState(int repairState)
    {
        this.repairState = repairState;
    }

    /**
     * Sets the position of the window.
     *
     * @param position the new position to set.
     */
    public void setPosition(Coordinate2D position)
    {
        this.position = position;
    }
}
