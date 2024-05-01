package com.github.fixitfelixjr.entities;

import com.github.fixitfelixjr.enums.Fonts;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ScoreBoard extends TextEntity
{
    public ScoreBoard(Coordinate2D initialLocation){
        super(initialLocation);

        Font font = Fonts.UPHEAVTT.getFont(20);
        setFill(Color.WHITE);
        setFont(font);

        setText("HI SCORE: 0"); // Initialisatie van de tekst
    }

    public void updateScore(int score){
        setText("HI SCORE: " + score); // Methode om de score bij te werken
    }
}
