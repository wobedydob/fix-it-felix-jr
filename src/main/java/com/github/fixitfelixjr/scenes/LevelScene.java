package com.github.fixitfelixjr.scenes;

import com.github.fixitfelixjr.entities.Building;
import com.github.fixitfelixjr.entities.Player;
import com.github.hanyaeger.api.scenes.DynamicScene;

public class LevelScene extends DynamicScene implements Scene
{
    private static final int SCENE_ID = 1;
    private static final String BACKGROUND = Building.SPRITE_IMAGE;

    private int levelStage;
    private Building building;

    public LevelScene(int stage)
    {
        this.levelStage = stage;
        this.building = Building.getInstance();
    }

    @Override
    public void setupScene()
    {
        setBackgroundImage(BACKGROUND);

        this.building.setStage(this.levelStage);
        this.building.createWindowFrames(this);
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

    public int getLevelStage()
    {
        return this.levelStage;
    }

    public Building getBuilding()
    {
        return building;
    }

    public void setLevelStage(int levelStage)
    {
        this.levelStage = levelStage;
    }

    public void setBuilding(Building building)
    {
        this.building = building;
    }
}
