package com.github.fixitfelixjr.scenes;

import com.github.fixitfelixjr.enums.ButtonText;
import com.github.fixitfelixjr.enums.Position;
import com.github.fixitfelixjr.scenes.parts.Button;
import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import com.github.hanyaeger.api.scenes.StaticScene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TitleScene extends StaticScene implements Scene
{

    private final int SCENE_ID = 0;
    private final String BACKGROUND = "backgrounds/titlescreen.png";

    @Override
    public void setupScene()
    {
        setBackgroundImage(BACKGROUND);
    }

    @Override
    public void setupEntities()
    {
        TextEntity pressStart = new Button(
                Position.BOTTOM_CENTER,
                ButtonText.START.getText(),
                Color.WHITE,
                Font.font("Roboto", FontWeight.BOLD, 30),
                AnchorPoint.CENTER_CENTER
        );
        addEntity(pressStart);
    }

    @Override
    public int getSceneId()
    {
        return SCENE_ID;
    }

    @Override
    public String getBackground()
    {
        return BACKGROUND;
    }
}
