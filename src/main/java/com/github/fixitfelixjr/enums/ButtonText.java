package com.github.fixitfelixjr.enums;

public enum ButtonText
{

    START("PRESS ENTER TO START"),
    QUIT("PRESS ENTER TO QUIT");

    private final String text;

    ButtonText(String text)
    {
        this.text = text;
    }

    public String getText()
    {
        return text;
    }

}
