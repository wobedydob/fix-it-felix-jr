package com.github.fixitfelixjr.entities;

import com.github.fixitfelixjr.Game;
import com.github.fixitfelixjr.scenes.LevelScene;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.Direction;

import java.util.ArrayList;
import java.util.Arrays;
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
        for (int floor = 0; floor < FLOORS; floor++) {
            for (int windowNum = 0; windowNum < WINDOWS_PER_FLOOR; windowNum++) {

                if(this.stage == Game.INITIAL_STAGE && floor == 0 && windowNum == MIDDLE_WINDOW_INDEX) continue;

                Coordinate2D position = new Coordinate2D(calculateXPosition(windowNum), calculateYPosition(floor));
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

        if (this.stage == Game.INITIAL_STAGE && index < windowsToIndex) {
            result = true;
        } else {
            result = false;
        }

        return result;
    }

    public boolean onBuildingEdge(int index)
    {
        boolean result = false;
        if (this.stage == Game.INITIAL_STAGE && this.onGroundFloor(index)) {
            result = index % (Building.WINDOWS_PER_FLOOR - 1) == 0;
        }

        if (index == 0) {
            result = false;
        }

        return result;
    }

    public List<WindowFrame> getWindowFrames()
    {
        return this.windowFrames;
    }

    public WindowFrame getRandomWindowFrame()
    {
        return this.windowFrames.get(new java.util.Random().nextInt(this.windowFrames.size()));
    }

    public boolean canSpawnPowerUp()
    {
        // so only one power up can be spawned
        for (WindowFrame windowFrame : this.windowFrames) {
            if (windowFrame.getPowerUp() != null) {
                return false;
            }
        }
        return true;
    }

    public void clearPowerUps()
    {
        for (WindowFrame windowFrame : this.windowFrames) {
            windowFrame.setPowerUp(null);
        }
    }

    public int getStage()
    {
        return this.stage;
    }

    public void setStage(int stage)
    {
        this.stage = stage;
    }

    // fix for finding nearest window
    public WindowFrame findNearestWindow(Coordinate2D position)
    {
        List<WindowFrame> windowFrames = Building.getInstance().getWindowFrames();
        WindowFrame nearestWindow = null;
        double nearestDistance = Double.MAX_VALUE;

        for (WindowFrame windowFrame : windowFrames) {
            double distance = position.distance(windowFrame.getAnchorLocation());

            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestWindow = windowFrame;
            }
        }

        if (nearestDistance <= WindowFrame.NEARBY_WINDOW_THRESHOLD) {
            return nearestWindow;
        } else {
            return null;
        }
    }

    public WindowFrame findNearestWindow(Coordinate2D position, Direction direction)
    {
        Building building = Building.getInstance();
        List<WindowFrame> windowFrames = building.getWindowFrames();
        WindowFrame nearestWindow = findNearestWindow(position);
        int index = windowFrames.indexOf(nearestWindow);

        if (direction == Direction.UP && index + Building.WINDOWS_PER_FLOOR < windowFrames.size()) {
            if (building.onGroundFloor(index) && index < 2) {
                index--;
            }
            index += Building.WINDOWS_PER_FLOOR;
            nearestWindow = windowFrames.get(index);
            return nearestWindow;
        }
        // TODO: fix magic numbers 3 (higher than ground floor indexes) and 6 (not the middle above door window)
        else if (direction == Direction.DOWN && index > 3 && index != 6) {
            index -= Building.WINDOWS_PER_FLOOR;
            if (building.onGroundFloor(index) && index < 2) {
                index++;
            }
            nearestWindow = windowFrames.get(index);
            return nearestWindow;
        }
        // TODO: fix magic numbers 0-14 (leftmost windows on each floor)
        else if (direction == Direction.LEFT && index != 0 && index != 4 && index != 9 && index != 14) {
            index--;
            if (!building.onBuildingEdge(index)) {
                nearestWindow = windowFrames.get(index);
            }
            return nearestWindow;
        }
        // TODO: fix magic numbers 3-18 (rightmost windows on each floor)
        else if (direction == Direction.RIGHT && index != 3 && index != 8 && index != 13 && index != 18) {
            index++;
            if (!building.onBuildingEdge(index)) {
                nearestWindow = windowFrames.get(index);
            }
            return nearestWindow;
        } else {
            return null;
        }
    }



}
