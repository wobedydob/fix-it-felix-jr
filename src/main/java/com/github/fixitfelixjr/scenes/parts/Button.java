package com.github.fixitfelixjr.scenes.parts;

import com.github.fixitfelixjr.enums.Position;
import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import com.github.hanyaeger.api.userinput.MouseButtonPressedListener;
import com.github.hanyaeger.api.userinput.MouseEnterListener;
import com.github.hanyaeger.api.userinput.MouseExitListener;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Button extends TextEntity
{

    public Button(Position position, String text, Color color, Font font, AnchorPoint anchorPoint)
    {
        super(new Coordinate2D(position.getX(), position.getY()), text);
        setAnchorPoint(anchorPoint);
        setFill(color);
        setFont(font);
    }
    
}
