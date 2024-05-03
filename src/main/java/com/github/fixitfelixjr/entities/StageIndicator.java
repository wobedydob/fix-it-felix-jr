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
public class StageIndicator extends TextEntity {

    /** The initial stage number. */
    public static final int INITIAL_STAGE = Game.INITIAL_STAGE;

    /** The text prefix for the stage indicator. */
    public static final String STAGE_TEXT = "STAGE: ";

    /** The current stage number. */
    private int stage;

    /**
     * Constructs a new StageIndicator object with the specified position.
     *
     * @param position the position of the StageIndicator on the screen
     */
    public StageIndicator(Coordinate2D position) {
        super(position);
        this.stage = INITIAL_STAGE;
        updateStageText();
    }

    /**
     * Sets the text style and content for the stage indicator.
     */
    private void updateStageText() {
        Font font = Fonts.UPHEAVTT.getFont(20);
        setFill(Color.WHITE);
        setFont(font);
        setText(STAGE_TEXT + this.stage);
    }

    /**
     * Updates the stage number based on the current score and updates the display.
     *
     * @param score the current score in the game
     */
    public void updateStage(int score) {
        addStage(score);
        updateStageText();
    }

    /**
     * Adds the specified number of stages to the current stage number.
     *
     * @param stage the number of stages to add
     */
    private void addStage(int stage) {
        this.stage += stage;
    }

    /**
     * Gets the current stage number.
     *
     * @return the current stage number
     */
    public int getStage() {
        return this.stage;
    }

    /**
     * Sets the current stage number and updates the display.
     *
     * @param stage the new stage number to set
     */
    public void setStage(int stage) {
        this.stage = stage;
        updateStageText();
    }
}
