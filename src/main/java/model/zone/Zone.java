package model.zone;

import model.common.Buildable;
import model.common.Citizen;
import model.util.BuildableType;
import model.util.Date;
import model.common.Constants;
import model.common.Coordinate;
import model.common.Dimension;
import model.util.Level;

import java.util.ArrayList;
import java.util.List;

public abstract class Zone implements Buildable {
    protected Level level;
    protected int dayToBuild;
    protected ZoneStatistics statistics;
    protected List<Citizen> citizens;
    protected Date birthday;
    protected Coordinate coordinate;
    protected final Dimension dimension;
    protected BuildableType buildableType;


    public Zone(Level level, int dayToBuild, ZoneStatistics statistics, Date birthday, Coordinate coordinate) {
        this.level = level;
        this.dayToBuild = dayToBuild;
        this.statistics = statistics;
        this.birthday = birthday;
        this.coordinate = coordinate;
        citizens = new ArrayList<>();
        this.dimension = new Dimension(1, 1);
        this.buildableType = BuildableType.UNDER_CONSTRUCTION;
    }

    @Override
    public BuildableType getBuildableType() {
        return buildableType;
    }

    public void setBuildableType(BuildableType buildableType) {
        this.buildableType = buildableType;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int getDayToBuild() {
        return dayToBuild;
    }

    public void setDayToBuild(int dayToBuild) {
        this.dayToBuild = dayToBuild;
    }

    public ZoneStatistics getStatistics() {
        return statistics;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setStatistics(ZoneStatistics statistics) {
        this.statistics = statistics;
    }

    public void updateBudgetEffect(int newValue) {
        statistics.getSatisfaction().setBudgetEffect(newValue);
    }
    public void updateTaxEffect(int newValue) {
        statistics.getSatisfaction().setTaxEffect(newValue);
    }
    public void updateComZoneBalanceEffect(double newValue) {
        statistics.getSatisfaction().setZoneBalanceEffect(newValue);
    }
    public void updatePoliceEffect(int newValue) {
        statistics.getSatisfaction().setPoliceEffect(newValue);
    }
    public void updateIndustrialEffect(int newValue) {
        statistics.getSatisfaction().setIndustrialEffect(newValue);
    }
    public void updateFreeWorkSpaceEffect(int newValue) {
        statistics.getSatisfaction().setFreeWorkplaceEffect(newValue);
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
    public void addCitizen(Citizen citizen) {
        citizens.add(citizen);
        this.statistics.setPopulation(statistics.getPopulation() + 1);
        updateCitizenAvgSatisfaction();
    }

    public void unregisterCitizen(Citizen citizen) {
        citizens.remove(citizen);
    }

    /**
     * Gets the satisfaction of the zone
     *
     * @return the satisfaction of the zone
     */
    public double getSatisfaction() {
        return statistics.getSatisfaction().getTotalSatisfaction();
    }

    /**
     * Collects the base tax from each citizen
     *
     * @param taxRate the tax rate of the city.
     * @return the sum of tax paid by citizens located in the zone
     */
    public int collectTax(int taxRate) {
        return taxRate * Constants.BASE_TAX * getPopulation();
    }

    /**
     * Levels up the zone by one level.
     * Levels can be constructing, 1, 2, 3
     */
    public void LevelUp() {
        if (level != Level.THREE) {
            level = Level.values()[level.ordinal() + 1];
            statistics.setCapacity(level.getCapacity());
        }
    }

    /**
     * Gets the cost for assigning/upgrading the zone
     *
     * @return the cost
     */
    public int getConstructionCost() {
        return level.getCost();
    }
    public int getPopulation() {
        return citizens.size();
    }

    @Override
    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public Dimension getDimension() {
        return dimension;
    }


    @Override
    public String toString() {
        return "Zone{" +
                "level=" + level +
                ", dayToBuild=" + dayToBuild +
                ", statistics=" + statistics +
                ", citizens=" + citizens.size() +
                ", birthday=" + birthday.toString() +
                ", coordinate=" + coordinate.toString() +
                ", dimension=" + dimension +
                ", type=" + getBuildableType() +
                '}';
    }

    private void updateCitizenAvgSatisfaction() {
        int sum = 0;
        for (Citizen citizen :
                citizens) {
            sum += citizen.getSatisfaction();
        }
        statistics.getSatisfaction().setCitizenAvgEffect(sum / getPopulation());
    }
}
