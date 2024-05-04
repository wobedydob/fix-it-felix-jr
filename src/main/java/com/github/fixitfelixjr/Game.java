/**
 * The Game class represents the main game controller for Fix-It Felix Jr.
 * It extends the YaegerGame class and manages the game's setup, scenes, and components.
 */
package com.github.fixitfelixjr;

import com.github.fixitfelixjr.entities.ScoreBoard;
import com.github.fixitfelixjr.entities.LevelStageIndicator;
import com.github.fixitfelixjr.enums.Position;
import com.github.fixitfelixjr.scenes.*;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.YaegerGame;
import com.github.hanyaeger.api.scenes.YaegerScene;
import javafx.stage.Stage;

/**
 * Retrieves the singleton instance of the Game class.
 * @return The instance of the Game class.
 */
public class Game extends YaegerGame
{
    public static final String GAME_TITLE = "Fix-It Felix Jr.";
    public static final int GAME_WIDTH = 1280;
    public static final int GAME_HEIGHT = 960;
    public static final int INITIAL_LEVEL = 1;
    public static final int INITIAL_STAGE = 1;
    public static final int FINAL_STAGE = 3;

    private static Game instance;

    private TitleScene titleScene;
    private LevelScene levelScene;
    private GameOverScene gameOverScene;
    private ScoreBoard scoreBoard;
    private LevelStageIndicator levelStageIndicator;

    public static Game getInstance()
    {
        return instance;
    }

    /**
     * The entry point of the application.
     * @param args The command line arguments.
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage)
    {
        super.start(primaryStage);
        instance = this;
    }

    @Override
    public void setupGame()
    {
        this.titleScene = new TitleScene();
        this.levelScene = new LevelScene(INITIAL_LEVEL, INITIAL_STAGE);
        this.gameOverScene = new GameOverScene(GameOverScene.GAME_OVER_BACKGROUND);
        this.scoreBoard = new ScoreBoard(Position.SCOREBOARD_POSITION_TITLE.getCoordinate(), ScoreBoard.INITIAL_SCORE);
        this.levelStageIndicator = new LevelStageIndicator(Position.STAGE_POSITION_TITLE.getCoordinate());

        setGameTitle(GAME_TITLE);
        setSize(new Size(GAME_WIDTH, GAME_HEIGHT));
    }

    @Override
    public void setupScenes()
    {
        registerScene(this.titleScene);
        registerScene(this.levelScene);
        registerScene(this.gameOverScene);
    }

    /**
     * Registers a scene in the game.
     * @param scene The scene to register.
     */
    public void registerScene(Scene scene)
    {
        addScene(scene.getSceneId(), (YaegerScene) scene);
    }

    public TitleScene getTitleScene()
    {
        return this.titleScene;
    }

    public LevelScene getLevelScene()
    {
        return this.levelScene;
    }

    public GameOverScene getGameOverScene()
    {
        return this.gameOverScene;
    }

    public ScoreBoard getScoreBoard()
    {
        return this.scoreBoard;
    }

    public LevelStageIndicator getStageIndicator()
    {
        return this.levelStageIndicator;
    }

    public void setTitleScene(TitleScene titleScene)
    {
        this.titleScene = titleScene;
    }

    public void setLevelScene(LevelScene levelScene)
    {
        this.levelScene = levelScene;
    }

    public void setGameOverScene(GameOverScene gameOverScene)
    {
        this.gameOverScene = gameOverScene;
    }

    public void setScoreBoard(ScoreBoard scoreBoard)
    {
        this.scoreBoard = scoreBoard;
    }

    public void setStageIndicator(LevelStageIndicator levelStageIndicator)
    {
        this.levelStageIndicator = levelStageIndicator;
    }
}
