package model;

import model.util.BuildableType;
import model.util.Coordinate;
import model.util.Dimension;

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
}