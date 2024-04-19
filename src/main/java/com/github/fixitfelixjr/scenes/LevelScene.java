package com.github.fixitfelixjr.scenes;

import com.github.fixitfelixjr.entities.Enemy;
import com.github.fixitfelixjr.entities.Player;
import com.github.hanyaeger.api.scenes.DynamicScene;

public class LevelScene extends DynamicScene implements Scene
{

    private final int SCENE_ID = 1;
    private final String BACKGROUND = "backgrounds/wireframes/GAME.png";

    @Override
    public void setupScene()
    {
        setBackgroundImage(BACKGROUND);
    }

    @Override
    public void setupEntities()
    {
        Player felix = new Player();
        addEntity(felix);

        Enemy ralph = new Enemy();
        addEntity(ralph);
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
