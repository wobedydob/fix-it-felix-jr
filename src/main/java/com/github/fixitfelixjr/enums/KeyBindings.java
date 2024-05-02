package com.github.fixitfelixjr.enums;

import javafx.scene.input.KeyCode;

public enum KeyBindings
{
    SELECT(KeyCode.ENTER),
    UP(KeyCode.W),
    DOWN(KeyCode.S),
    LEFT(KeyCode.A),
    RIGHT(KeyCode.D),
    REPAIR(KeyCode.ENTER),
    DESTROY(KeyCode.BACK_SPACE);

    private final KeyCode keyCode;

    KeyBindings(KeyCode keyCode)
    {
        this.keyCode = keyCode;
    }

    public KeyCode getKeyCode()
    {
        return keyCode;
    }
}
