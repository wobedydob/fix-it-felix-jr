package com.github.fixitfelixjr.entities;

import com.github.fixitfelixjr.scenes.LevelScene;
import com.github.hanyaeger.api.Coordinate2D;
import java.util.ArrayList;
import java.util.List;

public class Building
{

    public static final String SPRITE_IMAGE = "sprites/building.png";
    public static final int FLOORS = 5;
    public static final int WINDOWS_PER_FLOOR = 5;
    public static final int MIDDLE_WINDOW_INDEX = 2;

    private static final Building instance = new Building();
    private List<Window> windows;
    private List<Platform> platforms;

    private Building() { windows = new ArrayList<>(); platforms = new ArrayList<>(); }

    public static Building getInstance() {
        return instance;
    }

    public void createWindows(LevelScene scene) {
        // Code om ramen te creëren, exclusief de plek van de deur.
        for (int floor = 0; floor < FLOORS; floor++) {
            for (int windowNum = 0; windowNum < WINDOWS_PER_FLOOR; windowNum++) {
                // Uitsluiten van de deur positie
                if (floor == 0 && windowNum == MIDDLE_WINDOW_INDEX) continue;

                Coordinate2D position = new Coordinate2D(calculateXPosition(windowNum), calculateYPosition(floor));
                Window window = new Window(position);
                windows.add(window);
                scene.addEntity(window);
            }
        }
        createPlatforms(scene);
    }

    private void createPlatforms(LevelScene scene) {
        for (Window window : windows) {
            Coordinate2D platformPosition = new Coordinate2D(window.getPosition().getX() - 15, window.getPosition().getY() + 80);
            Platform platform = new Platform(platformPosition);
            platforms.add(platform);
            scene.addEntity(platform);
        }
    }

    private double calculateXPosition(int windowNum) {
        double windowWidth = 160;
        double startX = 620;
        double additionalDistance = 0;

        if(windowNum == 0 || windowNum == 4){
            additionalDistance = 20;
        }

        if (windowNum < MIDDLE_WINDOW_INDEX) {
            return startX - (MIDDLE_WINDOW_INDEX - windowNum) * (windowWidth - additionalDistance);
        } else {
            return startX + (windowNum - MIDDLE_WINDOW_INDEX) * (windowWidth - additionalDistance);
        }
    }


    private double calculateYPosition(int floor) {
        return 765 - floor * 220;
    }

    public List<Window> getWindows() {
        return windows;
    }

}
