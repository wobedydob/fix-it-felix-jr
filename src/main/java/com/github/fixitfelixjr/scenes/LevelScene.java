package com.github.fixitfelixjr.scenes;

import com.github.fixitfelixjr.TimeEvent;
import com.github.fixitfelixjr.entities.Building;
import com.github.fixitfelixjr.entities.Player;
import com.github.fixitfelixjr.entities.WindowFrame;
import com.github.fixitfelixjr.entities.powerups.PiePowerUp;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.TimerContainer;
import com.github.hanyaeger.api.scenes.DynamicScene;

import java.util.Random;

public class LevelScene extends DynamicScene implements Scene, TimerContainer
{
    public static final int SPRITE_SIZE_APPLIER = 4;
    public static final int SCENE_ID = 1;
    public static final String BACKGROUND = Building.SPRITE_IMAGE;
    public static final int POWERUP_SPAWN_RATE = 2; // in milliseconds
    public static final int POWERUP_CHANCE_RATE = 2; // 1 in x chance

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

    @Override
    public void setupTimers()
    {
        TimeEvent powerUpEvent = new TimeEvent(POWERUP_SPAWN_RATE * 1000, () -> this.spawnPowerUp(), true);
        addTimer(powerUpEvent);
    }

    // TODO: do powerups despawn?
    public void spawnPowerUp()
    {
        System.out.println(" ");
        System.out.println("-----------------------------------------------------------");
        System.out.println("trying to spawn a powerup");
        if (this.building.canSpawnPowerUp()) {
            System.out.println("can spawn a powerup");

            System.out.println("1/" + POWERUP_CHANCE_RATE +  " chance to spawn a powerup");
            int random = new Random().nextInt(POWERUP_CHANCE_RATE);
            if (random == 0) {
                System.out.println("lucky");

                System.out.println("get random window");
                WindowFrame windowFrame = this.building.getRandomWindowFrame();
                if (!windowFrame.hasPowerUp()) {
                    Coordinate2D windowPosition = windowFrame.getPlatform().getPosition();
                    Coordinate2D powerUpPosition = new Coordinate2D(windowPosition.getX() + (26), windowPosition.getY() - (63));
                    PiePowerUp piePowerUp = new PiePowerUp(powerUpPosition);
                    windowFrame.setPowerUp(piePowerUp);
                    addEntity(windowFrame.getPowerUp());
                    System.out.println("spawned powerup");
                }

            } else {
                System.out.println("unlucky");
            }

        } else {
            System.out.println("can't spawn a powerup");
        }

        System.out.println("-----------------------------------------------------------");
        System.out.println(" ");

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
