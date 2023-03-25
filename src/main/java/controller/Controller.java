package controller;

import controller.util.Event;
import controller.util.GameMode;
import controller.util.Property;
import model.GameModel;
import model.util.Coordinate;


public class Controller {
    private GameModel gameModel;
    private GameMode gameMode;
    private Publisher service;

    public Controller(GameModel gameModel) {
        this.gameModel = gameModel;
        gameMode = GameMode.SELECTION_MODE;
        service = new Publisher();
        registerListeners();
    }
    /// ------------ GETTERS, SETTERS START-----------------
    public GameModel getGameModel() {
        return gameModel;
    }

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

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
    public void mouseClickRequest(Coordinate coordinate) {
        service.notify(gameMode.getEvent(), coordinate);
    }

    /**
     * Handles client request of mode switching (Button click)
     * @param gameMode the mode to switch to.
     */
    public void switchModeRequest(GameMode gameMode) {
        this.setGameMode(gameMode);
    }

    /**
     * Register all necessary event handlers to publisher.
     * There has to be one for each EVENTs.
     */
    private void registerListeners() {
        Property property = new Property(gameMode, gameModel);
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
