package com.github.fixitfelixjr.scenes;

import com.github.fixitfelixjr.TimeEvent;
import com.github.fixitfelixjr.entities.*;
import com.github.fixitfelixjr.entities.powerups.HardhatPowerUp;
import com.github.fixitfelixjr.entities.powerups.PiePowerUp;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.TimerContainer;
import com.github.hanyaeger.api.scenes.DynamicScene;

import java.util.Random;

public class LevelScene extends DynamicScene implements Scene, TimerContainer
{
    public static final int SCENE_ID = 1;
    public static final int SPRITE_SIZE_APPLIER = 4;
    public static final String BACKGROUND = Building.SPRITE_IMAGE;
    public static final int POWERUP_SPAWN_RATE = 5; // in milliseconds
    public static final int POWERUP_CHANCE_RATE = 4; // 1 in x chance

    private int levelStage;
    private Building building;
    private Player player;
    private Enemy enemy;

    public LevelScene(int stage)
    {
        this.levelStage = stage;
        this.building = Building.getInstance();
        this.player = new Player();
        this.enemy = new Enemy();
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
        addEntity(this.player);
        addEntity(this.enemy);

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
        if (this.building.canSpawnPowerUp() && this.player.getPowerUp() == null) {
            System.out.println("can spawn a powerup");

            System.out.println("1/" + POWERUP_CHANCE_RATE + " chance to spawn a powerup");
            int random = new Random().nextInt(POWERUP_CHANCE_RATE);
            if (random == 0) {
                System.out.println("lucky");

                System.out.println("get random window");
                WindowFrame windowFrame = this.building.getRandomWindowFrame();
                if (!windowFrame.hasPowerUp()) {
                    random = new Random().nextInt(2); // TODO: make more dynamic, 2 specifies the amount of powerups...
                    switch (random) {
                        case 0:
                            this.spawnPiePowerUp(windowFrame);
                            break;
                        case 1:
                            this.spawnHardhatPowerUp(windowFrame);
                            break;
                    }
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

    public void spawnPiePowerUp(WindowFrame windowFrame)
    {
        System.out.println("spawned pie powerup");
        Coordinate2D windowPosition = windowFrame.getPlatform().getPosition();
        Coordinate2D powerUpPosition = new Coordinate2D(windowPosition.getX() + (26), windowPosition.getY() - (63));
        PiePowerUp piePowerUp = new PiePowerUp(powerUpPosition);
        windowFrame.setPowerUp(piePowerUp);
        addEntity(piePowerUp);
    }

    public void spawnHardhatPowerUp(WindowFrame windowFrame)
    {
        System.out.println("spawned hardhat powerup");
        Coordinate2D windowPosition = windowFrame.getPlatform().getPosition();
        Coordinate2D powerUpPosition = new Coordinate2D(windowPosition.getX() + (30), windowPosition.getY() - (43));
        HardhatPowerUp hardhatPowerUp = new HardhatPowerUp(powerUpPosition);
        windowFrame.setPowerUp(hardhatPowerUp);
        addEntity(hardhatPowerUp);
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
