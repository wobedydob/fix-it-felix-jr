package com.github.fixitfelixjr;

import com.github.hanyaeger.api.Timer;

public class TimeEvent extends Timer
{

    private Runnable eventAction;
    private boolean repeat;

    public TimeEvent(final long intervalInMilliseconds, Runnable eventAction)
    {
        super(intervalInMilliseconds);
        this.eventAction = eventAction;
        this.repeat = false;
    }

    public TimeEvent(final long intervalInMilliseconds, Runnable eventAction, boolean repeat)
    {
        super(intervalInMilliseconds);
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
