package model;

import model.util.BuildableType;
import model.util.Coordinate;
import model.util.Dimension;

public interface Buildable {
    public Coordinate getCoordinate();

    public Dimension getDimension();

    public BuildableType getBuildableType();
}
