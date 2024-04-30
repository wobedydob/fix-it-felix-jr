package com.github.fixitfelixjr;

import com.github.hanyaeger.api.Timer;

/**
 * TimeEvent manages a timer event that can be configured to execute at specified intervals.
 * The timer can be set to either repeat or execute just once based on the constructor used.
 */
public class TimeEvent extends Timer {

    private Runnable eventAction; // The action to be performed on each interval
    private boolean repeat; // Determines if the timer should repeat

    /**
     * Constructs a TimeEvent that will execute once after the specified interval.
     *
     * @param intervalInMilliseconds The interval in milliseconds after which the event action is executed.
     * @param eventAction The runnable action that will be executed by this timer event.
     */
    public TimeEvent(final long intervalInMilliseconds, Runnable eventAction) {
        super(intervalInMilliseconds);
        this.eventAction = eventAction;
        this.repeat = false;
    }

    /**
     * Constructs a TimeEvent that will execute repeatedly every specified interval or just once based on the repeat flag.
     *
     * @param intervalInMilliseconds The interval in milliseconds after which the event action is executed.
     * @param eventAction The runnable action that will be executed by this timer event.
     * @param repeat If true, the timer will reset itself and continue to execute at the set interval; if false, it will execute only once.
     */
    public TimeEvent(final long intervalInMilliseconds, Runnable eventAction, boolean repeat) {
        super(intervalInMilliseconds);
        this.eventAction = eventAction;
        this.repeat = repeat;
    }

    /**
     * Executes the associated action based on the timer's configuration.
     * If the timer is not set to repeat, it will remove itself after the action is executed.
     * If it is set to repeat, it will reset and continue.
     *
     * @param timestamp The current time in milliseconds at which the animation update is being called.
     */
    @Override
    public void onAnimationUpdate(long timestamp) {
        eventAction.run();

        if (!repeat) {
            this.remove();
        } else {
            reset();
        }
    }
}
