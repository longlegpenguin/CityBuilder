package controller.util;
import static controller.util.Event.*;
public enum GameMode {

    SELECTION_MODE(Event.NONE),
    RESIDENTIAL_MODE(ZONE),
    INDUSTRIAL_MODE(ZONE),
    COMMERCIAL_MODE(ZONE),
    ROAD_MODE(FACILITY),
    POLICE_MODE(FACILITY),
    STADIUM_MODE(FACILITY),
    SCHOOL_MODE(FACILITY),
    UNIVERSITY_MODE(FACILITY),
    FOREST_MODE(FACILITY),
    UPGRADE_MODE(UPGRADE),
    DEMOLISH_MODE(DEMOLISH);
    private final Event event;
    GameMode(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }
}
