package model.facility;

import model.Buildable;
import model.util.Coordinate;
import model.util.Dimension;

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

    public void setOneTimeCost(int oneTimeCost) {
        this.oneTimeCost = oneTimeCost;
    }

    public int getMaintenanceFee() {
        return maintenanceFee;
    }

    public void setMaintenanceFee(int maintenanceFee) {
        this.maintenanceFee = maintenanceFee;
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
        return "Facility{" +
                "oneTimeCost=" + oneTimeCost +
                ", maintenanceFee=" + maintenanceFee +
                ", coordinate=" + coordinate.toString() +
                ", dimension=" + dimension.toString() +
                ", type=" + getBuildableType() +
                '}';
    }
}
