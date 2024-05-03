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

    private int level;
    private int levelStage;
    private Building building;
    private Player player;
    private Enemy enemy;
    private Life[] lives;
    private String backgroundImage;

    public LevelScene(int level, int stage)
    {
        this.level = level;
        this.levelStage = stage;
        this.backgroundImage = BACKGROUND;
        this.building = new Building(stage);
        this.player = new Player();
        this.enemy = new Enemy();
    }

    @Override
    public void setupScene()
    {
        this.setupStage();
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
            this.player.remove();
            this.enemy.remove();
            this.levelStage++;
            this.setupScene();
            this.setupEntities();
        }
    }

    public void setupBackground()
    {
        String image = Building.SPRITE_IMAGE;
        if (this.levelStage == 1) {
            image = Building.SPRITE_IMAGE;
        } else if (this.levelStage == 2) {
            image = Building.SPRITE_IMAGE_MIDDLE;
        } else if (this.levelStage == 3) {
            image = Building.SPRITE_IMAGE_TOP;
        }
        this.backgroundImage = image;
        setBackgroundImage(this.backgroundImage);
    }

    public void setupStage()
    {
        this.setupBackground();
        this.setupBuilding();
        this.setupPlayer();
        this.setupLives();
        this.setupScoreBoard();
        this.setupStageIndicator();

        if (this.levelStage > Game.FINAL_STAGE) {
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
        this.building.removeWindowsFromScene();
        this.building.createWindowFrames();
    }

    public void setupPlayer()
    {
        if (this.player != null) {
            this.player.setPowerUp(null);
            this.player.setAnchorLocation(Position.PLAYER_INITIAL_POSITION.getCoordinate());
        }
    }

    public void setupScoreBoard()
    {
        int score = Game.getInstance().getScoreBoard().getScore();
        ScoreBoard scoreBoard = new ScoreBoard(Position.SCOREBOARD_POSITION_LEVEL.getCoordinate(), score);
        Game.getInstance().getScoreBoard().remove();
        Game.getInstance().setScoreBoard(scoreBoard);
        addEntity(scoreBoard);
    }

    public void setupStageIndicator()
    {
        int stage = Game.getInstance().getStageIndicator().getStage();
        StageIndicator stageIndicator = new StageIndicator(Position.STAGEINDICATOR_POSITION.getCoordinate());
        stageIndicator.setStage(stage);
        Game.getInstance().getStageIndicator().remove();
        Game.getInstance().setStageIndicator(stageIndicator);
        addEntity(stageIndicator);

    }

    public void setupLives()
    {
        this.removeLives(); // first remove any existing lives
        double lifeX = Position.PLAYER_LIFE_POSITION.getCoordinate().getX();
        double lifeY = Position.PLAYER_LIFE_POSITION.getCoordinate().getY();

        // determine player health
        int health = Player.MAX_HEALTH;
        if (this.player != null) {
            health = this.player.getHealth();
        }

        // create player lives
        this.lives = new Life[health];
        for (int i = 0; i < health; i++) {
            lives[i] = new Life(new Coordinate2D(lifeX, lifeY));
            addEntity(lives[i]);
            lifeX += Life.LIFE_SPRITE_SPACING;
        }
    }

    public void updateLives(int health)
    {
        System.out.println("updateLives(" + health + ")");
        for (int i = 0; i < this.lives.length; i++) {
            this.lives[i].setVisible(i < health);
        }
    }

    public void removeLives()
    {
        if (this.lives != null) {
            for (Life life : this.lives) {
                life.remove();
            }
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

    public int getLevel()
    {
        return this.level;
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

    public void setLevel(int level)
    {
        this.level = level;
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
