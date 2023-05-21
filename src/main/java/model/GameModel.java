package model;

import controller.ICallBack;
import model.city.CityRegistry;
import model.city.CityStatistics;
import model.city.SocialSecurity;
import model.common.*;
import model.exceptions.OperationException;
import model.facility.Facility;
import model.facility.Forest;
import model.facility.Road;
import model.util.*;
import model.zone.Zone;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static model.common.Constants.*;
import static model.util.BuildableType.*;

public class GameModel implements java.io.Serializable {
    private final int rows, cols;
    private final Buildable[][] map;
    private final CityRegistry cityRegistry;
    private final CityStatistics cityStatistics;
    private final Date dateOfWorld;
    private final List<Road> masterRoads;
    private List<Forest> youthForest;
    private Date lastTaxDate;
    private final SocialSecurity socialSecurity;

    public GameModel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        map = new Buildable[rows][cols];
        cityStatistics = new CityStatistics(new Budget(INITIAL_CITY_BALANCE, 0.3));
        cityRegistry = new CityRegistry(cityStatistics);
        dateOfWorld = new Date(1, Month.JANUARY, 2020);
        lastTaxDate = new Date(1, Month.JANUARY, 2020);
        masterRoads = new ArrayList<>();
        youthForest = new ArrayList<>();
        socialSecurity = new SocialSecurity(cityRegistry);
    }

    /**
     * Initializes the settings inside game model.
     */
    public void initialize() {
        for (int i = 0; i < cols; i++) {
            Road road = new Road(ROAD_ONE_TIME_COST, ROAD_MAINTENANCE_FEE, new Coordinate(rows - 1, i), new Dimension(1, 1));
            masterRoads.add(road);
            addToMap(road);
        }
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            Forest forest = new Forest(0, 0,
                    new Coordinate(random.nextInt(rows - 1), random.nextInt(cols - 1)),
                    new Dimension(1, 1),
                    FOREST_EFFECT_RADIUS, getCurrentDate());
            forest.setAgeToTen();
            forest.setTotalEffectCntToTenYears();
            try {
                addFacility(forest);
            } catch (OperationException ignored) {
            }
            addToMap(forest);
        }
        cityRegistry.getCityStatistics().setCitySatisfaction(this);
    }

    public Buildable[][] getMap() {
        return map;
    }

    /**
     * Gets everything on the map.
     *
     * @return list of buildable.
     */
    public List<Buildable> getAllBuildable() {
        List<Buildable> buildableList = new ArrayList<>();
        buildableList.addAll(cityRegistry.getZones());
        buildableList.addAll(cityRegistry.getFacilities());
        buildableList.addAll(masterRoads);
        return buildableList;
    }

    public List<Road> getMasterRoads() {
        return masterRoads;
    }

    public List<Buildable> getZoneBuildable() {
        return new ArrayList<>(cityRegistry.getZones());
    }

    public CityRegistry getCityRegistry() {
        return cityRegistry;
    }

    public List<Buildable> getFacilityBuildable() {
        List<Buildable> buildableList = new ArrayList<>();
        buildableList.addAll(cityRegistry.getFacilities());
        buildableList.addAll(masterRoads);
        return buildableList;
    }

    /**
     * Adds the zone to the city.
     * Updates its effect on satisfaction of other zones as well.
     *
     * @param zone the zone reference to be added
     */
    public void addZone(Zone zone) throws OperationException {

        if (!isPlotAvailable(zone)) {
            throw new OperationException("Add zone failed, no available plot.");
        }
        checkTemporaryDirectView(zone);
        addToMap(zone);
        zone.resetConnected(masterRoads.get(0), map);
        effectExists(zone);
        beEffectedByExisting(zone);
        cityRegistry.addZone(zone);
        cityRegistry.updateBalance(-zone.getOneTimeCost(), getCurrentDate());
    }

    /**
     * If the new buildable blocks the forest view, reverses the effect of the blocked.
     *
     * @param buildable the new buildable to check.
     */
    public void checkTemporaryDirectView(Buildable buildable) {
        for (Buildable b : getFacilityBuildable()) {
            if (b.getBuildableType() == FOREST) {
                Forest f = (Forest) b;
                for (Zone z : getAllZones()) {
                    boolean cond = f.condition(z, this);
                    addToMap(buildable);
                    boolean cond2 = f.condition(z, this);
                    removeFromMap(buildable);
                    if (cond2 != cond) {
                        f.reverseEffect(z, this);
                    }
                }
            }
        }
    }

    /**
     * Applies effects on all existing zones if it has side effect
     *
     * @param buildable the effective buildable
     */
    private void effectExists(Buildable buildable) {
        if (hasSideEffect(buildable)) {
            SideEffect sideEffect = (SideEffect) buildable;
            for (Zone z : getAllZones()) {
                sideEffect.effect(z, this);
            }
        }
    }

    private void forestEffect(Forest forest) {
        for (Zone z : getAllZones()) {
            forest.grewEffect(z, this);
        }
    }

    private List<Zone> getAllZones() {
        return new ArrayList<>(cityRegistry.getZones());
    }

    /**
     * Applies effects by all existing buildable if there is effect.
     *
     * @param zone the zone to be effected.
     */
    private void beEffectedByExisting(Zone zone) {
        for (Buildable existingBad :
                getAllBuildable()) {
            if (hasSideEffect(existingBad)) {
                ((SideEffect) existingBad).effect(zone, this);
            }
        }
    }

    private boolean hasSideEffect(Buildable buildable) {
        return SideEffect.class.isAssignableFrom(buildable.getClass());
    }

    /**
     * Adds the facility to the city.
     *
     * @param facility the facility reference to be added
     */
    public void addFacility(Facility facility) throws OperationException {

        if (!isPlotAvailable(facility)) {
            throw new OperationException("Add facility failed, no available slot");
        }
        checkTemporaryDirectView(facility);
        addToMap(facility);
        facility.setConnected(masterRoads.get(0), map);

        effectExists(facility);
        cityRegistry.updateBalance(-facility.getOneTimeCost(), getCurrentDate());
        cityStatistics.getBudget().addMaintenanceFee(facility.getMaintenanceFee());
        cityRegistry.addFacility(facility);

        if (facility.getBuildableType() == BuildableType.FOREST) {
            Forest forest = (Forest) facility;
            if (forest.getAge() < 10) {
                youthForest.add(forest);
            }
        }
        if (facility.getBuildableType() == ROAD) {
            recheckConnections();
        }
    }

    /**
     * Rechecks the connections and apply effect with new connections.
     */
    private void recheckConnections() {
        for (Zone z : getAllZones()) {
            if (!z.isConnected()) {
                z.resetConnected(masterRoads.get(0), map);
                beEffectedByExisting(z);
            }
        }
        for (Buildable buildable : getFacilityBuildable()) {
            Facility f = (Facility) buildable;
            if (!f.isConnected() && f.getBuildableType() != FOREST) {
                f.resetConnected(masterRoads.get(0), map);
                if (hasSideEffect(f)) {
                    effectExists(f);
                }
            }
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
            throw new OperationException("Removing fails, plot is empty.");
        } else if (bad.getBuildableType() == ROAD && roadIsEssentialForConnection((Road) bad)) {
            throw new OperationException("Removing fails, the selected road will break connections on remove.");
        } else if (isZone(bad) && !bad.isUnderConstruction()) {
            throw new OperationException("Removing fails, zone with assets cannot be removed.");
        } else if (bad.getBuildableType() == ROAD && masterRoads.contains((Road) bad)) {
            throw new OperationException("Removing fails, master roads cannot be removed.");
        } else if (bad.getBuildableType() == FOREST) {
            youthForest.remove((Forest) bad);
        }
        removeSideEffects(bad);
        removeFromMap(bad);
        cityStatistics.getBudget().addBalance(bad.getOneTimeCost() * Constants.RETURN_RATE, getCurrentDate());
        removeFromCity(bad);
        System.out.println("Remove Success");
    }

    /**
     * Removes the side effects on satisfaction if any.
     *
     * @param bad the possible buildable with side effect.
     */
    private void removeSideEffects(Buildable bad) {
        if (hasSideEffect(bad)) {
            SideEffect badBuildable = (SideEffect) bad;
            for (Zone z : cityRegistry.getZones()) {
                badBuildable.reverseEffect(z, this);
            }
        }
    }

    /**
     * Removes the buildable from the city registry and temporary lists.
     *
     * @param bad the building to be removed
     */
    private void removeFromCity(Buildable bad) {
        if (isZone(bad)) {
            cityRegistry.removeZone((Zone) bad);
        } else {
            cityRegistry.removeFacility((Facility) bad);
            cityStatistics.getBudget().deductMaintenanceFee(((Facility) bad).getMaintenanceFee());
        }
    }

    /**
     * Checks if the removal of road will result in lost of existing connection
     *
     * @param road th road to be checked
     * @return true if it will cause lost, otherwise false.
     */
    public boolean roadIsEssentialForConnection(Road road) {
        removeFromMap(road);
        for (Buildable b : getAllBuildable()) {
            if (b.isConnected() && new PathFinder(map).manhattanDistance(masterRoads.get(0), b) == -1) {
                addToMap(road);
                return true;
            }
        }
        addToMap(road);
        return false;
    }

    /**
     * Updates city's tax rate
     *
     * @param newTaxRate the new tax rate
     */
    public void updateTaxRate(double newTaxRate) throws OperationException {
        if (newTaxRate < 0 || newTaxRate > 1) {
            throw new OperationException("Tax rate must between 0..1");
        }
        cityRegistry.updateTaxRate(newTaxRate);
    }

    /**
     * Gets the statistics of the whole city
     *
     * @return statistics
     */
    public CityStatistics getCityStatistics() {
        return cityStatistics;
    }

    /**
     * Gets the statistics of a specified zone
     *
     * @param coordinate the coordinate of the zone for which statistics should be got
     * @return Zone
     * @throws OperationException when no zone is on the coordinate.
     */
    public Zone queryZoneStatistics(Coordinate coordinate) throws OperationException {
        Buildable b = map[coordinate.getRow()][coordinate.getCol()];
        if (isPlotAvailable(b) || !isZone(b)) {
            throw new OperationException("No zone on the selected field");
        }
        return (Zone) b;
    }

    private boolean isZone(Buildable buildable) {
        BuildableType buildableType = buildable.getBuildableType();
        return buildableType == COMMERCIAL ||
                buildableType == INDUSTRIAL ||
                buildableType == RESIDENTIAL;
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
        return new Date(dateOfWorld.getDay(), dateOfWorld.getMonth(), dateOfWorld.getYear());
    }

    /**
     * Adds the buildable to the world map, reverse operation of removeFromMap
     *
     * @param buildable buildable to be added.
     */
    public void addToMap(Buildable buildable) {
        Coordinate coordinate = buildable.getCoordinate();
        Dimension dimension = buildable.getDimension();

        for (int i = 0; i < dimension.getHeight(); i++) {
            for (int j = 0; j < dimension.getWidth(); j++) {
                map[coordinate.getRow() + i][coordinate.getCol() + j] = buildable;
            }
        }
    }

    /**
     * Removes the buildable to the world map, reverse operation of addToMap
     *
     * @param buildable buildable to be added.
     */
    private void removeFromMap(Buildable buildable) {
        Coordinate coordinate = buildable.getCoordinate();
        Dimension dimension = buildable.getDimension();

        for (int i = 0; i < dimension.getHeight(); i++) {
            for (int j = 0; j < dimension.getWidth(); j++) {
                map[coordinate.getRow() + i][coordinate.getCol() + j] = null;
            }
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
                } else {
                    BuildableType bt = map[i][j].getBuildableType();
                    if (bt == BuildableType.ROAD) {
                        sb.append("#");
                    } else if (bt == FOREST) {
                        sb.append("F");
                    } else if (bt == STADIUM) {
                        sb.append("S");
                    } else {
                        sb.append("$");
                    }
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Checks if there is space for a certain buildable
     *
     * @param b the buildable to be checked
     * @return true if there is space, otherwise, false.
     */
    public boolean isPlotAvailable(Buildable b) {
        if (b == null) {
            return true;
        }
        Coordinate coordinate = b.getCoordinate();
        Dimension dimension = b.getDimension();
        int topRow = coordinate.getRow();
        int leftestCol = coordinate.getCol();
        int rightestCol = leftestCol + dimension.getWidth();
        int bottomRow = topRow + dimension.getHeight();

        if (bottomRow > rows || rightestCol > cols) {
            return false;
        }

        for (int row = topRow; row < bottomRow; row++) {
            for (int col = leftestCol; col < rightestCol; col++) {
                if (map[row][col] != null) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean existFreeResidentialZones() {
        for (Buildable buildable : getZoneBuildable()) {
            Zone zone = (Zone) buildable;
            if (zone.getBuildableType() == BuildableType.RESIDENTIAL &&
                    zone.getStatistics().getPopulation() < zone.getLevel().getCapacity() &&
                    zone.isConnected()) {
                return true;
            }
        }
        return false;
    }

    private boolean existFreeWorkingZones() {
        for (Buildable buildable : getZoneBuildable()) {
            Zone zone = (Zone) buildable;
            if ((zone.getBuildableType() == BuildableType.INDUSTRIAL || zone.getBuildableType() == BuildableType.COMMERCIAL) &&
                    zone.getStatistics().getPopulation() < zone.getCapacity()) {
                return true;
            }
        }
        return false;
    }

    private void updateUnemployedStatusForCitizens() {
        for (Citizen citizen : cityRegistry.getAllCitizens()) {
            if (citizen.isUnemployed()) {
                citizen.setWorkplace(this, HumanManufacture.getWorkingPlace(this, citizen.getLivingPlace()));
            }
        }
    }

    /**
     * Regular updating of the world.
     *
     * @param dayPass  the day passed since last updates.
     * @param callBack a call back function, called after the updating,
     *                 to synchronize the change to the view.
     */
    public void regularUpdate(int dayPass, ICallBack callBack) {
        dateOfWorld.addDay(dayPass);
        filterConstructed();
        citizenshipManipulation();
        citizenshipEducationUpdate();
        cityAging();
        if (callBack == null) {
            return;
        }
        callBack.shoutLose(cityRegistry.getCityStatistics().getCitySatisfaction() < GAME_LOST_SATISFACTION);
    }

    /**
     * Updates citizen ages and budgets once a year.
     */
    private void cityAging() {
        if (lastTaxDate.dateDifference(dateOfWorld).get("years") >= 1) {
            socialSecurity.census(this);
            updateCityBalance();
            lastTaxDate = getCurrentDate();
            updateForests();
        }
    }

    /**
     * Distributes new citizens and new jobs.
     */
    private void citizenshipManipulation() {
        if (existFreeWorkingZones()) updateUnemployedStatusForCitizens();
        cityStatistics.setCitySatisfaction(this);
        if (existFreeResidentialZones()) {
            if (cityStatistics.getPopulation(cityRegistry) < HumanManufacture.startingNrCitizens) {
                HumanManufacture.createYoungCitizen(this);
            } else {
                Zone possibleLivingZone = HumanManufacture.getLivingPlace(this);
                Zone possibleWorkingZone = HumanManufacture.getWorkingPlace(this, possibleLivingZone);
                if (possibleLivingZone != null &&
                        ProbabilitySelector.decision(
                                (cityStatistics.getCitySatisfaction() +
                                        possibleLivingZone.getFreeWorkSpaceEffect() +
                                        possibleLivingZone.getIndustrialEffect())
                                        / 100)
                ) {
                    HumanManufacture.createYoungCitizen(this, possibleWorkingZone, possibleLivingZone);
                }
            }
        }
    }

    private void citizenshipEducationUpdate() {
        for (Citizen citizen : getCityRegistry().getAllCitizens()) {
            citizen.setLevelOfEducation(HumanManufacture.getEducationLevel(this, citizen.getLivingPlace()));
        }
    }

    /**
     * Collects tax and pays the maintenance fee as well as pension
     */
    private void updateCityBalance() {
        double revenue = calculateRevenue();
        double spend = calculateSpend();
        cityRegistry.updateBalance(revenue - spend, getCurrentDate());
    }

    /**
     * Getting the total expenses of the city.
     *
     * @return the total pension and maintenance
     */
    public double calculateSpend() {
        double spend = 0;
        spend += cityStatistics.getBudget().getTotalMaintenanceFee();
        spend += socialSecurity.payPension();
        return spend;
    }

    /**
     * Getting the revenue of the city.
     *
     * @return the amount of tax collected
     */
    public double calculateRevenue() {
        return socialSecurity.collectTax(queryCityBudget().getTaxRate());
    }

    /**
     * Filters the youthForest list and do the appending effects of youth forests.
     */
    private void updateForests() {
        List<Forest> newYouth = new ArrayList<>();
        for (Forest forest : youthForest) {
            forest.incAge(getCurrentDate());
            if (forest.getAge() > 10) {
                cityStatistics.getBudget().addMaintenanceFee((-1) * forest.getMaintenanceFee());
            } else {
                forestEffect(forest);
                newYouth.add(forest);
            }
        }
        youthForest = newYouth;
    }

    /**
     * Filter out the already finished constructions and update their level.
     */
    private void filterConstructed() {
        for (Zone zone : getUnderConstructions()) {
            if (zone.getBirthday().dateDifference(getCurrentDate()).get("days") > Constants.DAYS_FOR_CONSTRUCTION) {
                zone.setLevel(Level.ONE);
                zone.setUnderConstruction(false);
            }
        }
    }

    /**
     * Gets all zones which status is still under constructed.
     *
     * @return the list
     */
    public List<Zone> getUnderConstructions() {
        List<Zone> underConstructions = new ArrayList<>();
        for (Zone zone : getAllZones()) {
            if (zone.isUnderConstruction()) {
                underConstructions.add(zone);
            }
        }
        return underConstructions;
    }
}
