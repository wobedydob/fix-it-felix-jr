package com.github.fixitfelixjr.enums;

public enum ButtonText
{

    START("PRESS ENTER TO START"),
    RESTART("PRESS ENTER TO RESTART"),
    QUIT("Quit");

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
