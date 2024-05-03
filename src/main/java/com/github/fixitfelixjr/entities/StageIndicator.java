package com.github.fixitfelixjr.entities;

import com.github.fixitfelixjr.Game;
import com.github.fixitfelixjr.enums.Fonts;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class StageIndicator extends TextEntity
{

    public static final int INITIAL_STAGE = Game.INITIAL_STAGE;
    public static final String STAGE_TEXT = "STAGE: ";

    private int stage;

    public StageIndicator(Coordinate2D position)
    {

        super(position);
        this.stage = INITIAL_STAGE;
        this.stageText();


    }

    public void stageText()
    {
        Font font = Fonts.UPHEAVTT.getFont(20);
        setFill(Color.WHITE);
        setFont(font);
        setText(STAGE_TEXT + this.stage);
    }


    public void updateStage(int score)
    {
        this.addStage(stage);
        setText(STAGE_TEXT + this.stage);
    }


    public void addStage(int stage)
    {
        this.stage += stage;
    }


    public int getStage()
    {
        return this.stage;
    }


    public void setStage(int stage)
    {
        this.stage = stage;
        setText(STAGE_TEXT + this.stage);
    }

}




