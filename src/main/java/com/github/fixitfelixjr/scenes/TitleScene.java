package com.github.fixitfelixjr.scenes;

import com.github.fixitfelixjr.Game;
import com.github.fixitfelixjr.enums.ButtonText;
import com.github.fixitfelixjr.enums.Fonts;
import com.github.fixitfelixjr.enums.KeyBindings;
import com.github.fixitfelixjr.enums.Position;
import com.github.fixitfelixjr.scenes.parts.Button;
import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import com.github.hanyaeger.api.scenes.StaticScene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TitleScene extends StaticScene implements Scene
{

    public static final int SCENE_ID = 0;
    public static final String BACKGROUND = "backgrounds/screens/title_screen.png";

    @Override
    public void setupScene()
    {
        setBackgroundImage(BACKGROUND);

        getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyBindings.SELECT.getKeyCode()) {
                Game.getInstance().setActiveScene(LevelScene.SCENE_ID);
            }
        });
    }

    @Override
    public void setupEntities()
    {
        // Font loader
        Font font = Fonts.UPHEAVTT.getFont(40);

        TextEntity pressStart = new Button(
                Position.BUTTON_BOTTOM_CENTER,
                ButtonText.START.getText(),
                Color.WHITE,
                font,
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




