package com.github.fixitfelixjr.entities;

import com.github.fixitfelixjr.Game;
import com.github.fixitfelixjr.WindowRepairListener;
import com.github.fixitfelixjr.scenes.LevelScene;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 * The Building class represents a multi-floor building with specific navigation rules based on the floor and stage.
 * Each floor has a pre-defined number of windows, with special rules applied to the ground floor based on the building's stage.
 * Constants:
 * - FLOORS: Total number of floors in the building, set to 4.
 * - WINDOWS_PER_FLOOR: Number of windows per floor, set to 5.
 * - MIDDLE_WINDOW_INDEX: Index of the middle window on the bottom floor in the array of windows on a floor, set to 2.
 * Ground Floor Behavior:
 * - If the stage is set to 1, the number of navigable windows on the ground floor is reduced by one (from 5 to 4). This affects indices and navigation.
 * For the ground floor, the navigable index range is 0-3 instead of 0-4.
 * Navigation Adjustments:
 * - UP: When moving upwards in window indices on the ground floor, if the index is less than 2, it is decremented before moving up to account for the reduced window count.
 * This prevents going out of range and aligns with the indices of the floor above.
 * Example: Moving up from index 0 would usually go to index 5 in a typical setting, but with the stage 1 adjustment, it moves to index 4.
 * - DOWN: When moving downwards in window indices on the ground floor, if the index is less than 2, it is incremented after moving down to ensure correct positioning at the lower indices.
 * This adjustment keeps the index within the valid range, compensating for the reduced number of items on the ground floor.
 * Example: Moving down from index 4 would typically land at index 0. The adjustment ensures it aligns correctly with the reduced index range on the ground floor.
 * Note:
 * - The adjustments for UP and DOWN operations are only applied when the building is in stage 1 and only affect the ground floor navigation.
 */
public class Building
{

    public static final String SPRITE_IMAGE = "backgrounds/building.png";
    public static final int FLOORS = 4;
    public static final int WINDOWS_PER_FLOOR = 5;
    public static final int MIDDLE_WINDOW_INDEX = 2;

    private List<WindowFrame> windowFrames;
    private int stage;

    public Building(int stage)
    {
        this.stage = stage;
        this.windowFrames = new ArrayList<>();
    }

    public void createWindowFrames(LevelScene scene)
    {
        for (int floor = 0; floor < FLOORS; floor++) {
            for (int windowNum = 0; windowNum < WINDOWS_PER_FLOOR; windowNum++) {

                if (this.stage == Game.INITIAL_STAGE && floor == 0 && windowNum == MIDDLE_WINDOW_INDEX) continue;

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

    public WindowFrame getRandomAvailableWindowFrame()
    {
        WindowFrame windowFrame = this.getRandomWindowFrame();
        if (windowFrame.hasPowerUp() || windowFrame.hasNPC()) {
            return this.getRandomAvailableWindowFrame();
        }

        return windowFrame;
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
                System.out.println(windowFrame.getAnchorLocation());
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

    public int getStage()
    {
        return this.stage;
    }

    public void setStage(int stage)
    {
        this.stage = stage;
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

        if (direction == Direction.UP && index + Building.WINDOWS_PER_FLOOR < windowFrames.size()) {
            if (this.onGroundFloor(index) && index < 2) {
                index--;
            }
            index += Building.WINDOWS_PER_FLOOR;
            nearestWindow = windowFrames.get(index);
            return nearestWindow;
        }
        // TODO: fix magic numbers 3 (higher than ground floor indexes) and 6 (not the middle above door window)
        else if (direction == Direction.DOWN && index > 3 && index != 6) {
            index -= Building.WINDOWS_PER_FLOOR;
            if (this.onGroundFloor(index) && index < 2) {
                index++;
            }
            nearestWindow = windowFrames.get(index);
            return nearestWindow;
        }
        // TODO: fix magic numbers 0-14 (leftmost windows on each floor)
        else if (direction == Direction.LEFT && index != 0 && index != 4 && index != 9 && index != 14) {
            index--;
            if (!this.onBuildingEdge(index)) {
                nearestWindow = windowFrames.get(index);
            }
            return nearestWindow;
        }
        // TODO: fix magic numbers 3-18 (rightmost windows on each floor)
        else if (direction == Direction.RIGHT && index != 3 && index != 8 && index != 13 && index != 18) {
            index++;
            if (!this.onBuildingEdge(index)) {
                nearestWindow = windowFrames.get(index);
            }
            return nearestWindow;
        } else {
            return null;
        }
    }

//     TODO: workout these methods and introduce them above
//    public findNearestWindowHorizontally(){}
//    public findNearestWindowVertically(){}


}
