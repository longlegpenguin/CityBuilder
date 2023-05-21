package controller.listeners;

import controller.util.Property;
import model.common.Coordinate;

public abstract class ServiceListener {

    protected final Property property;

    public ServiceListener(Property property) {
        this.property = property;
    }

    /**
     * Update the game model.
     * @param coordinate Coordinate of the clicked place.
     */
    public abstract void update(Coordinate coordinate);
}
