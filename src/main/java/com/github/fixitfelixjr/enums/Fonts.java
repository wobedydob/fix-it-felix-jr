package com.github.fixitfelixjr.enums;

import javafx.scene.text.Font;

import java.io.InputStream;

/**
 * The {@code Fonts} enum provides a centralized and standardized approach to managing font resources in the application.
 * Each enum constant represents a specific font file, allowing easy access and consistent use of fonts throughout the application.
 * This setup ensures that font files are only loaded once and reused, improving performance and memory management.
 *
 * <p>This enum is particularly useful for applications requiring a consistent theme or style across various UI components,
 * as it simplifies the process of font application and maintenance.
 */
public enum Fonts
{
    UPHEAVTT("/fonts/upheavtt.ttf"); // Enum constant for the 'UPHEAVTT' font with the path to its .ttf file.

    private final String fontPath; // Path to the font file relative to the classpath.

    /**
     * Constructs a new {@code Fonts} instance with the specified font path.
     *
     * @param fontPath the path to the font file in the class resources.
     */
    Fonts(String fontPath)
    {
        this.fontPath = fontPath;
    }

    /**
     * Loads the font associated with this enum constant at the specified size. This method reads the font file
     * from the resources, ensuring that the font is only loaded into memory once and reused thereafter.
     *
     * @param size the size of the font to load.
     * @return a {@link Font} object representing the loaded font at the specified size.
     *         Returns {@code null} if the font file could not be loaded.
     */
    public Font getFont(double size)
    {
        InputStream fontStream = Fonts.class.getResourceAsStream(fontPath);
        return Font.loadFont(fontStream, size);
    }
}
