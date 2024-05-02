package com.github.fixitfelixjr.enums;

import javafx.scene.input.KeyCode;

/**
 * The {@code KeyBindings} enum defines the mapping of actions to specific keys within the application.
 * Each enum constant represents a different action that can be triggered by pressing the associated key.
 * This setup is crucial for handling user input in a consistent and easily manageable way across the entire application.
 *
 * <p>These key bindings are used primarily for navigation and game-specific actions such as select, move, repair, and destroy.
 */
public enum KeyBindings
{
    SELECT(KeyCode.ENTER),     // The key binding for selecting options or confirming actions.
    UP(KeyCode.W),             // The key binding for moving up or navigating upwards.
    DOWN(KeyCode.S),           // The key binding for moving down or navigating downwards.
    LEFT(KeyCode.A),           // The key binding for moving left or navigating to the left.
    RIGHT(KeyCode.D),          // The key binding for moving right or navigating to the right.
    REPAIR(KeyCode.ENTER),     // The key binding for initiating a repair action.
    DESTROY(KeyCode.BACK_SPACE); // The key binding for initiating a destroy action.

    private final KeyCode keyCode;

    /**
     * Constructs a new {@code KeyBindings} instance with the specified {@link KeyCode}.
     *
     * @param keyCode the {@link KeyCode} associated with this key binding.
     */
    KeyBindings(KeyCode keyCode)
    {
        this.keyCode = keyCode;
    }

    /**
     * Returns the {@link KeyCode} associated with this key binding. This is useful for handling
     * input events and determining which action should be triggered based on the key pressed by the user.
     *
     * @return the {@link KeyCode} corresponding to this key binding.
     */
    public KeyCode getKeyCode()
    {
        return keyCode;
    }
}
