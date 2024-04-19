package com.github.fixitfelixjr.scenes;

import com.github.fixitfelixjr.entities.Building;
import com.github.fixitfelixjr.entities.Enemy;
import com.github.fixitfelixjr.entities.Player;
import com.github.fixitfelixjr.entities.Window;
import com.github.hanyaeger.api.scenes.DynamicScene;

public class LevelScene extends DynamicScene implements Scene
{
    private final int SCENE_ID = 1;
    private final String BACKGROUND = "backgrounds/wireframes/GAME.png";
    private final int FLOORS = 5;
    private final int WINDOWS_PER_FLOOR = 5;

    @Override
    public void setupScene()
    {
        setBackgroundImage(BACKGROUND);
        Building.getInstance().generateBuilding(this);
    }

    @Override
    public void setupEntities()
    {
        Player felix = new Player();
        addEntity(felix);

//        Enemy ralph = new Enemy();
//        addEntity(ralph);
    }

    public int getSceneId()
    {
        return SCENE_ID;
    }

    public String getBackground()
    {
        return BACKGROUND;
    }
}
