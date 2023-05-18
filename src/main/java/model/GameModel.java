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

import static model.common.Constants.INITIAL_CITY_BALANCE;
import static model.util.BuildableType.*;

public class GameModel implements java.io.Serializable {
    private final int rows, cols;
    private final Buildable[][] map;
    private final CityRegistry cityRegistry;
    private final CityStatistics cityStatistics;
    private final Date dateOfWorld;
    private final List<Road> masterRoads;
    private List<Zone> underConstructions;
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
        underConstructions = new ArrayList<>();
        youthForest = new ArrayList<>();
        socialSecurity = new SocialSecurity(cityRegistry);
    }

    /**
     * Initializes the settings inside game model.
     */
    public void initialize() {
        for (int i = 0; i < cols; i++) {
            Road road = new Road(0, 0, new Coordinate(rows - 1, i), new Dimension(1, 1));
            masterRoads.add(road);
            addToMap(road);
        }
    }

    public Buildable[][] getMap() {
        return map;
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
        buildableList.addAll(underConstructions);
        buildableList.addAll(masterRoads);
        return buildableList;
    }

    public List<Road> getMasterRoads() {
        return masterRoads;
    }

    public List<Buildable> getZoneBuildable() {
        List<Buildable> buildableList = new ArrayList<>();
        buildableList.addAll(cityRegistry.getZones());
        buildableList.addAll(underConstructions);
        return buildableList;
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
            throw new OperationException("Add zone failed, no available plot.");
        }
        addToMap(zone);
        zone.setConnected(masterRoads.get(0), map);
        effectExists(zone);
        beEffectedByExisting(zone);
        cityStatistics.getBudget().deductBalance(zone.getConstructionCost());
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
                    getAllZones()) {
                bad.effect(z, this);
            }
        }
    }

    private List<Zone> getAllZones() {
        List<Zone> zl = new ArrayList<>(cityRegistry.getZones());
        zl.addAll(underConstructions);
        return zl;
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

        addToMap(facility);
        facility.setConnected(masterRoads.get(0), map);

        effectExists(facility);
        cityStatistics.getBudget().deductBalance(facility.getOneTimeCost());
        cityStatistics.getBudget().addMaintenanceFee(facility.getMaintenanceFee());
        cityRegistry.addFacility(facility);

        if (facility.getBuildableType() == BuildableType.FOREST) {
            youthForest.add((Forest) facility);
        }
        if (facility.getBuildableType() == ROAD) {
            for (Zone z: getAllZones()) {
                z.setConnected(masterRoads.get(0), map);
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
        } else if (isZone(bad) && !underConstructions.contains(bad)) {
            throw new OperationException("Removing fails, zone with assets cannot be removed.");
        } else if (masterRoads.contains(bad)) {
            throw new OperationException("Removing fails, master roads cannot be removed.");
        }
        removeFromMap(bad);

        if (SideEffect.class.isAssignableFrom(bad.getClass())) {
            SideEffect badBuildable = (SideEffect) bad;
            for (Zone z :
                    cityRegistry.getZones()) {
                badBuildable.reverseEffect(z, this);
            }
        }
        cityStatistics.getBudget().addBalance(bad.getConstructionCost() * Constants.RETURN_RATE);

        if (Zone.class.isAssignableFrom(bad.getClass())) {
            cityRegistry.removeZone((Zone) bad);
            underConstructions.remove((Zone) bad);
            updateIndustryCommercialBalanceSatisfactionIndex();
        } else {
            cityRegistry.removeFacility((Facility) bad);
            cityStatistics.getBudget().deductMaintenanceFee(((Facility) bad).getMaintenanceFee());
        }
        System.out.println("Remove Success");
    }

    public boolean roadIsEssentialForConnection(Road road) {
        removeFromMap(road);
        for (Buildable b :
                getAllBuildable()) {
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
        Zone z = (Zone) b;
        return z;
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
        cityStatistics.setZoneBalanceEffect(newVal);
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
        if (b == null) { return true; }
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
                    zone.getStatistics().getPopulation() < zone.getStatistics().getCapacity()) {
                return true;
            }
        }
        return false;
    }

    private void updateUnemployedStatusForCitizens() {
        for (Citizen citizen : cityRegistry.getAllCitizens()) {
            if (citizen.isUnemployed())
                citizen.setWorkplace(HumanManufacture.getWorkingPlace(this, citizen.getLivingplace()));
            citizen.setIsUnemployed(false);
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
        if (existFreeWorkingZones()) updateUnemployedStatusForCitizens();
        try {
            cityStatistics.setCitySatisfaction(this);
            if (existFreeResidentialZones()) {
                if (cityStatistics.getPopulation(cityRegistry) < HumanManufacture.startingNrCitizens) {
                    HumanManufacture.createYoungCitizen(this);
                } else {
                    Zone possibleLivingZone = HumanManufacture.getLivingPlace(this);
                    Zone possibleWorkingZone = HumanManufacture.getWorkingPlace(this, possibleLivingZone);
                    if (
                            possibleLivingZone != null && ProbabilitySelector.decision(
                                    cityStatistics.getCitySatisfaction() / 100)
                    ) {
                        HumanManufacture.createYoungCitizen(this, possibleWorkingZone, possibleLivingZone);
                    }
                }

            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

        if (lastTaxDate.dateDifference(dateOfWorld).get("years") >= 1) {
            socialSecurity.census(this);
            updateCityBalance();
            lastTaxDate = getCurrentDate();
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
        cityRegistry.updateBalance(revenue - spend);
        socialSecurity.appendTaxRecord();
    }

    /**
     * Getting the total expenses of the city.
     *
     * @return
     */
    public int calculateSpend() {
        int spend = 0;
        spend += cityStatistics.getBudget().getTotalMaintenanceFee();
        spend += socialSecurity.payPension();
        return spend;
    }

    /**
     * Getting the revenue of the city.
     *
     * @return
     */
    public int calculateRevenue() {
        int revenue = 0;
        for (Citizen c : cityRegistry.getAllCitizens()) {
            revenue += c.payTax(cityStatistics.getBudget().getTaxRate());
        }
        return revenue;
    }

    private void updateForests() {
        List<Forest> newYouth = new ArrayList<>();
        for (Forest forest : youthForest) {
            forest.incAge(getCurrentDate());
            if (forest.getAge() > 10) {
                cityStatistics.getBudget().addMaintenanceFee((-1) * forest.getMaintenanceFee());
                System.out.println(cityStatistics.getBudget().getTotalMaintenanceFee());
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
            if (zone.getBirthday().dateDifference(getCurrentDate()).get("days") > Constants.DAYS_FOR_CONSTRUCTION) {
                zone.setLevel(Level.ONE);
                zone.setUnderConstruction(false);
                cityRegistry.addZone(zone);
            } else {
                newUnderConstructions.add(zone);
            }
        }
        underConstructions = newUnderConstructions;
    }
}
