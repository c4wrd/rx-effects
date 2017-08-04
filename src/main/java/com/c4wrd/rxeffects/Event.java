package com.c4wrd.rxeffects;

public abstract class Event
{
    /**
     * Returns payload associated with this event. Not necessary for all events.
     */
    Object getPayload() {
        return null;
    }
}
