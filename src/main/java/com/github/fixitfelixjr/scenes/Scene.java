package com.github.fixitfelixjr.scenes;

/**
 * The {@code Scene} interface defines the essential framework for scene management within an application,
 * particularly for applications that use multiple scenes or screens, such as video games or multi-screen applications.
 * This interface requires implementing classes to provide methods that manage scene-specific identifiers and backgrounds.
 *
 * <p>Implementing this interface ensures a standardized approach to retrieving uniquely identifying information
 * and visual backgrounds associated with each scene, which can be crucial for scene switching and rendering.
 */
public interface Scene
{

    /**
     * Retrieves the unique identifier for this scene. The identifier is used to distinguish between
     * different scenes within the application's scene management system. It can be used for tasks
     * such as scene switching or querying specific properties associated with a scene.
     *
     * @return an {@code int} that represents the unique ID of the scene.
     */
    public int getSceneId();

    /**
     * Provides the file path or a resource identifier for the background image or pattern associated with this scene.
     * This is used to set or retrieve the visual background displayed when the scene is active.
     *
     * <p>Implementing this method allows for dynamic changes to the scene's appearance and can aid in creating
     * visually cohesive transitions between different parts of the application.
     *
     * @return a {@code String} that represents the path or resource identifier of the scene's background.
     */
    public String getBackground();

}
