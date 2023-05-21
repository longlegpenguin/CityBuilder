package controller;

import controller.listeners.ServiceListener;
import controller.util.Event;
import model.common.Coordinate;

import java.util.HashMap;
import java.util.Map;

public class Publisher {
    Map<Event, ServiceListener> listeners;

    public Publisher() {
        listeners = new HashMap<>();
    }

    /**
     * Register listeners to events
     * @param event Event
     * @param serviceListener ServiceListener
     */
    public void register(Event event, ServiceListener serviceListener) {
        listeners.put(event, serviceListener);
    }

    /**
     * Notifies according to the event the listener.
     * @param event Event
     * @param coordinate Coordinate
     */
    public void notify(Event event, Coordinate coordinate) {
        listeners.get(event).update(coordinate);
    }
}
