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
        dateOfWorld.addDay(daysPassed);
    }

    /**
     * Adds the zone to the city.
     * Updates its effect on satisfaction of other zones as well.
     * @param zone the zone reference to be added
     */
    public void addZone(Zone zone) throws OperationException {

        if (map[zone.getCoordinate().getRow()][zone.getCoordinate().getCol()] != null) {
            throw new OperationException("Fuck, already has something");
        }

        addToMap(zone);

        if (SideEffect.class.isAssignableFrom(zone.getClass())) {
            SideEffect badZone = (SideEffect)zone;
            for (Zone z :
                    cityRegistry.getZones()) {
                badZone.effect(z);
            }
        }
        cityRegistry.deductBalance(zone.getConstructionCost());
        cityRegistry.addZone(zone);
        updateIndustryCommercialBalanceSatisfactionIndex();
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

        if (SideEffect.class.isAssignableFrom(facility.getClass())) {
            SideEffect badFacility = (SideEffect)facility;
            for (Zone z :
                    cityRegistry.getZones()) {
                badFacility.effect(z);
            }
        }
        cityRegistry.deductBalance(facility.getOneTimeCost());
        cityRegistry.addMaintenanceFee(facility.getMaintenanceFee());
        cityRegistry.addFacility(facility);
    }

    /**
     * removes a buildable completely from the city.
     * @param coordinate coordinate of the buildable to remove
     * @throws OperationException if removing empty slot.
     */
    public void removeBuildable(Coordinate coordinate) throws OperationException {

        Buildable bad = map[coordinate.getRow()][coordinate.getCol()];
        if (bad == null) {
            throw new OperationException("Removing empty");
        }
        removeFromMap(bad);

        if (SideEffect.class.isAssignableFrom(bad.getClass())) {
            SideEffect badBuildable = (SideEffect)bad;
            for (Zone z :
                    cityRegistry.getZones()) {
                badBuildable.reverseEffect(z);
            }
        }
        cityRegistry.addBalance(bad.getConstructionCost() * Constants.RETURN_RATE);

        if (Zone.class.isAssignableFrom(bad.getClass())) {
            cityRegistry.removeZone((Zone)bad);
            updateIndustryCommercialBalanceSatisfactionIndex();
        } else {
            cityRegistry.removeFacility((Facility) bad);
        }

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
        return cityRegistry.getCityStatistics();
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

    private void removeFromMap(Buildable buildable) {
        Coordinate coordinate = buildable.getCoordinate();
        Dimension dimension = buildable.getDimension();

        for (int i = 0; i < dimension.getHeight(); i++) { // rows
            for (int j = 0; j < dimension.getWidth(); j++) { // cols
                map[coordinate.getRow()+i][coordinate.getCol()+j] = null;
            }
        }
    }

    /**
     * Updates all zones in city, as result of new industry commercial balance.
     */
    private void updateIndustryCommercialBalanceSatisfactionIndex() {
        CityStatistics cityStatistics = cityRegistry.getCityStatistics();
        int diff = Math.abs(cityStatistics.getNrIndustrialZones() - cityStatistics.getNrCommercialZones());
        double newVal = + 1.0 / (diff == 0 ? 1.0 : diff);
        for (Zone zone :
                cityRegistry.getZones()) {
            zone.updateComZoneBalanceEffect(newVal);
        }
    }

    /**
     * @return a string, whose prints out will be the map.
     */
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



/**
 * Removes the zone from the city.
 * @param coordinate the coordinate of zone reference to be removed.
 */
//    public void removeZone(Coordinate coordinate) throws OperationException {
//        Buildable bad = map[coordinate.getRow()][coordinate.getCol()];
//        if (bad == null) {
//            throw new OperationException("Removing empty");
//        }
//        Zone zone = (Zone)bad;
//        removeFromMap(zone);
//        // TODO integrate the zone into city
//        if (zone.getBuildableType() == BuildableType.INDUSTRIAL ||
//                zone.getBuildableType() == BuildableType.COMMERCIAL) {
//            SideEffect badZone = (SideEffect)zone;
//            for (Zone z :
//                    cityRegistry.getZones()) {
//                badZone.reverseEffect(z);
//            }
//        }
//        // TODO deduct money from the budget
////        cityRegistry.addBalance(zone.getConstructionCost() * Constants.RETURN_RATE);
//        // TODO call city registry remove zone
//        cityRegistry.removeZone(zone);
//    }

