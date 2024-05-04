package com.github.fixitfelixjr.enums;

/**
 * The {@code ButtonText} enum defines predefined text labels for buttons within the application.
 * Each enum constant represents a specific text used on a button, ensuring consistency and reusability of common button texts.
 * This approach helps maintain uniformity and reduces errors in button labeling across different user interfaces.
 *
 * Enum constants like {@code START} and {@code QUIT} encapsulate messages that are directly associated with user actions,
 * making the interface more intuitive and user-friendly.
 */
public enum ButtonText
{
    START("PRESS ENTER TO START"),
    QUIT("PRESS ENTER TO QUIT");

    private final String text;

    /**
     * Constructs a new {@code ButtonText} instance with the specified label text.
     *
     * @param text the text that will appear on the button. This should clearly describe the button's action to ensure
     *             user understanding and effective interaction.
     */
    ButtonText(String text)
    {
        this.text = text;
    }

    /**
     * Retrieves the text of this button label.
     * This method provides access to the text associated with a button, which can be used when creating button instances
     * or when dynamically updating the interface.
     *
     * @return the text of this enum constant, which is used as the label on a button.
     */
    public String getText()
    {
        return text;
    }
}