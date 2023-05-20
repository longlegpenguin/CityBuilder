package model.facility;

import model.common.Buildable;
import model.common.Coordinate;
import model.common.Dimension;
import model.util.PathFinder;

public abstract class Facility implements Buildable , java.io.Serializable {
    protected int oneTimeCost;
    protected int maintenanceFee;
    protected Coordinate coordinate;
    protected Dimension dimension;
    Boolean isConnected;

    public Facility(int oneTimeCost, int maintenanceFee, Coordinate coordinate, Dimension dimension) {
        this.oneTimeCost = oneTimeCost;
        this.maintenanceFee = maintenanceFee;
        this.coordinate = coordinate;
        this.dimension = dimension;
        this.isConnected = false;
    }

    @Override
    public boolean isConnected() {
        return isConnected;
    }

    /**
     * Resets the connected property with the current possibility of connection
     * @param connectingPoint master road
     * @param map map of city
     */
    public void resetConnected(Buildable connectingPoint, Buildable map[][]) {
        isConnected = new PathFinder(map).manhattanDistance(this, connectingPoint) > -1;
    }

    public void setConnected(Boolean connected) {
        isConnected = connected;
    }
    public void setConnected(Buildable connectingPoint, Buildable map[][]) {
        isConnected = new PathFinder(map).manhattanDistance(this, connectingPoint) > -1;
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
