package com.github.fixitfelixjr;

import com.github.hanyaeger.api.Timer;

/**
 * De TimeEvent klasse maakt gebruik van de Timer functionaliteit om gebeurtenissen uit te voeren
 * na een specifieke tijd.
 */
public class TimeEvent extends Timer
{

    private Runnable eventAction;

    /**
     * Constructor voor een TimeEvent.
     *
     * @param intervalInSeconds het interval in seconden waarna de eventAction uitgevoerd moet worden
     * @param eventAction de actie die uitgevoerd moet worden wanneer de timer afloopt
     */
    public TimeEvent(final long intervalInSeconds, Runnable eventAction) {
        super(intervalInSeconds * 1000); // Omzetten van seconden naar milliseconden
        this.eventAction = eventAction;
    }

    /**
     * Deze methode wordt aangeroepen elke keer dat de timer het ingestelde interval bereikt.
     *
     * @param timestamp de huidige tijd in nanoseconden
     */
    @Override
    public void onAnimationUpdate(long timestamp) {
        eventAction.run();
        this.remove(); // Verwijder deze timer na het uitvoeren om hem niet opnieuw te laten gebeuren
    }
}
