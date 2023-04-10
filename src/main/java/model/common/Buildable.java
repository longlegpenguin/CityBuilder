package model.common;

import model.util.BuildableType;
import model.common.Coordinate;
import model.common.Dimension;

public interface Buildable {
    /**
     * @return the coordinate of the buildable
     */
    public Coordinate getCoordinate();

    /**
     * @return the dimension of the buildable
     */
    public Dimension getDimension();

    /**
     * A type can be:
     * @return the type of the buildable
     */
    public BuildableType getBuildableType();

    /**
     * Every buildable cost some when built.
     * @return the costs.
     */
    public int getConstructionCost();
}
