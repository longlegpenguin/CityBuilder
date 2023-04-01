package model;

import model.facility.Facility;
import model.util.Budget;
import model.util.Coordinate;
import model.util.Dimension;
import model.util.Month;
import model.zone.Zone;

public class GameModel {
    private final int rows, cols;
    private Buildable[][] map;
    private CityRegistry cityRegistry;

    public GameModel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        map = new Buildable[rows][cols];
        cityRegistry = new CityRegistry();
    }

    /**
     * Updates the city's date
     * @param daysPassed the date passed since last update
     */
    public void update(int daysPassed) {
        // TODO call city registry
    }

    /**
     * Adds the zone to the city.
     * @param zone the zone reference to be added
     */
    public void addZone(Zone zone) {
        addToMap(zone);
        // TODO call city registry
    }
    /**
     * Adds the facility to the city.
     * @param facility the facility reference to be added
     */
    public void addFacility(Facility facility) {
        addToMap(facility);
        // TODO call city registry
    }

    /**
     * Updates city's tax rate
     * @param newTaxRate the new tax rate
     */
    public void updateTaxRate(double newTaxRate) {
        // TODO call city registry
    }

    /**
     * Gets the satisfaction of the whole city
     * @return satisfaction
     */
    public CityStatistics queryCityStatistics() {
        // TODO call city registry
        return null;
    }

    /**
     * Gets the satisfaction of a specified zone
     * @param zone the zone for which satisfaction should be got
     * @return satisfaction
     */
    public ZoneStatistics queryZoneStatistics(Zone zone) {
        // TODO call city registry
        return null;
    }
    /**
     * Gets the budget of the whole city
     * @return budget
     */
    public Budget queryCityBudget() {
        // TODO call city registry
        return null;
    }

    public Date getCurrentDate() {
        return new Date(1, Month.APRIL,21);
    }

    // ------------ Helper ---------------------------
    private void addToMap(Buildable buildable) {
        Coordinate coordinate = buildable.getCoordinate();
        Dimension dimension = buildable.getDimension();

        for (int i = 0; i < dimension.getHeight(); i++) { // rows
            for (int j = 0; j < dimension.getWidth(); j++) { // cols
                map[coordinate.getRow()+i][coordinate.getCol()+j] = buildable;
            }
        }

    }
}
