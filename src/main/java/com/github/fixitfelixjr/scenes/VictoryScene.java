package com.github.fixitfelixjr.scenes;

import com.github.fixitfelixjr.Game;
import com.github.fixitfelixjr.entities.ScoreBoard;
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

public class VictoryScene extends StaticScene implements Scene
{
    public static final int SCENE_ID = 3;
    public static final String BACKGROUND = "backgrounds/screens/victory_screen.png";

    @Override
    public void setupScene()
    {
        setBackgroundImage(BACKGROUND);

        getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyBindings.SELECT.getKeyCode()) {
                Game.getInstance().quit(); // TODO: why do we get an error?
            }
        });
    }

    @Override
    public void setupEntities()
    {
        // Font loader
        Font font = Fonts.UPHEAVTT.getFont(40);

        TextEntity pressQuit = new Button(
                Position.BUTTON_BOTTOM_CENTER,
                ButtonText.QUIT.getText(),
                Color.WHITE,
                font,
                AnchorPoint.CENTER_CENTER
        );

        addEntity(pressQuit);

        int score = Game.getInstance().getScoreBoard().getScore();
        ScoreBoard scoreBoard = new ScoreBoard(Position.SCOREBOARD_POSITION_END.getCoordinate(), score);
        addEntity(scoreBoard);
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




