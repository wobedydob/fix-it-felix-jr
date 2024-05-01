package com.github.fixitfelixjr;

import com.github.fixitfelixjr.scenes.GameOverScene;
import com.github.fixitfelixjr.scenes.LevelScene;
import com.github.fixitfelixjr.scenes.Scene;
import com.github.fixitfelixjr.scenes.TitleScene;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.YaegerGame;
import com.github.hanyaeger.api.scenes.YaegerScene;
import com.github.hanyaeger.core.YaegerCommandLineParser;
import com.github.hanyaeger.core.YaegerStage;
import com.github.hanyaeger.core.guice.YaegerModule;
import com.github.hanyaeger.core.media.BackgroundAudioMediaPlayer;
import com.google.inject.Guice;
import javafx.stage.Stage;

public class Game extends YaegerGame
{
    public static final int GAME_WIDTH = 1280;
    public static final int GAME_HEIGHT = 960;
    public static final int INITIAL_STAGE = 0;

    private static Game instance;

    public static Game getInstance()
    {
        return instance;
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        super.start(primaryStage);
        instance = this;
    }

    @Override
    public void setupGame()
    {
        setGameTitle("Fix-It Felix Jr.");
        setSize(new Size(GAME_WIDTH, GAME_HEIGHT));
    }

    @Override
    public void setupScenes()
    {
        registerScene(new TitleScene());
        registerScene(new LevelScene(INITIAL_STAGE));
        registerScene(new GameOverScene());
    }

    public void registerScene(Scene scene)
    {
        addScene(scene.getSceneId(), (YaegerScene) scene);
    }

}
