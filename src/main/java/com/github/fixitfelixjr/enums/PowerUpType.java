package com.github.fixitfelixjr.enums;

public enum PowerUpType
{
    PIE(0),
    HARDHAT(1),
    LIFE(2);

    private final int index;

    PowerUpType(int index)
    {
        this.index = index;
    }

    public int getIndex()
    {
        return index;
    }

    public static PowerUpType fromIndex(int index)
    {
        for (PowerUpType type : PowerUpType.values()) {
            if (type.getIndex() == index) {
                return type;
            }
        }
        return null;
    }
}
