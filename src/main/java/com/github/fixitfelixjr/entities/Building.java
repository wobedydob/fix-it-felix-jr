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

    private static Building instance;
    private List<Window> windows;

    private Building() {
        windows = new ArrayList<>();
    }

    public static synchronized Building getInstance() {
        if (instance == null) {
            instance = new Building();
        }
        return instance;
    }

    public void addWindow(Window window) {
        windows.add(window);
    }

    public List<Window> getWindows() {
        return windows;
    }

    public void generateBuilding(LevelScene scene) {
        for (int floor = 0; floor < FLOORS; floor++) {
            for (int windowNum = 0; windowNum < WINDOWS_PER_FLOOR; windowNum++) {
                Window window = new Window(calculateXPosition(windowNum), calculateYPosition(floor));
                addWindow(window);
                scene.addEntity(window);
            }
        }
    }

    private double calculateXPosition(int windowNum) {
        return 300 + windowNum * 150;
    }

    private double calculateYPosition(int floor) {
        return 750 - floor * 180;
    }
}
