package com.github.fixitfelixjr.scenes.parts;

import com.github.fixitfelixjr.enums.Position;
import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * The {@code Button} class extends {@code TextEntity} to provide a text-based button functionality for user interfaces.
 * This class simplifies the creation of buttons with configurable position, text, color, font, and anchor point.
 * It is intended to be used within graphical user interfaces where interactive text elements are needed.
 *
 * <p>This class is particularly useful in game scenes or any other visual scenes where interaction is required.
 * The button can be placed at a specified position, and displayed with customizable text and appearance.
 */
public class Button extends TextEntity
{
    /**
     * Creates a button with specified properties.
     *
     * @param position    the {@link Position} enum value specifying the initial position of the button.
     *                    The position enum holds predefined coordinates.
     * @param text        the text to be displayed on the button.
     * @param color       the {@link Color} of the text. This defines the visual appearance of the text.
     * @param font        the {@link Font} used to render the text. This affects the style and size of the text.
     * @param anchorPoint the {@link AnchorPoint} used to anchor the text entity. This determines how the text is positioned
     *                    relative to its specified coordinates.
     */
    public Button(Position position, String text, Color color, Font font, AnchorPoint anchorPoint)
    {
        super(new Coordinate2D(position.getX(), position.getY()), text);
        setAnchorPoint(anchorPoint);
        setFill(color);
        setFont(font);
    }
}
