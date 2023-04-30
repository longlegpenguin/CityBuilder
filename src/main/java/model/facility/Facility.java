package model.facility;

import model.common.Buildable;
import model.common.Coordinate;
import model.common.Dimension;

public abstract class Facility implements Buildable {
    protected int oneTimeCost;
    protected int maintenanceFee;
    protected Coordinate coordinate;
    protected Dimension dimension;

    public Facility(int oneTimeCost, int maintenanceFee, Coordinate coordinate, Dimension dimension) {
        this.oneTimeCost = oneTimeCost;
        this.maintenanceFee = maintenanceFee;
        this.coordinate = coordinate;
        this.dimension = dimension;
    }

    public int getOneTimeCost() {
        return oneTimeCost;
    }

    public int getConstructionCost() {
        return getOneTimeCost();
    }

    public int getMaintenanceFee() {
        return maintenanceFee;
    }

    @Override
    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public Dimension getDimension() {
        return dimension;
    }

    public void setOneTimeCost(int oneTimeCost) {
        this.oneTimeCost = oneTimeCost;
    }

    public void setMaintenanceFee(int maintenanceFee) {
        this.maintenanceFee = maintenanceFee;
    }

    @Override
    public String toString() {
        return "Facility{" +
                "oneTimeCost=" + oneTimeCost +
                ", maintenanceFee=" + maintenanceFee +
                ", coordinate=" + coordinate.toString() +
                ", dimension=" + dimension.toString() +
                ", type=" + getBuildableType() +
                '}';
    }
}
