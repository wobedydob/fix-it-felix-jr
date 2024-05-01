package com.github.fixitfelixjr.entities;

import com.github.fixitfelixjr.enums.Fonts;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ScoreBoard extends TextEntity
{
    public static final int INITIAL_SCORE = 0;

    private int score;

    public ScoreBoard(Coordinate2D initialLocation)
    {
        super(initialLocation);
        this.score = INITIAL_SCORE;

        Font font = Fonts.UPHEAVTT.getFont(20);
        setFill(Color.WHITE);
        setFont(font);

        setText("SCORE: 0"); // Initialisatie van de tekst
    }

    public void updateScore(int score)
    {
        this.addScore(score);
        setText("SCORE: " + this.score);
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
