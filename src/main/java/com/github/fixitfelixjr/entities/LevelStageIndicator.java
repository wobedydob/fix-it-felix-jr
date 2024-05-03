package com.github.fixitfelixjr.entities;

import com.github.fixitfelixjr.Game;
import com.github.fixitfelixjr.enums.Fonts;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * The StageIndicator class represents a visual indicator for the stage number in the game.
 * It extends TextEntity to display text on the screen.
 */
public class LevelStageIndicator extends TextEntity
{
    public static final int INITIAL_LEVEL = Game.INITIAL_LEVEL;
    public static final int INITIAL_STAGE = Game.INITIAL_STAGE;
    public static final String LEVEL_TEXT = "LEVEL: ";
    public static final String STAGE_TEXT = "STAGE: ";

    private int level;
    private int stage;

    /**
     * Constructs a new StageIndicator object with the specified position.
     *
     * @param position the position of the StageIndicator on the screen
     */
    public LevelStageIndicator(Coordinate2D position)
    {
        super(position);
        this.level = INITIAL_LEVEL;
        this.stage = INITIAL_STAGE;
        updateText();
    }

    /**
     * Sets the text style and content for the stage indicator.
     */
    private void updateText()
    {
        Font font = Fonts.UPHEAVTT.getFont(20);
        setFill(Color.WHITE);
        setFont(font);
        setText(LEVEL_TEXT + this.level + "\n" + STAGE_TEXT + this.stage);
    }

    /**
     * Updates the level number based on the current level and updates the display.
     *
     * @param level the current level in the game
     */
    public void updateLevel(int level)
    {
        addLevel(level);
        updateText();
    }

    /**
     * Adds the specified number of levels to the current level number.
     *
     * @param level the number of levels to add
     */
    private void addLevel(int level)
    {
        this.level += level;
    }

    /**
     * Updates the stage number based on the current stage and updates the display.
     *
     * @param stage the current stage in the game
     */
    public void updateStage(int stage)
    {
        addStage(stage);
        updateText();
    }

    /**
     * Adds the specified number of stages to the current stage number.
     *
     * @param stage the number of stages to add
     */
    private void addStage(int stage)
    {
        this.stage += stage;
    }

    /**
     * Gets the current level number.
     *
     * @return the current level number
     */
    public int getLevel()
    {
        return this.level;
    }
    
    /**
     * Gets the current stage number.
     *
     * @return the current stage number
     */
    public int getStage()
    {
        return this.stage;
    }

    /**
     * Sets the current level number and updates the display.
     *
     * @param level the new level number to set
     */
    public void setLevel(int level)
    {
        this.level = level;
        updateText();
    }
    
    /**
     * Sets the current stage number and updates the display.
     *
     * @param stage the new stage number to set
     */
    public void setStage(int stage)
    {
        this.stage = stage;
        updateText();
    }
}
