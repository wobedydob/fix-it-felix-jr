package com.github.fixitfelixjr.scenes;

import com.github.fixitfelixjr.Game;
import com.github.fixitfelixjr.TimeEvent;
import com.github.fixitfelixjr.WindowRepairListener;
import com.github.fixitfelixjr.entities.*;
import com.github.fixitfelixjr.entities.powerups.HardhatPowerUp;
import com.github.fixitfelixjr.entities.powerups.LifePowerUp;
import com.github.fixitfelixjr.entities.powerups.PiePowerUp;
import com.github.fixitfelixjr.entities.powerups.PowerUp;
import com.github.fixitfelixjr.enums.Position;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.TimerContainer;
import com.github.hanyaeger.api.scenes.DynamicScene;

import java.util.Random;

public class LevelScene extends DynamicScene implements Scene, WindowRepairListener, TimerContainer
{
    public static final int SCENE_ID = 1;
    public static final int SPRITE_SIZE_APPLIER = 4;
    public static final String BACKGROUND = Building.SPRITE_IMAGE;
    public static final int POWER_UP_SPAWN_RATE = 5; // in milliseconds
    public static final int POWER_UP_CHANCE_RATE = 4; // 1 in x chance
    public static final int NPC_SPAWN_RATE = 7; // in milliseconds
    public static final int NPC_CHANCE_RATE = 4; // 1 in x chance

    private int levelStage;
    private Building building;
    private Player player;
    private Enemy enemy;
    private Life[] lives;

    public LevelScene(int stage)
    {
        this.levelStage = stage;
        this.building = new Building(stage);
        this.player = new Player();
        this.enemy = new Enemy();
        this.lives = new Life[Player.MAX_HEALTH];
    }

    @Override
    public void setupScene()
    {
        setBackgroundImage(BACKGROUND);

        this.setupBuilding();
        this.setupScoreBoard(Game.getInstance().getScoreBoard());
        this.setupLives();
    }

    @Override
    public void setupEntities()
    {
        addEntity(this.player);
        addEntity(this.enemy);
    }

    @Override
    public void onWindowRepaired()
    {
        this.checkAllWindowsRepaired();
    }

    public void checkAllWindowsRepaired()
    {
        if (this.building.areAllWindowsRepaired()) {
            Game.getInstance().getGameOverScene().setBackground(GameOverScene.VICTORY_BACKGROUND);
            Game.getInstance().setActiveScene(GameOverScene.SCENE_ID);
        }
    }

    @Override
    public void setupTimers()
    {
        TimeEvent powerUpEvent = new TimeEvent(POWER_UP_SPAWN_RATE * 1000, this::spawnPowerUp, true);
        addTimer(powerUpEvent);

        TimeEvent npcEvent = new TimeEvent(NPC_SPAWN_RATE * 1000, this::spawnNPC, true);
        addTimer(npcEvent);
    }

    public void setupBuilding()
    {
        this.building.setStage(this.levelStage);
        this.building.createWindowFrames();
    }

    public void setupScoreBoard(ScoreBoard scoreBoard)
    {
        scoreBoard.setAnchorLocation(Position.SCOREBOARD_POSITION_LEVEL.getCoordinate());
        addEntity(scoreBoard);
    }

    public void setupLives()
    {
        double lifeX = Position.PLAYER_LIFE_POSITION.getCoordinate().getX();
        double lifeY = Position.PLAYER_LIFE_POSITION.getCoordinate().getY();
        for (int i = 0; i < Player.MAX_HEALTH; i++) {
            lives[i] = new Life(new Coordinate2D(lifeX, lifeY));
            addEntity(lives[i]);
            lifeX += Life.LIFE_SPRITE_SPACING;
        }
    }

    public void updateLives(int health)
    {
        for (int i = 0; i < lives.length; i++) {
            lives[i].setVisible(i < health);
        }
    }

    public void spawnPowerUp()
    {

        if (this.building.canSpawnPowerUp() && this.player.getPowerUp() == null) {

            int random = new Random().nextInt(POWER_UP_CHANCE_RATE);
            if (random == 0) {

                WindowFrame windowFrame = this.building.getRandomAvailableWindowFrame();
                if (!windowFrame.hasPowerUp()) {

                    random = new Random().nextInt(PowerUp.POWER_UP_COUNT);
                    switch (random) {
                        case 0:
                            this.spawnPiePowerUp(windowFrame);
                            break;
                        case 1:
                            this.spawnHardhatPowerUp(windowFrame);
                            break;
                        case 2:
                            this.spawnLifePowerUp(windowFrame);
                            break;
                    }

                }

            }

        }

    }

    public void spawnPiePowerUp(WindowFrame windowFrame)
    {
        Coordinate2D windowPosition = windowFrame.getPlatform().getPosition();
        Coordinate2D powerUpPosition = new Coordinate2D(windowPosition.getX() + (26), windowPosition.getY() - (63));
        PiePowerUp piePowerUp = new PiePowerUp(powerUpPosition);
        windowFrame.setPowerUp(piePowerUp);
        addEntity(piePowerUp);
    }

    public void spawnHardhatPowerUp(WindowFrame windowFrame)
    {
        Coordinate2D windowPosition = windowFrame.getPlatform().getPosition();
        Coordinate2D powerUpPosition = new Coordinate2D(windowPosition.getX() + (30), windowPosition.getY() - (43));
        HardhatPowerUp hardhatPowerUp = new HardhatPowerUp(powerUpPosition);
        windowFrame.setPowerUp(hardhatPowerUp);
        addEntity(hardhatPowerUp);
    }

    public void spawnLifePowerUp(WindowFrame windowFrame)
    {
        Coordinate2D windowPosition = windowFrame.getPlatform().getPosition();
        Coordinate2D powerUpPosition = new Coordinate2D(windowPosition.getX() + (35), windowPosition.getY() - (40));
        LifePowerUp lifePowerUp = new LifePowerUp(powerUpPosition);
        windowFrame.setPowerUp(lifePowerUp);
        addEntity(lifePowerUp);
    }

    public void spawnNPC()
    {
        if (this.building.canSpawnNPC()) {

            int random = new Random().nextInt(NPC_CHANCE_RATE);
            if (random == 0) {

                WindowFrame windowFrame = this.building.getRandomAvailableWindowFrame();

                if (!windowFrame.hasNPC()) {
                    Coordinate2D windowPosition = windowFrame.getPlatform().getPosition();
                    Coordinate2D npcPosition = new Coordinate2D(windowPosition.getX() + (24), windowPosition.getY() - (72));
                    int randomSpriteIndex = new Random().nextInt(NPC.NPC_COUNT);
                    NPC npc = new NPC(npcPosition, randomSpriteIndex, windowFrame);
                    addEntity(npc);
                }

            }

        }
    }

    public int getSceneId()
    {
        return SCENE_ID;
    }

    @Override
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
        return this.building;
    }

    public Player getPlayer()
    {
        return this.player;
    }

    public Enemy getEnemy()
    {
        return this.enemy;
    }

    public Life[] getLives()
    {
        return this.lives;
    }

    public void setLevelStage(int levelStage)
    {
        this.levelStage = levelStage;
    }

    public void setBuilding(Building building)
    {
        this.building = building;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public void setEnemy(Enemy enemy)
    {
        this.enemy = enemy;
    }

    public void setLives(Life[] lives)
    {
        this.lives = lives;
    }
}
