package model;

import model.city.CityRegistry;
import model.city.CityStatistics;
import model.common.*;
import model.exceptions.OperationException;
import model.facility.Facility;
import model.facility.Road;
import model.util.*;
import model.zone.Zone;
import model.zone.ZoneStatistics;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private final int rows, cols;
    private Buildable[][] map;
    private CityRegistry cityRegistry;
    private Date dateOfWorld;
    private Road masterRoad;

    public GameModel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        map = new Buildable[rows][cols];
        cityRegistry = new CityRegistry();
        dateOfWorld = new Date(1, Month.FEBRUARY, 2020);
        masterRoad = new Road(0,0,new Coordinate(rows-1, cols/2), new Dimension(1,1));
    }

    /**
     * Initializes the settings inside game model.
     */
    public void initialize() {
        addToMap(masterRoad);
    }

    /**
     * Gets everything on the map.
     * @return list of buildables.
     */
    public List<Buildable> getAllBuildable() {
        List<Buildable> buildableList = new ArrayList<>();
        buildableList.addAll(cityRegistry.getZones());
        buildableList.addAll(cityRegistry.getFacilities());
        buildableList.add(masterRoad);
        return buildableList;
    }

    /**
     * Updates the city's date
     * @param daysPassed the date passed since last update
     */
    public void timePassUpdate(int daysPassed) {
        // TODO wtf where is this method shit!!!
//        dateOfWorld.addDay(daysPassed);
    }

    /**
     * Adds the zone to the city.
     * @param zone the zone reference to be added
     */
    public void addZone(Zone zone) throws OperationException {
        if (map[zone.getCoordinate().getRow()][zone.getCoordinate().getCol()] != null) {
            throw new OperationException("Fuck, already has something");
        }
        addToMap(zone);
        // TODO integrate the zone into city
        if (zone.getBuildableType() == BuildableType.INDUSTRIAL ||
            zone.getBuildableType() == BuildableType.COMMERCIAL) {
            SideEffect badZone = (SideEffect)zone;
            for (Zone z :
                    cityRegistry.getZones()) {
                badZone.effect(z);
            }
        }
        // TODO deduct money from the budget
//        cityRegistry.deductBalance(zone.getConstructionCost());
        // TODO call city registry add zone
        cityRegistry.addZone(zone);
    }
    /**
     * Adds the facility to the city.
     * @param facility the facility reference to be added
     */
    public void addFacility(Facility facility) throws OperationException {
        if (isPlotAvailable(facility)) {
            throw new OperationException("Fuck, already has something");
        }
        addToMap(facility);
        // TODO integrate the facility into city
        if (SideEffect.class.isAssignableFrom(facility.getClass())) {
            SideEffect badFacility = (SideEffect)facility;
            for (Zone z :
                    cityRegistry.getZones()) {
                badFacility.effect(z);
            }
        }
        // TODO deduct money from the budget
//        cityRegistry.deductBalance(zone.getConstructionCost());
        // TODO call city registry
        cityRegistry.addFacility(facility);
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
     * @param coordinate the coordinate of the zone for which satisfaction should be got
     * @return satisfaction
     */
    public ZoneStatistics queryZoneStatistics(Coordinate coordinate) {
        Zone z = (Zone)map[coordinate.getRow()][coordinate.getCol()];
        return z.getStatistics();
    }
    /**
     * Gets the budget of the whole city
     * @return budget
     */
    public Budget queryCityBudget() {
        // TODO call city registry
//        cityRegistry.getBudget();
        return null;
    }

    /**
     * Gets the current date in sense of the world of city.
     * @return the date.
     */
    public Date getCurrentDate() {
        return dateOfWorld;
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

    public String printMap() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (map[i][j] == null) {
                    sb.append("-");
                } else if (map[i][j].getBuildableType() == BuildableType.ROAD) {
                    sb.append("#");
                } else {
                    sb.append("$");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    private boolean isPlotAvailable(Buildable b) {
        return map[b.getCoordinate().getRow()][b.getCoordinate().getCol()] != null;
    }
}
