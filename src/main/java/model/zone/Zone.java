package model.zone;

import model.GameModel;
import model.common.*;
import model.util.BuildableType;
import model.util.Date;
import model.util.Level;
import model.util.PathFinder;

import java.util.ArrayList;
import java.util.List;

public abstract class Zone implements Buildable, java.io.Serializable {
    protected Level level;
    protected int dayToBuild;
    protected ZoneStatistics statistics;
    protected final Date birthday;
    protected Coordinate coordinate;
    protected ArrayList<Citizen> citizens;
    protected final Dimension dimension;
    protected BuildableType buildableType;
    protected Boolean isUnderConstruction;
    protected float effectRadius;
    Boolean isConnected;


    public Zone(Level level, int dayToBuild, ZoneStatistics statistics, Date birthday, Coordinate coordinate, float effectRadius) {
        this.level = level;
        this.dayToBuild = dayToBuild;
        this.statistics = statistics;
        this.birthday = birthday;
        this.coordinate = coordinate;
        citizens = new ArrayList<>();
        this.dimension = new Dimension(1, 1);
        isUnderConstruction = true;
        this.effectRadius = effectRadius;
        this.isConnected = false;
    }

    public int getCapacity() {
        return level.getCapacity();
    }

    @Override
    public boolean isConnected() {
        return isConnected;
    }

    public int getOneTimeCost() {
        return Constants.ZONE_ONE_TIME_COST;
    }

    /**
     * Resets the connected property with the current possibility of connection
     *
     * @param connectingPoint master road
     * @param map             map of city
     */
    public void resetConnected(Buildable connectingPoint, Buildable[][] map) {
        isConnected = new PathFinder(map).manhattanDistance(this, connectingPoint) > -1;
    }

    public Level getLevel() {
        return level;
    }

    public ZoneStatistics getStatistics() {
        return statistics;
    }

    public Date getBirthday() {
        return birthday;
    }

    @Override
    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public Dimension getDimension() {
        return dimension;
    }

    public List<Citizen> getCitizens() {
        return citizens;
    }
    
    @Override
    public BuildableType getBuildableType() {
        return buildableType;
    }

    public void setUnderConstruction(Boolean underConstruction) {
        isUnderConstruction = underConstruction;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public double getFreeWorkSpaceEffect() {
        return getStatistics().getSatisfaction().getFreeWorkplaceEffect();
    }

    public double getIndustrialEffect() {
        return getStatistics().getSatisfaction().getIndustrialEffect();
    }

    /**
     * If new is positive, use new value as effect if is greater than the old one.
     * If is negative, set to zero if abs is the same.
     *
     * @param newValue double new value
     */
    public void updateForestEffect(double newValue) {
        statistics.getSatisfaction().setForestEffect(newValue);
    }

    public void updatePoliceEffect(int newValue) {
        statistics.getSatisfaction().setPoliceEffect(newValue);
    }

    public void updateIndustrialEffect(int newValue) {
        statistics.getSatisfaction().setIndustrialEffect(newValue);
    }

    public void updateStadiumEffect(int newValue) {
        statistics.getSatisfaction().setStadiumEffect(newValue);
    }

    /**
     * Adds new citizen to the zone.
     * Increments the population and updates the citizen avg satisfaction with the new citizen.
     *
     * @param citizen the citizen to be added.
     */
    public void addCitizen(Citizen citizen, GameModel gm) {
        citizens.add(citizen);
        this.statistics.setPopulation(statistics.getPopulation() + 1);
        gm.getCityStatistics().setCitySatisfaction(gm);
    }

    /**
     * Removes new citizen to the zone.
     * Increments the population and updates the citizen avg satisfaction with the new citizen.
     *
     * @param citizen the citizen to be added.
     */
    public void removeCitizen(Citizen citizen, GameModel gm) {
        citizens.remove(citizen);
        this.statistics.setPopulation(statistics.getPopulation() - 1);
        gm.getCityStatistics().setCitySatisfaction(gm);
    }

    /**
     * Gets the satisfaction of the zone
     *
     * @return the satisfaction of the zone
     */
    public double getZoneSatisfaction(GameModel gm) {

        return statistics.getSatisfaction().getTotalZoneSatisfaction(gm) +
                getPoliceEffect() * getPopulation() / 10 +
                60.0;
    }

    public double getPoliceEffect() {
        return statistics.getSatisfaction().getPoliceEffect();
    }

    /**
     * Gets the satisfaction of the zone
     *
     * @return the satisfaction of the zone
     */
    public double getZonRelatedSatisfaction() {
        return statistics.getSatisfaction().getZoneRelatedSatisfaction();
    }

    @Override
    public boolean isUnderConstruction() {
        return isUnderConstruction;
    }

    public int getPopulation() {
        return citizens.size();
    }

    @Override
    public String toString() {
        return "Zone{" +
                "level=" + level +
                ", isConnected=" + isConnected +
                ", dayToBuild=" + dayToBuild +
                ", statistics=" + statistics +
                ", citizens=" + getPopulation() +
                ", birthday=" + birthday.toString() +
                ", coordinate=" + coordinate.toString() +
                ", dimension=" + dimension +
                ", type=" + getBuildableType() +
                '}';
    }

}
