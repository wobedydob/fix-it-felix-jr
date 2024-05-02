package com.github.fixitfelixjr.entities;

import com.github.fixitfelixjr.Game;
import com.github.fixitfelixjr.scenes.LevelScene;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;

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

    public Window(Coordinate2D position)
    {
        super(SPRITE_IMAGE, position, SIZE, SPRITE_ROWS_COLS[0], SPRITE_ROWS_COLS[1]);
        this.position = position;
        this.repairState = MIN_REPAIR;
    }

    public void repair()
    {
        if (this.repairState < MAX_REPAIR) {
            boolean fullRepair = repairState == MAX_REPAIR - 1;
            this.repairState++;
            this.update(fullRepair);
        }
    }

    public void repair(int repairState)
    {
        if (repairState <= MAX_REPAIR) {
            boolean fullRepair = repairState == MAX_REPAIR - 1;
            this.repairState = repairState;
            this.update(fullRepair);
            Game.getInstance().getLevelScene().onWindowRepaired();
        }
    }

    private void update(boolean fullRepair)
    {
        setCurrentFrameIndex(repairState);
        int score = fullRepair ? SCORE_POINTS_FULL : SCORE_POINTS;
        Game.getInstance().getScoreBoard().updateScore(score);
        Game.getInstance().getLevelScene().onWindowRepaired();
    }

    public boolean isRepaired()
    {
        return this.repairState == MAX_REPAIR;
    }

    public int getRepairState()
    {
        return this.repairState;
    }

    public Coordinate2D getPosition()
    {
        return this.position;
    }

    public void setRepairState(int repairState)
    {
        this.repairState = repairState;
    }

    public void setPosition(Coordinate2D position)
    {
        this.position = position;
    }
}
