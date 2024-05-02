package com.github.fixitfelixjr.entities;

import com.github.fixitfelixjr.Game;
import com.github.fixitfelixjr.TimeEvent;
import com.github.fixitfelixjr.WindowRepairListener;
import com.github.fixitfelixjr.entities.powerups.PiePowerUp;
import com.github.fixitfelixjr.entities.powerups.PowerUp;
import com.github.fixitfelixjr.scenes.LevelScene;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.TimerContainer;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a window frame in the game, holding a window, a platform, and potentially a power-up or an NPC.
 * This class extends {@link DynamicSpriteEntity} to provide visual and interactive elements within the game.
 * The window frame serves as a core element in the game where different interactions can occur, such as NPCs appearing
 * or power-ups being granted.
 *
 * <p>Instances of this class are responsible for managing the visual representation and interactions related to
 * the window frame, including its associated window and platform, and any entities that may appear on it.
 */
public class WindowFrame extends DynamicSpriteEntity
{
    public static final String SPRITE_IMAGE = "sprites/window_frame.png";
    public static final double WIDTH = 24;
    public static final double HEIGHT = 43;
    public static final Size SIZE = new Size(WIDTH * LevelScene.SPRITE_SIZE_APPLIER, HEIGHT * LevelScene.SPRITE_SIZE_APPLIER);
    public final static int[] SPRITE_ROWS_COLS = {1, 3};
    public static final int NEARBY_WINDOW_THRESHOLD = 70;

    private Window window;
    private Platform platform;
    private PowerUp powerUp = null;
    private NPC npc = null;

    /**
     * Creates a new window frame with a specified position.
     *
     * @param position the initial position of the window frame within the game scene.
     */
    public WindowFrame(Coordinate2D position)
    {
        super(SPRITE_IMAGE, position, SIZE, SPRITE_ROWS_COLS[0], SPRITE_ROWS_COLS[1]);

        // Create and place the platform beneath the window
        Coordinate2D platformPosition = new Coordinate2D(position.getX(), position.getY() + (HEIGHT * LevelScene.SPRITE_SIZE_APPLIER) - 21);
        this.platform = new Platform(platformPosition);
        Game.getInstance().getLevelScene().addEntity(this.platform);

        // Create and place the window
        Coordinate2D windowPosition = new Coordinate2D(position.getX(), position.getY());
        this.window = new Window(windowPosition);
        Game.getInstance().getLevelScene().addEntity(this.window);
    }

    /**
     * Checks if there is a power-up associated with this window frame.
     *
     * @return true if there is a power-up, false otherwise.
     */
    public boolean hasPowerUp()
    {
        return this.powerUp != null;
    }

    /**
     * Checks if there is an NPC associated with this window frame.
     *
     * @return true if there is an NPC, false otherwise.
     */
    public boolean hasNPC()
    {
        return this.npc != null;
    }

    /**
     * Gets the window associated with this frame.
     *
     * @return the window.
     */
    public Window getWindow()
    {
        return this.window;
    }

    /**
     * Gets the platform associated with this frame.
     *
     * @return the platform.
     */
    public Platform getPlatform()
    {
        return this.platform;
    }

    /**
     * Gets the power-up associated with this frame, if any.
     *
     * @return the power-up, or null if none exists.
     */
    public PowerUp getPowerUp()
    {
        return this.powerUp;
    }

    /**
     * Gets the NPC associated with this frame, if any.
     *
     * @return the NPC, or null if none exists.
     */
    public NPC getNPC()
    {
        return this.npc;
    }

    /**
     * Sets the window for this frame.
     *
     * @param window the new window to associate with this frame.
     */
    public void setWindow(Window window)
    {
        this.window = window;
    }

    /**
     * Sets the platform for this frame.
     *
     * @param platform the new platform to associate with this frame.
     */
    public void setPlatform(Platform platform)
    {
        this.platform = platform;
    }

    /**
     * Sets the power-up for this frame.
     *
     * @param powerUp the new power-up to associate with this frame.
     */
    public void setPowerUp(PowerUp powerUp)
    {
        this.powerUp = powerUp;
    }

    /**
     * Sets the NPC for this frame.
     *
     * @param npc the new NPC to associate with this frame.
     */
    public void setNPC(NPC npc)
    {
        this.npc = npc;
    }
}
