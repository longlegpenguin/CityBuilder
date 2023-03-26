package controller.util;
import static controller.util.Event.*;
public enum GameMode {

//    SELECTION_MODE,
//    RESIDENTIAL_MODE,
//    INDUSTRIAL_MODE,
//    COMMERCIAL_MODE,
//    ROAD_MODE,
//    POLICE_MODE,
//    STADIUM_MODE,
//    SCHOOL_MODE,
//    UNIVERSITY_MODE,
//    FOREST_MODE,
//    UPGRADE_MODE,
//    DEMOLISH_MODE
//    NONE(Event.NONE);

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
