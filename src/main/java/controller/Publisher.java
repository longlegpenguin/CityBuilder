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

    public void register(Event event, ServiceListener serviceListener) {
        listeners.put(event, serviceListener);
    }

    public void deregister(Event event, ServiceListener serviceListener) {
        listeners.remove(event, serviceListener);
    }

    public void notify(Event event, Coordinate coordinate) {
        listeners.get(event).update(coordinate);
    }
}
