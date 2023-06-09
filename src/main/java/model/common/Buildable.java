package model.common;

import model.util.BuildableType;

public interface Buildable {
    /**
     * @return the coordinate of the buildable
     */
    Coordinate getCoordinate();

    /**
     * @return the dimension of the buildable
     */
    Dimension getDimension();

    /**
     * A type can be:
     *
     * @return the type of the buildable
     */
    BuildableType getBuildableType();

    /**
     * Every buildable cost some when built.
     *
     * @return the costs.
     */
    int getOneTimeCost();

    /**
     * Checks if the buildable is under construction
     *
     * @return boolean
     */
    boolean isUnderConstruction();

    /**
     * if buildable is connected to master road
     * @return true id connected
     */
    boolean isConnected();
}
