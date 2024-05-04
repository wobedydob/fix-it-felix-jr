package com.github.fixitfelixjr;

/**
 * The {@code WindowRepairListener} interface provides a mechanism for receiving notifications
 * about window repair events within an application. Implementers of this interface can receive
 * updates when certain actions, specifically window repairs, are completed.
 *
 * This interface is typically used in scenarios where various components of the application
 * might need to respond to the event of a window being repaired, such as updating the UI, logging
 * information, or enabling and disabling certain functionality.
 */
public interface WindowRepairListener
{

    /**
     * Called when a window repair event has been completed. Implementers of this interface
     * should override this method to define custom behavior that should occur immediately after
     * a window is repaired.
     *
     * <p>This method provides a way to ensure that specific actions are taken when the repair event occurs,
     * such as updating user interface components, triggering further business processes, or notifying other
     * parts of the application about the completion of the repair.
     */
    void onWindowRepaired();
}

