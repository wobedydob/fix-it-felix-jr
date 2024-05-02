package com.github.fixitfelixjr.entities;

import com.github.fixitfelixjr.Game;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.Direction;

import java.util.ArrayList;
import java.util.List;

public class Building
{
    public static final String SPRITE_IMAGE = "backgrounds/building.png";
    public static final int FLOORS = 1;
    public static final int WINDOWS_PER_FLOOR = 5;
    public static final int BUILDING_ENTRANCE_INDEX = 2;

    private List<WindowFrame> windowFrames;
    private int stage;

    public Building(int stage)
    {
        this.stage = stage;
        this.windowFrames = new ArrayList<>();
    }

    public void createWindowFrames()
    {
        for (int floor = 0; floor < FLOORS; floor++) {

            for (int windowNum = 0; windowNum < WINDOWS_PER_FLOOR; windowNum++) {

                if (this.stage == Game.INITIAL_STAGE && floor == 0 && windowNum == BUILDING_ENTRANCE_INDEX) continue;
                Coordinate2D position = new Coordinate2D(calculateXPosition(windowNum), calculateYPosition(floor));
                createWindowFrame(position);
            }

        }
    }

    public void createWindowFrame(Coordinate2D position)
    {
        WindowFrame windowFrame = new WindowFrame(position);
        Game.getInstance().getLevelScene().addEntity(windowFrame);
        this.windowFrames.add(windowFrame);
    }

    public WindowFrame findNearestWindow(Coordinate2D position)
    {
        List<WindowFrame> windowFrames = this.windowFrames;
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
        List<WindowFrame> windowFrames = this.getWindowFrames();
        WindowFrame nearestWindow = findNearestWindow(position);
        int index = windowFrames.indexOf(nearestWindow);

        // TODO: understand this....

        int checkedIndex = WINDOWS_PER_FLOOR - 1; // because we start from 0
        if (this.stage == Game.INITIAL_STAGE) {
            checkedIndex = WINDOWS_PER_FLOOR - 2; // because there is a door in the middle of the bottom floor
        }

        // calculate the index of the first window on the current floor
        int floorIndex = index / WINDOWS_PER_FLOOR;
        int bottomWindows = this.stage == Game.INITIAL_STAGE ? WINDOWS_PER_FLOOR - 1 : WINDOWS_PER_FLOOR;
        int firstWindowIndex = floorIndex * WINDOWS_PER_FLOOR + bottomWindows;

        // calculate the index of the last window on the current floor
        int lastWindowIndex = firstWindowIndex + WINDOWS_PER_FLOOR - 1;

        if (direction == Direction.UP && index + Building.WINDOWS_PER_FLOOR < windowFrames.size()) {
            if (this.onGroundFloor(index) && index < 2) {
                index--;
            }
            index += Building.WINDOWS_PER_FLOOR;
            nearestWindow = windowFrames.get(index);
            return nearestWindow;
        } else if (direction == Direction.DOWN && index > checkedIndex) {

            if (this.stage == Game.INITIAL_STAGE && index == (BUILDING_ENTRANCE_INDEX + WINDOWS_PER_FLOOR - 1)) {
                return null;
            }

            index -= Building.WINDOWS_PER_FLOOR;
            if (this.onGroundFloor(index) && index < BUILDING_ENTRANCE_INDEX) {
                index++;
            }
            nearestWindow = windowFrames.get(index);
            return nearestWindow;

        } else if (direction == Direction.LEFT && index != firstWindowIndex && index % WINDOWS_PER_FLOOR != bottomWindows) {

            index--;
            if (!this.onBuildingEdge(index)) {
                nearestWindow = windowFrames.get(index);
            }
            return nearestWindow;

        } else if (direction == Direction.RIGHT && index != lastWindowIndex && index % WINDOWS_PER_FLOOR != bottomWindows - 1) {

            index++;
            if (!this.onBuildingEdge(index)) {
                nearestWindow = windowFrames.get(index);
            }
            return nearestWindow;

        } else {
            return null;
        }
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

    public boolean areAllWindowsRepaired()
    {
        for (WindowFrame windowFrame : this.windowFrames) {
            if (!windowFrame.getWindow().isRepaired()) {
                return false;
            }
        }
        return true;
    }

    public boolean canSpawnPowerUp()
    {
        // so only one power up can be spawned
        for (WindowFrame windowFrame : this.windowFrames) {
            if (windowFrame.hasPowerUp()) {
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

    public boolean canSpawnNPC()
    {
        // so only one npc can be spawned
        for (WindowFrame windowFrame : this.windowFrames) {
            if (windowFrame.hasNPC()) {
                return false;
            }
        }
        return true;
    }

    public void clearNPCs()
    {
        for (WindowFrame windowFrame : this.windowFrames) {
            windowFrame.setNPC(null);
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

        if (windowNum < BUILDING_ENTRANCE_INDEX) {
            return startX - (BUILDING_ENTRANCE_INDEX - windowNum) * (windowWidth - additionalDistance);
        } else {
            return startX + (windowNum - BUILDING_ENTRANCE_INDEX) * (windowWidth - additionalDistance);
        }
    }

    private double calculateYPosition(int floor)
    {
        return 700 - floor * 220;
    }

    public WindowFrame getRandomWindowFrame()
    {
        return this.windowFrames.get(new java.util.Random().nextInt(this.windowFrames.size()));
    }

    public WindowFrame getRandomAvailableWindowFrame()
    {
        WindowFrame windowFrame = this.getRandomWindowFrame();
        if (windowFrame.hasPowerUp() || windowFrame.hasNPC()) {
            return this.getRandomAvailableWindowFrame();
        }

        return windowFrame;
    }

    public List<WindowFrame> getWindowFrames()
    {
        return this.windowFrames;
    }

    public int getStage()
    {
        return this.stage;
    }

    public void setWindowFrames(List<WindowFrame> windowFrames)
    {
        this.windowFrames = windowFrames;
    }

    public void setStage(int stage)
    {
        this.stage = stage;
    }
}
