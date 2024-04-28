package com.github.fixitfelixjr.entities;

import com.github.fixitfelixjr.Game;
import com.github.fixitfelixjr.scenes.LevelScene;
import com.github.hanyaeger.api.Coordinate2D;

import java.util.ArrayList;
import java.util.List;

public class Building
{

    public static final String SPRITE_IMAGE = "backgrounds/building.png";
    public static final int FLOORS = 4;
    public static final int WINDOWS_PER_FLOOR = 5;
    public static final int MIDDLE_WINDOW_INDEX = 2;

    private static final Building instance = new Building();
    private List<WindowFrame> windowFrames;
    private int stage;

    private Building()
    {
        this.windowFrames = new ArrayList<>();
    }

    public static Building getInstance()
    {
        return instance;
    }

    public void createWindowFrames(LevelScene scene)
    {

        // TODO: fix logic for stages and windows in array

        // Code om ramen te creëren, exclusief de plek van de deur.
        for (int floor = 0; floor < FLOORS; floor++) {
            for (int windowNum = 0; windowNum < WINDOWS_PER_FLOOR; windowNum++) {
                // Uitsluiten van de deur positie
                if (floor == 0 && windowNum == MIDDLE_WINDOW_INDEX) continue;

                Coordinate2D position = new Coordinate2D(calculateXPosition(windowNum), calculateYPosition(floor));
//                Coordinate2D position = new Coordinate2D(150, scene.getHeight() - WindowFrame.HEIGHT - 100);
                WindowFrame windowFrame = new WindowFrame(position, scene);
                scene.addEntity(windowFrame);
                this.windowFrames.add(windowFrame);

            }
        }
    }

    private double calculateXPosition(int windowNum)
    {
        double windowWidth = 160;
        double startX = 595;
        double additionalDistance = 0;

        if (windowNum == 0 || windowNum == 4) {
            additionalDistance = 20;
        }

        if (windowNum < MIDDLE_WINDOW_INDEX) {
            return startX - (MIDDLE_WINDOW_INDEX - windowNum) * (windowWidth - additionalDistance);
        } else {
            return startX + (windowNum - MIDDLE_WINDOW_INDEX) * (windowWidth - additionalDistance);
        }
    }

    private double calculateYPosition(int floor)
    {
        return 700 - floor * 220;
    }

    public boolean onGroundFloor(int index)
    {
        boolean result;
        int windowsToIndex = Building.WINDOWS_PER_FLOOR - 1;

        if(this.stage == Game.INITIAL_STAGE && index < windowsToIndex) {
            result = true;
        } else {
            result = false;
        }

        return result;
    }

    public boolean onBuildingEdge(int index)
    {
        boolean result = false;
        if(this.stage == Game.INITIAL_STAGE && this.onGroundFloor(index)) {
            result = index % (Building.WINDOWS_PER_FLOOR - 1) == 0;
        }

        if(index == 0) {
            result = false;
        }

        return result;
    }

    public List<WindowFrame> getWindowFrames()
    {
        return this.windowFrames;
    }

    public int getStage()
    {
        return this.stage;
    }

    public void setStage(int stage)
    {
        this.stage = stage;
    }

}
