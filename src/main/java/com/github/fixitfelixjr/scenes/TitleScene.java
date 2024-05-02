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

/**
 * The {@code TitleScene} class represents the title screen scene for the game.
 * This class extends {@code StaticScene} and implements the {@code Scene} interface to provide
 * necessary functionality for initializing and displaying the title screen with interactive elements.
 * This scene includes a background image and interactive buttons.
 */
public class TitleScene extends StaticScene implements Scene
{
    public static final int SCENE_ID = 0;
    public static final String BACKGROUND = "backgrounds/screens/title_screen.png";

    /**
     * Sets up the scene by defining its background image and key press handlers.
     * The background image is set to a predefined image and a key handler is configured
     * to change scenes when a specific key is pressed.
     */
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

    /**
     * Sets up the entities within the scene, such as buttons and text.
     * This method initializes and adds a {@code Button} that represents the start button.
     */
    @Override
    public void setupEntities()
    {
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

    /**
     * Retrieves the unique identifier for this scene.
     * @return the unique identifier for the title scene.
     */
    @Override
    public int getSceneId()
    {
        return SCENE_ID;
    }

    /**
     * Provides the path to the background image used for the title scene.
     * @return a string representing the path to the background image.
     */
    @Override
    public String getBackground()
    {
        return BACKGROUND;
    }
}
