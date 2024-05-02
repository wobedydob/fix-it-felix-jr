package com.github.fixitfelixjr.entities;

import com.github.fixitfelixjr.enums.Fonts;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * The {@code ScoreBoard} class extends {@link TextEntity} to display and manage the game's score. It handles
 * updating and rendering the score text dynamically on the game screen. The scoreboard displays the current score
 * using a predefined font and format, allowing easy visibility and readability during gameplay.
 */
public class ScoreBoard extends TextEntity
{
    public static final int INITIAL_SCORE = 0;
    public static final String SCORE_TEXT = "SCORE: ";

    private int score;

    /**
     * Constructs a new ScoreBoard at the specified position with an initial score.
     *
     * @param position the position on the screen where the scoreboard will be displayed.
     * @param score the initial score to display on the scoreboard.
     */
    public ScoreBoard(Coordinate2D position, int score)
    {
        super(position);
        this.score = score;
        this.scoreText();
    }

    /**
     * Initializes the scoreboard's text properties including the font and color.
     * This method is typically called internally to setup the scoreboard's visual appearance.
     */
    public void scoreText()
    {
        Font font = Fonts.UPHEAVTT.getFont(20);
        setFill(Color.WHITE);
        setFont(font);
        setText(SCORE_TEXT + this.score);
    }

    /**
     * Updates the score by adding the specified value to the current score and updates the display.
     *
     * @param score the score to add to the current score.
     */
    public void updateScore(int score)
    {
        this.addScore(score);
        setText(SCORE_TEXT + this.score);
    }

    /**
     * Adds the specified value to the current score. This method updates the internal score
     * but does not update the displayed text.
     *
     * @param score the amount to add to the score.
     */
    public void addScore(int score)
    {
        this.score += score;
    }

    /**
     * Returns the current score.
     *
     * @return the current score.
     */
    public int getScore()
    {
        return this.score;
    }

    /**
     * Sets the score to a specific value and updates the display.
     *
     * @param score the new score value to set.
     */
    public void setScore(int score)
    {
        this.score = score;
        setText(SCORE_TEXT + this.score);
    }
}
