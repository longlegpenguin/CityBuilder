package model;

import model.util.BuildableType;
import model.util.Coordinate;
import model.util.Dimension;

public abstract class Facility implements Buildable, SideEffect {
    private int oneTimeCost;
    private int maintenanceFee;
    private float influenceRadius;
    private Coordinate coordinate;
    private Dimension dimension;

    public Facility(int oneTimeCost, int maintenanceFee, float influenceRadius, Coordinate coordinate, Dimension dimension) {
        this.oneTimeCost = oneTimeCost;
        this.maintenanceFee = maintenanceFee;
        this.influenceRadius = influenceRadius;
        this.coordinate = coordinate;
        this.dimension = dimension;
    }

    public int getOneTimeCost() {
        return oneTimeCost;
    }

    public void setOneTimeCost(int oneTimeCost) {
        this.oneTimeCost = oneTimeCost;
    }

    public int getMaintenanceFee() {
        return maintenanceFee;
    }

    public void setMaintenanceFee(int maintenanceFee) {
        this.maintenanceFee = maintenanceFee;
    }

    public float getInfluenceRadius() {
        return influenceRadius;
    }

    public void setInfluenceRadius(float influenceRadius) {
        this.influenceRadius = influenceRadius;
    }

    @Override
    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public Dimension getDimension() {
        return dimension;
    }

    /**
     * Applies special effect of the facility on a given zone.
     * if condition is satisfied.
     * @param zone the zone to effect.
     */
    public abstract void effect(Zone zone);

    /**
     * Evaluates the condition for the facility to have effect on a given zone
     * @param zone the zone to check
     * @return  true if condition is satisfied, otherwise, false
     */
    public abstract boolean condition(Zone zone);

    @Override
    public String toString() {
        return "Facility{" +
                "oneTimeCost=" + oneTimeCost +
                ", maintenanceFee=" + maintenanceFee +
                ", influenceRadius=" + influenceRadius +
                ", coordinate=" + coordinate.toString() +
                ", dimension=" + dimension.toString() +
                ", type=" + getBuildableType() +
                '}';
    }
}
