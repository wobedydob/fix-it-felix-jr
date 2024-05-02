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

public class GameOverScene extends StaticScene implements Scene
{
    public static final int SCENE_ID = 2;
    public static final String GAME_OVER_BACKGROUND = "backgrounds/screens/game_over_screen.png";
    public static final String VICTORY_BACKGROUND = "backgrounds/screens/victory_screen.png";

    private String backgroundImage;

    public GameOverScene(String backgroundImage)
    {
        super();
        this.backgroundImage = backgroundImage;
    }

    @Override
    public void setupScene()
    {
        setBackgroundImage(this.backgroundImage);

        getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyBindings.SELECT.getKeyCode()) {
                Game.getInstance().quit();
            }
        });
    }

    @Override
    public void setupEntities()
    {
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
        return this.backgroundImage;
    }

    public void setBackground(String backgroundImage)
    {
        this.backgroundImage = backgroundImage;
    }
}




