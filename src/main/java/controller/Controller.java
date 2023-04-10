package controller;

import controller.listeners.DemolishListener;
import controller.listeners.FacilityBuildingListener;
import controller.listeners.ZoneBuildingListener;
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
     * @param callBack will be called after the handle of the request, can be null for defaults.
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
        service.register(Event.FACILITY, new FacilityBuildingListener(property));
        service.register(Event.DEMOLISH, new DemolishListener(property));
    }

}