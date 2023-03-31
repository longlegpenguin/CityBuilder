package model;

import model.util.*;

import java.util.ArrayList;
import java.util.List;

public abstract class Zone implements Buildable {
    private Level level;
    private int dayToBuild;
    private Statistics statistics;
    private List<Citizen> citizens;
    private Date birthday;
    private Coordinate coordinate;
    private final Dimension dimension;

    public Zone(Level level, int dayToBuild, Statistics statistics, Date birthday) {
        this.level = level;
        this.dayToBuild = dayToBuild;
        this.statistics = statistics;
        this.birthday = birthday;
        citizens = new ArrayList<>();
        this.dimension = new Dimension(1, 1);
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

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    /**
     * Adds new citizen to the zone.
     *
     * @param citizen the citizen to be added.
     */
    public void addCitizen(Citizen citizen) {
        citizens.add(citizen);
    }

    /**
     * Gets the satisfaction of the zone
     *
     * @return the satisfaction of the zone
     */
    public Satisfaction getSatisfaction() {
//        return statistics.getSatisfaction();
        return new Satisfaction();
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

    @Override
    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public Dimension getDimension() {
        return dimension;
    }

    private int getPopulation() {
        return citizens.size();
    }
}
