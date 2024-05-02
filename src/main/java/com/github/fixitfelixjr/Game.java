package com.github.fixitfelixjr;

import com.github.fixitfelixjr.entities.ScoreBoard;
import com.github.fixitfelixjr.enums.Position;
import com.github.fixitfelixjr.scenes.*;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.YaegerGame;
import com.github.hanyaeger.api.scenes.YaegerScene;
import javafx.stage.Stage;

public class Game extends YaegerGame
{
    public static final String GAME_TITLE = "Fix-It Felix Jr.";
    public static final int GAME_WIDTH = 1280;
    public static final int GAME_HEIGHT = 960;
    public static final int INITIAL_LEVEL = 1;
    public static final int INITIAL_STAGE = 0;

    private static Game instance;

    private TitleScene titleScene;
    private LevelScene levelScene;
    private GameOverScene gameOverScene;
    private ScoreBoard scoreBoard;

    public static Game getInstance()
    {
        return instance;
    }

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
}
