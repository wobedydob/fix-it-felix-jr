package com.github.fixitfelixjr.enums;

import javafx.scene.text.Font;

import java.io.InputStream;

public enum Fonts {

    UPHEAVTT("/fonts/upheavtt.ttf");


    private final String fontPath;

    Fonts(String fontPath) {
        this.fontPath = fontPath;
    }

    public Font getFont(double size) {
        InputStream fontStream = Fonts.class.getResourceAsStream(fontPath);
        return Font.loadFont(fontStream, size);
    }
}
