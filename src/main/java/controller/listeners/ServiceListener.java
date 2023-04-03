package controller.listeners;

import controller.util.Property;
import model.common.Coordinate;

public abstract class ServiceListener {

    protected final Property property;

    public ServiceListener(Property property) {
        this.property = property;
    }
    public abstract void update(Coordinate coordinate);
}
