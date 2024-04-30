package com.github.fixitfelixjr;

import com.github.hanyaeger.api.Timer;

public class TimeEvent extends Timer
{

    private Runnable eventAction;
    private long repeatInterval;
    private boolean repeat;

    public TimeEvent(final long intervalInSeconds, Runnable eventAction)
    {
        super(intervalInSeconds * 1000); // convert seconds to milliseconds
        this.eventAction = eventAction;
        this.repeat = false;
    }

    public TimeEvent(final long intervalInSeconds, Runnable eventAction, boolean repeat)
    {
        super(intervalInSeconds * 1000); // convert seconds to milliseconds
        this.eventAction = eventAction;
        this.repeat = repeat;
    }

    @Override
    public void onAnimationUpdate(long timestamp)
    {
        eventAction.run();

        if (!repeat) {
            this.remove();
        }

        if (repeat) {
            reset();
        }
    }
}
