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

    private Building() { windows = new ArrayList<>(); }

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
    }

    private double calculateXPosition(int windowNum) {
        return 300 + windowNum * 100;
    }

    private double calculateYPosition(int floor) {
        return 750 - floor * 180;
    }

    public List<Window> getWindows() {
        return windows;
    }

}
