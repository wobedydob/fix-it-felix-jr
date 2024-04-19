package com.github.fixitfelixjr.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;

public class Window extends DynamicSpriteEntity
{

    public static final String SPRITE_IMAGE = "sprites/window.png";
    public final static Size SIZE = new Size((24 * 3), (43 * 3));
    public final static int[] SPRITE_ROWS_COLS = {1, 7};
    public static final int MAX_REPAIR = 6; // 6 states for repairs
    public static final int MIN_REPAIR = 0; //

    private int repairState;

    public Window(double x, double y) {
        super(SPRITE_IMAGE, new Coordinate2D(x, y), SIZE, SPRITE_ROWS_COLS[0], SPRITE_ROWS_COLS[1]);
        this.repairState = MIN_REPAIR; // Beginnen met het raam volledig kapot
    }

    public void repair() {
        if (repairState < MAX_REPAIR) {
            repairState++;
            updateSprite();
        }
    }

    public void damage() {
        if (repairState > MIN_REPAIR) {
            repairState--;
            updateSprite();
        }
    }

    private void updateSprite() {
        setCurrentFrameIndex(repairState);
    }

    public int getRepairState() {
        return repairState;
    }

}
