package com.github.fixitfelixjr.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;

public class Window extends DynamicSpriteEntity
{

    //    public static final String SPRITE_IMAGE = "sprites/window.png";
    public static final String SPRITE_IMAGE = "sprites/windows2.png";
    //    public final static Size SIZE = new Size((12 * 4), (23 * 4));
    public final static Size SIZE = new Size((24 * 4), (43 * 4));
    public final static int[] SPRITE_ROWS_COLS = {1, 4};
    public static final int MAX_REPAIR = 3;
    public static final int MIN_REPAIR = 0;

    private int repairState;
    private Coordinate2D position;

    public Window(Coordinate2D position)
    {
        super(SPRITE_IMAGE, position, SIZE, SPRITE_ROWS_COLS[0], SPRITE_ROWS_COLS[1]);
        this.position = position;
        this.repairState = MIN_REPAIR;

        // todo:
        setCurrentFrameIndex(MAX_REPAIR);
    }

    public void repair()
    {
        if (repairState < MAX_REPAIR) {
            repairState++;
            updateSprite();
        }
    }

    public void damage()
    {
        if (repairState > MIN_REPAIR) {
            repairState--;
            updateSprite();
        }
    }

    private void updateSprite()
    {
        setCurrentFrameIndex(repairState);
    }

    public int getRepairState()
    {
        return repairState;
    }

    public Coordinate2D getPosition()
    {
        return position;
    }

}
