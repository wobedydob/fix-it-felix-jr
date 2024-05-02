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

/**
 * The {@code GameOverScene} class extends {@code StaticScene} and implements the {@code Scene} interface
 * to provide a scene for displaying a game over or victory screen, depending on the context in which it is instantiated.
 * This scene is responsible for handling the end-of-game logic and displaying the final score and a quit button.
 *
 * <p>This class allows different backgrounds to be set depending on the outcome of the game (game over or victory),
 * and provides functionality to quit the game when a certain key is pressed.
 */
public class GameOverScene extends StaticScene implements Scene
{
    public static final int SCENE_ID = 2;
    public static final String GAME_OVER_BACKGROUND = "backgrounds/screens/game_over_screen.png";
    public static final String VICTORY_BACKGROUND = "backgrounds/screens/victory_screen.png";

    private String backgroundImage;

    /**
     * Constructs a new {@code GameOverScene} with the specified background image.
     *
     * @param backgroundImage the path to the background image to be used for this scene.
     */
    public GameOverScene(String backgroundImage)
    {
        super();
        this.backgroundImage = backgroundImage;
    }

    /**
     * Sets up the scene's background image and the key event handler that allows the player to quit the game.
     */
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

    /**
     * Adds the necessary entities to the scene, such as a quit button and a score display.
     * This method is invoked to populate the scene with visual and interactive elements.
     */
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

    /**
     * Returns the scene identifier.
     *
     * @return the integer ID of this scene.
     */
    @Override
    public int getSceneId()
    {
        return SCENE_ID;
    }

    /**
     * Retrieves the background image path for this scene.
     *
     * @return a string representing the path to the background image.
     */
    @Override
    public String getBackground()
    {
        return this.backgroundImage;
    }

    /**
     * Sets the background image for this scene.
     *
     * @param backgroundImage the new background image to set.
     */
    public void setBackground(String backgroundImage)
    {
        this.backgroundImage = backgroundImage;
    }
}
