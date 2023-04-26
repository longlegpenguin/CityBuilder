package model;

import model.city.CityRegistry;
import model.city.CityStatistics;
import model.city.SocialSecurity;
import model.common.*;
import model.exceptions.OperationException;
import model.facility.Education;
import model.facility.Facility;
import model.facility.Forest;
import model.facility.Road;
import model.util.*;
import model.zone.Zone;
import model.zone.ZoneStatistics;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private final int rows, cols;
    public static Buildable[][] map;
    private final CityRegistry cityRegistry;
    private final CityStatistics cityStatistics;
    private final Date dateOfWorld;
    private final List<Road> masterRoads;
    private List<Zone> underConstructions;
    private List<Forest> youthForest;
    private List<Education> educations;
    private Date lastTaxDate;
    private SocialSecurity socialSecurity;

    public GameModel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        map = new Buildable[rows][cols];
        cityStatistics = new CityStatistics(new Budget(1000000, 0.3));
        cityRegistry = new CityRegistry(cityStatistics);
        dateOfWorld = new Date(1, Month.FEBRUARY, 2020);
        lastTaxDate = dateOfWorld;
        masterRoads = new ArrayList<>();
        underConstructions = new ArrayList<>();
        youthForest = new ArrayList<>();
        educations = new ArrayList<>();
        socialSecurity = new SocialSecurity(cityStatistics);
    }

    /**
     * Initializes the settings inside game model.
     */
    public void initialize() {
        Road road = new Road(0, 0, new Coordinate(rows - 1, cols / 2), new Dimension(1, 1));
        masterRoads.add(road);
        addToMap(road);
    }

    /**
     * Gets everything on the map.
     *
     * @return list of buildables.
     */
    public List<Buildable> getAllBuildable() {
        List<Buildable> buildableList = new ArrayList<>();
        buildableList.addAll(cityRegistry.getZones());
        buildableList.addAll(cityRegistry.getFacilities());
        buildableList.addAll(masterRoads);
        return buildableList;
    }

    /**
     * Updates the city's date
     *
     * @param daysPassed the days passed since last update
     */
    public void timePassUpdate(int daysPassed) {
        dateOfWorld.addDay(daysPassed);
    }

    /**
     * Adds the zone to the city.
     * Updates its effect on satisfaction of other zones as well.
     *
     * @param zone the zone reference to be added
     */
    public void addZone(Zone zone) throws OperationException {

        if (!isPlotAvailable(zone)) {
            throw new OperationException("Fuck, already has something");
        }
        addToMap(zone);

        effectExists(zone);
        beEffectedByExisting(zone);
        cityStatistics.getBudget().deductBalance(zone.getConstructionCost());
        cityRegistry.addZone(zone);
        updateIndustryCommercialBalanceSatisfactionIndex();
        underConstructions.add(zone);
    }

    /**
     * Applies effects on all existing zones if it has side effect
     *
     * @param buildable the effective buildable
     */
    private void effectExists(Buildable buildable) {
        if (hasSideEffect(buildable)) {
            SideEffect bad = (SideEffect) buildable;
            for (Zone z :
                    cityRegistry.getZones()) {
                bad.effect(z, map);
            }
        }
    }

    /**
     * Applies effects by all existing buildables if there is effect.
     *
     * @param zone the zone to be effected.
     */
    private void beEffectedByExisting(Zone zone) {
        for (Buildable existingBad :
                getAllBuildable()) {
            if (hasSideEffect(existingBad)) {
                ((SideEffect) existingBad).effect(zone, map);
            }
        }
    }

    private static boolean hasSideEffect(Buildable buildable) {
        return SideEffect.class.isAssignableFrom(buildable.getClass());
    }

    /**
     * Adds the facility to the city.
     *
     * @param facility the facility reference to be added
     */
    public void addFacility(Facility facility) throws OperationException {

        if (!isPlotAvailable(facility)) {
            throw new OperationException("Fuck, already has something");
        }

        addToMap(facility);

        effectExists(facility);
        cityStatistics.getBudget().deductBalance(facility.getOneTimeCost());
        cityStatistics.getBudget().addMaintenanceFee(facility.getMaintenanceFee());
        cityRegistry.addFacility(facility);

        if (facility.getBuildableType() == BuildableType.FOREST) {
            youthForest.add((Forest) facility);
        }
        if (facility.getBuildableType() == BuildableType.SCHOOL ||
            facility.getBuildableType() == BuildableType.UNIVERSITY) {
            educations.add((Education) facility);
        }
    }

    /**
     * removes a buildable completely from the city.
     *
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
            SideEffect badBuildable = (SideEffect) bad;
            for (Zone z :
                    cityRegistry.getZones()) {
                badBuildable.reverseEffect(z, map);
            }
        }
        cityStatistics.getBudget().addBalance(bad.getConstructionCost() * Constants.RETURN_RATE);

        if (Zone.class.isAssignableFrom(bad.getClass())) {
            cityRegistry.removeZone((Zone) bad);
            updateIndustryCommercialBalanceSatisfactionIndex();
        } else {
            cityRegistry.removeFacility((Facility) bad);
            cityStatistics.getBudget().deductMaintenanceFee(((Facility) bad).getMaintenanceFee());
        }

    }

    /**
     * Updates city's tax rate
     * @param newTaxRate the new tax rate
     */
    public void updateTaxRate(double newTaxRate) {
        cityRegistry.updateTaxRate(newTaxRate);
    }

    /**
     * Gets the satisfaction of the whole city
     *
     * @return satisfaction
     */
    public CityStatistics queryCityStatistics() {
        return cityStatistics;
    }

    /**
     * Gets the satisfaction of a specified zone
     *
     * @param coordinate the coordinate of the zone for which satisfaction should be got
     * @return satisfaction
     */
    public ZoneStatistics queryZoneStatistics(Coordinate coordinate) {
        Zone z = (Zone) map[coordinate.getRow()][coordinate.getCol()];
        return z.getStatistics();
    }

    /**
     * Gets the budget of the whole city
     *
     * @return budget
     */
    public Budget queryCityBudget() {
        return cityStatistics.getBudget();
    }

    /**
     * Gets the current date in sense of the world of city.
     *
     * @return the date.
     */
    public Date getCurrentDate() {
        return dateOfWorld;
    }

    private void addToMap(Buildable buildable) {
        Coordinate coordinate = buildable.getCoordinate();
        Dimension dimension = buildable.getDimension();

        for (int i = 0; i < dimension.getHeight(); i++) { // rows
            for (int j = 0; j < dimension.getWidth(); j++) { // cols
                map[coordinate.getRow() + i][coordinate.getCol() + j] = buildable;
            }
        }
    }

    private void removeFromMap(Buildable buildable) {
        Coordinate coordinate = buildable.getCoordinate();
        Dimension dimension = buildable.getDimension();

        for (int i = 0; i < dimension.getHeight(); i++) { // rows
            for (int j = 0; j < dimension.getWidth(); j++) { // cols
                map[coordinate.getRow() + i][coordinate.getCol() + j] = null;
            }
        }
    }

    /**
     * Updates all zones in city, as result of new industry commercial balance.
     */
    private void updateIndustryCommercialBalanceSatisfactionIndex() {
        int diff = Math.abs(cityStatistics.getNrIndustrialZones() - cityStatistics.getNrCommercialZones());
        double newVal = 1.0 / (diff == 0 ? 1.0 : diff);
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
        return map[b.getCoordinate().getRow()][b.getCoordinate().getCol()] == null;
    }

    /**
     * TODO
     *
     * @param dayPass the day passed since last updates
     */
    public void regularUpdate(int dayPass) {
        dateOfWorld.addDay(dayPass);
        filterConstructed();
// TODO
//        add new guys (use human manufacture)
        socialSecurity.census();
        if (lastTaxDate.dateDifference(dateOfWorld).get("year") >= 1) {
            updateCityBalance();
        }
        updateForests();

    }

    /**
     * Collects tax and pays the maintenance fee as well as pension
     * Records the tax rate.
     */
    private void updateCityBalance() {
        int revenue = calculateRevenue();
        int spend = calculateSpend();
        cityRegistry.updateBalance(revenue-spend);
        socialSecurity.appendTaxRecord();
    }

    private int calculateSpend() {
        int spend = 0;
        for (Facility facility: cityRegistry.getFacilities()) {
            spend += facility.getMaintenanceFee();
        }
        spend += socialSecurity.payPension();
        return spend;
    }

    private int calculateRevenue() {
        int revenue = 0;
        for (Zone zone:
                cityRegistry.getZones()) {
            revenue += zone.collectTax(cityStatistics.getBudget().getTaxRate());
        }
        for (Education education: educations) {
            revenue += education.getAdditionalValue();
        }
        return revenue;
    }

    private void updateForests() {
        List<Forest> newYouth = new ArrayList<>();
        for (Forest forest : youthForest) {
            forest.incAge(dateOfWorld);
            if (forest.getAge() > 10) {
                cityStatistics.getBudget().addMaintenanceFee(-forest.getMaintenanceFee());
            } else {
                newYouth.add(forest);
            }
            effectExists(forest);
        }
        youthForest = newYouth;
    }

    /**
     * Filter out the already finished constructions and update their level.
     */
    private void filterConstructed() {
        List<Zone> newUnderConstructions = new ArrayList<>();
        for (Zone zone : underConstructions) {
            if (zone.getBirthday().dateDifference(dateOfWorld).get("days") > Constants.CONSTRUCTION_DAY) {
                zone.setLevel(Level.ONE);
            } else {
                newUnderConstructions.add(zone);
            }
        }
        underConstructions = newUnderConstructions;
    }
}