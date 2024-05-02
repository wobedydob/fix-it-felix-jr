package com.github.fixitfelixjr.scenes;

import com.github.fixitfelixjr.Game;
import com.github.fixitfelixjr.TimeEvent;
import com.github.fixitfelixjr.WindowRepairListener;
import com.github.fixitfelixjr.entities.*;
import com.github.fixitfelixjr.entities.powerups.HardhatPowerUp;
import com.github.fixitfelixjr.entities.powerups.PiePowerUp;
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
    public static final int POWERUP_SPAWN_RATE = 5; // in milliseconds
    public static final int POWERUP_CHANCE_RATE = 4; // 1 in x chance
    public static final int NPC_SPAWN_RATE = 2; // in milliseconds
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
        this.building.setStage(this.levelStage);
        this.building.createWindowFrames(this);

        ScoreBoard scoreBoard = Game.getInstance().getScoreBoard();
        scoreBoard.setAnchorLocation(Position.SCOREBOARD_POSITION_LEVEL.getCoordinate());
        addEntity(scoreBoard);

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
        checkAllWindowsRepaired();
    }

    public void checkAllWindowsRepaired()
    {
        if (this.building.areAllWindowsRepaired()) {
            System.out.println("all windows are repaired");
            Game.getInstance().setActiveScene(VictoryScene.SCENE_ID);
        }
    }

    @Override
    public void setupTimers()
    {
        TimeEvent powerUpEvent = new TimeEvent(POWERUP_SPAWN_RATE * 1000, () -> this.spawnPowerUp(), true);
        addTimer(powerUpEvent);

        TimeEvent npcEvent = new TimeEvent(NPC_SPAWN_RATE * 1000, () -> this.spawnNPC(), true);
        addTimer(npcEvent);
    }

    public void setupLives()
    {
        int lifeX = (int) Position.PLAYER_LIFE_POSITION.getCoordinate().getX();
        int lifeY = (int) Position.PLAYER_LIFE_POSITION.getCoordinate().getY();
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
                WindowFrame windowFrame = this.building.getRandomAvailableWindowFrame();
                if (!windowFrame.hasPowerUp()) {
                    random = new Random().nextInt(PiePowerUp.POWER_UP_COUNT);
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

    public void spawnNPC()
    {
        System.out.println(" ");
        System.out.println("-----------------------------------------------------------");
        System.out.println("trying to spawn npc");

        if (this.building.canSpawnNPC()) {
            System.out.println("can spawn npc");

            System.out.println("1/" + NPC_CHANCE_RATE + " chance to spawn npc");
            int random = new Random().nextInt(NPC_CHANCE_RATE);
            if (random == 0) {
                System.out.println("lucky");

                System.out.println("get random window");
                WindowFrame windowFrame = this.building.getRandomAvailableWindowFrame();

                if (!windowFrame.hasNPC()) {
                    Coordinate2D windowPosition = windowFrame.getPlatform().getPosition();
                    Coordinate2D npcPosition = new Coordinate2D(windowPosition.getX() + (24), windowPosition.getY() - (72));
                    int randomSpriteIndex = new Random().nextInt(NPC.NPC_COUNT);
                    NPC npc = new NPC(npcPosition, randomSpriteIndex, windowFrame);
                    addEntity(npc);
                }


            } else {
                System.out.println("unlucky");
            }

        } else {
            System.out.println("can't spawn a npc");
        }

        System.out.println("-----------------------------------------------------------");
        System.out.println(" ");

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
        return levelStage;
    }

    public Building getBuilding()
    {
        return building;
    }

    public Player getPlayer()
    {
        return player;
    }

    public Enemy getEnemy()
    {
        return enemy;
    }

    public Life[] getLives()
    {
        return lives;
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
