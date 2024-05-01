package com.github.fixitfelixjr;

import com.github.fixitfelixjr.entities.ScoreBoard;
import com.github.fixitfelixjr.enums.ButtonText;
import com.github.fixitfelixjr.enums.Fonts;
import com.github.fixitfelixjr.enums.Position;
import com.github.fixitfelixjr.scenes.Scene;
import com.github.fixitfelixjr.scenes.parts.Button;
import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import com.github.hanyaeger.api.scenes.StaticScene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class VictoryScene extends StaticScene implements Scene
{
    public static final int SCENE_ID = 3;
    public static final String BACKGROUND = "backgrounds/screens/victory_screen.png";

    private ScoreBoard scoreBoard;

    public VictoryScene()
    {
    }

    @Override
    public void setupScene()
    {
        setBackgroundImage(BACKGROUND);

        getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                Game.getInstance().quit();
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
                ButtonText.QUIT.getText(),
                Color.WHITE,
                font,
                AnchorPoint.CENTER_CENTER
        );

        addEntity(pressStart);

        scoreBoard = new ScoreBoard(new Coordinate2D(10, 10)); // Positie linksboven
        addEntity(scoreBoard);
    }

    public void updateScore(int score)
    {
        scoreBoard.updateScore(score);
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






}
