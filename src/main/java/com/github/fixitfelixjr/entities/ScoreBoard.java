package com.github.fixitfelixjr.entities;

import com.github.fixitfelixjr.enums.Fonts;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ScoreBoard extends TextEntity
{
    public static final int INITIAL_SCORE = 0;
    public static final String SCORE_TEXT = "SCORE: ";

    private int score;

    public ScoreBoard(Coordinate2D position, int score)
    {
        super(position);
        this.score = score;
        this.scoreText();
    }

    public void scoreText()
    {
        Font font = Fonts.UPHEAVTT.getFont(20);
        setFill(Color.WHITE);
        setFont(font);
        setText(SCORE_TEXT + this.score);
    }

    public void updateScore(int score)
    {
        this.addScore(score);
        setText(SCORE_TEXT + this.score);
    }

    public int getScore()
    {
        return this.score;
    }

    public void addScore(int score)
    {
        this.score += score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }
}
