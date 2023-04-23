package model.facility;

import model.common.Coordinate;
import model.common.Dimension;
import model.util.BuildableType;

public class University extends Education {

    public University(int oneTimeCost, int maintenanceFee, Coordinate coordinate, Dimension dimension) {
        super(oneTimeCost, maintenanceFee, coordinate, dimension, 5000, 4);
    }

    @Override
    public BuildableType getBuildableType() {
        return BuildableType.UNIVERSITY;
    }

    @Override
    public boolean isUnderConstruction() { return false; }
}
