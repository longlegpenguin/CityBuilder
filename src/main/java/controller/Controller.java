package controller;

import controller.util.Event;
import controller.util.GameMode;
import controller.util.Property;
import model.GameModel;
import model.common.Coordinate;


public class Controller {
    private Property property;
    private Publisher service;

    public Controller(GameModel gameModel) {
        property = new Property(GameMode.SELECTION_MODE, gameModel);
        service = new Publisher();
        registerListeners();
    }
    /// ------------ GETTERS, SETTERS START-----------------

    public Publisher getService() {
        return service;
    }

    public void setService(Publisher service) {
        this.service = service;
    }
    /// ------------ GETTERS, SETTERS END -----------------

    /**
     * Handles every mouse click (on grid system) from the user
     * @param coordinate the coordinate of the cell the user click in grid sense.
     */
    public void mouseClickRequest(Coordinate coordinate, ICallBack callBack) {
        if (callBack != null) {
            property.setCallBack(callBack);
        }
        service.notify(property.getGameMode().getEvent(), coordinate);
    }

    /**
     * Handles client request of mode switching (Button click)
     * @param gameMode the mode to switch to.
     */
    public void switchModeRequest(GameMode gameMode) {
        this.property.setGameMode(gameMode);
    }

    /**
     * Register all necessary event handlers to publisher.
     * There has to be one for each EVENTs.
     */
    private void registerListeners() {
        service.register(Event.ZONE, new ZoneBuildingListener(property));
    }

}

/*
 * Handles client request of constructing new zones.
 * @param coordinate the coordinate at where new zone should be created.
 */
//    public void newZoneRequest(Coordinate coordinate) {
//        service.notify(Event.ZONE, coordinate);
//    }
