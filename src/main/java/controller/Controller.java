package controller;

import controller.listeners.DemolishListener;
import controller.listeners.FacilityBuildingListener;
import controller.listeners.SelectionListener;
import controller.listeners.ZoneBuildingListener;
import controller.util.Event;
import controller.util.GameMode;
import controller.util.Property;
import static controller.util.TimeMode.*;

import controller.util.TimeMode;
import model.GameModel;
import model.common.Coordinate;


public class Controller {
    private Property property;
    private Publisher service;

    public Controller(GameModel gameModel) {
        property = new Property(GameMode.SELECTION_MODE, gameModel, DAILY);
        service = new Publisher();
        registerListeners();
    }

    public Publisher getService() {
        return service;
    }

    public void setService(Publisher service) {
        this.service = service;
    }

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
    public void switchGameModeRequest(GameMode gameMode) {
        this.property.setGameMode(gameMode);
    }

    /**
     * Handles client request of time mode switching (Button click)
     * @param timeMode the time mode to switch to
     */
    public void switchTimeModeRequest(TimeMode timeMode) {
        this.property.setTimeMode(timeMode);
    }

    /**
     * Update the game model regularly with calculated time pass.
     * The actual day pass will be according to the time mode.
     * @param dayPass the pass of time in the unit of day.
     * @param callBack will be called after the handle of the request, can be null for defaults.
     */
    public void regularUpdateRequest(int dayPass, ICallBack callBack) {
        if (callBack != null) {
            property.setCallBack(callBack);
        } else {
            callBack = property.getCallBack();
        }
        this.property.getGameModel().regularUpdate(
                dayPass * property.getTimeMode().getMultiplier(),
                property.getCallBack()
        );
        callBack.updateDatePanel(property.getGameModel().getCurrentDate());
        callBack.updateCityStatisticPanel(property.getGameModel().getCityStatistics());
    }

    /**
     * Register all necessary event handlers to publisher.
     * There has to be one for each EVENTs.
     */
    private void registerListeners() {
        service.register(Event.ZONE, new ZoneBuildingListener(property));
        service.register(Event.FACILITY, new FacilityBuildingListener(property));
        service.register(Event.DEMOLISH, new DemolishListener(property));
        service.register(Event.SELECTION, new SelectionListener(property));
    }

    /**
     * Updates the tax rate.
     * @param newTaxRate the new rate.
     * @param callBack will be called after the handle of the request, can be null for defaults.
     */
    public void updateTaxRate(double newTaxRate, ICallBack callBack) {
        if (callBack != null) {
            property.setCallBack(callBack);
        } else {
            callBack = property.getCallBack();
        }
        property.getGameModel().updateTaxRate(newTaxRate);
        assert callBack != null;
        callBack.updateBudgetPanel(property.getGameModel().queryCityBudget());
    }
    public GameMode getGameMode() {
        return property.getGameMode();
    }
}